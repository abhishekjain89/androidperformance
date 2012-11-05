package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.num.listeners.ResponseListener;
import com.num.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Application implements Model,Comparable<Application> {
	private String name = "";
	private String packageName = "";
	private long total_sent = -1;
	private long total_recv = -1;
	private boolean isRunning = false;
	private Drawable icon;
	private static String DESCRIPTION = "Shows data send and received for application";

	public String getDescription() {
		return DESCRIPTION;
	}
	public Drawable getAppIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
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
	
	public long getTotal() {
		return getTotal_recv() + getTotal_sent();
	}
	
	public JSONObject toJSON() {
		
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("name",name);
			obj.putOpt("packageName",packageName);
			obj.putOpt("total_sent", total_sent);
			obj.putOpt("total_recv", total_recv);
			if(isRunning)
				obj.putOpt("isRunning", 1);
			else
				obj.putOpt("isRunning", 0);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public boolean getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(boolean b) {
		this.isRunning = b;
	}
	public int totalDataInMB(){
		return (int) ((this.total_recv + this.total_sent)/(1000*1000));
	}
	
	public String getTitle() {
		
		return "Application";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}
	public int compareTo(Application other) {
		if(this.totalDataInMB() > other.totalDataInMB()) return -1;
		if(this.totalDataInMB()==other.totalDataInMB()) return 0;
		return 1;
	}
	
	public int getIcon() {

		return R.drawable.usage;
	}
}
