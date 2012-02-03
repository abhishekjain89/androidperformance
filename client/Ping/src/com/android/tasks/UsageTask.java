package com.android.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.helpers.AppUsageHelper;
import com.android.helpers.DeviceHelper;
import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.models.Usage;
import com.android.utils.HTTPUtil;

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
