package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Battery {
	
	boolean isPresent;
	String technology;
	int plugged;
	int scale;
	int health;
	int voltage;
	int level;
	int temperature;
	int status;
	
	

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

	public Object toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("isPresent", isPresent);
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

}
