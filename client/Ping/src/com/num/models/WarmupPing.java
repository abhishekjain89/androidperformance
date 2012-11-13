package com.num.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;
import com.num.Values;

public class WarmupPing implements Model{

	
	double value = -1;
	int sequence_count=-1;
	double period=-1;
		
	public WarmupPing(double value,int count, double gap) {
		this.value = value;
		this.sequence_count = count;
		this.period = (count-1)*gap;
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			
			obj.putOpt("value", value);
			obj.putOpt("sequence", sequence_count);
			obj.putOpt("period", period);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	public String getTitle() {
		return "Warmup_Ping";
	}
	
	public int getIcon() {
		return R.drawable.png;
	}

	public ArrayList<Row> getDisplayData(Context context) {
		return null;
	}


}
