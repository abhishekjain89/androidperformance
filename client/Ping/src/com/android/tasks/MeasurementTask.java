package com.android.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.helpers.DeviceHelper;
import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.utils.HTTPUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class MeasurementTask extends ServerTask{

	public MeasurementTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}

	@Override
	public void runTask() {
		
		
		// TODO Run ping task with list of things such as ip address and number of pings	
		ArrayList pings = new ArrayList<Ping>();
		
		Ping ping = (PingHelper.pingHelp("localhost", 5));
		pings.add(ping);
		getResponseListener().onCompletePing(ping);
		
		Measurement measurement = DeviceHelper.deviceHelp(getContext());
		//getResponseListener().onCompleteMeasurement(measurement);
		measurement.setPings(pings);
		
		JSONObject object = measurement.toJSON();
		
		HTTPUtil http = new HTTPUtil();
		
		try {
			//String output = http.request(this.getReqParams(), "POST", "measurement", "", object.toString());
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public String toString() {
		return "Measurement Task";
	}
	

}
