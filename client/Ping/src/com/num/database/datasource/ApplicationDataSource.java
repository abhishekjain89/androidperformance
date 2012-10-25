package com.num.database.datasource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.cookie.SetCookie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.num.database.DatabaseOutput;
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.BaseMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.database.mapping.ThroughputMapping;
import com.num.helpers.GAnalytics;
import com.num.models.GraphData;

import com.num.models.Application;
import com.num.models.GraphPoint;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measure;
import com.num.models.Model;
import com.num.models.Ping;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.utils.DeviceUtil;

public class ApplicationDataSource extends DataSource {

	private final String GRAPH_TYPE = "bar";
	private final String Y_AXIS_UNITS = "KB";
	private final String[] MODES = {"aggregate"};

	public ApplicationDataSource(Context context) {
		super(context);
		setDBHelper(new ApplicationMapping(context));
		
	}

	private void addRow(Application app) {
		ContentValues value = new ContentValues();

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, app.getName());
		value.put(ApplicationMapping.COLUMN_DATA, app.getTotal_sent());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "sent");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, app.getName());
		value.put(ApplicationMapping.COLUMN_DATA, app.getTotal_recv());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "received");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, app.getName());
		value.put(ApplicationMapping.COLUMN_DATA, app.getTotal());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "total");
		database.insert(dbHelper.getTableName(), null, value);
	}

	private void addRow(Usage usage) {
		ContentValues value = new ContentValues();

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getTotal_sent());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "sent");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getTotal_recv());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "received");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getTotal());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "total");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Mobile");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getMobile_sent());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "sent");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Mobile");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getMobile_recv());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "received");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Mobile");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getMobile());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "total");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Wifi");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getWifi_sent());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "sent");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Wifi");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getWifi_recv());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "received");
		database.insert(dbHelper.getTableName(), null, value);

		value.put(ApplicationMapping.COLUMN_TIME, getTime());
		value.put(ApplicationMapping.COLUMN_NAME, "All Wifi");
		value.put(ApplicationMapping.COLUMN_DATA, usage.getWifi());
		value.put(ApplicationMapping.COLUMN_DIRECTION, "total");
		database.insert(dbHelper.getTableName(), null, value);

	}

	protected void insertModel(Model model) {

		Usage usage = (Usage) model;

		for (Application app : usage.getApplications()) {
			try {

				addRow(app);

			} catch (Exception e) {
				GAnalytics.log(GAnalytics.DATABASE, "Insert Fail " + dbHelper.getDBName(),e.getMessage());
				e.printStackTrace();
			}

		}

		addRow(usage);

	}

	public DatabaseOutput getOutput() {
		open();
		close();
		return null;

	}

	public HashMap<String, ArrayList<GraphPoint>> getGraphData() {
		// return getGraphData(DeviceUtil.getNetworkInfo(context),
		// "Atlanta, GA");
		return null;
	}

	public ArrayList<GraphPoint> getAggregateGraphData(HashMap<String, String> filter) {

		List<Map<String, String>> allData = getDataStores(filter);
		ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();

		int oldValue = 0;
		boolean isFirst = true;

		Map<String, GraphPoint> pointmap = new HashMap<String, GraphPoint>();

		for (Map<String, String> data : allData) {

			int newValue = extractValue(data);
			int difference = 0;
			if (newValue < oldValue)
				difference = newValue;
			else
				difference = newValue - oldValue;
			if (isFirst) {
				isFirst = false;
			} else {
				GraphPoint newPoint = new GraphPoint(0, difference,
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
			oldValue = newValue;
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

	@Override
	public int extractValue(Map<String, String> data) {
		return (int) Double.parseDouble(data
				.get(ApplicationMapping.COLUMN_DATA)) / 1000;
	}

	@Override
	public Date extractTime(Map<String, String> data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = data.get(LatencyMapping.COLUMN_TIME);
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	@Override
	public String getGraphType() {
		return GRAPH_TYPE;
	}

	@Override
	public String getYAxisLabel() {
		return Y_AXIS_UNITS;
	}

	@Override
	public void aggregatePoints(GraphPoint oldP, GraphPoint newP) {
		oldP.y+=newP.y;
	}

	@Override
	public String[] getModes() {
		return MODES;
	}
}
