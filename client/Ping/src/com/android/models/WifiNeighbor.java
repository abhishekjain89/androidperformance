package com.android.models;

public class WifiNeighbor {
	

	String SSID;
	String macAddress;
	int signalLevel;
	int frequency;
	String capability;
	boolean isConnected;
	boolean isPreferred;
	
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
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
}
