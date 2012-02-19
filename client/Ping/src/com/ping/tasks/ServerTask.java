package com.ping.tasks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.ping.listeners.ResponseListener;

public abstract class ServerTask implements Runnable{

	private Context context;
	private Map<String,String> reqParams;
	private ResponseListener listener;

	public ServerTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super();
		this.context = context;
		this.reqParams = reqParams;
		this.listener = listener;
	}

	public void run() {
		
		long startTime = System.currentTimeMillis();
		
		this.runTask();
		
		long endTime = System.currentTimeMillis();
		Log.i(toString(), "Total execution time: " + (endTime-startTime) + " ms");
		

	}

	public abstract void runTask();
	
	public abstract String toString();
	
	public Context getContext()
	{
		return context;
	}
	
	public HashMap<String, String> getReqParams()
	{
		return (HashMap<String, String>) reqParams;
	}
	
	public ResponseListener getResponseListener()
	{
		return listener;
	}
	
	
}
