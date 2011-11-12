package com.android.models;

import java.util.ArrayList;

import org.json.JSONObject;



public class Measurement {
	
	Device device;
	User user;
	ArrayList<Ping> pings;
	
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Ping> getPings() {
		return pings;
	}

	public void setPings(ArrayList<Ping> pings) {
		this.pings = pings;
	}

	public Measurement(Device device, User user, ArrayList<Ping> pings) {
		super();
		this.device = device;
		this.user = user;
		this.pings = pings;
	}

	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
