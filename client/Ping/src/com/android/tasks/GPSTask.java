package com.android.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.helpers.DeviceHelper;
import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.utils.HTTPUtil;



public class GPSTask extends ServerTask{
	
	public GPSTask(Context context, Map<String, String> reqParams,ResponseListener listener) {
		super(context, reqParams, listener);
		
	}

	@Override
	public void runTask() {
		
		GPS gps = null;
		this.getResponseListener().onCompleteGPS(gps);
	}

	@Override
	public String toString() {
		return "GPS Task";
	}
	

}
