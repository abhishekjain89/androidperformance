package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.num.R;
import com.num.Values;

public class Throughput implements MainModel{
	
	public Link downLink;
	public Link upLink;
	public boolean isComplete = false;
	

	private static String DESCRIPTION = "Upload and Download speeds";

	public String getDescription() {
		return DESCRIPTION;
	}
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
		
		if (upL>0) {
			data.add(new Row("Upload",(int)upLink.getTime()/(Values.UPLINK_DURATION/100),upL+ " Kbps"));
		}
		
		if (downL>0) {
			data.add(new Row("Download",(int)downLink.getTime()/(Values.DOWNLINK_DURATION/100),downL + " Kbps"));
		}
		
		if(isComplete) {
			data.add(new Row("Test Complete"));
		} else {
			data.add(new Row("In progress ..."));
		}
		
		return data;
	}
	
	public int getIcon() {

		return R.drawable.throughput;
	}

	

}
