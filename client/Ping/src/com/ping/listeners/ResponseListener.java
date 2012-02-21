package com.ping.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
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


public interface ResponseListener {

    public void onComplete(String response);
    
    public void onCompletePing(Ping response);
    
    public void onCompleteMeasurement(Measurement response);
    
    public void onCompleteDevice(Device response);
    
    public void onIOException(IOException e);


    public void onFileNotFoundException(FileNotFoundException e);

    public void onCompleteBattery(Battery response);
    
    public void onException(Exception e);
    
    public void onUpdateProgress(int val);

	public void onCompleteGPS(GPS gps);
	
	public void onCompleteUsage(Usage usage);
	
	public void onCompleteThroughput(Throughput throughput);

	public void makeToast(String text);

	public void onCompleteSignal(String signalStrength);

	public void onCompleteWifi(Wifi wifiList);

	public void onCompleteNetwork(Network network);

	public void onCompleteSIM(Sim sim);
	
	public void onFail(String response);
	
	public void onCompleteSummary(JSONObject Object);

	

}