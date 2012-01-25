package com.android.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.helpers.DeviceHelper;
import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.utils.GPSUtil.LocationResult;
import com.android.utils.GPSUtil;
import com.android.utils.HTTPUtil;



public class GPSTask extends ServerTask{
	
	ResponseListener responseListener;
	static boolean isRunning = false;
	
	public GPSTask(Context context, Map<String, String> reqParams,ResponseListener listener) {
		super(context, reqParams, listener);
		
	}

	@Override
	public void runTask() {
		isRunning = true;
		boolean gps = GPSUtil.getLocation(getContext(), locationResult);
		this.responseListener = this.getResponseListener();
		while(isRunning){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}


    public LocationResult locationResult = new LocationResult(){
        @Override
        public void gotLocation(final Location location){
        	GPS gps = new GPS();
            if (location != null)
            {
            	gps.setAltitude("" + location.getAltitude());
            	gps.setLatitude("" + location.getLatitude());
            	gps.setLongitude("" + location.getLongitude());
            	isRunning = false;
        		responseListener.onCompleteGPS(gps);
            }
            isRunning = false;
        }
    };
	
	@Override
	public String toString() {
		return "GPS Task";
	}
	

}
