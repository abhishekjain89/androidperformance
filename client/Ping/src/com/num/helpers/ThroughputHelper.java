package com.num.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import android.content.Context;

import com.num.listeners.BaseResponseListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measure;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.utils.CommandLineUtil;
import com.num.utils.ParseUtil;
import com.num.utils.ThroughputUtil;

public class ThroughputHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String throughputOutput;
	public static Throughput t = new Throughput();
	public static ResponseListener listener;
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @param responseListener 
	 * @return
	 */
	public static Throughput getThroughput(Context context, ResponseListener responseListener) {
		listener = responseListener;
		t = new Throughput();
		try {
			Link up=ThroughputUtil.uplinkmeasurement(context,new ThroughputListener());
			t.setUpLink(up);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Link down=ThroughputUtil.downlinkmeasurement(context,new ThroughputListener());
			t.setDownLink(down);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TRIGGER DB
		t.isComplete=true;
		return t;
		
	}
	


	public static class ThroughputListener extends BaseResponseListener{

		public void onCompletePing(Ping response) { }

		public void onCompleteDevice(Device response) { }

		public void onCompleteMeasurement(Measurement response) { }

		public void onCompleteOutput(MainModel model) { }

		public void onComplete(String response) { }

		public void onUpdateProgress(int val){ }

		public void onCompleteGPS(GPS response) { }

		public void makeToast(String text) { }

		public void onCompleteSignal(String signalStrength) { }
		public void onCompleteUsage(Usage response) { }

		public void onCompleteThroughput(Throughput response) { }

		public void onCompleteWifi(Wifi response) { }

		public void onCompleteBattery(Battery response) { }

		public void onCompleteNetwork(Network response) { }

		public void onCompleteSIM(Sim response) { }

		public void onCompleteSummary(JSONObject Object) { }

		public void onFail(String response) { }

		public void onCompleteLastMile(LastMile lastMile) { }

		public void onUpdateUpLink(Link link) {
			t.setUpLink(link);
			listener.onUpdateThroughput(t);
		}

		public void onUpdateDownLink(Link link) {
			t.setDownLink(link);
			listener.onUpdateThroughput(t);
			
		}

		public void onUpdateThroughput(Throughput throughput) {
			listener.onUpdateThroughput(throughput);
			
		}

		public void onCompleteTraceroute(Traceroute traceroute) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroute) {
			// TODO Auto-generated method stub
			
		}
	}



}
