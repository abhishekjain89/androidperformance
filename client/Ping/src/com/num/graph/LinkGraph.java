package com.num.graph;

import com.num.models.Link;
import com.num.models.Ping;

public class LinkGraph {
	
	Link link;	
	int maxTime;
	String name;
	String type;
	
	public LinkGraph(Link link, int maxTime, String name, String type) {
		super();
		this.link = link;
		this.maxTime = maxTime;
		this.name = name;
		this.type = type;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getProgressValue() {
		return ((int)link.getTime()*100)/(maxTime);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
