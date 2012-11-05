package com.num.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.num.Values;
import com.num.database.datasource.LatencyDataSource;
import com.num.helpers.MeasurementHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Screen;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.Wifi;


/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class AllTraceroutesTask extends ServerTask{
	Measurement measurement; 
	ThreadPoolHelper serverhelperTraceroute;
	int startindex;
	int endindex;
	Address dst;
	Traceroute traceroute;
	
	public AllTraceroutesTask(Context context, Map<String, String> reqParams,
			Address dst, int startindex, int endindex, ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);
		this.startindex = startindex;
		this.endindex = endindex;
		this.dst = dst;
		traceroute = new Traceroute(startindex, endindex);
	}
	public AllTraceroutesTask(Context context, Map<String, String> reqParams,
			Address dst,  int endindex, ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);
		this.startindex = 2;
		this.endindex = endindex;
		this.dst = dst;
		traceroute = new Traceroute(startindex, endindex);
	}
	
	
	public void killAll(){
		try{
		serverhelperTraceroute.shutdown();
		}
		catch(Exception e){
			
		}
	}
	
	public void runTask() {
		
		MeasurementListener listener= new MeasurementListener();
		
		Values session = this.getValues();
		ThreadPoolHelper serverhelperTraceroute = new ThreadPoolHelper(20,session.THREADPOOL_KEEPALIVE_SEC);
		
		for(int i = 2; i < 20; i++)
		{
			serverhelperTraceroute.execute(new TracerouteTask(getContext(),new HashMap<String,String>(),dst, i ,listener));
		}
			
		serverhelperTraceroute.waitOnTasks();
				
		(new MeasurementListener()).onCompleteTraceroute(traceroute);
		
		


	}

	@Override
	public String toString() {
		return "All Traceroute Task";
	}


	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			
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

		public void onCompleteLastMile(LastMile lastMile) {
			
		}

		public void onUpdateUpLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateDownLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateThroughput(Throughput throughput) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteTraceroute(Traceroute traceroute) {
			getResponseListener().onCompleteTraceroute(traceroute);
			
		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroutehop) {
			traceroute.addToList(traceroutehop);
			getResponseListener().onCompleteTraceroute(traceroute);
			
			
		}
	}

}
