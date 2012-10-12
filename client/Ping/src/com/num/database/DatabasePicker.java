package com.num.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.num.database.datasource.DataSource;
import com.num.models.GraphData;
import com.num.models.GraphPoint;

public class DatabasePicker {
	
	public DataSource datasource;
	HashMap<String, String> filter = new HashMap<String, String>();
	String title="Graph";
	String description="";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public DatabasePicker(DataSource dataSource) {
		this.datasource = dataSource;
	}
	
	public void filterBy(String field) {
		filterBy(field,"");
	}
	
	public void filterBy(String field,String value) {
		filter.put(field, value);
	}
	
	public GraphData getGraphData() {
		
		return new GraphData(datasource.getGraphData(filter));
		
	}

}
