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
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.BaseMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.database.mapping.ThroughputMapping;
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
import com.sun.org.apache.regexp.internal.recompile;

public class ApplicationDataSource extends DataSource {
	
	public ApplicationDataSource(Context context) {
		super(context);
		setDBHelper(new ApplicationMapping(context));
	}
	
	private void addRow(Application app) {
		ContentValues value = new ContentValues();			    
	    
		value.put(ApplicationMapping.COLUMN_TIME, getTime());		
		value.put(ApplicationMapping.COLUMN_NAME, app.getName());
		value.put(ApplicationMapping.COLUMN_PACKAGE, app.getPackageName());
		value.put(ApplicationMapping.COLUMN_TOTAL_SENT, app.getTotal_sent());
		value.put(ApplicationMapping.COLUMN_TOTAL_RECV, app.getTotal_recv());
		value.put(ApplicationMapping.COLUMN_IS_RUNNING, app.getIsRunning());		
		long insertId = database.insert(dbHelper.getTableName(), null, value);				
	}
	
	private void addRow(Usage usage) {
		ContentValues value = new ContentValues();		
	    
		value.put(ApplicationMapping.COLUMN_TIME, getTime());					
		value.put(ApplicationMapping.COLUMN_NAME, "Total");
		value.put(ApplicationMapping.COLUMN_PACKAGE, "total");
		value.put(ApplicationMapping.COLUMN_TOTAL_SENT, usage.getTotal_sent());
		value.put(ApplicationMapping.COLUMN_TOTAL_RECV, usage.getTotal_recv());
		value.put(ApplicationMapping.COLUMN_IS_RUNNING, true);		
		database.insert(dbHelper.getTableName(), null, value);
		
		value.put(ApplicationMapping.COLUMN_TIME, getTime());					
		value.put(ApplicationMapping.COLUMN_NAME, "Mobile");
		value.put(ApplicationMapping.COLUMN_PACKAGE, "Mobile");
		value.put(ApplicationMapping.COLUMN_TOTAL_SENT, usage.getMobile_sent());
		value.put(ApplicationMapping.COLUMN_TOTAL_RECV, usage.getMobile_recv());
		value.put(ApplicationMapping.COLUMN_IS_RUNNING, true);		
		database.insert(dbHelper.getTableName(), null, value);
		
		value.put(ApplicationMapping.COLUMN_TIME, getTime());					
		value.put(ApplicationMapping.COLUMN_NAME, "Wifi");
		value.put(ApplicationMapping.COLUMN_PACKAGE, "Wifi");
		value.put(ApplicationMapping.COLUMN_TOTAL_SENT,  usage.getTotal_sent() - usage.getMobile_sent());
		value.put(ApplicationMapping.COLUMN_TOTAL_RECV,  usage.getTotal_sent() - usage.getMobile_recv());
		value.put(ApplicationMapping.COLUMN_IS_RUNNING, true);		
		database.insert(dbHelper.getTableName(), null, value);
		
	}
	
	protected void insertModel(Model model) {
		
		Usage usage = (Usage) model;
		
		for(Application app : usage.getApplications()) {
			addRow(app);
		
		}		
		
		addRow(usage);
		
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
		//return getGraphData(DeviceUtil.getNetworkInfo(context), "Atlanta, GA");
		return  null;
	}
	
}
