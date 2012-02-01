package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Link {
	
	public int count; 
	public int message_size; //in bytes
	public double time; // milliseconds
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getMessage_size() {
		return message_size;
	}
	public void setMessage_size(int message_size) {
		this.message_size = message_size;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public Link(int count, int message_size, double time) {
		super();
		this.count = count;
		this.message_size = message_size;
		this.time = time;
	}
	
	public double speedInBytes(){
		return ((double)count*message_size)/time;
	}
	
	public double speedInBits(){
		return speedInBytes()*8;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			obj.putOpt("speedInBits", speedInBits());		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
}
