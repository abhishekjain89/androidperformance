package com.num.tasks;

import java.util.HashMap;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.num.Values;
import com.num.listeners.ResponseListener;
import com.num.models.ClientLog;

public abstract class ServerTask implements Runnable{

	private Context context;
	private Map<String,String> reqParams;
	private ResponseListener listener;
	private Values session;

	public ServerTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super();
		this.context = context;
		this.reqParams = reqParams;
		this.listener = listener;
		session = (Values) getContext().getApplicationContext();
	}

	public void run() {

		long startTime = System.currentTimeMillis();
		
		try{
			this.runTask();
		}
		catch(Exception e){
			ClientLog.log(getContext(), e, toString());
		}

		long endTime = System.currentTimeMillis();
		Log.i(toString(), "Total execution time: " + (endTime-startTime) + " ms");


	}

	public abstract void runTask();

	public abstract String toString();

	public Context getContext()
	{
		return context;
	}

	public Values getValues()
	{
		return session;
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
