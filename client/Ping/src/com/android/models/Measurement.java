package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Measurement {
	
	Info device;
	Device user;
	ArrayList<Ping> pings;
	
	
	public Info getDevice() {
		return device;
	}

	public void setDevice(Info device) {
		this.device = device;
	}

	public Device getUser() {
		return user;
	}

	public void setUser(Device user) {
		this.user = user;
	}

	public ArrayList<Ping> getPings() {
		return pings;
	}

	public void setPings(ArrayList<Ping> pings) {
		this.pings = pings;
	}

	public Measurement(Info device, Device user, ArrayList<Ping> pings) {
		super();
		this.device = device;
		this.user = user;
		this.pings = pings;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("device", null);
			obj.put("user", user.toJSON());
			
			JSONArray array = new JSONArray();
			
			for(Ping p: pings){
				array.put(p.toJSON());
			}
			
			obj.put("pings", array);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	

}
