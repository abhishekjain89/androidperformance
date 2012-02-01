package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Measurement {
	
/*     DESIRED STRUCTURE
  -----------------------*/	
	ArrayList<Ping> pings; 
	Device device; // Need
	Network network; // Need
	Sim sim; // Need
	Throughput throughput;
	GPS gps;
	String time;
	String deviceId;
/*-----------------------*/	
	
/*		MOVE THIS !	
  -----------------------*/	
	String phoneDetail;
	String networkDetail;	
	
	// Device 
	String phoneType;
	String phoneNumber;
	String softwareVersion;
	
	// Sim 
	String simNetworkCountry;
	String simState;
	String simOperatorName;
	String simOperatorCode;
	String simSerialNumber;

	// Network
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

	// GPS
	String longitude;
	String latitude;
	String altitude;

/*-------------------------*/	

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public Sim getSim() {
		return sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	public Throughput getThroughput() {
		return throughput;
	}

	public void setThroughput(Throughput throughput) {
		this.throughput = throughput;
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
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
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
	
	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		
		try {
			
			JSONArray array = new JSONArray();
			for(Ping p: pings){
				array.put(p.toJSON());
			}
			obj.putOpt("pings", array);
			obj.putOpt("deviceid", deviceId);
			obj.putOpt("time", time);	
			obj.putOpt("device",device.toJSON());
			//obj.putOpt("throughput",throughput.toJSON());
			obj.putOpt("gps",gps.toJSON());
			obj.putOpt("network",network.toJSON());
			obj.putOpt("sim",sim.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	

}
