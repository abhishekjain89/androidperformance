package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.num.R;

import android.content.Context;

public class Loss implements MainModel
{

	double losspercentage;
	private static String DESCRIPTION = "Measures the loss on the network. Takes about 1 minute";
	
	public String getDescription()
	{
		return DESCRIPTION;
	}
	
	public JSONObject toJSON() {
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("loss", losspercentage);
		} catch (Exception e) {
			e.printStackTrace();
		}// TODO Auto-generated method stub
		return obj;
	}

	public int getIcon() {
		// TODO Auto-generated method stub
		return R.drawable.usage;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "Loss Measurement";
	}

	public ArrayList<Row> getDisplayData(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
