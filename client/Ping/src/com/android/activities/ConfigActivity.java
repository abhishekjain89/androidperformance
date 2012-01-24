package com.android.activities;


import com.android.R;
import com.android.helpers.ServiceHelper;
import com.android.services.PerformanceService;
import com.android.utils.PreferencesUtil;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class ConfigActivity extends Activity 
{
	
	private Button cancelButton;
	private Button saveButton;
	private Spinner freqSpinner;
	private ToggleButton on;
	private String freq[];
	//private Spinner maxRunSpinner;
	private String run[];
	
	public String serviceTag = "PerformanceService";
	
	public int settingFreq;
	public boolean settingServ;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		cancelButton=(Button)findViewById(R.id.cancel);
		saveButton=(Button)findViewById(R.id.save);
		freqSpinner=(Spinner)findViewById(R.id.spinnerfreq);
		freq=new String[4];
		on=(ToggleButton)findViewById(R.id.toggleButton);
		
		freq[0]="10 min";
		freq[1]="15 min";
		freq[2]="30 min";
		freq[3]="60 min";
		
		ArrayAdapter<String> adapterfreq = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, freq);
		freqSpinner.setAdapter(adapterfreq);
		
		loadSettings();
		
		cancelButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	
				saveSettings();
				Intent intent = new Intent();
                intent.putExtra("returnKey","test");
                setResult(RESULT_OK,intent);
				finish();
			}
		});

	}
	
	private void loadSettings(){
		
		settingFreq=PreferencesUtil.getFrequency(this);
		settingServ=PreferencesUtil.isPing(this);
		
		if (settingServ)
			on.setChecked(true);
		else
			on.setChecked(false);
		
		switch(settingFreq){
		case 10:
			freqSpinner.setSelection(0);
			break;
		case 15:
			freqSpinner.setSelection(1);
			break;
		case 30:
			freqSpinner.setSelection(2);
			break;
		case 60:
			freqSpinner.setSelection(3);
			break;
		default:
			freqSpinner.setSelection(1);
			break;
		}	
		
	}
	
	private void saveSettings(){
				
		switch(freqSpinner.getSelectedItemPosition()){
			case 0:
				settingFreq=10;
				break;
			case 1:
				settingFreq=15;
				break;
			case 2:
				settingFreq=30;
				break;
			case 3:
				settingFreq=60;
				break;
			default:
				settingFreq=15;
				break;	
		}
		
		PreferencesUtil.setData(this, settingFreq, settingServ);
		
		ServiceHelper.processStopService(this,"com.android.services.PerformanceService");
		ServiceHelper.processStartService(this,"com.android.services.PerformanceService");
	}
	
	
	    
	
}