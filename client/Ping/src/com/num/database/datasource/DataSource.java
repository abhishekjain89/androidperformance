package com.num.database.datasource;

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

import com.num.database.DatabaseOutput;
import com.num.database.mapping.BaseMapping;
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

		Cursor cursor = database.query(dbHelper.getTableName(),
				getColumns(), null, null, null, null, null);

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
	
	
}
