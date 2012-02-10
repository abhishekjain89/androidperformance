package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Throughput implements Model{
	
	public Link downLink;
	public Link upLink;
	
	public Link getDownLink() {
		return downLink;
	}
	public void setDownLink(Link downLink) {
		this.downLink = downLink;
	}
	public Link getUpLink() {
		return upLink;
	}
	public void setUpLink(Link upLink) {
		this.upLink = upLink;
	}

	public Throughput() {
		super();
		
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			
			obj.put("downLink", downLink.toJSON());
			obj.put("upLink", upLink.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	

}
