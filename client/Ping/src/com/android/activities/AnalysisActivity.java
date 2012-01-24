package com.android.activities;


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
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.android.R;
import com.android.Session;
import com.android.helpers.ThreadPoolHelper;
import com.android.helpers.ServiceHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.ResponseListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.services.PerformanceService;
import com.android.tasks.InstallBinariesTask;
import com.android.tasks.MeasurementTask;


public class AnalysisActivity extends Activity 
{
	
	//private TableLayout table;
	private LinearLayout table;
	private Button testButton;
	private Button configButton;
	//private TextView tv;
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Session session = null;
	private boolean firstPing=true;
	public String serviceTag = "PerformanceService";
	
	public static final String SETTINGS_FILE_NAME = "PingSettings";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		serverhelper.execute(new InstallBinariesTask(this,new HashMap<String,String>(), new String[0], new com.android.listeners.FakeListener()));
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			Log.v(this.toString(),"Installing Binaries...");
		}
		Log.v(this.toString(),"Binaries Installed");
		
		setContentView(R.layout.main_screen);
		
		activity = this;
				
		serverhelper = new ThreadPoolHelper(5,10);
		testButton=(Button)findViewById(R.id.test);
		configButton=(Button)findViewById(R.id.config);
		table = (LinearLayout)findViewById(R.id.measurementslayout);
		
		
		
		ServiceHelper.processStopService(this,"com.android.services.PerformanceService");
		ServiceHelper.processStartService(this,"com.android.services.PerformanceService");
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				//processStopService(serviceTag);
				//startService(new Intent(v.getContext(), PerformanceService.class));
				//serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
				//processStartService(serviceTag);
				
				Intent myIntent = new Intent(v.getContext(), RunActivity.class);
                startActivity(myIntent);
			}
		});
		
		configButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {		
				Intent myIntent = new Intent(v.getContext(), ConfigActivity.class);
                startActivityForResult(myIntent, 0);
			}
		});

	}	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent!=null){
	        Bundle extras = intent.getExtras();
	        //tv.setText(extras!=null ? extras.getString("returnKey") : "empty");
        }
    }
	
	
}