package com.ping.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.ping.Values;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.Measurement;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
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
				Thread.sleep(Values.SHORT_SLEEP_TIME);
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
	
	public String batteryStatus(int v){
		switch (v){
		case BatteryManager.BATTERY_STATUS_CHARGING: return "Charging";
		case BatteryManager.BATTERY_STATUS_DISCHARGING: return "OK";
		case BatteryManager.BATTERY_STATUS_FULL: return "Full";
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING: return "OK";		
		}
		return "Unknown";
	}
	
	public String batteryHealth(int v){
		switch (v){
		case BatteryManager.BATTERY_HEALTH_DEAD: return "Dead";
		case BatteryManager.BATTERY_HEALTH_GOOD: return "Good";
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE: return "Over Voltage";
		case BatteryManager.BATTERY_HEALTH_OVERHEAT: return "Over Heat";
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE: return "Failure";
				
		}
		return "Unknown";
	}
	
	public String batteryPlugged(int v){
		switch (v){
		case BatteryManager.BATTERY_PLUGGED_AC: return "Plugged AC";
		case BatteryManager.BATTERY_PLUGGED_USB: return "Plugged USB";
				
		}
		return "Not Plugged";
	}

}
