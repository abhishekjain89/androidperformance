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



public class DataFormActivity extends Activity 
{

	//private TableLayout table;

	private Activity activity;

	private Values session = null;

	private Button saveButton;
	private RadioGroup rGroup;
	final RadioButton[] rb = new RadioButton[5];
	UserDataHelper userhelp;
	Boolean force = false;
	int[] limit_val= {1,0};


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		activity = this;
		userhelp = new UserDataHelper(activity);
		int old_val= userhelp.getDataEnable();

		
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}

		
		
		setContentView(R.layout.dataform_screen);


		String[] limit_text = {"yes","no"};
		
		

		
		saveButton = (Button) this.findViewById(R.id.save);
		rGroup = (RadioGroup) findViewById(R.id.radio_group);

		LinearLayout.LayoutParams rg = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,50);

		for (int i = 0; i < limit_text.length; i++) {
			RadioButton radiobutton = new RadioButton(this);
			radiobutton.setTextColor(Color.BLACK);
			radiobutton.setTextSize(18);
			radiobutton.setText(limit_text[i]);
			if(old_val == limit_val[i])
				radiobutton.setChecked(true);
			
			radiobutton.setId(i);
			rGroup.addView(radiobutton, rg);

		}



			saveButton.setOnClickListener(new OnClickListener()  {
				public void onClick(View v) {	

					int checkedRadioButton = rGroup.getCheckedRadioButtonId();

					if(checkedRadioButton<0) return;


					try{		
						userhelp.setDataEnable(limit_val[checkedRadioButton]);
					}
					catch(Exception e){
						e.printStackTrace();
					}

					finish();
					if(!force){
						Intent myIntent = new Intent(v.getContext(), AnalysisActivity.class);
						startActivity(myIntent);
					}

				}
			});


		}


}