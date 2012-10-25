package com.num.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.R;
import com.num.Values;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.FakeListener;
import com.num.models.PingData;
import com.num.tasks.ValuesTask;

public class PreviousActivity extends TrackedActivity 
{
	
	private ListView listview;
	private TextView previous;
	private TextView devicetext;
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.previous);
		
		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		serverhelper = new ThreadPoolHelper(5,10);
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);
		serverhelper.execute(new ValuesTask(this,new FakeListener()));
		previous =  (TextView) findViewById(R.id.previous_data);

		
		
	}	
	
}