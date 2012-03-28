package com.num.services;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.receivers.ScreenReceiver;
import com.num.tasks.MeasurementTask;
import com.num.tasks.ParameterTask;
import com.num.utils.GPSUtil;
import com.num.utils.PreferencesUtil;
import com.num.utils.StateUtil;

public class PerformanceServiceAll extends Service{

	private Context context;
	private ThreadPoolHelper serverhelper;

	private Timer updateTimer;
	
	public static String TAG = "PerformanceService-All";
	BroadcastReceiver mReceiver;
	PowerManager.WakeLock wl;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		updateTimer = new Timer("measurementTaskAll");
		context = this.getApplicationContext();
		Values session = (Values) context.getApplicationContext();
		serverhelper = new ThreadPoolHelper(1,session.THREADPOOL_KEEPALIVE_SEC);

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		mReceiver = new ScreenReceiver();
		registerReceiver(mReceiver, filter);
		
		/*PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock TAG");
		wl.acquire();*/
	}

	@Override
	public void onDestroy() {
		updateTimer.cancel();
		unregisterReceiver(mReceiver);
		serverhelper.shutdown();
		//wl.release();
		Log.v(TAG,"Destroying " + TAG);
	}

	@Override
	public void onStart(Intent intent, int startId) {

	super.onStart(intent, startId);
	runTask();
	
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		
		runTask();   
		ServiceHelper.recurringStartService(context);
		return Service.START_STICKY;
	}

	
	
	boolean doThroughput = false;
	

	private void runTask() {

		Values session = (Values) context.getApplicationContext();

		
		doThroughput=session.doThroughput();

		
		session.incrementThroughput();


		if(doThroughput){

			StateUtil stateutil = new StateUtil(context);
			boolean clear = stateutil.isNetworkClear();

			if(!clear){
				session.decrementThroughput();
				doThroughput=false;
			}
		}

		if(doThroughput){
			serverhelper.execute(new ParameterTask(context,(new Listener())));
		}
		else{
			serverhelper.execute(new MeasurementTask(context,false,false, false, new FakeListener()));
		}

		Log.i(TAG," Throughput:" + session.getThroughput());



	}


	public void onDestroyed(){
		super.onDestroy();
	}


	public class Listener extends BaseResponseListener{
		Values session = (Values) context.getApplicationContext();
		public void onComplete(String response) {
			System.out.println("throughput succeed");
			ThroughputHandler.sendEmptyMessage(0);

		}

		public void onFail(String response){
			System.out.println("throughput failed");
			session.decrementThroughput();
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
					serverhelper.execute(new MeasurementTask(context,false,true, false,new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		private Handler NoThroughputHandler = new Handler() {
			public void  handleMessage(Message msg) {
				try {
					serverhelper.execute(new MeasurementTask(context,false,false, false,new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

	}


}