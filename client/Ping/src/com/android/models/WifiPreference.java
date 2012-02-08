package com.android.models;

public class WifiPreference {

	public String id;
	public String protocols;
	public String ssid;
	public String priority;
	public String pairwiseCiphers;
	public String groupCiphers;
	public String authAlgorithms;
	public String bssid;
	public String keyMgmt;
	
	public boolean isConnected;
	
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPairwiseCiphers() {
		return pairwiseCiphers;
	}
	public void setPairwiseCiphers(String pairwiseCiphers) {
		this.pairwiseCiphers = pairwiseCiphers;
	}
	public String getGroupCiphers() {
		return groupCiphers;
	}
	public void setGroupCiphers(String groupCiphers) {
		this.groupCiphers = groupCiphers;
	}
	public String getAuthAlgorithms() {
		return authAlgorithms;
	}
	public void setAuthAlgorithms(String authAlgorithms) {
		this.authAlgorithms = authAlgorithms;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getKeyMgmt() {
		return keyMgmt;
	}
	public void setKeyMgmt(String keyMgmt) {
		this.keyMgmt = keyMgmt;
	}

}
