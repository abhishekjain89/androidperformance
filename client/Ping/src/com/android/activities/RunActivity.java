package com.android.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.R;
import com.android.Session;
import com.android.helpers.ServiceHelper;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Battery;
import com.android.models.Device;
import com.android.models.GPS;

import com.android.models.Measurement;
import com.android.models.Model;
import com.android.models.Network;
import com.android.models.Ping;
import com.android.models.Sim;
import com.android.models.Throughput;
import com.android.models.Usage;
import com.android.models.Wifi;
import com.android.models.WifiNeighbor;
import com.android.services.PerformanceService;
import com.android.tasks.MeasurementTask;
import com.android.ui.adapter.ListAdapter;
import com.google.android.maps.MapActivity;

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


public class RunActivity extends MapActivity 
{
	//private LinearLayout table;

	private ThreadPoolHelper serverhelper;
	private Session session = null;
	private Activity activity;
	private boolean firstPing=true;
	public String serviceTag = "PerformanceService";
	private Button backButton;
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
		listview = (ListView) findViewById(R.id.allview);
		
		backButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				finish();
			}
		});

		ServiceHelper.processStopService(this,serviceTag);
		
		items = new ArrayList<Model>();
		listadapter = new ListAdapter(activity,R.layout.item_view,items);
		serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
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
			
			Model item = (Model)msg.obj;
			items.add(item);
			listadapter.add(item);			
			listadapter.notifyDataSetChanged();		
			
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

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}