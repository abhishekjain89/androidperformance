package com.android.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.android.models.Ping;


public interface ResponseListener {

    public void onComplete(String response);
    
    public void onCompletePing(Ping response);
    
    public void onIOException(IOException e);


    public void onFileNotFoundException(FileNotFoundException e);

    
    public void onException(Exception e);


}