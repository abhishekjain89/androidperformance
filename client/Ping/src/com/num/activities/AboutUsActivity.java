package com.num.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.Values;
import com.num.helpers.AppRater;

import com.num.R;



public class AboutUsActivity extends TrackedActivity
{
	private Activity activity;
	private Button rateButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about_us);

		activity = this;			
		rateButton = (Button) this.findViewById(R.id.back);		

		rateButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				AppRater.startIntent(activity);
				finish();

			}
		});


	}	

}