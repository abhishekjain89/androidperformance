package com.android.models;

import android.graphics.drawable.Drawable;

import com.android.R;
import com.android.ui.IconKeyProgressViewGenerator;
import com.android.ui.KeyProgressViewGenerator;
import com.android.ui.KeyValueViewGenerator;
import com.android.ui.TitleViewGenerator;
import com.android.ui.ViewGenerator;

public class Row {
	
	public String first="";
	public String second="";
	public int value=0;
	public Drawable image;
	
	
	ViewGenerator viewgen;
	
	public Row(String first){
		this.first = first;

		viewgen = new TitleViewGenerator(R.layout.cell_view_title);
	}
	
	public Row(String first,String second){
		this.first = first;
		this.second = second;
		
		viewgen = new KeyValueViewGenerator(R.layout.cell_view_keyvalue);
	}
	
	public Row(String first,int value){
		this.first = first;
		this.value = value;
		this.second = value + " %";
		viewgen = new KeyProgressViewGenerator(R.layout.cell_view_keyprogress);
	}
	
	public Row(String first,String second,int value){
		this.first = first;
		this.value = value;
		this.second = second;
		viewgen = new KeyProgressViewGenerator(R.layout.cell_view_keyprogress);
	}
	
	public Row(Drawable icon,String first,String second,int value){
		this.image = icon;
		this.first = first;
		this.value = value;
		this.second = second;
		viewgen = new IconKeyProgressViewGenerator(R.layout.cell_view_iconkeyprogress);
	}
	
	public ViewGenerator getViewGenerator(){
		return viewgen;
	}

}
