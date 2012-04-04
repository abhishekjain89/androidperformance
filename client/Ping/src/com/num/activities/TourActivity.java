package com.num.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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



public class TourActivity extends Activity 
{

	//private TableLayout table;

	private Activity activity;

	private Values session = null;

	private TextView text;
	private ImageView image;
	private Button back;
	private Button next;

	boolean force = false;

	int[] image_list={R.drawable.unknown_network,R.drawable.periodic_data,R.drawable.anonymize_data};
	String[] text_list={"There is not much known about Cellular Networks","This app takes study measurements and stores on a central server","sensitive data is anonymized and sent securely"};


	int i=0;

	int len = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}

		setContentView(R.layout.tour_screen);
		text = (TextView) findViewById(R.id.text);
		image = (ImageView) findViewById(R.id.image);
		
		next = (Button) findViewById(R.id.next);

		len = image_list.length;
		updateState(len);
		
		next.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				
				updateState(++i);

			}
		});


	}


	public void updateState(int i){
		if(i<0) i=0;
		if(i>len) i=len;

		else if(i==len){
			finish();
			if(!force){
				
				Intent myIntent = new Intent(this, UserFormActivity.class);
                startActivity(myIntent);
				
			}
			return;
		}
		text.setText(text_list[i]);
		image.setImageResource(image_list[i]);


	}


}