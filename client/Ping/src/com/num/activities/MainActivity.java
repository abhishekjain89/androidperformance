package com.num.activities;


import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
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



public class MainActivity extends Activity 
{

	//private TableLayout table;
	
	private LinearLayout measurementButton;
	private LinearLayout settingsButton;
	private LinearLayout aboutusButton;
	private LinearLayout previousButton;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Values session = (Values) this.getApplicationContext();

		setContentView(R.layout.main_screen);
		

		settingsButton=(LinearLayout)findViewById(R.id.settings);
		aboutusButton=(LinearLayout)findViewById(R.id.aboutus);
		measurementButton = (LinearLayout)findViewById(R.id.measurement);
		previousButton = (LinearLayout)findViewById(R.id.previous);

		if(!session.DEBUG){
			aboutusButton.setVisibility(View.GONE);
			previousButton.setVisibility(View.GONE);
		}

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		serverhelper.execute(new ValuesTask(this,new FakeListener()));

		ServiceHelper.processStopService(this);
		ServiceHelper.processStartService(this);


		settingsButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				Intent myIntent = new Intent(v.getContext(), UserFormActivity.class);
				myIntent.putExtra("force",true);
				startActivity(myIntent);
			}
			
		});

		measurementButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				Intent myIntent = new Intent(v.getContext(), AnalysisActivity.class);

				startActivity(myIntent);
			}
		});

		aboutusButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), AboutUsActivity.class);
				startActivity(myIntent);
			}
		});



		previousButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				Intent myIntent = new Intent(v.getContext(), PreviousActivity.class);
				startActivity(myIntent);
			}
		});
	
	}	



}