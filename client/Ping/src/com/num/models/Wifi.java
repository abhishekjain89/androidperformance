package com.num.models;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.utils.SHA1Util;
import com.num.R;

public class Wifi implements MainModel{

	public boolean isWifi = false;
	public int strength = -1;	
	public int ipAddress = -1;
	public int speed = -1;
	public int networkId = -1;
	public int rssi = -1;
	public String macAddress = "";
	public String ssid = "";				
	public String detailedInfo = "";
	public String units = "";
	public boolean isPreferred = false;
	public ArrayList<WifiNeighbor> neighbors;
	public ArrayList<WifiPreference> preference;

	private static String DESCRIPTION = "Details of your current and neighboring WiFi connections";

	public String getDescription() {
		return DESCRIPTION;
	}
	
	public Wifi()
	{
		setNeighbors(new ArrayList<WifiNeighbor>());
		setPreference(new ArrayList<WifiPreference>());
	}
	

	public boolean isWifi() {
		return isWifi;
	}

	public void setWifi(boolean isWifi) {
		this.isWifi = isWifi;
	}
	
	public boolean isPreferred() {
		return isPreferred;
	}
	public void setPreferred(boolean isPreferred) {
		this.isPreferred = isPreferred;
	}
	public ArrayList<WifiPreference> getPreference() {
		return preference;
	}
	public void setPreference(ArrayList<WifiPreference> preference) {
		this.preference = preference;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(int ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getNetworkId() {
		return networkId;
	}
	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getDetailedInfo() {
		return detailedInfo;
	}
	public void setDetailedInfo(String detailedInfo) {
		this.detailedInfo = detailedInfo;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public ArrayList<WifiNeighbor> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(ArrayList<WifiNeighbor> neighbors) {
		this.neighbors = neighbors;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("strength", "" + strength);
			obj.putOpt("ipAddress", "" + ipAddress);
			obj.putOpt("speed", "" + speed);
			obj.putOpt("networkId", "" + networkId);
			obj.putOpt("rssi", "" + rssi);
			obj.putOpt("macAddress", SHA1Util.SHA1(macAddress));
			obj.putOpt("ssid", "" + SHA1Util.SHA1(ssid));
			obj.putOpt("detailedInfo", "" + detailedInfo);
			obj.putOpt("units", "" + units);
			obj.putOpt("isPreferred", "" + isPreferred);
			JSONArray neighbor = new JSONArray();
			JSONArray prefer = new JSONArray();
			for (WifiNeighbor wn: neighbors) {
				neighbor.put(wn.toJSON());
			}
			for (WifiPreference wp: preference) {
				prefer.put(wp.toJSON());
			}
			obj.putOpt("wifiNeighbors", neighbor);
			//obj.putOpt("wifiPreference", prefer);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Wifi";
	}
	
	public ArrayList<Row> getDisplayData(Context context){

		ArrayList<Row> data = new ArrayList<Row>();
		
		if(this.getSpeed()<1){
			data.add(new Row("THE WIFI IS UNAVAILABLE"));
			
			return data;
		}
		data.add(new Row("YOUR INFO"));
		data.add(new Row("Hotspot",this.getSsid()));
		data.add(new Row("Status",this.getDetailedInfo()));
		data.add(new Row("Speed",this.getSpeed() + " " + this.getUnits()));
		data.add(new Row("Strength",this.getStrength()*10));
		data.add(new Row("Neighbors",""+this.getNeighbors().size()));
		data.add(new Row("NEIGHBORING WIFIS"));
		Collections.sort(this.getNeighbors());
		
		ArrayList<String> used = new ArrayList<String>();
		
		for(WifiNeighbor wifi: this.getNeighbors()){
			if(!used.contains(wifi.getSSID())){
				if(wifi.capability.length()<=1)
					data.add(new Row(wifi.getSSID(),wifi.getSignalPercentage()));
				else
					
					data.add(new Row(wifi.getSSID(),R.drawable.lock,wifi.getSignalPercentage()));
			}
				
			used.add(wifi.getSSID());
		}
		
		return data;
	}
	
	public int getIcon() {

		return R.drawable.wifi;
	}

	
}
