package com.android.models;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.R;

public class Wifi implements Model{

	public int strength;	
	public int ipAddress;
	public int speed;
	public int networkId;
	public int rssi;
	public String macAddress;
	public String ssid;				
	public String detailedInfo;
	public String units;
	public boolean isPreferred;
	public ArrayList<WifiNeighbor> neighbors;
	public ArrayList<WifiPreference> preference;

	public Wifi()
	{
		setNeighbors(new ArrayList<WifiNeighbor>());
		setPreference(new ArrayList<WifiPreference>());
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
			obj.putOpt("macAddress", "" + macAddress);
			obj.putOpt("ssid", "" + ssid);
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
			obj.putOpt("wifiPreference", prefer);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Wifi";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Your Info"));
		data.add(new Row("Hotspot",this.getSsid()));
		data.add(new Row("Status",this.getDetailedInfo()));
		data.add(new Row("Speed",this.getSpeed() + " " + this.getUnits()));
		data.add(new Row("Strength",""+this.getStrength()));
		data.add(new Row("Neighbors",""+this.getNeighbors().size()));
		data.add(new Row("Neighboring Wifis"));
		Collections.sort(this.getNeighbors());
		for(WifiNeighbor wifi: this.getNeighbors()){
			data.add(new Row(wifi.getSSID(),wifi.getSignalPercentage()));
		}
		
		return data;
	}
	
	public int getIcon() {

		return R.drawable.wifi;
	}

	
}
