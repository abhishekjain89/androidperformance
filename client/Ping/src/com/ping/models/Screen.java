package com.ping.models;

import java.util.ArrayList;

import org.json.JSONObject;

import com.ping.utils.SHA1Util;

public class Screen implements Model {
	
	public String time = "";
	public String localtime = "";
	
	
	public String getLocaltime() {
		return localtime;
	}
	public void setLocaltime(String localtime) {
		this.localtime = localtime;
	}
	public boolean on;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public Screen(String time, String local,boolean on) {
		super();
		this.time = time;
		this.localtime = local;
		this.on = on;
	}
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {		
			
			obj.putOpt("time", time);
			obj.putOpt("localtime", localtime);
			if(on)
				obj.putOpt("isOn", 1);
			else
				obj.putOpt("isOn", 0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	public int getIcon() {

		return 0;
	}
	public String getTitle() {
		return "Screen";
	}
	public ArrayList<Row> getDisplayData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
