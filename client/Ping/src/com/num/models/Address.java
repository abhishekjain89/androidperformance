package com.num.models;

public class Address{
	
	public String ip = "";
	public String tagname = "";
	public String type = "";
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Address(String ip, String tagname, String type) {
		super();
		this.ip = ip;
		this.tagname = tagname;
		this.type = type;
	}
	

}
