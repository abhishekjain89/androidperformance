package com.num.models;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.num.ui.viewgenerator.IconKeyProgressViewGenerator;
import com.num.ui.viewgenerator.KeyFourValueViewGenerator;
import com.num.ui.viewgenerator.KeyIconProgressViewGenerator;
import com.num.ui.viewgenerator.KeyProgressViewGenerator;
import com.num.ui.viewgenerator.KeyValueViewGenerator;
import com.num.ui.viewgenerator.MapViewGenerator;
import com.num.ui.viewgenerator.TitleViewGenerator;
import com.num.ui.viewgenerator.ViewGenerator;
import com.num.R;

public class Row {

	public String first="";
	public String key="";
	public String second="";
	public int value=0;
	public int valueOne = 0;
	public int valueTwo = 0;
	public Drawable image;
	public int imageResourceID;
	public ArrayList<String> seconds;

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

	public Row(int resourceid,String first){
		this(first);

		viewgen = new TitleViewGenerator(resourceid);
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




	public Row(String first,int imageid,int value){
		this(first,value);
		this.imageResourceID = imageid;
		viewgen = new KeyIconProgressViewGenerator(R.layout.cell_view_keyiconprogress);
	}

	public Row(Drawable appIcon, String name, String output, int value,String output2, int value2) {
		this.key = name;
		this.first = output;
		this.second = output2;
		this.valueOne = value;
		this.valueTwo=value2;
		this.image = appIcon;
		viewgen = new IconKeyProgressViewGenerator(R.layout.cell_view_iconkeyprogress);
	}

	public ViewGenerator getViewGenerator(){
		return viewgen;
	}

}
