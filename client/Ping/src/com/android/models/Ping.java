package com.android.models;

import java.util.Date;

public class Ping {

	String deviceId;
	String srcIp;
	String dstIp;
	Measurement measure;
	
	Date time;
	
	public Ping(String deviceId, String scrIp, String dstIp, Measurement measure) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		this.deviceId=deviceId;
		this.srcIp=scrIp;
		this.dstIp=dstIp;
		this.measure = measure;
		this.time=new Date();
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

	public Measurement getMeasure() {
		return measure;
	}

	public void setMeasure(Measurement measure) {
		this.measure = measure;
	}
	
	

}
