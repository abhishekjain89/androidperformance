package com.num.activities;


import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.num.R;
import com.num.Values;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.UrlTask;
import com.num.utils.PreferencesUtil;


public class PrivacyActivity extends Activity 
{
	
	//private TableLayout table;
	
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	final String mimeType = "text/html";
	private Button acceptButton;
	private Button rejectButton;
	public String serviceTag = "PerformanceService";
	
	public static final String SETTINGS_FILE_NAME = "PingSettings";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		Values session = (Values) this.getApplicationContext();
		
		if(!session.DEBUG&&PreferencesUtil.isAccepted(this)){

			finish();
			System.out.println("ACCEPT");
			Intent myIntent = new Intent(this, UserFormActivity.class);
            startActivity(myIntent);
		}
		
		setContentView(R.layout.privacy_screen);
		
		activity = this;
				
		serverhelper = new ThreadPoolHelper(5,10);
		serverhelper.execute(new UrlTask(activity,new HashMap<String,String>(), "http://ruggles.gtnoise.net/static/Conditions_of_Use.html", new UrlListener()));

		acceptButton = (Button) findViewById(R.id.accept);
		rejectButton = (Button) findViewById(R.id.reject);
		WebView webview = (WebView) findViewById(R.id.policyText);
		webview.getSettings().setJavaScriptEnabled(true);
		//webview.loadData("Conditions of Use<br>Welcome to our application - Network Usage Monitor! Team Network Usage Monitor provides this application to you subject to the following conditions. To use the application you must accept these conditions. Please read them carefully.<br>PRIVACY<br>Please review our Privacy Policy, which explains the data displayed and collected from your device and its uses.",mimeType,null);
		//webview.loadUrl("http://ruggles.gtnoise.net/static/Conditions_of_Use.html");
		
		rejectButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				finish();
			}
		});
		
		acceptButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				finish();
				PreferencesUtil.acceptConditions(activity);
				
				Intent myIntent = new Intent(v.getContext(), TourActivity.class);
                startActivity(myIntent);
				
			}
		});
		

	}	
	
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent!=null){
	        Bundle extras = intent.getExtras();
	        //tv.setText(extras!=null ? extras.getString("returnKey") : "empty");
        }
    }
	

	private Handler UrlHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				String data = (String)msg.obj;
				ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarPrivacy);
				
				pb.setVisibility(View.INVISIBLE);
				WebView webview = (WebView) findViewById(R.id.policyText);
				webview.loadData(data,mimeType,null);
				webview.setVisibility(View.VISIBLE);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private class UrlListener extends BaseResponseListener{

		public void onComplete(String response) {
			Message msg = new Message();
			msg.obj = response;
			UrlHandler.sendMessage(msg);			
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
			// TODO Auto-generated method stub
			
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

		public void onFail(String response) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteSummary(JSONObject Object) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteLastMile(LastMile lastMile) {
			// TODO Auto-generated method stub
			
		} 
	}
}