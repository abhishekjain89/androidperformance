package com.android.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

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
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager;
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		/*
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		    	GPS gps = gpsUtil.getGps(location);
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		*/
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
