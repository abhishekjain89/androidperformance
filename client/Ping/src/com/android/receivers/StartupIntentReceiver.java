package com.android.receivers;

import java.util.HashMap;

import com.android.helpers.ServiceHelper;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.FakeListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.tasks.DeviceTask;
import com.android.tasks.InstallBinariesTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.v("startup","Starting Intent Receiver");

		/* INSTALL BINARIES*/

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		serverhelper.execute(new InstallBinariesTask(context,new HashMap<String,String>(), new String[0], new FakeListener()));
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			Log.v(this.toString(),"Installing Binaries...");
		}
		Log.v(this.toString(),"Binaries Installed");
		/*   START PERFORMANCE SERVICE */
		ServiceHelper.processStartService(context,"on startup");
		 
	}


	

	public String toString(){
		return "StartIntentReceiver";
	}

}