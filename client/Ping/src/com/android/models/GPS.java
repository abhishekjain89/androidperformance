package com.android.models;

public class GPS {
	
	String latitude; 
	String longitude;
	String altitude;
	static String oldLatitude;
	static String oldLongitude;
	static String oldAltitude;

	public GPS() {
		
	}
	
	public GPS(String latitude, String longitude, String altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public static String getOldLatitude() {
		return oldLatitude;
	}

	public static void setOldLatitude(String oldLatitude) {
		GPS.oldLatitude = oldLatitude;
	}

	public static String getOldLongitude() {
		return oldLongitude;
	}

	public static void setOldLongitude(String oldLongitude) {
		GPS.oldLongitude = oldLongitude;
	}

	public static String getOldAltitude() {
		return oldAltitude;
	}

	public static void setOldAltitude(String oldAltitude) {
		GPS.oldAltitude = oldAltitude;
	}
}
