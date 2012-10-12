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
	
	
	public DataSource(Context context) {		
		this.context = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
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
	
	public List<Map<String,String>> getDataStores() {
		List<Map<String,String>> dataStores = new ArrayList<Map<String,String>>();
		System.out.println("querying db");
		Cursor cursor = database.query(dbHelper.getTableName(),
				getColumns(), null, null, null, null, null);
		System.out.println("querying db DONE");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Map<String,String> dataStore = 
					dbHelper.getDatabaseColumns().getDataStore(cursor);
			dataStores.add(dataStore);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		
		return dataStores;
	}
	
	public void insert(Model model) {
		open();
		insertModel(model);
		close();
	}

	public ArrayList<GraphPoint> getGraphData(HashMap<String, String> filter) {
		open();
		List<Map<String,String>> allData = getDataStores();
		ArrayList<GraphPoint> points = new ArrayList<GraphPoint>();
		
		ArrayList<String> fields = new ArrayList<String>();
		
		Iterator<String> iter = filter.keySet().iterator();		
		while(iter.hasNext()) {			
			fields.add(iter.next());
		}
		
		
		for (Map<String,String> data : allData) {
			
			boolean approved = true;
				
			for(String field : fields) {
				if(filter.get(field).equals("")) continue;
				
				String value = filter.get(field);
				
				if(!data.get(field).equals(value)){
					approved=false;
					break;
				}
								
			}
			
			if(approved) {
				points.add(new GraphPoint(data.size(),extractPoint(data)));
			}
		}
				
		return points;
		
	}
	
	public abstract int extractPoint(Map<String,String> data);
	
	
}
