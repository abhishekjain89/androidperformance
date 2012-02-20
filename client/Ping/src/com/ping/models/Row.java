package com.ping.models;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.ping.R;
import com.ping.ui.viewgenerator.IconKeyProgressViewGenerator;
import com.ping.ui.viewgenerator.KeyFourValueViewGenerator;
import com.ping.ui.viewgenerator.KeyIconProgressViewGenerator;
import com.ping.ui.viewgenerator.KeyProgressViewGenerator;
import com.ping.ui.viewgenerator.KeyValueViewGenerator;
import com.ping.ui.viewgenerator.MapViewGenerator;
import com.ping.ui.viewgenerator.TitleViewGenerator;
import com.ping.ui.viewgenerator.ViewGenerator;

public class Row {
	
	public String first="";
	public String second="";
	public int value=0;
	public double valueOne = 0;
	public double valueTwo = 0;
	public Drawable image;
	public int imageResourceID;
	public ArrayList<String> seconds;
	
	ViewGenerator viewgen;
	
	public Row(String first){
		this.first = first;

		viewgen = new TitleViewGenerator(R.layout.cell_view_title);
	}
	
	public Row(double v1,double v2){
		valueOne = v1;
		valueTwo = v2;
		viewgen = new MapViewGenerator(R.layout.cell_view_map);
	}
	
	public Row(String first,String second){
		this(first);
		
		this.second = second;
		
		viewgen = new KeyValueViewGenerator(R.layout.cell_view_keyvalue);
	}
	
	public Row(int resourceid,String first){
		this(first);
		
		viewgen = new KeyValueViewGenerator(resourceid);
	}
	
	public Row(String first,ArrayList<String> seconds){
		this(first);
		
		this.seconds = seconds;
	
		viewgen = new KeyFourValueViewGenerator(R.layout.cell_view_keyfourvalue);
	}
	
	public Row(String first,int value){
		this(first,value+" %",value);
		viewgen = new KeyProgressViewGenerator(R.layout.cell_view_keyprogress);
	}
	
	public Row(String first,String second,int value){
		this(first,second);
		this.value = value;
		viewgen = new KeyProgressViewGenerator(R.layout.cell_view_keyprogress);
	}

	
	public Row(Drawable icon,String first,String second,int value){
		this(first,second,value);
		this.image = icon;
		
		viewgen = new IconKeyProgressViewGenerator(R.layout.cell_view_iconkeyprogress);
	}
	
	public Row(String first,int imageid,int value){
		this(first,value);
		this.imageResourceID = imageid;
		viewgen = new KeyIconProgressViewGenerator(R.layout.cell_view_keyiconprogress);
	}
	
	public ViewGenerator getViewGenerator(){
		return viewgen;
	}

}