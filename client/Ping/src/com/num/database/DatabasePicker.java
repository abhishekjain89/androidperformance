package com.num.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import android.os.Handler;

import com.num.database.datasource.DataSource;
import com.num.models.GraphData;
import com.num.models.GraphPoint;
import com.num.models.Row;

public class DatabasePicker {

	public DataSource datasource;
	HashMap<String, String> filter = new HashMap<String, String>();
	HashMap<String, String> display = new HashMap<String, String>();
	String title = "Graph";
	String description = "";
	Handler handler;
	float outlierFraction = 0.05f;
	boolean displayOutlier = true;
	float[] outlierOptions = { 0.0f, 0.05f, 0.10f, 0.15f, 0.20f };
	
	String[] mode = { "last 24 hours","time of day",""};

	public String getChartType() {
		return datasource.getGraphType();
	}
	
	public String getYAxisLabel() {
		return datasource.getYAxisLabel();
	}

	public DatabasePicker() {
		filter = new HashMap<String, String>();
		display = new HashMap<String, String>();
		outlierFraction = (float) 0.05;
	}

	public boolean isDisplayOutlier() {
		return displayOutlier;
	}

	public void setDisplayOutlier(boolean displayOutlier) {
		this.displayOutlier = displayOutlier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.toUpperCase();
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

	public void filterBy(String field, String displayField) {
		filterBy(field, "", displayField);
	}

	public void updateFilter(String field, String value) {
		filter.put(field, value);
		updateGraph();
	}

	public String get(String field) {
		return filter.get(field);
	}

	public void filterBy(String field, String value, String displayField) {
		filter.put(field, value);
		display.put(field, displayField);
	}

	public GraphData getGraphData() {

		return new GraphData(datasource.getGraphData(filter));

	}

	public GraphData getGraphDataWithoutOutliers() {
		
		GraphData data = getGraphData(outlierFraction);
		return data;
	}

	private GraphData getGraphData(float outlierFraction) {
		try {

			GraphData data = getGraphData();

			if (displayOutlier) {

				int originalSize = data.getPoints().size();
				int newSize = (int) (data.getPoints().size() * (1 - outlierFraction));
				Collections.sort(data.getPoints());

				ArrayList<GraphPoint> newPoints = new ArrayList<GraphPoint>();

				for (int i = 0; i < newSize; i++) {
					newPoints.add(data.getPoints().get(i));
				}

				data.setPoints(newPoints);
			}
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return getGraphData();
		}

	}

	public ArrayList<Row> getRows() {
		ArrayList<Row> data = new ArrayList<Row>();

		Iterator<String> iter = filter.keySet().iterator();
		while (iter.hasNext()) {
			data.add(new Row(this, iter.next()));
		}
		if (displayOutlier)
			data.add(new Row(this));

		return data;

	}

	public void setGraphUpdateHandler(Handler updateGraphHandler) {
		handler = updateGraphHandler;

	}

	public void updateGraph() {
		handler.sendEmptyMessage(0);
	}

	public String getDisplayName(String field) {
		return display.get(field);
	}

	public float getOutlierFraction() {
		return outlierFraction;
	}

	public void setOutlierFraction(float outlierFraction) {
		this.outlierFraction = outlierFraction;
	}

	public float[] getOutlierOptions() {
		return outlierOptions;
	}

	public void setOutlierOptions(float[] outlierOptions) {
		outlierOptions = outlierOptions;
	}

}
