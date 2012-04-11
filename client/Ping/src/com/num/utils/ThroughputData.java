package com.num.utils;

public class ThroughputData {
	private long id;
	private String downlink;
	private String uplink;
	
	public String getDownlink() {
		return downlink;
	}

	public void setDownlink(String downlink) {
		this.downlink = downlink;
	}

	public String getUplink() {
		return uplink;
	}

	public void setUplink(String uplink) {
		this.uplink = uplink;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return downlink + " Kbps, " + uplink + " Kbps";
	}
}
