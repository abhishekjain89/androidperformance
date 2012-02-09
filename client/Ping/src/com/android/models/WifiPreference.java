package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("id", id);
			obj.putOpt("protocols", protocols);
			obj.putOpt("ssid", ssid);
			obj.putOpt("priority", priority);
			obj.putOpt("networkid", networkid);
			obj.putOpt("status", status);
			obj.putOpt("pairwiseCiphers", pairwiseCiphers);
			obj.putOpt("groupCiphers", groupCiphers);
			obj.putOpt("authAlgorithms", authAlgorithms);
			obj.putOpt("bssid", bssid);
			obj.putOpt("keyMgmt", keyMgmt);
			obj.putOpt("isConnected", isConnected);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}