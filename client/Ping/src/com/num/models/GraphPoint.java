package com.num.models;

import java.util.Date;

public class GraphPoint implements Comparable<GraphPoint> {
	public int x;
	public int y;
	public Date datetime;
	
	public GraphPoint(int x,int y,Date date) {
		this.x = x;
		this.y = y;
		this.datetime = date;
	}

	public int compareTo(GraphPoint another) {
		if(this.y>another.y) {
			return 1;
		}
		return -1;
	}
}