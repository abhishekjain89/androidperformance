package com.android.models;

public class Measurement {
	
	int max;
	int min;
	int average;
	int stddev;
	
	public Measurement(int max,int min, int average, int stddev){
		this.max = max;
		this.min = min;
		this.average = average;
		this.stddev = stddev;
	}
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public int getStddev() {
		return stddev;
	}
	public void setStddev(int stddev) {
		this.stddev = stddev;
	}

}
