package com.num.models;

import android.R.integer;
import android.app.Activity;
import android.os.Handler;

public class ActivityItem {
	
	
	String title;
	String description;
	Handler handle;
	int imageResource;
	
	public ActivityItem(String title, String description, Handler handle,
			int imageResource) {
		super();
		this.title = title;
		this.description = description;
		this.handle = handle;
		this.imageResource = imageResource;
	}
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
	public Handler getHandle() {
		return handle;
	}
	public void setHandle(Handler handle) {
		this.handle = handle;
	}
	public int getImageResource() {
		return imageResource;
	}
	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}
	
	
	

}
