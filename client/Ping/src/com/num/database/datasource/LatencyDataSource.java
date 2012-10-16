package com.num.database.datasource;

import java.text.ParseException;
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
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measure;
import com.num.models.Model;
import com.num.models.Ping;
import com.num.models.Throughput;
import com.num.utils.DeviceUtil;
import com.sun.org.apache.regexp.internal.recompile;

public class LatencyDataSource extends DataSource {
	
	public LatencyDataSource(Context context) {
		super(context);
		setDBHelper(new LatencyMapping(context));		
	}
	
	private void addRow(Ping p, String connectionType) {
		
		ContentValues value = new ContentValues();		
		Measure m = p.getMeasure();	
		value.put(LatencyMapping.COLUMN_TIME, getTime());		
		value.put(LatencyMapping.COLUMN_TYPE, p.getDst().getType());
		value.put(LatencyMapping.COLUMN_CONNECTION,connectionType);
		value.put(LatencyMapping.COLUMN_VALUE, m.getAverage());
		value.put(LatencyMapping.COLUMN_MEASUREMENT, "average");
		value.put(LatencyMapping.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value.put(LatencyMapping.COLUMN_DSTIP, p.getDst().getTagname());		
	
		
		ContentValues value2 = new ContentValues();
		
		value2.put(LatencyMapping.COLUMN_TIME, getTime());		
		value2.put(LatencyMapping.COLUMN_TYPE, p.getDst().getType());
		value2.put(LatencyMapping.COLUMN_CONNECTION,connectionType);
		value2.put(LatencyMapping.COLUMN_VALUE, m.getMin());
		value2.put(LatencyMapping.COLUMN_MEASUREMENT, "minimum");
		value2.put(LatencyMapping.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value2.put(LatencyMapping.COLUMN_DSTIP, p.getDst().getTagname());		
	
		
		ContentValues value3 = new ContentValues();
		
		value3.put(LatencyMapping.COLUMN_TIME, getTime());		
		value3.put(LatencyMapping.COLUMN_TYPE, p.getDst().getType());
		value3.put(LatencyMapping.COLUMN_CONNECTION,connectionType);
		value3.put(LatencyMapping.COLUMN_VALUE, m.getMax());
		value3.put(LatencyMapping.COLUMN_MEASUREMENT, "maximum");
		value3.put(LatencyMapping.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value3.put(LatencyMapping.COLUMN_DSTIP, p.getDst().getTagname());		
	
		
		ContentValues value4 = new ContentValues();
		
		value4.put(LatencyMapping.COLUMN_TIME, getTime());		
		value4.put(LatencyMapping.COLUMN_TYPE, p.getDst().getType());
		value4.put(LatencyMapping.COLUMN_CONNECTION,connectionType);
		value4.put(LatencyMapping.COLUMN_VALUE, m.getStddev());
		value4.put(LatencyMapping.COLUMN_MEASUREMENT, "std deviation");
		value4.put(LatencyMapping.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value4.put(LatencyMapping.COLUMN_DSTIP, p.getDst().getTagname());
		
		database.insert(dbHelper.getTableName(), null, value);
		database.insert(dbHelper.getTableName(), null, value2);
		database.insert(dbHelper.getTableName(), null, value3);
		database.insert(dbHelper.getTableName(), null, value4);
		
	}
	
	
	
	protected void insertModel(Model model) {
		String currentConnectionType = DeviceUtil.getNetworkInfo(context);
		try{			
			addRow((Ping) model, currentConnectionType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public DatabaseOutput getOutput() {
		
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
		
		return output;
		
	}
	
	public HashMap<String, ArrayList<GraphPoint>> getGraphData() {
		return getGraphData(DeviceUtil.getNetworkInfo(context), "Atlanta, GA");
	}
	
	public HashMap<String, ArrayList<GraphPoint>> getGraphData(String currentConnectionType, String destination) {
	
		List<Map<String,String>> allData = getDataStores();
		
		ArrayList<GraphPoint> roundtripPoints = new ArrayList<GraphPoint>();
		ArrayList<GraphPoint> firsthopPoints = new ArrayList<GraphPoint>();
		
		for (Map<String,String> data : allData) {			
			
			if(!data.get(LatencyMapping.COLUMN_CONNECTION).equals(currentConnectionType)) {
				continue;
			}
			
			if(!data.get(LatencyMapping.COLUMN_DSTIP).equals(destination)) {
				continue;
			}
			
			
			
			if(data.get(ThroughputMapping.COLUMN_TYPE).equals("ping")){
				try {
					roundtripPoints.add(new GraphPoint(roundtripPoints.size(),extractValue(data),extractTime(data)));
				} catch (Exception e) {
					continue;
				}				
			} else if(data.get(ThroughputMapping.COLUMN_TYPE).equals("firsthop")){
				try {
					firsthopPoints.add(new GraphPoint(firsthopPoints.size(),extractValue(data),extractTime(data)));
				} catch (Exception e) {
					continue;
				}				
			}						
		}
		
		HashMap collection = new HashMap<String,ArrayList<GraphPoint>>();
		
		collection.put("ping", roundtripPoints);
		collection.put("firsthop", firsthopPoints);

		return collection;
		
	}

	@Override
	public int extractValue(Map<String, String> data) {
		return  (int)Double.parseDouble(data.get(LatencyMapping.COLUMN_VALUE));
	}

	@Override
	public Date extractTime(Map<String, String> data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString =  data.get(LatencyMapping.COLUMN_TIME);
		try {
			return df.parse(dateString);
		} catch (ParseException e) {			
			e.printStackTrace();
			return new Date();
		}
	}

}
