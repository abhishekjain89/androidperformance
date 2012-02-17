package com.android.helpers;

import com.android.services.PerformanceService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ServiceHelper {
	
	final static String Bigtag = "ServiceHelper";
	
	public static void processStartService(Context context, String tag) {
		Intent serviceIntent = new Intent(context, PerformanceService.class);
	
	
		serviceIntent.putExtras(makeBundle(91,true,true));
		context.startService(serviceIntent);		
		Log.i(Bigtag, "STARTED: " + tag);
	}
	
	public static Bundle makeBundle(int f,boolean one,boolean two){
		Bundle b = new Bundle();
		b.putInt("freq", f);
		b.putBoolean("gps", one);
		b.putBoolean("throughput", two);
		return b;
	}
	
	public static void processStopService(Context context, String tag) {
		Intent serviceIntent = new Intent(context, PerformanceService.class);
		//serviceIntent.setAction(tag);
		context.stopService(serviceIntent);
		Log.i(Bigtag, "STOPPED: " + tag);
	}
	
	public static void processRestartService(Context context, String tag){
		Log.i(Bigtag, "RESTARTING....... " + tag);
		processStopService(context, tag);
		processStartService(context, tag);
	}
}
