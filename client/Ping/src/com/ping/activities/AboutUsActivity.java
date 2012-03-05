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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.ping.R;
import com.ping.Values;

import com.ping.helpers.ServiceHelper;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.helpers.UserDataHelper;
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


public class AboutUsActivity extends Activity 
{

	//private TableLayout table;

	private Activity activity;

	private Values session = null;

	private Button backButton;
	
	private Button increment;
	private Button decrement;
	Boolean force = false;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about_us);

		activity = this;

		backButton = (Button) this.findViewById(R.id.back);
	

		backButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				finish();

			}
		});


	}	

	public static int forceLimits(int val){
		if(val<1) val=1;
		if(val>30) val=30;
		return val;
	}

}