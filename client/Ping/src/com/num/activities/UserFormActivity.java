package com.num.activities;


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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.helpers.UserDataHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Model;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.services.PerformanceServiceAll;
import com.num.tasks.InstallBinariesTask;
import com.num.tasks.MeasurementTask;
import com.num.tasks.SummaryTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.utils.PreferencesUtil;
import com.num.R;



public class UserFormActivity extends Activity 
{

	//private TableLayout table;

	private Activity activity;

	private Values session = null;

	private Button saveButton;
	private EditText dataCapInput;
	private EditText billingCycleInput;
	private Button increment;
	private Button decrement;
	Boolean force = false;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		UserDataHelper userhelp = new UserDataHelper(this);
		Bundle extras = getIntent().getExtras();
		

		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}


		if(!force && userhelp.isFilled()){
			finish();
			Intent myIntent = new Intent(this, AnalysisActivity.class);
			startActivity(myIntent);
		}

		setContentView(R.layout.userform_screen);

		activity = this;

		saveButton = (Button) this.findViewById(R.id.save);
		dataCapInput = (EditText) this.findViewById(R.id.dataCap);
		billingCycleInput = (EditText) this.findViewById(R.id.billingCycle);
		/*
		increment.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {
				int day = 1;
				try{
					day = Integer.parseInt(billingCycleInput.getText().toString());
					day+=1;
				}
				catch(Exception e){
					day=1;
				}
				day=forceLimits(day);
				billingCycleInput.setText(day+"");

			}
		});

		decrement.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {
				int day = 1;
				try{
					day = Integer.parseInt(billingCycleInput.getText().toString());
					day-=1;
				}
				catch(Exception e){
					day=1;
				}

				day=forceLimits(day);
				billingCycleInput.setText(day+"");

			}
		});*/

		saveButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				UserDataHelper userhelp = new UserDataHelper(activity);
				try{
					int day = Integer.parseInt(billingCycleInput.getText().toString());
					day=forceLimits(day);
					userhelp.setBillingCycle(day);
					userhelp.setDataCap(Integer.parseInt(dataCapInput.getText().toString()));
				}
				catch(Exception e){
					return;
				}

				finish();
				if(!force){
					Intent myIntent = new Intent(v.getContext(), AnalysisActivity.class);
					startActivity(myIntent);
				}

			}
		});


	}	

	public static int forceLimits(int val){
		if(val<1) val=1;
		if(val>30) val=30;
		return val;
	}

}