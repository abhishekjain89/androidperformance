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
import com.ping.listeners.FakeListener;
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
import com.ping.tasks.ValuesTask;
import com.ping.ui.UIUtil;
import com.ping.ui.adapter.ItemAdapter;


public class AnalysisActivity extends Activity 
{
	
	//private TableLayout table;
	private LinearLayout table;
	private Button testButton;
	private Button settingsButton;
	private Button aboutusButton;
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
		
		setContentView(R.layout.main_screen);
		
		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		
		serverhelper = new ThreadPoolHelper(5,10);
		testButton=(Button)findViewById(R.id.test);
		settingsButton=(Button)findViewById(R.id.settings);
		aboutusButton=(Button)findViewById(R.id.aboutus);
		
		
		//configButton=(Button)findViewById(R.id.config);
		
		
		ServiceHelper.processStopService(this);
		ServiceHelper.processStartService(this);
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				ServiceHelper.processStopService(activity);
				
				Intent myIntent = new Intent(v.getContext(), RunActivity.class);
                startActivity(myIntent);
			}
		});
		
		settingsButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				Intent myIntent = new Intent(v.getContext(), UserFormActivity.class);
				myIntent.putExtra("force",true);
                startActivity(myIntent);
			}
		});
		
		aboutusButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), AboutUsActivity.class);
                startActivity(myIntent);
			}
		});
	

	}	
	

	
}