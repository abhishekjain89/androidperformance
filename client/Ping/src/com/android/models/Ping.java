package com.android.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

public class Ping implements Model{

	
	String srcIp;
	String dstIp;
	Measure measure;
	
	String time;
	
	public Ping(String scrIp, String dstIp, Measure measure) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		
		this.srcIp=scrIp;
		this.dstIp=dstIp;
		this.measure = measure;		
		

	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    this.time = utcTime;
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
			
			obj.putOpt("src_ip", srcIp);
			obj.putOpt("dst_ip", dstIp);
			obj.putOpt("time", time);
			obj.putOpt("measure", measure.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}

}
