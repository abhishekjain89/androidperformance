package com.android.models;

import java.util.Date;

public class Ping {

	String deviceId;
	String srcIp;
	String dstIp;
	String eventStamp;
	int max;
	int min;
	int average;
	int stddev;
	Date time;
	
	public Ping(String deviceId, String scrIp, String dstIp, String eventStamp, int max, int min, int average, int stddev) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		this.deviceId=deviceId;
		this.srcIp=scrIp;
		this.dstIp=dstIp;
		this.eventStamp=eventStamp;
		this.max=max;
		this.min=min;
		this.average=average;
		this.stddev=stddev;
		time=new Date();
	}
	
	public String getEventStamp() {
		return eventStamp;
	}

	public void setEventStamp(String eventStamp) {
		this.eventStamp = eventStamp;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
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
