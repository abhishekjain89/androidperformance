package com.num.models;

public class PingData {
	private long id;
	private String time;
	private float avg;
	private float min;
	private float max;
	private float std;
	private String srcip;
	private String dstip;
	private String connection;


	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "time: " + time
			+ " avg: " + avg
			+ " min: " + min
			+ " max: " + max
			+ " std: " + std
			+ " srcip: " + srcip
			+ " dstip: " + dstip
			+ " connection: " + connection;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public float getStd() {
		return std;
	}
	public void setStd(float std) {
		this.std = std;
	}
	public String getSrcip() {
		return srcip;
	}
	public void setSrcip(String srcip) {
		this.srcip = srcip;
	}
	public String getDstip() {
		return dstip;
	}
	public void setDstip(String dstip) {
		this.dstip = dstip;
	}
	
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
}
