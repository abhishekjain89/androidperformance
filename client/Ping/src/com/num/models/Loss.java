package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.num.R;

import android.content.Context;

public class Loss implements MainModel
{

	

	int total;
	int lost;
	double losspercentage;
	Ipdv ipdv;
	private static String DESCRIPTION = "Measures the loss on the network. Takes about 1 minute";
	
	
	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public double getLosspercentage() {
		return losspercentage;
	}

	public void setLosspercentage(double losspercentage) {
		this.losspercentage = losspercentage;
	}
	
	public Ipdv getIpdv() {
		return ipdv;
	}

	public void setIpdv(Ipdv ipdv) {
		this.ipdv = ipdv;
	}
	
	public String getDescription()
	{
		return DESCRIPTION;
	}
	
		
	public JSONObject toJSON() {
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("total", total);
			obj.put("lost", lost);
			obj.put("losspercentage", losspercentage);
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
