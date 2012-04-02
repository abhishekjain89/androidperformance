package com.num.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.num.helpers.DeviceHelper;
import com.num.helpers.PingHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.Measurement;
import com.num.models.Ping;
import com.num.utils.HTTPUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class PingTask extends ServerTask{
	Address dst;
	int count;
	String type;
	public PingTask(Context context, Map<String, String> reqParams, Address dst, int count, String type,
			ResponseListener listener) {
		super(context, reqParams, listener);
		this.dst  = dst;
		this.count = count;
		this.type = type;
	}

	@Override
	public void runTask() {
		
		Ping ping = PingHelper.pingHelp(dst, count, type);
		this.getResponseListener().onCompletePing(ping);
	}

	@Override
	public String toString() {
		return "Ping Task";
	}
	

}
