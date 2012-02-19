package com.ping.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ping.helpers.AppUsageHelper;
import com.ping.helpers.DeviceHelper;
import com.ping.helpers.PingHelper;
import com.ping.listeners.ResponseListener;
import com.ping.models.Measurement;
import com.ping.models.Ping;
import com.ping.models.Usage;
import com.ping.utils.HTTPUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class UsageTask extends ServerTask{
	String dstIp;
	int count;
	
	public UsageTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}

	@Override
	public void runTask() {
		
		Usage usage = AppUsageHelper.getUsageData(getContext());
		getResponseListener().onCompleteUsage(usage);
	}

	@Override
	public String toString() {
		return "Usage Task";
	}
	

}
