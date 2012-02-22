package com.ping.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ping.R;

public class Usage implements Model{


	public List<Application> applications;
	private long total_sent;
	private long total_recv;
	private long mobile_sent;
	private long mobile_recv;



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

	public ArrayList<Row> getDisplayData(){
		
		ArrayList<Application> applications = new ArrayList<Application>();
		
		for(Application app: this.applications){
			Application app2 = new Application();
			app2.setIcon(app.getAppIcon());
			app2.setName(app.getName());
			app2.setPackageName(app.getPackageName());
			
			applications.add(app2);
			
		}
		
		
		
		JSONArray appArray = new JSONArray();
		String range = "";
		try {
			range = ",Last " + backendData.getString("range");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			appArray = backendData.getJSONArray("app-data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long total_use = 0;
		HashMap<String,Integer> app_hash = new HashMap<String,Integer>();

		for(int i=0;i<appArray.length();i++){
			try {
				JSONObject obj = appArray.getJSONObject(i);
				
				for(Application app: applications){
					if(app.getPackageName().equals(obj.getString("app").split("'")[1])){
						app.setTotal_recv(Long.parseLong(obj.getString("total").split("L")[0])*1000*1000);
						app.setTotal_sent(0);
						break;
					}
				}
				
				
				total_use+=Long.parseLong(obj.getString("total").split("L")[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Total Apps",""+applications.size()));
		data.add(new Row("Application Usage " + range));

		Collections.sort(applications);
		for(Application app: applications){

				if(app.totalDataInMB()>=1)
					data.add(new Row(app.getAppIcon(),app.getName(),app.totalDataInMB() + " MB",Math.max((int)((app.totalDataInMB()*100)/total_use),5)));
			
		}



		return data;
	}

	public int getIcon() {

		return R.drawable.usage;
	}



}
