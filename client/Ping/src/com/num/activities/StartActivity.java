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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.Values;
import com.num.activities.FullDisplayActivity.MeasurementListener;
import com.num.helpers.ServiceHelper;
import com.num.helpers.TaskHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Model;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.InstallBinariesTask;
import com.num.tasks.MeasurementTask;
import com.num.tasks.SummaryTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.utils.PreferencesUtil;
import com.num.R;


public class StartActivity extends TrackedActivity 
{

	//private TableLayout table;

	private Activity activity;
	private TextView title;
	private ThreadPoolHelper serverhelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = this;

		setContentView(R.layout.startup);
		title = (TextView) findViewById(R.id.title);
		//title.setTextColor(title.getTextColors().withAlpha(0));

		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		//in.setDuration(2000);

		title.setAnimation(in);

		in.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation animation) {
				finish();

				Intent myIntent = new Intent(activity, PrivacyActivity.class);
				startActivity(myIntent);


			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});


	}	



}