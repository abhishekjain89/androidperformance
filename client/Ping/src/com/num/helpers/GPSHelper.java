package com.num.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.num.models.GPS;
import com.num.utils.GPSUtil;
import com.num.utils.GPSUtil.LocationResult;

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

		String gps = "";//gpsUtil.getGpsString(location);
		this.setGpsOutput(gps);
		return gps;
	}
	
	public GPS getGps(Context context) {
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager;
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		gpsUtil = new GPSUtil();
		GPS gps = null; //gpsUtil.getGps(location);
		return gps;
	}

    private void searchLocation(GPSUtil loc, Context context) {
        loc.getLocation(context, locationResult);
    }


    public LocationResult locationResult = new LocationResult(){
        @Override
        public void gotLocation(final Location location){
            if (location != null)
            {
            	// Save to GPS location
            }
            
        }
    };
	
	public String getGpsOutput() {
		return gpsOutput;
	}

	public void setGpsOutput(String gpsOutput) {
		this.gpsOutput = gpsOutput;
	}
	
}
