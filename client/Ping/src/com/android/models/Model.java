package com.android.models;

import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

public interface Model{
	
	public JSONObject toJSON();
	public String getTitle();
	public ArrayList<Row> getDisplayData();

}
