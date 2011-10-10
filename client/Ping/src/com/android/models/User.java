package com.android.models;

public class User {
	String name;
	String location;
	String carrier;
	String plantype;
	
	public User(String name, String location, String carrier, String pantype){
		this.name=name;
		this.location=location;
		this.carrier=carrier;
		this.plantype=plantype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getPlantype() {
		return plantype;
	}

	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}

}
