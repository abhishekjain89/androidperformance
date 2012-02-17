package com.android.activities;


import com.android.R;
import com.android.Values;
import com.android.helpers.ServiceHelper;
import com.android.services.PerformanceServiceAll;
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
		freq=new String[Values.SERVICE_FREQUENCY_MINS.length];
		on=(ToggleButton)findViewById(R.id.toggleButton);
		
		for(int i=0;i<freq.length;i++){
			freq[i]=Values.SERVICE_FREQUENCY_MINS[i] + " minutes";
		}
	
		ArrayAdapter<String> adapterfreq = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, freq);
		adapterfreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
		
		
		int[] freq_times = Values.SERVICE_FREQUENCY_MINS;
		
		freqSpinner.setSelection(0);
		
		for(int i=0;i<freq_times.length;i++)
		{
			if(freq_times[i]==settingFreq){
				freqSpinner.setSelection(i);
				break;
			}
		}
		
	}
	
	private void saveSettings(){
				
		if(freqSpinner.getSelectedItemPosition()>=Values.SERVICE_FREQUENCY_MINS.length){
			settingFreq=Values.SERVICE_DEFAULT_FREQUENCY_MINS;
		}
		else{
			int pos = freqSpinner.getSelectedItemPosition();
			settingFreq = Values.SERVICE_FREQUENCY_MINS[pos];
		}
		
		PreferencesUtil.setData(this, settingFreq, settingServ);
		
		ServiceHelper.processStopService(this,"com.android.services.PerformanceService");
		ServiceHelper.processStartService(this,"com.android.services.PerformanceService");
	}
	
	
	    
	
}