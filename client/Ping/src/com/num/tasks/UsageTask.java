package com.num.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.num.helpers.AppUsageHelper;
import com.num.helpers.DeviceHelper;
import com.num.helpers.PingHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Measurement;
import com.num.models.Ping;
import com.num.models.Usage;
import com.num.utils.DeviceUtil;
import com.num.utils.HTTPUtil;
import com.num.utils.SHA1Util;

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
	boolean getAll;
	
	public UsageTask(Context context, Map<String, String> reqParams, boolean getAll,
			ResponseListener listener) {
		super(context, reqParams, listener);
		this.getAll = getAll;
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
