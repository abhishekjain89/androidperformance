package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


import com.num.utils.BatteryUtil;
import com.num.R;

import android.content.Context;
import android.graphics.drawable.Drawable;


public class Battery  implements MainModel{
	
	boolean isPresent = false;
	String technology = "";
	int plugged = -1;
	int scale = -1;
	int health = -1;
	int voltage = -1;
	int level = -1;
	int temperature = -1;
	int status = -1;
	

	private static String DESCRIPTION = "Details of your device's battery";

	public String getDescription() {
		return DESCRIPTION;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public int getPlugged() {
		return plugged;
	}

	public void setPlugged(int plugged) {
		this.plugged = plugged;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			if(isPresent) obj.putOpt("isPresent", 1);
			else obj.putOpt("isPresent", 0);
			obj.putOpt("technology", technology);
			obj.putOpt("plugged", plugged);
			obj.putOpt("scale", scale);
			obj.putOpt("health", health);
			obj.putOpt("voltage", voltage);
			obj.putOpt("level", level);
			obj.putOpt("temperature", temperature);
			obj.putOpt("status", status);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return obj;
	}
	
	public String getTitle() {
		
		return "Battery";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		BatteryUtil util = new BatteryUtil();
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Charge",(level*100)/scale));
		data.add(new Row("Type",technology));
		data.add(new Row("Health",util.batteryHealth(health)));
		data.add(new Row("Status",util.batteryStatus(status)));
		data.add(new Row("Plugged",util.batteryPlugged(plugged)));
		return data;
	}

	public int getIcon() {

		return R.drawable.usage;
	}


}
