package com.num.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class Ipdv implements MainModel
{

	

	ArrayList<IpdvUnit> ipdvlist = new ArrayList<IpdvUnit>();
	private static String DESCRIPTION = "List of all the IP packet delay variation units for one measurement";
	
	public ArrayList<IpdvUnit> getIpdvlist() {
		return ipdvlist;
	}

	public void setIpdvlist(ArrayList<IpdvUnit> ipdvlist) {
		this.ipdvlist = ipdvlist;
	}
	
	public void addToList(IpdvUnit ipdvunit)
	{
		ipdvlist.add(ipdvunit);
	}
	
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		
		try {
			JSONArray array = new JSONArray();
			for(IpdvUnit entry : ipdvlist) {
				array.put(entry.toJSON());
			}
			obj.put("ipdvlist", array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return obj;
	}

	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Row> getDisplayData(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
}