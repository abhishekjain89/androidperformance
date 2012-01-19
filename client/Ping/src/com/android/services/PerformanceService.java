package com.android.services;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;
import com.android.utils.PreferencesUtil;

public class PerformanceService extends Service{

	private Context context;
	private ServerHelper serverhelper;
	private Session session = null;
	private Timer updateTimer;
	public static String TAG = "PerformanceService";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		updateTimer = new Timer("measurementTask");
		context = this.getApplicationContext();
		session = (Session) (this.getApplicationContext());
		serverhelper = new ServerHelper(session);
	}

	@Override
	public void onDestroy() {
		updateTimer.cancel();
		Log.v("PerformanceService","Destroying PerformanceService");
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		int freqValue;
		try{
		freqValue = PreferencesUtil.getFrequency(this.context);
		}
		catch (Exception e){
			freqValue = 15;
		}
		System.out.println("starting service, freq set to " + freqValue);
		boolean autoUpdate = true;
		updateTimer.cancel();
		if (autoUpdate) {
			updateTimer = new Timer("measurementTask");
			updateTimer.scheduleAtFixedRate(doRefresh, 0, freqValue * 60 * 1000);
		}
		else {
			runTask();
		}
		
		
		return Service.START_STICKY;	
	}

	private TimerTask doRefresh = new TimerTask() {
		public void run() {
			runTask();
		}
	};
	
	private void runTask() {
		//MeasurementTask mt = new MeasurementTask(context,new HashMap<String,String>(), new MeasurementListener());
		serverhelper.execute(new MeasurementTask(context,new HashMap<String,String>(), new MeasurementListener()));
		
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
	
	public void onDestroyed(){
	    super.onDestroy();
	}

}
