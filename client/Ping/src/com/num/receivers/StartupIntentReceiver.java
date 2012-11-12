package com.num.receivers;

import java.util.HashMap;

import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.Device;
import com.num.models.Measurement;
import com.num.models.Ping;
import com.num.tasks.DeviceTask;
import com.num.tasks.InstallBinariesTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ServiceHelper.processStartService(context);
		 
	}


	

	public String toString(){
		return "StartIntentReceiver";
	}

}