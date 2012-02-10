package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Measure implements Model{
	
	double max;
	double min;
	double average;
	double stddev;
	
	public Measure(double max, double min, double average, double stddev){
		this.max = max;
		this.min = min;
		this.average = average;
		this.stddev = stddev;
	}
	
	public double getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public double getStddev() {
		return stddev;
	}
	public void setStddev(int stddev) {
		this.stddev = stddev;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("max",  max);
			obj.putOpt("min", min);
			obj.putOpt("average", average);
			obj.putOpt("stddev", stddev);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
