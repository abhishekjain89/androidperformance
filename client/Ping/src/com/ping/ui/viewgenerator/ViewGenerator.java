package com.ping.ui.viewgenerator;

import java.util.Map;

import com.ping.R;
import com.ping.listeners.ResponseListener;
import com.ping.models.Model;
import com.ping.models.Row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class ViewGenerator {
	
	
	private int resourceID;
	
	public ViewGenerator(int resource) {
		
		this.resourceID = resource;
		
	}
	public int getResourceID(){
		return resourceID;
	}
	public View generateViewHolder(LayoutInflater inflater){
		View view = inflater.inflate(getResourceID(), null);
		
		ViewHolder holder = fillViewHolder(view,inflater);
		
		view.setTag(holder);
		return view;
	}
	public abstract ViewHolder fillViewHolder(View view,LayoutInflater inflater);
	
	public abstract void populateView(Row item);
	
	

}
