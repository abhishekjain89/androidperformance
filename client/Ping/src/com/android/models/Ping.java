package com.android.models;

import java.util.Date;

public class Ping {

	String deviceId;
	String srcIp;
	String dstIp;
	String eventStamp;
	Measurement measure;
	
	Date time;
	
	public Ping(String deviceId, String scrIp, String dstIp, String eventStamp, Measurement measure) {
		//from an activity object, to get the device id :
		//Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		this.deviceId=deviceId;
		this.srcIp=scrIp;
		this.dstIp=dstIp;
		this.eventStamp=eventStamp;
		this.measure = measure;
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


}
