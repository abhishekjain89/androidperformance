package com.android.models;

public class Throughput {
	
	public double downLink;
	public double upLink;
	public String dstIp;
	
	public Throughput(double downLink, double upLink, String dstIp) {
		super();
		this.downLink = downLink;
		this.upLink = upLink;
		this.dstIp = dstIp;
	}
	public double getDownLink() {
		return downLink;
	}
	public void setDownLink(double downLink) {
		this.downLink = downLink;
	}
	public double getUpLink() {
		return upLink;
	}
	public void setUpLink(double upLink) {
		this.upLink = upLink;
	}
	public String getDstIp() {
		return dstIp;
	}
	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}
	
	

}
