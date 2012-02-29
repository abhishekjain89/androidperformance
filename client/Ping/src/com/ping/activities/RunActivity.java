package com.ping.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ping.R;

import com.google.android.maps.MapActivity;
import com.ping.Session;
import com.ping.helpers.ServiceHelper;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.listeners.BaseResponseListener;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.GPS;
import com.ping.models.Measurement;
import com.ping.models.Model;
import com.ping.models.Network;
import com.ping.models.Ping;
import com.ping.models.Sim;
import com.ping.models.Throughput;
import com.ping.models.Usage;
import com.ping.models.Wifi;
import com.ping.models.WifiNeighbor;
import com.ping.services.PerformanceServiceAll;
import com.ping.tasks.MeasurementTask;
import com.ping.ui.adapter.ListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;


public class RunActivity extends Activity 
{
	//private LinearLayout table;

	private ThreadPoolHelper serverhelper;
	private Session session = null;
	private Activity activity;
	private boolean firstPing=true;
	public String serviceTag = "PerformanceService";
	private Button backButton;
	private Button noteButton;
	private Button loadButton;
	//private ProgressBar progress;
	//private ProgressBar progressBar;
	public ArrayList<Model> items;
	public ListView listview;
	public ListAdapter listadapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual_test);

		activity = this;

		serverhelper = new ThreadPoolHelper(5,10);
		backButton=(Button)findViewById(R.id.back);
		noteButton = (Button)findViewById(R.id.note);
		loadButton = (Button)findViewById(R.id.load);
		listview = (ListView) findViewById(R.id.allview);
				
		backButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {
				ServiceHelper.processStopService(activity,"com.android.services.PerformanceService");
				ServiceHelper.processStartService(activity,"com.android.services.PerformanceService");
				finish();
			}
		});

		ServiceHelper.processStopService(this,serviceTag);
		
		items = new ArrayList<Model>();
		listadapter = new ListAdapter(activity,noteButton,R.layout.item_view,items);
		serverhelper.execute(new MeasurementTask(activity,true,true,true, new MeasurementListener()));
		listview.setAdapter(listadapter);

	}




	public class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			
			//onCompleteOutput(response);
		}

		public void onCompleteDevice(Device response) {
			
			onCompleteOutput(response);
		}

		public void onCompleteMeasurement(Measurement response) {
			LoadBarHandler.sendEmptyMessage(0);
			onCompleteOutput(response);
		}
		
		public void onCompleteOutput(Model model){
			
			Message msg2=Message.obtain(UIHandler, 0, model);
			UIHandler.sendMessage(msg2);
		}

		public void onComplete(String response) {

		}

		public void onUpdateProgress(int val){
			Message msg=Message.obtain(progressBarHandler, 0, val);
			progressBarHandler.sendMessage(msg);
		}

		public void onCompleteGPS(GPS response) {
			onCompleteOutput(response);

		}

		public void makeToast(String text) {
			Message msg=Message.obtain(toastHandler, 0, text);
			toastHandler.sendMessage(msg);

		}

		public void onCompleteSignal(String signalStrength) {

		}
		public void onCompleteUsage(Usage response) {
			onCompleteOutput(response);

		}

		public void onCompleteThroughput(Throughput response) {
			onCompleteOutput(response);

		}

		public void onCompleteWifi(Wifi response) {
			onCompleteOutput(response);

		}
		
		public void onCompleteBattery(Battery response) {
			onCompleteOutput(response);

		}

		public void onCompleteNetwork(Network response) {
			onCompleteOutput(response);
			
		}

		public void onCompleteSIM(Sim response) {
			onCompleteOutput(response);
			
		}

		public void onCompleteSummary(JSONObject Object) {
			// TODO Auto-generated method stub
			
		}

		public void onFail(String response) {
			// TODO Auto-generated method stub
			
		}
	}

	private Handler toastHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				String text = (String)msg.obj;
				Toast.makeText(activity, text, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};



	private Handler UIHandler = new Handler(){
		public void  handleMessage(Message msg) {
			noteButton.setVisibility(View.VISIBLE);
			Model item = (Model)msg.obj;
			items.add(item);
			listadapter.add(item);			
			listadapter.notifyDataSetChanged();		
			
		}
	};
	
	private Handler LoadBarHandler = new Handler(){
		public void  handleMessage(Message msg) {
			loadButton.setVisibility(View.GONE);
			ServiceHelper.processStopService(activity,"com.android.services.PerformanceService");
			ServiceHelper.processStartService(activity,"com.android.services.PerformanceService");
		}
	};

	private Handler progressBarHandler = new Handler() {

		public void  handleMessage(Message msg) {
			try {
				int value=(Integer)msg.obj;
				//progressBar.setProgress(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}