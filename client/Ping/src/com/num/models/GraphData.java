package com.num.models;

import java.util.List;

public class GraphData {
	
	List<GraphPoint> points;
	
	String xAxisTitle="";
	String xAxisUnits="";
	
	String yAxisTitle="";
	String yAxisUnits="";
	
	public int getyMax() {
		int yMax = 0;
		
		for(GraphPoint point : points) {
			yMax = Math.max(yMax, point.y);
		}
		
		return yMax;
	}

	public GraphData(List<GraphPoint> points){
		this.points = points;
		
	}
	
	public List<GraphPoint> getPoints() {
		return points;
	}
	public void setPoints(List<GraphPoint> points) {
		this.points = points;
	}
	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	public String getxAxisUnits() {
		return xAxisUnits;
	}

	public void setxAxisUnits(String xAxisUnits) {
		this.xAxisUnits = xAxisUnits;
	}

	public String getyAxisTitle() {
		return yAxisTitle;
	}

	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	public String getyAxisUnits() {
		return yAxisUnits;
	}

	public void setyAxisUnits(String yAxisUnits) {
		this.yAxisUnits = yAxisUnits;
	}

}
