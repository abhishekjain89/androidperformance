package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Sim {

	String simNetworkCountry;
	String simState;
	String simOperatorName;
	String simOperatorCode;
	String simSerialNumber;
	
	public String getSimNetworkCountry() {
		return simNetworkCountry;
	}
	public void setSimNetworkCountry(String simNetworkCountry) {
		this.simNetworkCountry = simNetworkCountry;
	}
	public String getSimState() {
		return simState;
	}
	public void setSimState(String simState) {
		this.simState = simState;
	}
	public String getSimOperatorName() {
		return simOperatorName;
	}
	public void setSimOperatorName(String simOperatorName) {
		this.simOperatorName = simOperatorName;
	}
	public String getSimOperatorCode() {
		return simOperatorCode;
	}
	public void setSimOperatorCode(String simOperatorCode) {
		this.simOperatorCode = simOperatorCode;
	}
	public String getSimSerialNumber() {
		return simSerialNumber;
	}
	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {		
			
			obj.putOpt("networkCountry", simNetworkCountry);
			obj.putOpt("state", simState);
			obj.put("operatorName", simOperatorName);
			obj.put("soperatorCode", simOperatorCode);
			obj.put("serialNumber", simSerialNumber);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
}
