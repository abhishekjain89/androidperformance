package com.num.helpers;

import java.util.Calendar;

import com.num.Values;
import com.num.receivers.ScreenReceiver;
import com.num.services.PerformanceServiceAll;

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
	
	static PowerManager.WakeLock wl;

	public static Values values;
	
	public static void processStartService(Context context) {
		/*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock TAG");
		wl.acquire();*/
		
		recurringStartService(context);
	}
	
	public static void recurringStartService(Context context) {
		Intent serviceIntent = new Intent(context, PerformanceServiceAll.class);
		serviceIntent.putExtras(makeBundle(2));
		
		pendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		
		values = (Values) context.getApplicationContext();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, values.FREQUENCY_SECS);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		
		
		Log.i(Bigtag, "STARTED service");
	}
	
	
	
	public static Bundle makeBundle(int f){
		Bundle b = new Bundle();
		b.putInt("freq", f);
		
		return b;
	}
	
	public static void processStopService(Context context) {
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

		alarmManager.cancel(pendingIntent);

		/*if(wl!=null)
			if(wl.isHeld())
				wl.release();*/
		Log.i(Bigtag, "STOPPED: service");
	}
	
	public static void processRestartService(Context context){
		Log.i(Bigtag, "RESTARTING....... service");
		processStopService(context);
		processStartService(context);
	}
}
