package com.ping.services;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.ping.Session;
import com.ping.Values;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.listeners.BaseResponseListener;
import com.ping.listeners.FakeListener;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.GPS;
import com.ping.models.Measurement;
import com.ping.models.Network;
import com.ping.models.Ping;
import com.ping.models.Sim;
import com.ping.models.Throughput;
import com.ping.models.Usage;
import com.ping.models.Wifi;
import com.ping.tasks.MeasurementTask;
import com.ping.tasks.ParameterTask;
import com.ping.utils.GPSUtil;
import com.ping.utils.PreferencesUtil;
import com.ping.utils.StateUtil;

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
	boolean doGPS = false;
	boolean doThroughput = false;
	
	private void runTask() {

		

		if(gps_count==0)
			doGPS = true;
		if(throughput_count==0)
			doThroughput= true;

		gps_count+=1;
		gps_count%=4;
		throughput_count+=1;
		throughput_count%=48;


		if(doThroughput){

			StateUtil stateutil = new StateUtil(context);
			boolean clear = stateutil.isNetworkClear();

			if(!clear){
				throughput_count-=1;
				doThroughput=false;
			}
		}

		if(doThroughput){
			serverhelper.execute(new ParameterTask(context,(new Listener())));
		}
		else{
			serverhelper.execute(new MeasurementTask(context,doGPS,false, new FakeListener()));
		}

		Log.i(TAG,"GPS:"+gps_count + " Throughput:" + throughput_count);



	}


	public void onDestroyed(){
		super.onDestroy();
	}


	public class Listener extends BaseResponseListener{

		public void onComplete(String response) {
			ThroughputHandler.sendEmptyMessage(0);

		}
		
		public void onFail(String response){
			throughput_count-=1;
			doThroughput=false;
			NoThroughputHandler.sendEmptyMessage(0);
		}

		public void onCompletePing(Ping response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteMeasurement(Measurement response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteDevice(Device response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteBattery(Battery response) {
			// TODO Auto-generated method stub

		}

		public void onUpdateProgress(int val) {
			// TODO Auto-generated method stub

		}

		public void onCompleteGPS(GPS gps) {
			// TODO Auto-generated method stub

		}

		public void onCompleteUsage(Usage usage) {
			// TODO Auto-generated method stub

		}

		public void onCompleteThroughput(Throughput throughput) {
			// TODO Auto-generated method stub

		}

		public void makeToast(String text) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSignal(String signalStrength) {
			// TODO Auto-generated method stub

		}

		public void onCompleteWifi(Wifi wifiList) {
			// TODO Auto-generated method stub

		}

		public void onCompleteNetwork(Network network) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSIM(Sim sim) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSummary(JSONObject Object) {
			

		}
		
		private Handler ThroughputHandler = new Handler() {
			public void  handleMessage(Message msg) {
				try {
					serverhelper.execute(new MeasurementTask(context,doGPS,true,new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		private Handler NoThroughputHandler = new Handler() {
			public void  handleMessage(Message msg) {
				try {
					serverhelper.execute(new MeasurementTask(context,doGPS,false,new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

	}


}
