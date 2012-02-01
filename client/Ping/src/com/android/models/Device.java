package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;


public class Device {
	
	String phoneType;
	String phoneNumber;
	String softwareVersion;

	Battery battery;
	

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	String phoneModel;
	String androidVersion;
	String phoneBrand; 
	String deviceDesign; 
	String manufacturer; 
	String productName; 
	String radioVersion;
	String boardName;

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getDeviceDesign() {
		return deviceDesign;
	}

	public void setDeviceDesign(String deviceDesign) {
		this.deviceDesign = deviceDesign;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRadioVersion() {
		return radioVersion;
	}

	public void setRadioVersion(String radioVersion) {
		this.radioVersion = radioVersion;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("phoneType", phoneType);
			obj.putOpt("phoneNumber", phoneNumber);
			obj.putOpt("softwareVersion", softwareVersion);
			obj.putOpt("battery", battery.toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return obj;
	}

	

}
