package com.num.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.num.Values;
import com.num.helpers.MeasurementHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Screen;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.utils.GPSUtil;
import com.num.utils.GPSUtil.LocationResult;
import com.num.utils.SignalUtil;
import com.num.utils.SignalUtil.SignalResult;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class AllPingTask extends ServerTask{
	
	ThreadPoolHelper serverhelper;

	Measurement measurement; 
	ArrayList<Ping> pings = new ArrayList<Ping>();
	
	public AllPingTask(Context context, ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(getValues().THREADPOOL_MAX_SIZE,getValues().THREADPOOL_KEEPALIVE_SEC);
	}
	
	public void killAll(){
		try{
		serverhelper.shutdown();
		}
		catch(Exception e){
			
		}
	}
	
	public void runTask() {

		measurement = new Measurement();
		// TODO Run ping task with list of things such as ip address and number of pings	
		//android.os.Debug.startMethodTracing("lsd");
		Values session = this.getValues();
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(session.THREADPOOL_MAX_SIZE,session.THREADPOOL_KEEPALIVE_SEC);
		
		ArrayList<Address> dsts = session.getPingServers();

		for(Address dst : dsts)
			serverhelper.execute(new PingTask(getContext(),new HashMap<String,String>(), dst, 5, "ping", new MeasurementListener()));
		
		try {
			Thread.sleep(session.NORMAL_SLEEP_TIME);
		} catch (InterruptedException e1) {
			this.killAll();
			return;
		}
		
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(session.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				this.killAll();
				return;	
			}
		}
		
	
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;

		}
		measurement.setPings(pings);
		getResponseListener().onCompleteMeasurement(measurement);


	}

	@Override
	public String toString() {
		return "Measurement Task";
	}


	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			pings.add(response);
			
		}

		public void onComplete(String response) {

		} 

		public void onCompleteMeasurement(Measurement response) {
			getResponseListener().onCompleteMeasurement(response);
		}

		public void onCompleteDevice(Device response) {
			getResponseListener().onCompleteDevice(response);

		}
 
		public void onUpdateProgress(int val) {
			// TODO Auto-generated method stub

		}

		public void onCompleteGPS(GPS gps) {
			measurement.setGps(gps);
			getResponseListener().onCompleteGPS(gps);

		}

		public void makeToast(String text) {
			getResponseListener().makeToast(text);

		}

		public void onCompleteSignal(String signalStrength) {
		}
		public void onCompleteUsage(Usage usage) {
			measurement.setUsage(usage);
			getResponseListener().onCompleteUsage(usage);

		}

		public void onCompleteThroughput(Throughput throughput) {
			measurement.setThroughput(throughput);
			getResponseListener().onCompleteThroughput(throughput);


		}

		public void onCompleteWifi(Wifi wifi) {		
			//if (wifiRunning)
			//{
			//	wifiRunning = false;
			measurement.setWifi(wifi);
			getResponseListener().onCompleteWifi(wifi);
			//}
		}

		public void onCompleteNetwork(Network network) {
			getResponseListener().onCompleteNetwork(network);

		}

		public void onCompleteSIM(Sim sim) {
			getResponseListener().onCompleteSIM(sim);

		}

		public void onCompleteBattery(Battery response) {
			measurement.setBattery(response);
			getResponseListener().onCompleteBattery(response);

		}

		public void onCompleteSummary(JSONObject Object) {
			// TODO Auto-generated method stub
			
		}

		public void onFail(String response) {
			// TODO Auto-generated method stub
			
		}
	}

}
