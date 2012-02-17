package com.android.listeners;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.ScanResult;

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

}


