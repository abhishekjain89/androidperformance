package com.android.utils;

import android.location.Location;

import com.android.models.GPS;

public class GPSUtil {
	
	/**
	 * Gets gps location 
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
}
