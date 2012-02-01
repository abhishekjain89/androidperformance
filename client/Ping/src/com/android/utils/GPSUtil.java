package com.android.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.models.GPS;
import com.android.models.Measurement;

public class GPSUtil {
	
	/**
	 * Gets ngps location 
	 * @param location
	 * @return 
	 */
	public String getGpsString(Location location) {

		double lat, lng, alt;
		String gpsString;
		GPS gps = new GPS();
		if (location != null)
		{
			lat = location.getLatitude();
			lng = location.getLongitude();
			alt = location.getAltitude();

			gps.setLatitude("" + lat);
			gps.setLongitude("" + lng);
			gps.setAltitude("" + alt);

			gpsString = "Latitude: " + lat + "\nLongitude: " + lng + "\nAltitude: " + alt;
		}
		else
		{
			gpsString = "No Location Found";
		}
		return gpsString;
	}
	
	public GPS getGps(Location location) {
		double lat, lng, alt;
		GPS gps = new GPS();
		if (location != null)
		{
			lat = location.getLatitude();
			lng = location.getLongitude();
			alt = location.getAltitude();

			gps.setLatitude("" + lat);
			gps.setLongitude("" + lng);
			gps.setAltitude("" + alt);
		}
		else
		{
			gps.setLatitude("Not Found");
			gps.setLongitude("Not Found");
			gps.setAltitude("Not Found");
		}
		return gps;
	}
	
    static Timer timer;
    static LocationManager locationManager;
    static LocationResult locationResult;
    static boolean gps_enabled = false;
    static boolean network_enabled = false; 
    
    
    public static boolean getLocation(Context context, LocationResult result)
    {
    	
        // Use LocationResult callback class to pass location value from GPSUtil to user code.
        locationResult = result;
        if(locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Exceptions will be thrown if provider is not permitted.
        try{gps_enabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        // Don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled) {
            return false;
        }

        if(gps_enabled) {
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1, locationListenerGps);
        }
        if(network_enabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListenerNetwork); 
        }
        timer = new Timer();
        timer.schedule(new GetLastLocation(), 20000);
        
        return true;
    }
    

    static LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {        	
        }
        public void onProviderEnabled(String provider) {	
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    static LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {        	
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    static class GetLastLocation extends TimerTask {
        @Override
        public void run() {
        	
             locationManager.removeUpdates(locationListenerGps);
             locationManager.removeUpdates(locationListenerNetwork);

             Location net_loc = null;
             Location gps_loc = null;

             if(gps_enabled) {
                 gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             }
             if(network_enabled) {
                 net_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             }
             
             //if there are both values use the latest one
             if (gps_loc != null && net_loc != null) {
                 if(gps_loc.getTime()>net_loc.getTime()) {
                     locationResult.gotLocation(gps_loc);
                 }
                 else {
                     locationResult.gotLocation(net_loc);
                 }
                 return;
             }

             if(gps_loc != null) {
                 locationResult.gotLocation(gps_loc);
                 return;
             }
             if(net_loc != null) { 
                 locationResult.gotLocation(net_loc);
                 return;
             }
             locationResult.gotLocation(null);
        }
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
