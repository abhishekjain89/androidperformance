package com.num.models;

import java.util.ArrayList;

import org.json.JSONObject;

import com.num.R;

import android.content.Context;

public class IpdvUnit implements MainModel
{

	int sequencenumber;
	long ipdv;
	private static String DESCRIPTION = "Measures the IP packet delay variation";
	
	public IpdvUnit(int sequencenumber, long ipdv)
	{
		this.sequencenumber = sequencenumber;
		this.ipdv = ipdv;
	}
	public int getSequencenumber() {
		return sequencenumber;
	}

	public void setSequencenumber(int sequencenumber) {
		this.sequencenumber = sequencenumber;
	}

	public long getIpdv() {
		return ipdv;
	}

	public void setIpdv(long ipdv) {
		this.ipdv = ipdv;
	}
	
	public JSONObject toJSON() {
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("sequence", sequencenumber);
			obj.put("ipdv", ipdv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int getIcon() {
		// TODO Auto-generated method stub
		return R.drawable.usage;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "IpdvUnit";
	}

	public ArrayList<Row> getDisplayData(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return DESCRIPTION;
	}
	
}