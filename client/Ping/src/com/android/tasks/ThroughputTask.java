package com.android.tasks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.helpers.ThroughputHelper;
import com.android.listeners.ResponseListener;
import com.android.models.Throughput;

public class ThroughputTask extends ServerTask{

	
	
	public ThroughputTask(Context context, Map<String, String> reqParams, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		
	}

	@Override
	public void runTask() {
		
		try {
			
			Throughput t = ThroughputHelper.getThroughput();
			getResponseListener().onCompleteThroughput(t);
			
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}

	@Override
	public String toString() {
		return "ThroughputTask";
	}

	
}
