package com.android.activities;


import com.android.R;
import android.app.Activity;
import android.content.Intent;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		cancelButton=(Button)findViewById(R.id.cancel);
		saveButton=(Button)findViewById(R.id.save);
		freqSpinner=(Spinner)findViewById(R.id.spinnerfreq);
		freq=new String[4];
		on=(ToggleButton)findViewById(R.id.toggleButton);
		//maxRunSpinner=(Spinner)findViewById(R.id.spinnermaxrun);
		//run=new String[30];
		
		freq[0]="10 min";
		freq[1]="15 min";
		freq[2]="30 min";
		freq[3]="60 min";
		
		/*for (int i=0; i<24; i++)
			run[i]=String.valueOf(i+1);*/
		
		ArrayAdapter<String> adapterfreq = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, freq);
		freqSpinner.setAdapter(adapterfreq);
		freqSpinner.setSelection(1);
		
		on.setChecked(true);
		
		//ArrayAdapter<String> adapterrun = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, run);
		//maxRunSpinner.setAdapter(adapterrun);
		//maxRunSpinner.setSelection(0);
		
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
	
	private void saveSettings(){
		freqSpinner.getSelectedItem();
		on.isChecked();
		//maxRunSpinner.getSelectedItem();
		
	}
}