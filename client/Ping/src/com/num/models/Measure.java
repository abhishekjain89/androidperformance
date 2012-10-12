package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Measure implements Model{
	
	double max = -1;
	double min = -1;
	double average = -1;
	double stddev = -1;

	private static String DESCRIPTION = "";

	public String getDescription() {
		return DESCRIPTION;
	}
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
	
	public String showText() {
		return (int)getAverage()+" ms";
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
	public String getTitle() {
		
		return "Measure";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First","Second"));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.battery;
	}


}
