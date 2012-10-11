package com.num.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class LastMile extends Ping{

	int hopCount = -1;
	String firstIp = "";
	
	public LastMile(String scrIp2, Address dst, Measure measure, int hopCount, String firstIp) {
		
		super(scrIp2,dst,measure);		
		this.hopCount = hopCount;
		this.firstIp = firstIp;
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = super.toJSON();
		try {
			obj.putOpt("hopcount", hopCount);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	public String getTitle() {
		return "LastMile";
	}

}
