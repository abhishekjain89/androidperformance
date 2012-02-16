package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.R;

import android.content.Context;



public class Measurement implements Model{
	
	
	ArrayList<Ping> pings; 
	Device device; 
	Network network; 
	Sim sim; 
	Throughput throughput;
	GPS gps;
	String time;
	String deviceId;
	Usage usage;
	Battery battery;
	Wifi wifi;

	public Wifi getWifi() {
		return wifi;
	}

	public void setWifi(Wifi wifi) {
		this.wifi = wifi;
	}

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

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
			obj.putOpt("throughput",throughput.toJSON());
			obj.putOpt("gps",gps.toJSON());
			obj.putOpt("battery", battery.toJSON());
			obj.putOpt("usage",usage.toJSON());
			obj.putOpt("network",network.toJSON());
			obj.putOpt("sim",sim.toJSON());
			obj.putOpt("wifi", wifi.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	public String getTitle() {
		
		return "Pings";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Latency (Avg,Max,Min,Std)"));
		for(Ping p: pings){
			ArrayList<String> str = new ArrayList<String>();
			str.add(""+(int)p.measure.getAverage());
			str.add(""+(int)p.measure.getMax());
			str.add(""+(int)p.measure.getMin());
			str.add(""+(int)p.measure.getStddev());
			data.add(new Row(p.getDstIp(),str));
		}
		
		return data;
	}
	
	public int getIcon() {

		return R.drawable.battery;
	}


}
