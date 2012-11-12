package com.num.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Traceroute implements MainModel{

	ArrayList<TracerouteEntry> traceroutelist = new ArrayList<TracerouteEntry>();
	int startindex, endindex;
	private static String DESCRIPTION = "List of ip address of hops from your device to Georgia tech server";

	public String getDescription() {
		return DESCRIPTION;
	}
	public Traceroute(int startindex, int endindex){
		this.startindex = startindex;
		this.endindex = endindex;
	}
	
	
	public ArrayList<TracerouteEntry> getTraceroutelist() {
		return traceroutelist;
	}
	
	public void addToList(TracerouteEntry traceentry)
	{
		
		traceroutelist.add(traceentry);
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("traceroutelist",  traceroutelist);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	public String getTitle() {
		
		return "Traceroute";
	}
	
		
	public int getIcon() {

		return R.drawable.usage;
	}
	public ArrayList<Row> getDisplayData(Context context) {
		
		ArrayList<Row> data = new ArrayList<Row>();
		Collections.sort(traceroutelist);
		
		for(TracerouteEntry entry : traceroutelist) {
			data.add(new Row(entry));		
		}
		
		return data;
		
	}


}
