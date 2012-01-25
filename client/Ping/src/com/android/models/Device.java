package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
	
	String phoneType;
	String phoneNumber;
	String softwareVersion;


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
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return obj;
	}

}
