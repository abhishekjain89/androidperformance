package com.android.services;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.android.Session;
import com.android.Values;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.FakeListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;
import com.android.utils.PreferencesUtil;

public class PerformanceService extends Service{

	private Context context;
	private ThreadPoolHelper serverhelper;
	private boolean doGPS;
	private boolean doThroughput;
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
		
		serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,Values.THREADPOOL_KEEPALIVE_SEC);
	}

	@Override
	public void onDestroy() {
		updateTimer.cancel();
		serverhelper.shutdown();
		Log.v("PerformanceService","Destroying PerformanceService");
	}
	
	
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		int freqValue;
		Bundle b = intent.getExtras();
		
		freqValue = (Integer)b.get("freq");
		this.doGPS = b.getBoolean("gps");
		this.doThroughput = b.getBoolean("throughput");
		
		freqValue = Values.SERVICE_DEFAULT_FREQUENCY_MINS;
		
		System.out.println("starting service, freq set to " + freqValue);
		 if (doRefresh != null) {
				doRefresh.cancel();
			}
		doRefresh = new myTimerTask();
		boolean autoUpdate = true;
		updateTimer.cancel();
		if (autoUpdate) {
			updateTimer = new Timer("measurementTask");
			updateTimer.scheduleAtFixedRate(doRefresh, 0, freqValue * Values.ONE_MINUTE_TIME);
			
		}
		else {
			runTask();
		}
		
		
		return Service.START_STICKY;	
	}

	
	  private Handler handler = new Handler();
		myTimerTask doRefresh = null;
		
		public class myTimerTask extends TimerTask {
			private Runnable runnable = new Runnable() {
				public void run() {
					try {
						runTask();
					} catch(Exception e) {
						Log.e(">>>> Error executing runTaask ( PerformanceService ): " , 
							e.getMessage());
					}
				}
			};
			
			public void run() {
				handler.post(runnable);
			}
		}
	
	private void runTask() {
		//MeasurementTask mt = new MeasurementTask(context,new HashMap<String,String>(), new MeasurementListener());
		serverhelper.execute(new MeasurementTask(context,doGPS,doThroughput, new FakeListener()));
		
	}
	
	
	public void onDestroyed(){
	    super.onDestroy();
	}
	

}
