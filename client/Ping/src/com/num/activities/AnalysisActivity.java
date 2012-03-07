package com.num.activities;


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

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.SummaryTask;
import com.num.tasks.ValuesTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.R;



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