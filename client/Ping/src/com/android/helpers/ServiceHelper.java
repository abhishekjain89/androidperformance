package com.android.helpers;

import com.android.services.PerformanceServiceAll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ServiceHelper {
	
	final static String Bigtag = "ServiceHelper";
	
	public static void processStartService(Context context, String tag) {
		Intent serviceIntent = new Intent(context, PerformanceServiceAll.class);
	
	
		serviceIntent.putExtras(makeBundle(15));
		context.startService(serviceIntent);
		
		
		Log.i(Bigtag, "STARTED: " + tag);
	}
	
	
	
	public static Bundle makeBundle(int f){
		Bundle b = new Bundle();
		b.putInt("freq", f);
		
		return b;
	}
	
	public static void processStopService(Context context, String tag) {
		Intent serviceIntent = new Intent(context, PerformanceServiceAll.class);
		context.stopService(serviceIntent);
		
		Log.i(Bigtag, "STOPPED: " + tag);
	}
	
	public static void processRestartService(Context context, String tag){
		Log.i(Bigtag, "RESTARTING....... " + tag);
		processStopService(context, tag);
		processStartService(context, tag);
	}
}
