package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Measurement {
	
	//Info info;
	
	ArrayList<Ping> pings;
	

	// Not used at the moment
	String phoneDetail;
	String networkDetail;	
	String deviceId;
	
	String time;

	String phoneNumber;
	String softwareVersion;
	String networkCountry;
	String networkOperatorId;
	String networkName;
	String networkType;
	String simNetworkCountry;
	String simState;
	String simOperatorName;
	String simOperatorCode;
	String simSerialNumber;
	String connectionType;
	//String mobileNetworkState;
	//String mobileNetworkDetailedState;
	String mobileNetworkInfo;

	String wifiState;
	String longitude;
	String latitude;
	String altitude;
	String cellId;
	String cellLac;
	

	public String getMobileNetworkInfo() {
		return mobileNetworkInfo;
	}

	public void setMobileNetworkInfo(String mobileNetworkInfo) {
		this.mobileNetworkInfo = mobileNetworkInfo;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	
	
	public String getPhoneType() {
		return phoneNumber;
	}

	public void setPhoneType(String phoneType) {
		this.phoneNumber = phoneType;
	}

	public String getPhoneDetail() {
		return phoneDetail;
	}

	public void setPhoneDetail(String phoneDetail) {
		this.phoneDetail = phoneDetail;
	}

	public String getNetworkDetail() {
		return networkDetail;
	}

	public void setNetworkDetail(String networkDetail) {
		this.networkDetail = networkDetail;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNetworkCountryISO() {
		return networkCountry;
	}

	public void setNetworkCountryISO(String networkCountry) {
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

	public String getSimState() {
		return simState;
	}

	public void setSimState(String simState) {
		this.simState = simState;
	}

	public String getNetworkCountry() {
		return networkCountry;
	}

	public void setNetworkCountry(String networkCountry) {
		this.networkCountry = networkCountry;
	}

	public String getSimNetworkCountry() {
		return simNetworkCountry;
	}

	public void setSimNetworkCountry(String simNetworkCountry) {
		this.simNetworkCountry = simNetworkCountry;
	}
	
	public String getSimOperatorCode() {
		return simOperatorCode;
	}

	public void setSimOperatorCode(String simOperatorCode) {
		this.simOperatorCode = simOperatorCode;
	}

	public String getSimOperatorName() {
		return simOperatorName;
	}

	public void setSimOperatorName(String simOperatorName) {
		this.simOperatorName = simOperatorName;
	}

	public String getSimSerialNumber() {
		return simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	/*public String getMobileNetworkState() {
		return mobileNetworkState;
	}

	public void setMobileNetworkState(String mobileNetworkState) {
		this.mobileNetworkState = mobileNetworkState;
	}

	public String getMobileNetworkDetailedState() {
		return mobileNetworkDetailedState;
	}

	public void setMobileNetworkDetailedState(String mobileNetworkDetailedState) {
		this.mobileNetworkDetailedState = mobileNetworkDetailedState;
	}*/

	public String getWifiState() {
		return wifiState;
	}

	public void setWifiState(String wifiState) {
		this.wifiState = wifiState;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public ArrayList<Ping> getPings() {
		return pings;
	}

	public void setPings(ArrayList<Ping> pings) {
		this.pings = pings;
	}

	public Measurement() {
		
	}
	
	/*
	public Measurement(Info device, Device user, ArrayList<Ping> pings) {
		super();
		this.info = device;
		this.device = user;
		this.pings = pings;
	}
	*/

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		try {
			
			//ADD key-value pairs
			
			JSONArray array = new JSONArray();
			for(Ping p: pings){
				array.put(p.toJSON());
			}
			obj.putOpt("pings", array);
			obj.putOpt("phoneNumber", phoneNumber);
			obj.putOpt("deviceid", deviceId);
			obj.putOpt("softwareVersion", softwareVersion);
			obj.putOpt("networkCountry", networkCountry);
			obj.putOpt("networkOperatorId", networkOperatorId);
			obj.putOpt("networkName", networkName);
			obj.putOpt("networkType", networkType);
			obj.putOpt("simNetworkCountry", simNetworkCountry);
			obj.putOpt("simState", simState);
			obj.putOpt("simOperatorName", simOperatorName);
			obj.putOpt("simOperatorCode", simOperatorCode);
			obj.putOpt("simSerialNumber", simSerialNumber);
			obj.putOpt("connectionType", connectionType);
//			obj.putOpt("mobileNetworkState", mobileNetworkState);
//			obj.putOpt("mobileNetworkDetailedState", mobileNetworkDetailedState);
			obj.putOpt("mobileNetworkInfo", mobileNetworkInfo);
			obj.putOpt("wifiState", wifiState);
			obj.putOpt("longitude", longitude);
			obj.putOpt("latitude", latitude);
			obj.putOpt("altitude", altitude);
			obj.putOpt("cellId", cellId);
			obj.putOpt("cellLac", cellLac);		
			obj.putOpt("time", time);			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	

}
