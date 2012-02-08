package com.android.models;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.WifiConfiguration;

public class Wifi {

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

}
