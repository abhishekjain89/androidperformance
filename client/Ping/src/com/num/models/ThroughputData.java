package com.num.models;

public class ThroughputData {
	private long id;
	private String time;
	private String speed;
	private String type;
	private String connection;


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

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connectionType) {
		this.connection = connectionType;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "time: " + time
			+ " speed: " + speed
			+ " type: " + type
			+ " connection: " + connection;
	}
}
