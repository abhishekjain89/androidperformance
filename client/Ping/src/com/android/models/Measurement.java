package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;



public class Measurement {
	
/*     DESIRED STRUCTURE
  -----------------------*/	
	ArrayList<Ping> pings; 
	Device device; // Need
	Network network; // Need
	Sim sim; // Need
	Throughput throughput;
	GPS gps;
	String time;
	String deviceId;
/*-----------------------*/	
	

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public Sim getSim() {
		return sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	public Throughput getThroughput() {
		return throughput;
	}

	public void setThroughput(Throughput throughput) {
		this.throughput = throughput;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public ArrayList<Ping> getPings() {
		return pings;
	}

	public void setPings(ArrayList<Ping> pings) {
		this.pings = pings;
	}

	public Measurement() {
		
	}
	
	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		try {
			
			JSONArray array = new JSONArray();
			for(Ping p: pings){
				array.put(p.toJSON());
			}
			obj.putOpt("pings", array);
			obj.putOpt("deviceid", deviceId);
			obj.putOpt("time", time);	
			obj.putOpt("device",device.toJSON());
			//obj.putOpt("throughput",throughput.toJSON());
			obj.putOpt("gps",gps.toJSON());
			obj.putOpt("network",network.toJSON());
			obj.putOpt("sim",sim.toJSON());

			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	

}
