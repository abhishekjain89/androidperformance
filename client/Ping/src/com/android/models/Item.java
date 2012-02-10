package com.android.models;

import org.json.JSONObject;

public class Item {

	public String title;
	public JSONObject json;

	public Item(String t, JSONObject j){
		title=t;
		json=j;
	}

}
