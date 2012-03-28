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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
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
		WebView webview = (WebView) findViewById(R.id.policyText);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadData("Conditions of Use<br>Welcome to our application - Network Usage Monitor! Team Network Usage Monitor provides this application to you subject to the following conditions. To use the application you must accept these conditions. Please read them carefully.<br>PRIVACY<br>Please review our Privacy Policy, which explains the data displayed and collected from your device and its uses." 
/*
				OWNERSHIP
				All content included on the application, such as text, button icons, images, data compilations, source code, and software, is the property of the Georgia Institute of Technology.

				LICENSE AND APPLICATION USAGE
				Team Network Usage Monitor grants you a limited license to access and make personal use of this application and not to modify it, or any portion of it. The source code may be accessed for viewing purposes or copied as long as modifications are not made directly to the source code. This license does not include any sale or commercial use of this application or its contents. This application or any portion of its code may not be sold, resold, or otherwise exploited for any commercial purpose. Any unauthorized use terminates the permission or license granted by the Ping team. You are granted a limited, revocable, and nonexclusive right to create a hyperlink to the android marketplace page of Ping so long as the link does not portray the Georgia Institute of Technology, the Ping application or members of the Ping team in a false, misleading, derogatory, or otherwise offensive matter. 

				QUESTIONS:
				Questions regarding our source code, suggestions, reviews, Privacy Policy, or other policy related material can be directed to us by emailing us at: projectping@gmail.com.
				"*/
				,mimeType,null);
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