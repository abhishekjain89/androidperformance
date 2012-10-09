package com.num.database.datasource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.num.database.mapping.BaseMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.database.mapping.ThroughputMapping;
import com.num.models.GraphData;

import com.num.models.GraphPoint;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measure;
import com.num.models.Model;
import com.num.models.Ping;
import com.num.models.Throughput;
import com.num.utils.DeviceUtil;

public class LatencyDataSource extends DataSource {
	
	public LatencyDataSource(Context context) {
		super(context);
		setDBHelper(new LatencyMapping(context));
	}
	
	public void addRow(Ping p, String connectionType) {
		ContentValues value = new ContentValues();		
		Measure m = p.getMeasure();
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = sdf.format(new Date());		
		value.put(LatencyMapping.COLUMN_TIME, time);		
		value.put(LatencyMapping.COLUMN_TYPE, p.getDst().getType());
		value.put(LatencyMapping.COLUMN_CONNECTION,connectionType);
		value.put(LatencyMapping.COLUMN_AVG, m.getAverage());
		value.put(LatencyMapping.COLUMN_MIN, m.getMin());
		value.put(LatencyMapping.COLUMN_MAX, m.getMax());
		value.put(LatencyMapping.COLUMN_STD, m.getStddev());
		value.put(LatencyMapping.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value.put(LatencyMapping.COLUMN_DSTIP, p.getDst().getTagname());
		
		long insertId = database.insert(dbHelper.getTableName(), null, value);
		Cursor cursor = database.query(dbHelper.getTableName(),
				getColumns(), LatencyMapping.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Map<String,String> newThroughputData = dbHelper.getDatabaseColumns().getDataStore(cursor);
		cursor.close();
	}
	
	protected void insertModel(Model model) {
		
		Ping ping = (Ping) model;
		String currentConnectionType = DeviceUtil.getNetworkInfo(context);
		addRow((Ping) model, currentConnectionType);				
	}
	
	
	
	public DatabaseOutput getOutput() {
		open();
		List<Map<String,String>> allData = getDataStores();
		
		int totalUpload = 0;
		int totalDownload = 0;
		int countUpload = 0;
		int countDownload = 0;		
		
		String currentConnectionType = DeviceUtil.getNetworkInfo(context); 
		
		for (Map<String,String> data : allData) {
			
		}
		
		if(countDownload==0) countDownload++;
		if(countUpload==0) countUpload++;
		
		DatabaseOutput output = new DatabaseOutput();
				
		output.add("avg_upload", ""+totalUpload/countUpload);
		output.add("avg_download", ""+totalDownload/countDownload);
		close();
		return output;
		
	}
	
	public HashMap<String, ArrayList<GraphPoint>> getGraphData() {
		open();
		List<Map<String,String>> allData = getDataStores();
		
		ArrayList<GraphPoint> downloadPoints = new ArrayList<GraphPoint>();
		ArrayList<GraphPoint> uploadPoints = new ArrayList<GraphPoint>();
		
		for (Map<String,String> data : allData) {
			String currentConnectionType = DeviceUtil.getNetworkInfo(context);
			
			if(!data.get(ThroughputMapping.COLUMN_CONNECTION).equals(currentConnectionType)) {
				continue;
			}
			
			if(data.get(ThroughputMapping.COLUMN_TYPE).equals("uplink")){
				try {
					uploadPoints.add(new GraphPoint(uploadPoints.size(), (int)Double.parseDouble(data.get(ThroughputMapping.COLUMN_SPEED))));
				} catch (Exception e) {
					continue;
				}				
			} else if(data.get(ThroughputMapping.COLUMN_TYPE).equals("downlink")){
				try {
					downloadPoints.add(new GraphPoint(downloadPoints.size(), (int)Double.parseDouble(data.get(ThroughputMapping.COLUMN_SPEED))));
				} catch (Exception e) {
					continue;
				}				
			}						
		}
		
		HashMap collection = new HashMap<String,ArrayList<GraphPoint>>();
		
		collection.put("uplink", uploadPoints);
		collection.put("downlink", downloadPoints);
		close();
		return collection;
		
	}

}
