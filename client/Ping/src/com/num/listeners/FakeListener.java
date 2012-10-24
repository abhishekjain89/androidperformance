package com.num.listeners;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.net.wifi.ScanResult;

import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.models.WifiNeighbor;



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
	
	public void onFail(String response){
		
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

	public void onCompleteLastMile(LastMile lastMile) {
		// TODO Auto-generated method stub
		
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

}


