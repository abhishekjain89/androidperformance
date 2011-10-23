package com.android.utils;

import android.location.Location;

public class GPSUtil {
	public String getGPS(Location location) {

		double lat, lng, alt;
		String gpsString;
		if (location != null)
		{
			lat = location.getLatitude();
			lng = location.getLongitude();
			alt = location.getAltitude();
			gpsString = "Latitude: " + lat + "\nLongitude: " + lng + "\nAltitude: " + alt;
		}
		else
		{
			gpsString = "No Location Found";
		}
		return gpsString;
	}
}
