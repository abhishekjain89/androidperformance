package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.num.R;

import android.content.Context;

public class Loss implements MainModel
{

	int total;
	int received_count;
	double losspercentage;
	private static String DESCRIPTION = "Measures the loss on the network. Takes about 1 minute";
	
	
	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getReceived_count() {
		return received_count;
	}

	public void setReceived_count(int received_count) {
		this.received_count = received_count;
	}

	public double getLosspercentage() {
		return losspercentage;
	}

	public void setLosspercentage(double losspercentage) {
		this.losspercentage = losspercentage;
	}
	
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
	
	public ArrayList<Row> getDisplayData(Context context) {
		// TODO Auto-generated method stub
		ArrayList<Row> data = new ArrayList<Row>();
		return null;
	}

	public int getIcon() {
		// TODO Auto-generated method stub
		return R.drawable.usage;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "Loss Measurement";
	}


	
	
}
