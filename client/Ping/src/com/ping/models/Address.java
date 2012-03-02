package com.ping.models;

public class Address{
	
	public String ip = "";
	public String tagname = "";
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public Address(String ip, String tagname) {
		super();
		this.ip = ip;
		this.tagname = tagname;
	}
	

}
