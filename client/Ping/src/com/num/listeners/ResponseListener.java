package com.num.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.net.wifi.ScanResult;

import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.models.WifiNeighbor;


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

	public void onCompleteLastMile(LastMile lastMile);

	

}