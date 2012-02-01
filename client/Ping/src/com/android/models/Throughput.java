package com.android.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Throughput {
	
	public Link downLink;
	public Link upLink;
	public String dstIp;
	public String dstPort;
	public Link getDownLink() {
		return downLink;
	}
	public void setDownLink(Link downLink) {
		this.downLink = downLink;
	}
	public Link getUpLink() {
		return upLink;
	}
	public void setUpLink(Link upLink) {
		this.upLink = upLink;
	}
	public String getDstIp() {
		return dstIp;
	}
	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}
	public String getDstPort() {
		return dstPort;
	}
	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}
	public Throughput(Link downLink, Link upLink, String dstIp, String dstPort) {
		super();
		this.downLink = downLink;
		this.upLink = upLink;
		this.dstIp = dstIp;
		this.dstPort = dstPort;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			
			obj.putOpt("dstIp", dstIp);
			obj.putOpt("dstPort", dstPort);
			obj.put("downLink", downLink.toJSON());
			obj.put("upLink", upLink.toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	

}
