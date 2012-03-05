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
		int downL = -1;
		int upL = -1;
		if (downLink == null) {
			downL = -1;
		} else {
			downL = (int)downLink.speedInBits();
		}
		if (upLink == null) {
			upL = -1;
		} else {
			upL = (int)upLink.speedInBits();
		}
		data.add(new Row("Downlink",downL + " Kbps"));
		data.add(new Row("Uplink",upL+ " Kbps"));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.throughput;
	}

	

}
