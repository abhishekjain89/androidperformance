package com.num.receivers;

import com.num.services.MeasurementService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, MeasurementService.class));
		 
	}


	

	public String toString(){
		return "StartIntentReceiver";
	}

}