package com.android.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.android.models.GPS;
import com.android.utils.GPSUtil;

public class GPSHelper {

	public GPSUtil gpsUtil;
	public String gpsOutput;
	
	/**
	 * Gets GPS data for location by calling gps util
	 * @param context
	 * @return
	 */
	public String runGPS(Context context) {
		LocationManager locationManager;
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		gpsUtil = new GPSUtil();
		String gps = gpsUtil.getGpsString(location);
		this.setGpsOutput(gps);
		return gps;
	}
	
	public GPS getGps(Context context) {
		LocationManager locationManager;
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		gpsUtil = new GPSUtil();
		GPS gps = gpsUtil.getGps(location);
		return gps;
	}

	public String getGpsOutput() {
		return gpsOutput;
	}

	public void setGpsOutput(String gpsOutput) {
		this.gpsOutput = gpsOutput;
	}
	
}
