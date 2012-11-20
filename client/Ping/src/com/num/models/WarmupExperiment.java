package com.num.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;
import com.num.Values;

public class WarmupExperiment implements Model{

	
	double lowest = -1;
	double highest = -1;
	static String version="1";
	Address address=Values.PING_SEQUENCE_ADDRESS;
	int total_count = Values.PING_WARMUP_SEQUENCE_TOTAL;
	double time_gap = Values.PING_WARMUP_SEQUENCE_GAP;
	
	ArrayList<WarmupPing> sequence = new ArrayList<WarmupPing>();
	
	public WarmupExperiment() {
		
	}

	public static String getVersion() {
		return version;
	}



	public static void setVersion(String version) {
		WarmupExperiment.version = version;
	}



	public Address getAddress() {
		return address;
	}



	public void setAddress(Address address) {
		this.address = address;
	}



	public int getTotal_count() {
		return total_count;
	}



	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}



	public double getTime_gap() {
		return time_gap;
	}



	public void setTime_gap(double time_gap) {
		this.time_gap = time_gap;
	}



	public ArrayList<WarmupPing> getSequence() {
		return sequence;
	}



	public void setSequence(ArrayList<WarmupPing> sequence) {
		this.sequence = sequence;
	}



	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("highest", highest);
			obj.putOpt("lowest", lowest);
			obj.putOpt("version", version);
			obj.putOpt("total_count", total_count);
			obj.putOpt("time_gap", time_gap);
			obj.putOpt("dstip", address.getIp());
			JSONArray array = new JSONArray();
			for(WarmupPing ping : sequence) {
				array.put(ping.toJSON());
			}
			obj.putOpt("sequence", array);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	public void addSequence(double value,int count) {
		
		if(value<lowest || lowest == -1) {
			lowest = value;
		}
		
		if(value>highest|| highest == -1) {
			highest = value;
		}
		
		sequence.add(new WarmupPing(value, count, time_gap));
	}
	
	public String getTitle() {
		return "Warmup_Experiment";
	}
	
	public int getIcon() {
		return R.drawable.png;
	}

	public ArrayList<Row> getDisplayData(Context context) {
		return null;
	}


}
