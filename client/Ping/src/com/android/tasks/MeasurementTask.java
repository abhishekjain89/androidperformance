package com.android.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.Values;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.FakeListener;
import com.android.listeners.ResponseListener;
import com.android.models.Battery;
import com.android.models.Device;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Network;
import com.android.models.Ping;
import com.android.models.Sim;
import com.android.models.Throughput;
import com.android.models.Usage;
import com.android.models.Wifi;
import com.android.models.WifiNeighbor;
import com.android.models.WifiPreference;
import com.android.utils.GPSUtil;
import com.android.utils.GPSUtil.LocationResult;
import com.android.utils.HTTPUtil;
import com.android.utils.NeighborWifiUtil;
import com.android.utils.NeighborWifiUtil.NeighborResult;
import com.android.utils.SignalUtil;
import com.android.utils.WifiUtil;

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
	public boolean wifiRunning = false;
	public long startTime = 0;
	@Override
	public void runTask() {

		measurement = new Measurement();
		// TODO Run ping task with list of things such as ip address and number of pings	
		//android.os.Debug.startMethodTracing("lsd");

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,Values.THREADPOOL_KEEPALIVE_SEC);

		serverhelper.execute(new InstallBinariesTask(getContext(),new HashMap<String,String>(), new String[0], new FakeListener()));
		try {
			Thread.sleep(Values.SHORT_SLEEP_TIME);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(Values.SHORT_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			Log.v(this.toString(),"Installing Binaries...");
		}
		Log.v(this.toString(),"Binaries Installed");

		String[] dstIps = Values.PING_SERVERS;

		for(int i=0;i<dstIps.length;i++)
			serverhelper.execute(new PingTask(getContext(),new HashMap<String,String>(), dstIps[i], 5, new MeasurementListener()));
		serverhelper.execute(new DeviceTask(getContext(),new HashMap<String,String>(), new MeasurementListener(), measurement));
		serverhelper.execute(new UsageTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		//serverhelper.execute(new GPSTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		startTime = System.currentTimeMillis();
		gpsRunning = true;
		signalRunning = true;
		wifiRunning = true;
		WifiHandler.sendEmptyMessage(0);
		GPSHandler.sendEmptyMessage(0);
		SignalHandler.sendEmptyMessage(0);


		int total_threads = 3 + dstIps.length;
		int done_threads = 0;

		try {
			Thread.sleep(Values.NORMAL_SLEEP_TIME);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int loop_threads = serverhelper.getThreadPoolExecutor().getActiveCount();
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			int left = total_threads - done_threads - (loop_threads - serverhelper.getThreadPoolExecutor().getActiveCount());
			getResponseListener().onUpdateProgress((100*(left))/total_threads);
			Log.v(this.toString(), "left: " + left + " done: " + (total_threads - left));
		}
		done_threads+=loop_threads;


		while(gpsRunning && (System.currentTimeMillis() - startTime)<Values.GPS_TIMEOUT){
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

		while(signalRunning && (System.currentTimeMillis() - startTime)<Values.SIGNALSTRENGTH_TIMEOUT){
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}


		while(wifiRunning && (System.currentTimeMillis() - startTime)<Values.WIFI_TIMEOUT){
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

		done_threads+=1;
		getResponseListener().onUpdateProgress((100*(done_threads))/total_threads);
		if(gpsRunning){
			locationResult.gotLocation(null);
		}

		measurement.setPings(pings);

		serverhelper.execute(new ThroughputTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		try {
			Thread.sleep(Values.NORMAL_SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loop_threads = serverhelper.getThreadPoolExecutor().getActiveCount();
		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

			int left = total_threads - done_threads - (loop_threads - serverhelper.getThreadPoolExecutor().getActiveCount());
			getResponseListener().onUpdateProgress((100*(left))/total_threads);
			Log.v(this.toString(), "left: " + left + " done: " + (total_threads - left));
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		done_threads+=loop_threads;

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
		//android.os.Debug.stopMethodTracing();

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

		public void onCompleteSignal(int signalStrength) {
			signalRunning = false;
			Network network = measurement.getNetwork();
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
			measurement.setWifi(wifi);
			wifiRunning = false;
			getResponseListener().onCompleteWifi(wifi);
		}

		public void onCompleteNetwork(Network network) {
			getResponseListener().onCompleteNetwork(network);

		}

		public void onCompleteSIM(Sim sim) {
			getResponseListener().onCompleteSIM(sim);

		}

		public void onCompleteBattery(Battery response) {
			getResponseListener().onCompleteBattery(response);

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

	private Handler WifiHandler = new Handler() {
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

	public NeighborResult neighborResult = new NeighborResult(){
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

			measurement.setGps(gps);
			(new MeasurementListener()).onCompleteGPS(gps);
		}
	};


}
