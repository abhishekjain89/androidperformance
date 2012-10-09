package com.num.database;

import java.util.HashMap;

public class DatabaseOutput {
	
	HashMap<String, String> data;
	
	public DatabaseOutput() {
		data = new HashMap<String, String>();
	}
	
	public String getString(String key) {
		return data.get(key);
	}
	
	public Long getLong(String key) {
		return Long.parseLong(data.get(key));
	}
	
	public Double getDouble(String key) {
		return Double.parseDouble(data.get(key));
	}
	
	public void add(String key, String value) {
		data.put(key, value);
	}

}
