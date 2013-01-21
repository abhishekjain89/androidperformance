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
import com.num.models.Loss;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Screen;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.WarmupExperiment;
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
	Measurement measurement; 
	ThreadPoolHelper serverhelperPing;
	ThreadPoolHelper serverhelperLastMile;
	ArrayList<Ping> pings = new ArrayList<Ping>();
	ArrayList<LastMile> lastMiles = new ArrayList<LastMile>();
	LatencyDataSource dataSource = new LatencyDataSource(getContext());
	public AllPingTask(Context context, ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);

	}
	
	public void killAll(){
		try{
		serverhelperPing.shutdown();
		serverhelperLastMile.shutdown();
		}
		catch(Exception e){
			
		}
	}
	
	public void runTask() {
		
		MeasurementListener listener= new MeasurementListener();
		
		measurement = new Measurement();
		measurement.setPings(pings);
		measurement.setLastMiles(lastMiles);
		measurement.isComplete = false;
		
		Values session = this.getValues();
		ThreadPoolHelper serverhelperPing = new ThreadPoolHelper(session.THREADPOOL_MAX_SIZE,session.THREADPOOL_KEEPALIVE_SEC);
		ThreadPoolHelper serverhelperLastMile = new ThreadPoolHelper(session.THREADPOOL_MAX_SIZE,session.THREADPOOL_KEEPALIVE_SEC);
		
		ArrayList<Address> dsts = session.getPingServers();

		for(Address dst : dsts)
			if(dst.getType().equals("ping"))
				serverhelperPing.execute(new PingTask(getContext(),new HashMap<String,String>(), dst, 5,listener));
			else
				serverhelperLastMile.execute(new PingTask(getContext(),new HashMap<String,String>(), dst, 5,listener));
		
		serverhelperPing.waitOnTasks();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;

		}
		measurement.isComplete = true;
		(new MeasurementListener()).onCompleteMeasurement(measurement);


	}

	@Override
	public String toString() {
		return "Measurement Task";
	}


	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			dataSource.insert(response);
			pings.add(response);			
			onCompleteMeasurement(measurement);
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
			lastMiles.add(lastMile);
			dataSource.insert(lastMile);
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
			// TODO Auto-generated method stub
			
		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroute) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteWarmupExperiment(WarmupExperiment experiment) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteLoss(Loss loss) {
			// TODO Auto-generated method stub
			
		}
	}

}
