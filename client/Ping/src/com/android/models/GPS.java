package com.android.models;

public class GPS {
	
	String latitude; 
	String longitude;
	String altitude;

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

}
