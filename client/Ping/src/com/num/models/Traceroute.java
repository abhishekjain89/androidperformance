package com.num.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Traceroute implements MainModel{

	HashMap<Integer,TracerouteEntry> traceroutemap = new HashMap<Integer, TracerouteEntry>();
	int startindex, endindex;
	private static String DESCRIPTION = "List of ip address of hops from your device to Georgia tech server";

	public String getDescription() {
		return DESCRIPTION;
	}
	public Traceroute(int startindex, int endindex){
		this.startindex = startindex;
		this.endindex = endindex;
	}
	
	
	public HashMap<Integer, TracerouteEntry> getTraceroutelist() {
		return traceroutemap;
	}
	public void setTraceroutelist(HashMap<Integer, TracerouteEntry> traceroutemap) {
		this.traceroutemap = traceroutemap;
	}
	
	public void addToList(TracerouteEntry traceentry)
	{
		
		traceroutemap.put(traceentry.hopnumber,traceentry);
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("traceroutelist",  traceroutemap);
			
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
		
		for(int i = startindex; i <= endindex; i++) 
		{
			if(traceroutemap.containsKey(i))
			{
				data.add(new Row(""+i,""+traceroutemap.get(i).ipAddr+"\n"+ traceroutemap.get(i).rtt));
			}
		}
		
		return data;
		
	}


}
