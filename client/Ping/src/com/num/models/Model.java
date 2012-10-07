package com.num.models;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public interface Model {
	
	public JSONObject toJSON();
	public int getIcon();
	public String getTitle();
	public ArrayList<Row> getDisplayData(Context context);

}
