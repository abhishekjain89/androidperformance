package com.android.utils;

import com.android.models.Battery;
import com.android.models.Device;
import com.android.models.Measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

public class BatteryUtil {
	
	Context context=null;
	
	Battery battery = null;
	
	boolean notDone = false;
	public String getBattery(Context context,Measurement measurement){
		this.context = context;
		battery = new Battery();
		measurement.setBattery(battery);
		
		registerBatteryLevelReceiver(this.context);
		
		notDone = true;
		
		while(notDone){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		context.unregisterReceiver(battery_receiver);

		return ""; 
	}

	private BroadcastReceiver battery_receiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{	
		
			boolean isPresent = intent.getBooleanExtra("present", false);
			String technology = intent.getStringExtra("technology");
			int plugged = intent.getIntExtra("plugged", -1);
			int scale = intent.getIntExtra("scale", -1);
			int health = intent.getIntExtra("health", 0);
			int status = intent.getIntExtra("status", 0);
			int rawlevel = intent.getIntExtra("level", -1);
			int temperature = intent.getIntExtra("temperature", -1);
			int voltage = intent.getIntExtra("voltage", 0);
			int level = 0;
			
			Bundle bundle = intent.getExtras();

			Log.i("BatteryLevel", bundle.toString());

			if(isPresent)
			{
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}

			}
	
			battery.setPlugged(plugged);
			battery.setTechnology(technology);
			battery.setHealth(health);
			battery.setLevel(level);
			battery.setScale(scale);
			battery.setPresent(isPresent);
			battery.setTemperature(temperature);
			battery.setVoltage(voltage);
			battery.setStatus(status);
			
			notDone = false;
			
		}


	};


	private void registerBatteryLevelReceiver(Context context){
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

		context.registerReceiver(battery_receiver, filter);
	}

}
