package com.android.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
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

	

}