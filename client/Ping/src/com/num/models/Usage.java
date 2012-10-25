package com.num.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Usage implements MainModel{


	public List<Application> applications;
	private long total_sent = -1;
	private long total_recv = -1;
	private long mobile_sent = -1;
	private long mobile_recv = -1;

	private static String DESCRIPTION = "Network usage per application";

	public String getDescription() {
		return DESCRIPTION;
	}


	public long getMobile_sent() {
		return mobile_sent;
	}
	public void setMobile_sent(long mobile_sent) {
		this.mobile_sent = mobile_sent;
	}
	public long getMobile_recv() {
		return mobile_recv;
	}
	public void setMobile_recv(long mobile_recv) {
		this.mobile_recv = mobile_recv;
	}
	

	public long getMobile() {
		return getMobile_recv() + getMobile_sent();
	}
	

	public long getWifi() {
		return getTotal() - getMobile();
	}
	
	public long getWifi_sent() {
		return getTotal_sent() - getMobile_sent();
	}
	
	public long getWifi_recv() {
		return getTotal_recv() - getMobile_recv();
	}
	
	public Usage() {
		// TODO Auto-generated constructor stub
	}
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public long getTotalInMB() {
		return (total_sent + total_recv)/(1000*1000);
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

	public JSONObject toJSON(){

		JSONObject obj = new JSONObject();
		try {

			JSONArray array = new JSONArray();

			for(Application app: applications){
				array.put(app.toJSON());
			}

			obj.putOpt("applications", array);			
			obj.putOpt("total_sent", total_sent);
			obj.putOpt("total_recv", total_recv);
			obj.putOpt("mobile_sent", mobile_sent);
			obj.putOpt("mobile_recv", mobile_recv);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;

	}



	public String getTitle() {

		return "Usage";
	}


	JSONObject backendData;

	public JSONObject getBackendData(){
		return backendData;
	}

	public void setBackendData(JSONObject res){
		backendData = res;
	}

	public ArrayList<Row> getDisplayData(Context context){

		long total_use = 0;


		ArrayList<Row> data = new ArrayList<Row>();
		
		
		try {
			Collections.sort(applications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Application app: applications){

			if(isOurApp(app)) continue;
			if(app.getTotal_recv() + app.getTotal_sent() < 1000000) continue;

			try {
				data.add(new Row(app,getTotal()));
			} catch (Exception e) {
				System.out.print(e.getLocalizedMessage());
			}
		}

		return data;
	}

	

	public int getIcon() {

		return R.drawable.usage;
	}

	public boolean isOurApp(Application app){
		if(app.getPackageName().contains("com.num")) return true;
		return false;
	}



}
