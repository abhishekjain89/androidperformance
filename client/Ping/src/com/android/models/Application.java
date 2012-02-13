package com.android.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.listeners.ResponseListener;

import android.graphics.drawable.Drawable;

public class Application implements Model{
	private String name;
	private String packageName;
	private long total_sent;
	private long total_recv;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public long getTotal_sent() {
		return total_sent;
	}
	public void setTotal_sent(long total_sent) {
		this.total_sent = total_sent;
	}
	public long getTotal_recv() {
		return total_recv;
	}
	public void setTotal_recv(long total_recv) {
		this.total_recv = total_recv;
	}
	public JSONObject toJSON() {

		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("name",name);
			obj.putOpt("packageName",packageName);
			obj.putOpt("total_sent", total_sent);
			obj.putOpt("total_recv", total_recv);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}
	public String getTitle() {
		
		return "Application";
	}
	
	public ArrayList<Row> getDisplayData(){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}


}
