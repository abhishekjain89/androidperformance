package com.android.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.FakeListener;
import com.android.listeners.ResponseListener;
import com.android.models.Device;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Network;
import com.android.models.Ping;
import com.android.utils.GPSUtil;
import com.android.utils.GPSUtil.LocationResult;
import com.android.utils.HTTPUtil;
import com.android.utils.SignalUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class MeasurementTask extends ServerTask{

	public MeasurementTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}
	Measurement measurement; 
	ArrayList<Ping> pings = new ArrayList<Ping>();
	public boolean gpsRunning  = false;
	public boolean signalRunning = false;
	public long startTime = 0;
	@Override
	public void runTask() {
		
		measurement = new Measurement();
		// TODO Run ping task with list of things such as ip address and number of pings	
		android.os.Debug.startMethodTracing("lsd");
        
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);
		
		serverhelper.execute(new InstallBinariesTask(getContext(),new HashMap<String,String>(), new String[0], new FakeListener()));
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
		
		String[] dstIps = {"143.215.131.173", "143.225.229.254","128.48.110.150","localhost"};
			
		for(int i=0;i<dstIps.length;i++)
			serverhelper.execute(new PingTask(getContext(),new HashMap<String,String>(), dstIps[i], 5, new MeasurementListener()));
		serverhelper.execute(new DeviceTask(getContext(),new HashMap<String,String>(), new MeasurementListener(), measurement));
		//serverhelper.execute(new GPSTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		startTime = System.currentTimeMillis();
		gpsRunning = true;
		signalRunning = true;
		GPSHandler.sendEmptyMessage(0);
		SignalHandler.sendEmptyMessage(0);
		
		
		int total_threads = 2 + dstIps.length;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			getResponseListener().onUpdateProgress(100-(serverhelper.getThreadPoolExecutor().getActiveCount()*100)/total_threads);
			Log.v(this.toString(), "left: " + serverhelper.getThreadPoolExecutor().getActiveCount() + " pings: " + pings.size());
		}
		getResponseListener().onUpdateProgress(100);
		
		
		while(gpsRunning && (System.currentTimeMillis() - startTime)<20*1000){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		
		while(signalRunning && (System.currentTimeMillis() - startTime)<10*1000){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		
		if(gpsRunning){
			locationResult.gotLocation(null);
		}
		
		measurement.setPings(pings);
		getResponseListener().onCompleteMeasurement(measurement);
		
		JSONObject object = measurement.toJSON();
		
		HTTPUtil http = new HTTPUtil();
		
		try {
			String output = http.request(this.getReqParams(), "POST", "measurement", "", object.toString());
			System.out.println(object.toString());
			System.out.println(output);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		(new MeasurementListener()).onCompleteMeasurement(measurement);
		android.os.Debug.stopMethodTracing();
		
	}

	@Override
	public String toString() {
		return "Measurement Task";
	}
	
	
	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			pings.add(response);
			getResponseListener().onCompletePing(response);
		}
		
		public void onComplete(String response) {
		
		}

		public void onCompleteMeasurement(Measurement response) {
			getResponseListener().onCompleteMeasurement(response);
		}

		public void onCompleteDevice(Device response) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateProgress(int val) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteGPS(GPS gps) {
			measurement.setGps(gps);
			
		}

		public void makeToast(String text) {
			getResponseListener().makeToast(text);
			
		}

		public void onCompleteSignal(int signalStrength) {
			signalRunning = false;
			Network network = measurement.getNetwork();
			network.setSignalStrength("" + signalStrength);
			measurement.setNetwork(network);
		}
	}
	
	
	private Handler GPSHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				boolean gps = GPSUtil.getLocation(getContext(), locationResult);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Handler SignalHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				SignalUtil.getSignal(getResponseListener(), getContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	
	
	public LocationResult locationResult = new LocationResult(){
        @Override
        public void gotLocation(final Location location){
        	GPS gps = new GPS();
            if (location != null)
            {
            	gps.setAltitude("" + location.getAltitude());
            	gps.setLatitude("" + location.getLatitude());
            	gps.setLongitude("" + location.getLongitude());
            	gpsRunning = false;
            	
            }
            else{
            	gps = new GPS("Not Found","Not Found","Not Found");        
            	gpsRunning = false;
            }
            getResponseListener().makeToast(gps.toJSON().toString());
            measurement.setGps(gps);
        }
    };
	

}
