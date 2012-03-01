package com.ping.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.ping.R;
import com.ping.Values;
import com.ping.helpers.ServiceHelper;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.listeners.BaseResponseListener;
import com.ping.listeners.ResponseListener;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.GPS;
import com.ping.models.Measurement;
import com.ping.models.Model;
import com.ping.models.Network;
import com.ping.models.Ping;
import com.ping.models.Row;
import com.ping.models.Sim;
import com.ping.models.Throughput;
import com.ping.models.Usage;
import com.ping.models.Wifi;
import com.ping.services.PerformanceServiceAll;
import com.ping.tasks.InstallBinariesTask;
import com.ping.tasks.MeasurementTask;
import com.ping.tasks.SummaryTask;
import com.ping.ui.UIUtil;
import com.ping.ui.adapter.ItemAdapter;
import com.ping.utils.PreferencesUtil;


public class PrivacyActivity extends Activity 
{
	
	//private TableLayout table;

	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	private Button acceptButton;
	private Button rejectButton;
	public String serviceTag = "PerformanceService";
	
	public static final String SETTINGS_FILE_NAME = "PingSettings";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if(PreferencesUtil.isAccepted(this)){
			finish();
			System.out.println("ACCEPT");
			Intent myIntent = new Intent(this, UserFormActivity.class);
            startActivity(myIntent);
		}
		
		setContentView(R.layout.privacy_screen);
		
		activity = this;
				
		serverhelper = new ThreadPoolHelper(5,10);
		
		acceptButton = (Button) findViewById(R.id.accept);
		rejectButton = (Button) findViewById(R.id.reject);
		
		rejectButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				finish();
			}
		});
		
		acceptButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				finish();
				PreferencesUtil.acceptConditions(activity);
				
				Intent myIntent = new Intent(v.getContext(), UserFormActivity.class);
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
	
	
}