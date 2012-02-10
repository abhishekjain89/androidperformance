package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class WifiNeighbor implements Model{
	

	String ssid;
	String macAddress;
	int signalLevel;
	int frequency;
	String capability;
	boolean isConnected;
	boolean isPreferred;
	
	public String getSSID() {
		return ssid;
	}
	public void setSSID(String sSID) {
		ssid = sSID;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public int getSignalLevel() {
		return signalLevel;
	}
	public void setSignalLevel(int signalLevel) {
		this.signalLevel = signalLevel;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getCapability() {
		return capability;
	}
	public void setCapability(String capability) {
		this.capability = capability;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public boolean isPreferred() {
		return isPreferred;
	}
	public void setPreferred(boolean isPreferred) {
		this.isPreferred = isPreferred;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("ssid", "" + ssid);
			obj.putOpt("macAddress", "" + macAddress);
			obj.putOpt("signalLevel", "" + signalLevel);
			obj.putOpt("frequency", "" + frequency);
			obj.putOpt("capability", "" + capability);
			obj.putOpt("isConnected", "" + isConnected);
			obj.putOpt("isPreferred", "" + isPreferred);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
