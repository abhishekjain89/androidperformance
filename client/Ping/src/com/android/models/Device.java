package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
	
	String name;
	String planType ;
	String deviceId;
	String phoneNumber;
	
	
	public Device(String name, String planType, String deviceId,
			String phoneNumber) {
		super();
		this.name = name;
		this.planType = planType;
		this.deviceId = deviceId;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("name", name);
			obj.putOpt("plantype",  planType );
			obj.putOpt("deviceId", deviceId);
			obj.putOpt("phoneNumber", phoneNumber);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}

}
