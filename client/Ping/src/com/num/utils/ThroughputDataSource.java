package com.num.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.num.helpers.ThroughputDatabaseHelper;
import com.num.models.GraphData;

import com.num.models.GraphPoint;
import com.num.models.Link;
import com.num.models.Throughput;
import com.num.models.ThroughputData;

public class ThroughputDataSource {
	// Database fields
	private SQLiteDatabase database;
	private Context context;
	private ThroughputDatabaseHelper dbHelper;
	private String[] allColumns = { 
			ThroughputDatabaseHelper.COLUMN_ID,
			ThroughputDatabaseHelper.COLUMN_TIME, 
			ThroughputDatabaseHelper.COLUMN_SPEED, 
			ThroughputDatabaseHelper.COLUMN_TYPE,
			ThroughputDatabaseHelper.COLUMN_CONNECTION};

	public ThroughputDataSource(Context context) {
		dbHelper = new ThroughputDatabaseHelper(context);
		this.context = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void addThroughput(Link l, String type, String connectionType) {
		ContentValues value = new ContentValues();		

	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = sdf.format(new Date());		
		value.put(ThroughputDatabaseHelper.COLUMN_TIME, time);
		value.put(ThroughputDatabaseHelper.COLUMN_SPEED, "" + l.speedInBits());
		value.put(ThroughputDatabaseHelper.COLUMN_TYPE, type);
		value.put(ThroughputDatabaseHelper.COLUMN_CONNECTION,connectionType);
		long insertId = database.insert(ThroughputDatabaseHelper.TABLE_THROUGHPUT, null, value);
		Cursor cursor = database.query(ThroughputDatabaseHelper.TABLE_THROUGHPUT,
				allColumns, ThroughputDatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ThroughputData newThroughputData = cursorToThroughputData(cursor);
		cursor.close();
	}
	
	public void createThroughput(Throughput t, String connectionType) {
		addThroughput(t.getDownLink(), "downlink", connectionType);
		addThroughput(t.getUpLink(), "uplink", connectionType);
	}

	public void deleteThroughputData(ThroughputData throughput) {
		long id = throughput.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(ThroughputDatabaseHelper.TABLE_THROUGHPUT, ThroughputDatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<ThroughputData> getAllThroughputData() {
		List<ThroughputData> throughputs = new ArrayList<ThroughputData>();

		Cursor cursor = database.query(ThroughputDatabaseHelper.TABLE_THROUGHPUT,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ThroughputData throughput = cursorToThroughputData(cursor);
			throughputs.add(throughput);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return throughputs;
	}

	private ThroughputData cursorToThroughputData(Cursor cursor) {
		ThroughputData throughput = new ThroughputData();
		throughput.setId(cursor.getLong(0));
		throughput.setTime(cursor.getString(1));
		throughput.setSpeed(cursor.getString(2));
		throughput.setType(cursor.getString(3));
		throughput.setConnection(cursor.getString(4));
		return throughput;
	}
	
	public class ThroughputOutput {
		
		public int averageDownload;
		public int averageUpload;
		
		public ThroughputOutput(int down,int up) {
			averageDownload = down;
			averageUpload = up;
		}
		
	}
	
	
	public ThroughputOutput getOutput() {
		List<ThroughputData> allData = getAllThroughputData();
		
		int totalUpload = 0;
		int totalDownload = 0;
		int countUpload = 0;
		int countDownload = 0;		
		
		String currentConnectionType = DeviceUtil.getNetworkInfo(context); 
		
		for (ThroughputData data : allData) {
			
			if(!data.getConnection().equals(currentConnectionType)) {
				continue;
			}
			
			if(data.getType().equals("uplink")){
				try {
				totalUpload+=data.getSpeed();
				countUpload++;
				} catch (Exception e) {
					continue;
				}				
			} else if(data.getType().equals("downlink")){
				try {
				totalDownload+=data.getSpeed();
				countDownload++;
				} catch (Exception e) {
					continue;
				}				
			}						
		}
		
		if(countDownload==0) countDownload++;
		if(countUpload==0) countUpload++;
		
		return new ThroughputOutput(totalDownload/countDownload,totalUpload/countUpload);
		
	}
	
	public HashMap<String, ArrayList<GraphPoint>> getGraphData() {
		List<ThroughputData> allData = getAllThroughputData();
		
		ArrayList<GraphPoint> downloadPoints = new ArrayList<GraphPoint>();
		ArrayList<GraphPoint> uploadPoints = new ArrayList<GraphPoint>();
		
		for (ThroughputData data : allData) {
			String currentConnectionType = DeviceUtil.getNetworkInfo(context);
			if(!data.getConnection().equals(currentConnectionType)) {
				continue;
			}
			
			if(data.getType().equals("uplink")){
				try {
					uploadPoints.add(new GraphPoint(uploadPoints.size(), data.getSpeed()));
				} catch (Exception e) {
					continue;
				}				
			} else if(data.getType().equals("downlink")){
				try {
					downloadPoints.add(new GraphPoint(downloadPoints.size(), data.getSpeed()));
				} catch (Exception e) {
					continue;
				}				
			}						
		}
		
		HashMap collection = new HashMap<String,ArrayList<GraphPoint>>();
		
		collection.put("uplink", uploadPoints);
		collection.put("downlink", downloadPoints);
		
		return collection;
		
	}
	
 
	
}
