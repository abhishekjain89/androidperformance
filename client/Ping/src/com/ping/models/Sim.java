package com.ping.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ping.R;

public class Sim implements Model{

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
			obj.put("operatorCode", simOperatorCode);
			obj.put("serialNumber", simSerialNumber);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Sim";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Country",simNetworkCountry));
		data.add(new Row("Status",simState));
		data.add(new Row("Operator",simOperatorName));
		data.add(new Row("Serial",simSerialNumber));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.sim;
	}

	
}
