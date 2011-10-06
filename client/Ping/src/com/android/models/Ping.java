package com.android.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.view.View;

public class Ping {

	String title = "";
	String thumb_url = "";
	String description = "";
	String link="";
	String type="";
	String author="";
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	int resource;
	
	public Ping(String title,String thumb_url, String description,int resource,String type,String link) {
		this.title = title;
		this.thumb_url = thumb_url;
		this.description = description;
		this.resource = resource;
		this.link = link;
		this.type = type;
	}
	
	public Ping(String title,String thumb_url, String description,int resource,String type, String author, String link) {
		this.title = title;
		this.thumb_url = thumb_url;
		this.description = description;
		this.resource = resource;
		this.link = link;
		this.type = type;
		this.author = author;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

}
