package com.num.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.util.Log;


/**
 * Skeleton base class for RequestListeners, providing default error 
 * handling. Applications should handle these error conditions.
 *
 */
public abstract class BaseResponseListener implements ResponseListener {

    public void onFileNotFoundException(FileNotFoundException e) {
    	e.printStackTrace();
        Log.e("ID-Request", e.getMessage());
        
    }

    public void onIOException(IOException e) {
    	e.printStackTrace();
        Log.e("ID-Request", e.getMessage());
        
    }

    public void onMalformedURLException(MalformedURLException e) {
    	 e.printStackTrace();
        Log.e("ID-Request", e.getMessage());
       
    }
    
    public void onException(Exception e) {
    	e.printStackTrace();
        Log.e("ID-Request", e.getMessage());
 
    }
    
    public void JSONException(String s) {
    	Log.e("ID-Request", "JSON error");
 
    }
    
    public void XMLException(String s) {
    	Log.e("ID-Request", "XML error");
 
    }


    
}