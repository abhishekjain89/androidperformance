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
public class PingTask extends ServerTask{
	String dstIp;
	int count;
	public PingTask(Context context, Map<String, String> reqParams, String dstIp, int count,
			ResponseListener listener) {
		super(context, reqParams, listener);
		this.dstIp = dstIp;
		this.count = count;
	}

	@Override
	public void runTask() {
		
		Ping ping = PingHelper.pingHelp(dstIp, count);
		this.getResponseListener().onCompletePing(ping);
	}

	@Override
	public String toString() {
		return "Ping Task";
	}
	

}
