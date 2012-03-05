package com.ping.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ping.helpers.DeviceHelper;
import com.ping.helpers.PingHelper;
import com.ping.listeners.ResponseListener;
import com.ping.models.Measurement;
import com.ping.models.Ping;
import com.ping.utils.HTTPUtil;
import com.ping.utils.SignalUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class DeviceTask extends ServerTask{
	
	Measurement measurement;
	
	public DeviceTask(Context context, Map<String, String> reqParams, ResponseListener listener, Measurement measurement) {
		super(context, reqParams, listener);
		this.measurement = measurement;
	}
 
	@Override
	public void runTask() {
		 
		measurement = DeviceHelper.deviceHelp(getContext(), measurement);
		this.getResponseListener().onCompleteDevice(measurement.getDevice());
		this.getResponseListener().onCompleteNetwork(measurement.getNetwork());
		this.getResponseListener().onCompleteSIM(measurement.getSim());
		this.getResponseListener().onCompleteBattery(measurement.getBattery());
		
	}

	@Override
	public String toString() {
		return "Device Task";
	}
	

}
