package com.num.database.datasource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.num.database.DatabaseOutput;
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
	public boolean inTransaction=false;

	public DataSource(Context context) {
		this.context = context;

	}
	
	public void delay() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		database =dbHelper.getWritableDatabase();
		
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
	}

	public ArrayList<String> getDistinctValues(String column) {
		System.out.println("fetch Distinct Values " + column);
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

	protected List<Map<String, String>> getDataStores(
			HashMap<String, String> filter) {
		System.out.println("fetch DataSource with filter");
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

		selection = selection.substring(0, selection.length() - 4);

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
				
		open();
		insertModel(model);
		close();		

	}

	public ArrayList<GraphPoint> getGraphData(HashMap<String, String> filter) {

		List<Map<String, String>> allData = getDataStores(filter);
		ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();

		for (Map<String, String> data : allData) {
			points.add(new GraphPoint(data.size(), extractValue(data),
					extractTime(data)));
		}

		return points;

	}

	public abstract int extractValue(Map<String, String> data);

	public abstract Date extractTime(Map<String, String> data);

}
