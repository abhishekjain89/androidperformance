package com.android.models;

public class Network {

	String networkCountry;
	String networkOperatorId;
	String networkName;
	String networkType;
	String connectionType;
	String mobileNetworkInfo;
	String wifiState;
	String cellId;
	String cellLac;	
	String dataState;
	String dataActivity;
	
	public String getNetworkCountry() {
		return networkCountry;
	}
	public void setNetworkCountry(String networkCountry) {
		this.networkCountry = networkCountry;
	}
	public String getNetworkOperatorId() {
		return networkOperatorId;
	}
	public void setNetworkOperatorId(String networkOperatorId) {
		this.networkOperatorId = networkOperatorId;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public String getMobileNetworkInfo() {
		return mobileNetworkInfo;
	}
	public void setMobileNetworkInfo(String mobileNetworkInfo) {
		this.mobileNetworkInfo = mobileNetworkInfo;
	}
	public String getWifiState() {
		return wifiState;
	}
	public void setWifiState(String wifiState) {
		this.wifiState = wifiState;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getCellLac() {
		return cellLac;
	}
	public void setCellLac(String cellLac) {
		this.cellLac = cellLac;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String getDataActivity() {
		return dataActivity;
	}
	public void setDataActivity(String dataActivity) {
		this.dataActivity = dataActivity;
	}
	
}
