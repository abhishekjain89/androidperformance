package com.ping.listeners;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.net.wifi.ScanResult;

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
import com.ping.models.WifiNeighbor;



public class FakeListener extends BaseResponseListener{

	public void onCompletePing(Ping response) {

	}

	public void onComplete(String response) {

	}

	public void onCompleteMeasurement(Measurement response) {
	}

	public void onCompleteDevice(Device response) {
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
	
	public void makeToast(String text) {
		// TODO Auto-generated method stub
		
	}

	public void onCompleteSignal(String signalStrength) {
		
	}
	
	public void onCompleteThroughput(Throughput throughput) {

		// TODO Auto-generated method stub
		
	}

	public void onCompleteWifi(Wifi wifi) {
		// TODO Auto-generated method stub
		
	}

	public void onCompleteNetwork(Network network) {
		// TODO Auto-generated method stub
		
	}

	public void onCompleteSIM(Sim sim) {
		// TODO Auto-generated method stub
		
	}

	public void onCompleteBattery(Battery response) {
		// TODO Auto-generated method stub
		
	}

	public void onCompleteSummary(JSONObject Object) {
		// TODO Auto-generated method stub
		
	}

}


