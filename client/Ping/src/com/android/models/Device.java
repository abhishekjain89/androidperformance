package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
	String name;
	String plantype;
	
	public Device(String name, String location, String carrier, String pantype){
		this.name=name;
		this.plantype=plantype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPlantype() {
		return plantype;
	}

	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			
			obj.put("name", name);
			obj.put("plantype", plantype);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}

}
