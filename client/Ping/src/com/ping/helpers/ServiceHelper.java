package com.ping.helpers;

import java.util.Calendar;

import com.ping.services.PerformanceServiceAll;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ServiceHelper {
	
	final static String Bigtag = "ServiceHelper";
	
	private static PendingIntent pendingIntent;
	
	public static void processStartService(Context context, String tag) {
		Intent serviceIntent = new Intent(context, PerformanceServiceAll.class);
		serviceIntent.putExtras(makeBundle(2));
		
		pendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60*15);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		
		
		Log.i(Bigtag, "STARTED: " + tag);
	}
	
	
	
	public static Bundle makeBundle(int f){
		Bundle b = new Bundle();
		b.putInt("freq", f);
		
		return b;
	}
	
	public static void processStopService(Context context, String tag) {
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

		alarmManager.cancel(pendingIntent);
		
		Log.i(Bigtag, "STOPPED: " + tag);
	}
	
	public static void processRestartService(Context context, String tag){
		Log.i(Bigtag, "RESTARTING....... " + tag);
		processStopService(context, tag);
		processStartService(context, tag);
	}
}
