package com.ping.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ping.R;

public class Link implements Model{

	public long count; 
	public long message_size; //in bytes
	public double time; // milliseconds
	public String dstIp;
	public String dstPort;
	

	public String getDstIp() {
		return dstIp;
	}
	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}
	public String getDstPort() {
		return dstPort;
	}
	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}

	public long getCount() {
		return count;
	}
	public void setCount(long count2) {
		this.count = count2;
	}
	public long getMessage_size() {
		return message_size;
	}
	public void setMessage_size(long message_size) {
		this.message_size = message_size;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public Link() {
		super();

	}

	public double speedInBytes(){
		return ((double)count*message_size)/time;
	}

	public double speedInBits(){
		return speedInBytes()*8;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			obj.putOpt("count", count);
			obj.putOpt("message_size",message_size);
			obj.putOpt("time", time);
			obj.putOpt("speedInBits", speedInBits());
			obj.put("dstIp", dstIp);
			obj.put("dstPort", dstPort);
		} catch (Exception e) {
			obj = new JSONObject();
		}

		return obj;
	}
	
	public String getTitle() {
		
		return "Link";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.battery;
	}


}
