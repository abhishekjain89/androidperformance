package com.num.ui.viewgenerator;

import java.util.Map;

import com.num.listeners.ResponseListener;
import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.app.Activity;
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
	
	public abstract void populateView(Row item, Context context, View view);
	
	

}
