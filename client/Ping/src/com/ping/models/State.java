package com.ping.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class State implements Model{
	
	public String cellId;
	public String time;
	public String local_time;
	public String deviceid;
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocal_time() {
		return local_time;
	}
	public void setLocal_time(String local_time) {
		this.local_time = local_time;
	}
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {		
			
			obj.putOpt("cellId", cellId);
			obj.putOpt("time", time);
			obj.putOpt("localtime", local_time);
			obj.putOpt("deviceid", deviceid);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	public int getIcon() {
		
		return 0;
	}
	public String getTitle() {
		return "State";
	}
	public ArrayList<Row> getDisplayData() {
		return null;
	}

}
