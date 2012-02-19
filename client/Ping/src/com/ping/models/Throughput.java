package com.ping.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ping.R;

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
		Link downLink = new Link();
		Link upLink = new Link();
		
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			
			obj.put("downLink", downLink.toJSON());
			obj.put("upLink", upLink.toJSON());
			
		} catch (Exception e) {
			obj = new JSONObject();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Throughput";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Downlink",(int)(downLink.speedInBytes()) + " KBps"));
		data.add(new Row("Uplink",(int)(upLink.speedInBytes()) + " KBps"));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.throughput;
	}

	

}
