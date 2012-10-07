package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class GPS implements MainModel {
	
	String latitude = ""; 
	String longitude = "";
	String altitude = "";

	private static String DESCRIPTION = "";

	public String getDescription() {
		return DESCRIPTION;
	}
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
		} catch (Exception e) {
			obj = new JSONObject();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "GPS";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Latitude",latitude));
		data.add(new Row("Longitude",longitude));
		//data.add(new Row(Double.parseDouble(longitude),Double.parseDouble(latitude)));
		//data.add(new Row("Latitude",altitude));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.gps;
	}


}
