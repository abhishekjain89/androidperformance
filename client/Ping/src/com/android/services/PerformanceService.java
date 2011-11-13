package com.android.services;

import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.R;
import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;

public class PerformanceService extends Service{

	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		runTask();
	}

	@Override
	public void onDestroy() {

	}
	
	@Override
	public void onStart(Intent intent, int startid) {

	}
	

	private void runTask() {
		serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
	}

	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
		}
		
		public void onCompleteDevice(Device response) {
		}
		
		public void onCompleteMeasurement(Measurement response) {
		}

		public void onComplete(String response) {		
		}
	}

}
