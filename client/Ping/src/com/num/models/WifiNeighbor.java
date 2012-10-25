package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.utils.SHA1Util;
import com.num.R;

public class WifiNeighbor implements MainModel,Comparable<WifiNeighbor>{
	

	String ssid = "";
	String macAddress = "";
	int signalLevel = -1;
	int frequency = -1;
	String capability = "";
	boolean isConnected = false;
	boolean isPreferred = false;

	private static String DESCRIPTION = "";

	public String getDescription() {
		return DESCRIPTION;
	}
	
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
	
	public int getSignalPercentage(){
		return Math.max(Math.min((int) (signalLevel*2.5 + 250),100),0);
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
			obj.putOpt("ssid", "" + SHA1Util.SHA1(ssid));
			obj.putOpt("macAddress", SHA1Util.SHA1(macAddress));
			obj.putOpt("signalLevel", "" + signalLevel);
			obj.putOpt("frequency", "" + frequency);
			obj.putOpt("capability", "" + capability);
			obj.putOpt("isConnected", "" + isConnected);
			obj.putOpt("isPreferred", "" + isPreferred);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Wifi Neighbor";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}
	
	public int compareTo(WifiNeighbor another) {
		if(another.getSignalPercentage()>this.getSignalPercentage()) return 1;
		return -1;
	}
	
	public int getIcon() {

		return R.drawable.usage;
	}

	
	
}
