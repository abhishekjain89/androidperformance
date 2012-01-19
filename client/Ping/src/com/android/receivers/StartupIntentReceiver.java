package com.android.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.android.services.PerformanceService");
		context.startService(serviceIntent); 
		Log.v("startup","Starting Intent Receiver");
		
	}
	
}
