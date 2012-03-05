package com.ping.activities;


import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ping.R;

import com.ping.Values;
import com.ping.helpers.ServiceHelper;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.listeners.BaseResponseListener;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.GPS;
import com.ping.models.Measurement;
import com.ping.models.Network;
import com.ping.models.Ping;
import com.ping.models.Row;
import com.ping.models.Sim;
import com.ping.models.Throughput;
import com.ping.models.Usage;
import com.ping.models.Wifi;
import com.ping.tasks.SummaryTask;
import com.ping.ui.UIUtil;
import com.ping.ui.adapter.ItemAdapter;


public class AnalysisActivity extends BaseActivity 
{
	
	//private TableLayout table;
	private LinearLayout table;
	private Button testButton;
	private Button configButton;
	private ListView listview;
	private TextView apptext;
	private TextView devicetext;
	//private TextView tv;
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		//serverhelper.execute(new InstallBinariesTask(this,new HashMap<String,String>(), new String[0], new com.ping.listeners.FakeListener()));
		serverhelper.execute(new SummaryTask(this,new Listener()));
		
		setContentView(R.layout.main_screen);
		
		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		
		serverhelper = new ThreadPoolHelper(5,10);
		testButton=(Button)findViewById(R.id.test);
		
		listview = (ListView) findViewById(R.id.allview);
		//configButton=(Button)findViewById(R.id.config);
		
		
		ServiceHelper.processStopService(this,"com.android.services.PerformanceService");
		ServiceHelper.processStartService(this,"com.android.services.PerformanceService");
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				ServiceHelper.processStopService(activity,"com.android.services.PerformanceService");
				
				Intent myIntent = new Intent(v.getContext(), RunActivity.class);
                startActivity(myIntent);
			}
		});
	

	}	
	
	public class Listener extends BaseResponseListener{

		public void onComplete(String response) {
			// TODO Auto-generated method stub
			
		}

		public void onCompletePing(Ping response) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteMeasurement(Measurement response) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteDevice(Device response) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteBattery(Battery response) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateProgress(int val) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteGPS(GPS gps) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteUsage(Usage usage) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteThroughput(Throughput throughput) {
			// TODO Auto-generated method stub
			
		}

		public void makeToast(String text) {
		}

		public void onCompleteSignal(String signalStrength) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteWifi(Wifi wifiList) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteNetwork(Network network) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteSIM(Sim sim) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteSummary(JSONObject Object) {
			Message msg=Message.obtain(UIHandler, 0, Object);
			UIHandler.sendMessage(msg);
			
		}

		public void onFail(String response) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private Handler UIHandler = new Handler(){
		public void  handleMessage(Message msg) {
			JSONObject obj = (JSONObject)msg.obj;
			
	 		ArrayList<Row> cells = new ArrayList<Row>();
			
	 		try {
	 			cells.add(new Row("Project Statistics"));
				cells.add(new Row("Total Apps",""+obj.get("total-apps")));
				cells.add(new Row("Total Devices",""+obj.get("total-devices")));
				cells.add(new Row("Total CellTowers",""+obj.get("total-cells")));
				cells.add(new Row("Total Wifi-spots",""+obj.get("total-wifis")));
				cells.add(new Row(R.layout.cell_view_title_banner,"Status: " + obj.get("status")));
				
				if(cells.size()!=0){
					ItemAdapter itemadapter = new ItemAdapter(activity,cells);
					for(Row cell: cells)
						itemadapter.add(cell);
					listview.setAdapter(itemadapter);


					itemadapter.notifyDataSetChanged();
					UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	};
	
	
}