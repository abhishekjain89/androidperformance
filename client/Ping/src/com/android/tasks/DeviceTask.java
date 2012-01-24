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
public class DeviceTask extends ServerTask{
	
	public DeviceTask(Context context, Map<String, String> reqParams, ResponseListener listener) {
		super(context, reqParams, listener);
	}

	@Override
	public void runTask() {
		
		Measurement measurement = DeviceHelper.deviceHelp(getContext());
		this.getResponseListener().onCompleteMeasurement(measurement);
	}

	@Override
	public String toString() {
		return "Device Task";
	}
	

}
