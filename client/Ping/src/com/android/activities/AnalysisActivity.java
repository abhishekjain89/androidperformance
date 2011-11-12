package com.android.activities;


import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.android.R;
import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;


public class AnalysisActivity extends Activity 
{
	
	//private TableLayout table;
	private LinearLayout table;
	private Button testButton;
	private Button configButton;
	//private TextView tv;
	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;
	private boolean firstPing=true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		activity = this;
		session =  (Session) getApplicationContext();		
		serverhelper = new ServerHelper(session);
		testButton=(Button)findViewById(R.id.test);
		configButton=(Button)findViewById(R.id.config);
		table = (LinearLayout)findViewById(R.id.measurementslayout);
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {		
				serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
			}
		});
		
		configButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {		
				Intent myIntent = new Intent(v.getContext(), ConfigActivity.class);
                startActivityForResult(myIntent, 0);
			}
		});

	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent!=null){
	        Bundle extras = intent.getExtras();
	        //tv.setText(extras!=null ? extras.getString("returnKey") : "empty");
        }
    }
	
	private void initPingTable(){
		LinearLayout row = new LinearLayout(this);
		//row.setOrientation(HORIZONTAL);
		row.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell2 = new TextView(this);
        cell2.setText("Max");
        cell2.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell2,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell3 = new TextView(this);
        cell3.setText("Min");
        cell3.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell3,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell4 = new TextView(this);
        cell4.setText("StdDev");
        cell4.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell4,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell5 = new TextView(this);
        cell5.setText("Avr");
        cell5.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell5,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		
        /*TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
        
        TextView cell1 = new TextView(this);
        cell1.setText("#  ");
        cell1.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell1,new TableRow.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell2 = new TextView(this);
        cell2.setText("Max  ");
        cell2.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell2,new TableRow.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell3 = new TextView(this);
        cell3.setText("Min  ");
        cell3.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell3,new TableRow.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell4 = new TextView(this);
        cell4.setText("StdDev  ");
        cell4.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell4,new TableRow.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell5 = new TextView(this);
        cell5.setText("Avr  ");
        cell5.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell5,new TableRow.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));*/
        
        
        table.addView(row,new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	private void newPingTable(Ping p){
		LinearLayout row = new LinearLayout(this);
		//row.setOrientation(HORIZONTAL);
		row.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell2 = new TextView(this);
        cell2.setText(String.valueOf(p.getMeasure().getMax()));
        cell2.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell2,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell3 = new TextView(this);
        cell3.setText(String.valueOf(p.getMeasure().getMin()));
        cell3.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell3,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell4 = new TextView(this);
        cell4.setText(String.valueOf(p.getMeasure().getStddev()));
        cell4.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell4,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView cell5 = new TextView(this);
        cell5.setText(String.valueOf(p.getMeasure().getAverage()));
        cell5.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT, 
				(float) 0.25));
        row.addView(cell5,new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        
        table.addView(row,new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	private void newDeviceTable(Device d){
		
		JSONObject obj=d.toJSON();
		Iterator keyes=obj.keys();
		LinearLayout row;
		
		for (String key=(String)keyes.next();keyes.hasNext();key=(String)keyes.next()){
		
			row = new LinearLayout(this);
			//row.setOrientation(HORIZONTAL);
			row.setLayoutParams(new LinearLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        TextView name = new TextView(this);
	        name.setText(key);
	        name.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, 
					LayoutParams.WRAP_CONTENT, 
					(float) 0.5));
	        row.addView(name,new LinearLayout.LayoutParams(
	        		LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	       
	        try {
	        	TextView nameV = new TextView(this);
				nameV.setText((String)obj.get(key));
				nameV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT, 
						(float) 0.5));
		        row.addView(nameV,new LinearLayout.LayoutParams(
		        		LayoutParams.FILL_PARENT,
		                LayoutParams.WRAP_CONTENT));
			} catch (JSONException e) {e.printStackTrace();}
	        
	        
	        table.addView(row,new LinearLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
		}     
	}
	
	private void newMeasurementTable(Measurement m){
		JSONObject obj=m.toJSON();
		Iterator keyes=obj.keys();
		LinearLayout row;
		
		for (String key=(String)keyes.next();keyes.hasNext();key=(String)keyes.next()){
		
			row = new LinearLayout(this);
			//row.setOrientation(HORIZONTAL);
			row.setLayoutParams(new LinearLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        TextView name = new TextView(this);
	        name.setText(key);
	        name.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, 
					LayoutParams.WRAP_CONTENT, 
					(float) 0.5));
	        row.addView(name,new LinearLayout.LayoutParams(
	        		LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	       
	        try {
	        	TextView nameV = new TextView(this);
				nameV.setText((String)obj.get(key));
				nameV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT, 
						(float) 0.5));
		        row.addView(nameV,new LinearLayout.LayoutParams(
		        		LayoutParams.FILL_PARENT,
		                LayoutParams.WRAP_CONTENT));
			} catch (JSONException e) {e.printStackTrace();}
	        
	        
	        table.addView(row,new LinearLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
		}  
	}
	
	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			Message msg=Message.obtain(pingHandler, 0, response);
			pingHandler.sendMessage(msg);		
		}
		
		public void onCompleteDevice(Device response) {
			Message msg=Message.obtain(deviceHandler, 0, response);
			deviceHandler.sendMessage(msg);		
		}
		
		public void onCompleteMeasurement(Measurement response) {
			Message msg=Message.obtain(measurementHandler, 0, response);
			deviceHandler.sendMessage(msg);		
		}

		public void onComplete(String response) {
		
		}
	}

	private Handler pingHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				Ping p=(Ping)msg.obj;
				if (firstPing){
					initPingTable();
					firstPing=false;
				}
				newPingTable(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Handler deviceHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				firstPing=false;
				Device d=(Device)msg.obj;
				newDeviceTable(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Handler measurementHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				firstPing=false;
				Measurement m=(Measurement)msg.obj;
				newMeasurementTable(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}