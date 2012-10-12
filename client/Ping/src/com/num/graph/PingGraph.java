package com.num.graph;

import com.num.models.Ping;

public class PingGraph {
	
	Ping ping;
	int pingMax;
	
	public PingGraph(Ping ping, int max) {
		this.ping = ping;
		this.pingMax = max;
	}

	public Ping getPing() {
		return ping;
	}

	public void setPing(Ping ping) {
		this.ping = ping;
	}

	public int getPingMax() {
		return pingMax;
	}

	public void setPingMax(int pingMax) {
		this.pingMax = pingMax;
	}
	
	public int getProgressValue() {
		return ((int)ping.getMeasure().getAverage()*100)/pingMax;
	}

}
