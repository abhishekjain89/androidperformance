package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GPS implements Model {
	
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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			obj.putOpt("latitude", latitude);
			obj.putOpt("longitude", longitude);
			obj.putOpt("altitude", altitude);		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "GPS";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Latitude",latitude));
		data.add(new Row("Longitude",longitude));
		//data.add(new Row("Latitude",altitude));
		return data;
	}


}
