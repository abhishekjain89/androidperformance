package com.android.services;

import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;

import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;

public class PerformanceService extends Service{

	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;

	public void onCreate() {
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	

	private void runTask() {
		serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
	}
	
	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			Message msg=Message.obtain(handler, 0, response);
			handler.sendMessage(msg);		
		}

		public void onComplete(String response) {
		
		}
	}

	private Handler handler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				Ping p=(Ping)msg.obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
