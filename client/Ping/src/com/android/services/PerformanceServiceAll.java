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

public class PerformanceServiceAll extends Service{

	private Context context;
	private ThreadPoolHelper serverhelper;
	
	private Timer updateTimer;
	private int gps_count;
	private int throughput_count;
	public static String TAG = "PerformanceService-All";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		
		updateTimer = new Timer("measurementTaskAll");
		context = this.getApplicationContext();
		gps_count = 0;
		throughput_count=0;
		serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,Values.THREADPOOL_KEEPALIVE_SEC);
	}

	@Override
	public void onDestroy() {
		updateTimer.cancel();
		
		serverhelper.shutdown();
		Log.v(TAG,"Destroying " + TAG);
	}
	
	
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		int freqValue;
		Bundle b = intent.getExtras();
		
		freqValue = (Integer)b.get("freq");
		
		System.out.println("starting service, freq set to " + freqValue);
		 if (doRefresh != null) {
				doRefresh.cancel();
			}
		doRefresh = new myTimerTask();
		
		boolean autoUpdate = true;
		updateTimer.cancel();
		if (autoUpdate) {
			updateTimer = new Timer("measurementTaskAll");
			updateTimer.scheduleAtFixedRate(doRefresh, 0, 720 * Values.ONE_MINUTE_TIME);
		
			
			
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
		
		boolean doGPS = false;
		boolean doThroughput = false;
		
		if(gps_count==0)
			doGPS = true;
		if(throughput_count==0)
			doThroughput= true;
		
		gps_count+=1;
		gps_count%=4;
		throughput_count+=1;
		throughput_count%=48;
		Log.i(TAG,"GPS:"+gps_count + " Throughput:" + throughput_count);
		
		serverhelper.execute(new MeasurementTask(context,doGPS,doThroughput, new FakeListener()));
		
	}
	
	
	public void onDestroyed(){
	    super.onDestroy();
	}
	

}
