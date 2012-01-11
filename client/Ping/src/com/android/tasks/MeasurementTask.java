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
		ArrayList<Ping> pings = new ArrayList<Ping>();
		
		pings.add(PingHelper.pingHelp("localhost", 5));
		pings.add(PingHelper.pingHelp("143.215.131.173", 5));
		pings.add(PingHelper.pingHelp("143.225.229.254", 5));
		pings.add(PingHelper.pingHelp("128.48.110.150", 5));
		
		if(getResponseListener() != null)
		{
			for(Ping ping: pings)
				getResponseListener().onCompletePing(ping);
		}
		
		Measurement measurement = DeviceHelper.deviceHelp(getContext());
		measurement.setPings(pings);
		getResponseListener().onCompleteMeasurement(measurement);
		
		JSONObject object = measurement.toJSON();
		
		HTTPUtil http = new HTTPUtil();
		
		try {
			String output = http.request(this.getReqParams(), "POST", "measurement", "", object.toString());
			System.out.println(object.toString());
			System.out.println(output);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "Measurement Task";
	}
	

}
