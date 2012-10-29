package com.num.database.datasource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.num.database.DatabaseOutput;
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.BaseMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.database.mapping.ThroughputMapping;
import com.num.models.GraphData;

import com.num.models.GraphPoint;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Model;
import com.num.models.Throughput;
import com.num.utils.DeviceUtil;

public abstract class DataSource {
	// Database fields
	public SQLiteDatabase database;
	public Context context;
	public BaseMapping dbHelper;
	public boolean inTransaction = false;
	private final boolean IS_PURGE_ALLOWED = true;
	public int currentMode = 0;

	public DataSource(Context context) {
		this.context = context;

	}

	public boolean isPurgeAllowed() {
		return IS_PURGE_ALLOWED;
	}

	public void delay() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO this right now

	public void waitForTransaction() {

		while (inTransaction) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void open() throws SQLException {
		waitForTransaction();
		inTransaction = true;
		database = dbHelper.getWritableDatabase();

	}

	public void close() {
		inTransaction = false;

		dbHelper.close();
	}

	public String getTime() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public abstract HashMap<String, ArrayList<GraphPoint>> getGraphData();

	public abstract DatabaseOutput getOutput();

	protected abstract void insertModel(Model model);

	public String[] getColumns() {
		return dbHelper.getDatabaseColumns().getColumnNames();
	}

	public void setDBHelper(BaseMapping helper) {
		dbHelper = helper;
		if (isPurgeAllowed()) {
			purgeOldData(5, 10);

			Random random = new Random();
			int lower = random.nextInt(10) * 5 + 10;
			int higher = lower + 5;

			purgeOldData(lower, higher);

		}
	}

	public ArrayList<String> getDistinctValues(String column) {
		open();
		String[] columns = new String[1];
		columns[0] = column;
		Cursor cursor = database.query(true, dbHelper.getTableName(), columns,
				null, null, null, null, null, null);
		ArrayList<String> ret = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			ret.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		close();
		System.out.println("fetch Distinct Values " + column + " DONE");
		return ret;
	}

	private void purgeOldData(int startdays, int enddays) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1 * enddays);
		Date endDelete = cal.getTime();
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1 * startdays);
		Date onDelete = cal.getTime();

		open();

		while (onDelete.after(endDelete)) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String s = sdf.format(onDelete);
			System.out.println("s is " + s);
			database.delete(dbHelper.getTableName(),
					ApplicationMapping.COLUMN_TIME + " LIKE '%" + s + "%'",
					null);
			System.out.println("delete happened : for real");
			cal.add(Calendar.DAY_OF_MONTH, -1);
			onDelete = cal.getTime();
		}

		close();

	}

	protected List<Map<String, String>> getDataStores(
			HashMap<String, String> filter) {
		open();

		List<Map<String, String>> dataStores = new ArrayList<Map<String, String>>();

		String selection = "";
		String[] arguments = new String[filter.size()];

		int count = 0;

		Iterator<String> iter = filter.keySet().iterator();
		while (iter.hasNext()) {

			String key = iter.next();
			String value = filter.get(key);

			selection += key + " = ? and ";
			arguments[count++] = value;
		}

		if (selection.length() > 5) {
			selection = selection.substring(0, selection.length() - 4);
		}

		Cursor cursor = database.query(dbHelper.getTableName(), getColumns(),
				selection, arguments, null, null, "_id");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			Map<String, String> dataStore = dbHelper.getDatabaseColumns()
					.getDataStore(cursor);
			dataStores.add(dataStore);

			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();

		close();

		return dataStores;

	}

	public List<Map<String, String>> getDataStores() {

		open();
		List<Map<String, String>> dataStores = new ArrayList<Map<String, String>>();

		Cursor cursor = database.query(dbHelper.getTableName(), getColumns(),
				null, null, null, null, "_id");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Map<String, String> dataStore = dbHelper.getDatabaseColumns()
					.getDataStore(cursor);
			dataStores.add(dataStore);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		close();

		return dataStores;
	}

	public void insert(Model model) {
		Log.w("db", "inserting");
		open();
		insertModel(model);
		close();

	}

	public ArrayList<GraphPoint> getGraphData(HashMap<String, String> filter) {

		if (getMode().equals("normal"))
			return getNormalGraphData(filter);
		else if (getMode().equals("aggregate"))
			return getAggregateGraphData(filter);
		else
			return null;

	}

	public ArrayList<GraphPoint> getNormalGraphData(
			HashMap<String, String> filter) {

		List<Map<String, String>> allData = getDataStores(filter);
		ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();

		for (Map<String, String> data : allData) {
			points.add(new GraphPoint(data.size(), extractValue(data),
					extractTime(data)));
		}

		return points;

	}

	public ArrayList<GraphPoint> getAggregateGraphData(
			HashMap<String, String> filter) {

		List<Map<String, String>> allData = getDataStores(filter);
		ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();

		Map<String, GraphPoint> pointmap = new HashMap<String, GraphPoint>();

		for (Map<String, String> data : allData) {

			GraphPoint newPoint = new GraphPoint(0, extractValue(data),
					extractTime(data));
			newPoint.setString(extractDate(data));
			newPoint.sortByDate(true);
			String date = extractDate(data);

			if (pointmap.containsKey(date)) {
				GraphPoint oldPoint = pointmap.get(date);
				aggregatePoints(oldPoint, newPoint);
			} else {
				pointmap.put(date, newPoint);
			}

		}

		Iterator<String> iter = pointmap.keySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			String date = iter.next();
			points.add(pointmap.get(date));

		}

		try {
			Collections.sort(points);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (GraphPoint point : points) {
			point.x = count++;
		}

		return points;

	}

	public abstract int extractValue(Map<String, String> data);

	public abstract Date extractTime(Map<String, String> data);

	public String extractDate(Map<String, String> data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = data.get(LatencyMapping.COLUMN_TIME);
		try {
			Date d = df.parse(dateString);
			return (d.getMonth() + 1) + "-" + d.getDate();
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date().toString();
		}
	}

	public Date extractDate(String dateString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public abstract void aggregatePoints(GraphPoint oldP, GraphPoint newP);

	public abstract String getGraphType();

	public abstract String getYAxisLabel();

	public abstract String[] getModes();

	public String getMode() {
		return getModes()[currentMode];
	}

	public void setMode(int mode) {
		currentMode = mode;
	}

}
