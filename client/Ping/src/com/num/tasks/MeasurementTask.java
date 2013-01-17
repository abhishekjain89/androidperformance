package com.num.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
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
import com.num.models.ClientLog;
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
public class MeasurementTask extends ServerTask {

	ThreadPoolHelper serverhelper;
	Values session;
	MeasurementListener listener;
	Throughput throughput;
	LatencyDataSource dataSource = new LatencyDataSource(getContext());
	Measurement measurement;
	ArrayList<Ping> pings = new ArrayList<Ping>();
	ArrayList<LastMile> lastMiles = new ArrayList<LastMile>();

	public MeasurementTask(Context context, Throughput throughput,
			boolean isManual, ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		measurement = new Measurement();
		measurement.setManual(isManual);
		this.listener = new MeasurementListener();
		session = this.getValues();
		serverhelper = new ThreadPoolHelper(session.THREADPOOL_MAX_SIZE,
				session.THREADPOOL_KEEPALIVE_SEC);
		this.throughput = throughput;
	}

	public void killAll() {
		try {
			serverhelper.shutdown();
		} catch (Exception e) {
			ClientLog.log(getContext(), e, toString());
		}
	}

	public void runTask() {

		ArrayList<Address> dsts = session.getPingServers();
		
		if(throughput!=null)
			listener.onCompleteThroughput(throughput);

		serverhelper.execute(new WarmupSequenceTask(getContext(), listener));
		serverhelper.execute(new DeviceTask(getContext(),
				new HashMap<String, String>(), listener, measurement));
		
		
		serverhelper.waitOnTasks();
		// serverhelper.execute(new InstallBinariesTask(getContext(),new
		// HashMap<String,String>(), new String[0], new FakeListener()));

		serverhelper.execute(new UsageTask(getContext(),
				new HashMap<String, String>(), listener));
		serverhelper.execute(new BatteryTask(getContext(),
				new HashMap<String, String>(), listener));
		serverhelper.execute(new SignalStrengthTask(getContext(),
				new HashMap<String, String>(), listener));
		//serverhelper.execute(new TracerouteTask(getContext(),
		//		new HashMap<String, String>(), null, 0, listener));

		for (Address dst : dsts) {
			serverhelper.execute(new PingTask(getContext(),
					new HashMap<String, String>(), dst, 5, listener));
		}

		measurement.setPings(pings);
		measurement.setLastMiles(lastMiles);

		serverhelper.waitOnTasks();

		getResponseListener().onCompleteMeasurement(measurement);

		MeasurementHelper.sendMeasurement(getContext(), measurement);

	}

	@Override
	public String toString() {
		return "Measurement Task";
	}

	private class MeasurementListener extends BaseResponseListener {

		public void onCompletePing(Ping response) {
			pings.add(response);
			dataSource.insert(response);
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

			Network network = measurement.getNetwork();
			network.setSignalStrength(signalStrength);
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

			if (wifi.isWifi()) {

				measurement.setWifi(wifi);
				getResponseListener().onCompleteWifi(wifi);
			}
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
			//

		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroute) {
			// TODO Auto-generated method stub

		}

		public void onCompleteWarmupExperiment(WarmupExperiment experiment) {
			measurement.setWarmupExperiment(experiment);

		}
	}

	/*private Handler GPSHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				boolean gps = GPSUtil.getLocation(getContext(), locationResult);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};*/

	public LocationResult locationResult = new LocationResult() {
		@Override
		public void gotLocation(final Location location) {
			GPS gps = new GPS();
			if (location != null) {
				gps.setAltitude("" + location.getAltitude());
				gps.setLatitude("" + location.getLatitude());
				gps.setLongitude("" + location.getLongitude());
				// gpsRunning = false;

			} else {
				gps = new GPS("Not Found", "Not Found", "Not Found");
				// gpsRunning = false;
			}

			(new MeasurementListener()).onCompleteGPS(gps);
		}
	};

}
