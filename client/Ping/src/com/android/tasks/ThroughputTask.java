package com.android.tasks;

import java.util.HashMap;

import android.content.Context;

import com.android.listeners.ResponseListener;

public class ThroughputTask extends ServerTask{

	private String data;
	
	public ThroughputTask(Context context, String data, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		this.setData(data);
	}

	@Override
	public void runTask() {
		
		try {
			
			// Throughput  tp = ThroughputHelper.help(getData());
			// getResponseListener().onCompleteThroughput(tp);
			
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}

	@Override
	public String toString() {
		return "ThroughputTask";
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
}
