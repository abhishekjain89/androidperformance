package com.num.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
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
import com.num.models.WifiNeighbor;
import com.num.models.WifiPreference;
import com.num.utils.GPSUtil;
import com.num.utils.HTTPUtil;
import com.num.utils.NeighborWifiUtil;
import com.num.utils.SignalUtil;
import com.num.utils.WifiUtil;
import com.num.utils.GPSUtil.LocationResult;
import com.num.utils.NeighborWifiUtil.NeighborResult;
import com.num.utils.SignalUtil.SignalResult;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class MeasurementTask extends ServerTask{
	
	ThreadPoolHelper serverhelper;
	boolean doGPS;
	boolean doThroughput;

	boolean isManual = false;

	Measurement measurement; 
	ArrayList<Ping> pings = new ArrayList<Ping>();
	public boolean gpsRunning  = false;
	public boolean signalRunning = false;
	//public boolean wifiRunning = false;
	public long startTime = 0;

	public MeasurementTask(Context context,boolean doGPS,boolean doThroughput,
			boolean isManual, ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);
		this.doGPS = doGPS;
		this.doThroughput = doThroughput;
		this.isManual = isManual;
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
		measurement.setManual(isManual);
		// TODO Run ping task with list of things such as ip address and number of pings	
		//android.os.Debug.startMethodTracing("lsd");
		Values session = this.getValues();
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(session.THREADPOOL_MAX_SIZE,session.THREADPOOL_KEEPALIVE_SEC);
		

		serverhelper.execute(new InstallBinariesTask(getContext(),new HashMap<String,String>(), new String[0], new FakeListener()));
		try {
			Thread.sleep(session.SHORT_SLEEP_TIME);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(session.SHORT_SLEEP_TIME);
			} catch (InterruptedException e) {
				this.killAll();
				return;
			}

			Log.v(this.toString(),"Installing Binaries...");
		}
		Log.v(this.toString(),"Binaries Installed");

		ArrayList<Address> dsts = session.getPingServers();

		for(Address dst : dsts)
			serverhelper.execute(new PingTask(getContext(),new HashMap<String,String>(), dst, 5, new MeasurementListener()));
		serverhelper.execute(new DeviceTask(getContext(),new HashMap<String,String>(), new MeasurementListener(), measurement));
		serverhelper.execute(new UsageTask(getContext(),new HashMap<String,String>(), doThroughput, new MeasurementListener()));
		serverhelper.execute(new BatteryTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		serverhelper.execute(new WifiTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));

		signalRunning = true;
		//wifiRunning = true;
		//WifiHandler.sendEmptyMessage(0);
		/*
		if(false){
			GPSHandler.sendEmptyMessage(0);
			gpsRunning = true;
		}
		else{
			gpsRunning = false;
		}*/
		SignalHandler.sendEmptyMessage(0);
		
		measurement.setPings(pings);
		
		startTime = System.currentTimeMillis();
		
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
		


		while((gpsRunning||signalRunning/*||wifiRunning*/) && (System.currentTimeMillis() - startTime)<session.GPS_TIMEOUT){
			try {
				Thread.sleep(session.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				return;
			}
		}
		/*
		if(gpsRunning){
			locationResult.gotLocation(null);
		}*/

		
		
		if(doThroughput){
			serverhelper.execute(new ThroughputTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		}
		else{
			new MeasurementListener().onCompleteThroughput(new Throughput());
		}
		
		try {
			Thread.sleep(session.NORMAL_SLEEP_TIME);
		} catch (InterruptedException e) {
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
		
		ArrayList<Screen> scrs = new ArrayList<Screen>();
		
		ArrayList<Screen> buffer = session.screenBuffer;
		for(Screen s: buffer)
			scrs.add(s);
		
		measurement.setScreens(scrs);
		getResponseListener().onCompleteMeasurement(measurement);
		
		String isSuccess = MeasurementHelper.sendMeasurement(getContext(), measurement);
		
		if (isManual) {
			new MeasurementListener().makeToast(isSuccess);

		}
		

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
			signalRunning = false;
			Network network = measurement.getNetwork();
			int i = 100;
			while(network == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				network = measurement.getNetwork();
				if (i-- == 0) {
					break;
				}
			}
			network.setSignalStrength("" + signalStrength);
			measurement.setNetwork(network);
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


	private Handler GPSHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				boolean gps = GPSUtil.getLocation(getContext(), locationResult);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};


	/*private Handler WifiHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				WifiUtil wifiUtil = new WifiUtil();
				Wifi wifi = wifiUtil.getWifiDetail(getContext());
				measurement.setWifi(wifi);

				NeighborWifiUtil neighborWifiUtil = new NeighborWifiUtil();
				neighborWifiUtil.getNeighborWifi(getContext(),neighborResult  );

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
*/

	private Handler SignalHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				SignalUtil.getSignal(signalResult, getContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public SignalResult signalResult = new SignalResult() { 
		public void gotSignal(String signal) {
			(new MeasurementListener()).onCompleteSignal(signal);
		}
	};
	
	/*public NeighborResult neighborResult = new NeighborResult(){
		@Override
		public void gotNeighbor(List<ScanResult> wifiList){
			Wifi wifi = measurement.getWifi();
			ArrayList<WifiNeighbor> neighbors = new ArrayList<WifiNeighbor>();
			ArrayList<WifiPreference> prefers = wifi.getPreference();
			for (int i = 0; i < wifiList.size(); i++) {
				WifiNeighbor n = new WifiNeighbor();
				String bssid = wifiList.get(i).BSSID;
				String capability = wifiList.get(i).capabilities;
				int frequency = wifiList.get(i).frequency;
				int signalLevel = wifiList.get(i).level;
				String ssid = wifiList.get(i).SSID;

				n.setCapability(capability);
				n.setMacAddress(bssid);
				n.setFrequency(frequency);
				n.setSignalLevel(signalLevel);
				n.setSSID(ssid);
				n.setPreferred(false);
				if (ssid.equalsIgnoreCase(wifi.getSsid())) {
					n.setConnected(true);
				}
				else {
					n.setConnected(false);
				}
				for (int j = 0; j < prefers.size(); j++) {
					if (ssid.equalsIgnoreCase(prefers.get(j).getSsid())) {
						n.setPreferred(true);
						break;
					}
				}
				neighbors.add(n);
			}
			wifi.setNeighbors(neighbors);	
			(new MeasurementListener()).onCompleteWifi(wifi);
		}
	};*/

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

			
			(new MeasurementListener()).onCompleteGPS(gps);
		}
	};


}
