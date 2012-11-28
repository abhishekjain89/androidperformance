package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;
public class TracerouteEntry implements Model, Comparable<TracerouteEntry>{
	
	String hostname = "";
	String ipAddr = "";
	String rtt = "";
	int hopnumber = -1;	
	
	private static String DESCRIPTION = "";

	public String getDescription() {
		return DESCRIPTION;
	}
	public TracerouteEntry(String ipAddr, String hostname, String rtt, int hopnumber){
		this.ipAddr = ipAddr;
		this.rtt = rtt;
		this.hostname= hostname;
		this.hopnumber = hopnumber;
	}
	
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getRtt() {
		return rtt;
	}
	public void setRtt(String rtt) {
		this.rtt = rtt;
	}
	public int getHopnumber() {
		return hopnumber;
	}
	public void setHopnumber(int hopnumber) {
		this.hopnumber = hopnumber;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("ipAddr",  ipAddr);
			obj.putOpt("hopnumber", hopnumber);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	public String getTitle() {
		
		return "TracerouteEntry";
	}
	
		
	public int getIcon() {

		return R.drawable.usage;
	}
	public ArrayList<Row> getDisplayData(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	public int compareTo(TracerouteEntry another) {
		
		if(this.hopnumber > another.hopnumber) return 1;
		if(this.hopnumber == another.hopnumber) return 0;
		return -1;
		
	}


}
