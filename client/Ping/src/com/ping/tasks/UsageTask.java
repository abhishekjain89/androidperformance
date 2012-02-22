package com.ping.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.ping.helpers.AppUsageHelper;
import com.ping.helpers.DeviceHelper;
import com.ping.helpers.PingHelper;
import com.ping.listeners.ResponseListener;
import com.ping.models.Measurement;
import com.ping.models.Ping;
import com.ping.models.Usage;
import com.ping.utils.DeviceUtil;
import com.ping.utils.HTTPUtil;
import com.ping.utils.SHA1Util;

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
		
		
		if(getAll){
			HTTPUtil http = new HTTPUtil();
			
			DeviceUtil util = new DeviceUtil();
			String deviceId = util.getDeviceId(getContext());
			
			
			try {
				String output = http.request(this.getReqParams(), "GET", "traffic/?device=" + SHA1Util.SHA1(deviceId) + "&hours=720", "", "".toString());
				
				
				usage.setBackendData(new JSONObject(output));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		getResponseListener().onCompleteUsage(usage);
		
	}

	@Override
	public String toString() {
		return "Usage Task";
	}
	

}
