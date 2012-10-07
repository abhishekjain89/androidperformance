package com.num.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Ping implements Model{

	
	String srcIp = "";	
	Address dst;
	Measure measure;
	String time = "";


	private static String DESCRIPTION = "Details of delay in milliseconds experienced on the network";

	public String getDescription() {
		return DESCRIPTION;
	}
	
	public Ping(String scrIp, Address dst, Measure measure) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		
		this.srcIp=scrIp;
		this.dst = dst;
		this.measure = measure;		
		

	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    this.time = utcTime;
	    
	}
	public Address getDst() {
		return dst;
	}

	public void setDst(Address dst) {
		this.dst = dst;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
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
			obj.putOpt("dst_ip", dst.getIp());
			obj.putOpt("time", time);
			obj.putOpt("measure", measure.toJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	public String getTitle() {
		
		return "Ping";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.png;
	}


}
