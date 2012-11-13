package com.num.helpers;

import java.util.Calendar;

import com.num.Values;
import com.num.receivers.ScreenReceiver;
import com.num.services.MeasurementService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

public class ServiceHelper {
	
	final static String Bigtag = "ServiceHelper";
	
	private static PendingIntent pendingIntent;

	public static Values values;
	
	public static void processStartService(Context context) {
		startService(context, 5);
	}
	
	public static void recurringStartService(Context context) {
		startService(context, Values.FREQUENCY_SECS);        
	}
	
	public static void startService(Context context,int seconds) {
		Intent serviceIntent = new Intent(context, MeasurementService.class);
				
		pendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, seconds);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		
		Log.i(Bigtag, "STARTED service");
	}
	
	public static void processStopService(Context context) {
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		
		try{
		alarmManager.cancel(pendingIntent);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Log.i(Bigtag, "STOPPED: service");
	}
	
	public static void processRestartService(Context context){
		Log.i(Bigtag, "RESTARTING....... service");
		
		processStopService(context);
		processStartService(context);
	}
}
