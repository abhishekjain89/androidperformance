package com.android.models;

import android.graphics.drawable.Drawable;

import com.android.R;
import com.android.ui.viewgenerator.IconKeyProgressViewGenerator;
import com.android.ui.viewgenerator.KeyIconProgressViewGenerator;
import com.android.ui.viewgenerator.KeyProgressViewGenerator;
import com.android.ui.viewgenerator.KeyValueViewGenerator;
import com.android.ui.viewgenerator.TitleViewGenerator;
import com.android.ui.viewgenerator.ViewGenerator;

public class Row {
	
	public String first="";
	public String second="";
	public int value=0;
	public Drawable image;
	public int imageResourceID;
	
	ViewGenerator viewgen;
	
	public Row(String first){
		this.first = first;

		viewgen = new TitleViewGenerator(R.layout.cell_view_title);
	}
	
	public Row(String first,String second){
		this(first);
		
		this.second = second;
		
		viewgen = new KeyValueViewGenerator(R.layout.cell_view_keyvalue);
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
