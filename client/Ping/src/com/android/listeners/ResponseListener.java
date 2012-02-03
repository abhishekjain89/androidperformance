package com.android.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.android.models.Device;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Ping;


public interface ResponseListener {

    public void onComplete(String response);
    
    public void onCompletePing(Ping response);
    
    public void onCompleteMeasurement(Measurement response);
    
    public void onCompleteDevice(Device response);
    
    public void onIOException(IOException e);


    public void onFileNotFoundException(FileNotFoundException e);

    
    public void onException(Exception e);
    
    public void onUpdateProgress(int val);

	public void onCompleteGPS(GPS gps);

	public void makeToast(String text);

	public void onCompleteSignal(int signalStrength);

}