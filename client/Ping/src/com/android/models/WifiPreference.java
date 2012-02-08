package com.android.models;

public class WifiPreference {

	public String id;
	public String protocols;
	public String ssid;
	public int priority;
	public int networkid;
	public int status;
	public String pairwiseCiphers;
	public String groupCiphers;
	public String authAlgorithms;
	public String bssid;
	public String keyMgmt;
	public boolean isConnected;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProtocols() {
		return protocols;
	}
	public void setProtocols(String protocols) {
		this.protocols = protocols;
	}
	public int getNetworkid() {
		return networkid;
	}
	public void setNetworkid(int networkid) {
		this.networkid = networkid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
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
