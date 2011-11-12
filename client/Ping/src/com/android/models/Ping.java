package com.android.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Ping {

	
	String srcIp;
	String dstIp;
	Measure measure;
	
	Date time;
	
	public Ping(String scrIp, String dstIp, Measure measure) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		
		this.srcIp=scrIp;
		this.dstIp=dstIp;
		this.measure = measure;
		this.time=new Date();
	}
	
	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			
			obj.put("src_ip", srcIp);
			obj.put("dst_ip", dstIp);
			obj.put("time", time);
			obj.put("measure", measure.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}

}
