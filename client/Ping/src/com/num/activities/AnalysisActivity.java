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
	
	private LinearLayout testButton;
	private LinearLayout pingButton;
	private LinearLayout wifiButton;
	private LinearLayout usageButton;
	private LinearLayout throughputButton;
	private LinearLayout tourButton;
	
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
		
		
		setContentView(R.layout.measurement_screen);
		
		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		
		serverhelper = new ThreadPoolHelper(5,10);
		testButton=(LinearLayout)findViewById(R.id.full_test);
		
		
		
		pingButton=(LinearLayout)findViewById(R.id.ping);
		wifiButton=(LinearLayout)findViewById(R.id.wifi);
		usageButton=(LinearLayout)findViewById(R.id.usage);
		throughputButton=(LinearLayout)findViewById(R.id.throughput);
	
		
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		serverhelper.execute(new ValuesTask(this,new FakeListener()));

		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				ServiceHelper.processStopService(activity);
				
				Intent myIntent = new Intent(v.getContext(), RunActivity.class);
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
		
	
	

	}	
	

	
}