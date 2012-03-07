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

		Log.v("startup","Starting Intent Receiver");

		/* INSTALL BINARIES*/

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);

		//serverhelper.execute(new InstallBinariesTask(context,new HashMap<String,String>(), new String[0], new FakeListener()));
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			Log.v(this.toString(),"Installing Binaries...");
		}*/
		Log.v(this.toString(),"Binaries Installed");
		/*   START PERFORMANCE SERVICE */
		ServiceHelper.processStartService(context);
		 
	}


	

	public String toString(){
		return "StartIntentReceiver";
	}

}