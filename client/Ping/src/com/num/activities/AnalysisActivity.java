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
import com.num.activities.AboutUsActivity.Listener;
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
	private Button pingButton;
	private Button wifiButton;
	private Button usageButton;
	private Button throughputButton;
	private Button tourButton;
	
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
		testButton=(Button)findViewById(R.id.full_test);
		settingsButton=(Button)findViewById(R.id.settings);
		aboutusButton=(Button)findViewById(R.id.aboutus);
		
		pingButton=(Button)findViewById(R.id.ping);
		wifiButton=(Button)findViewById(R.id.wifi);
		usageButton=(Button)findViewById(R.id.usage);
		throughputButton=(Button)findViewById(R.id.throughput);
		tourButton = (Button)findViewById(R.id.tour);
		
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		serverhelper.execute(new ValuesTask(this,new FakeListener()));

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
		
		pingButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), FullDisplayActivity.class);
				myIntent.putExtra("model_key","latency");
				myIntent.putExtra("time","6");
                startActivity(myIntent);
			}
		});
		
		wifiButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), FullDisplayActivity.class);
				myIntent.putExtra("model_key","wifi");
                startActivity(myIntent);
			}
		});
		usageButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), FullDisplayActivity.class);
				myIntent.putExtra("model_key","usage");
                startActivity(myIntent);
			}
		});
		
		throughputButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), FullDisplayActivity.class);
				myIntent.putExtra("model_key","throughput");
				myIntent.putExtra("time","40");
                startActivity(myIntent);
			}
		});
		
		tourButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(),TourActivity.class);
				
                startActivity(myIntent);
			}
		});
	

	}	
	

	
}