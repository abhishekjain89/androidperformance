package com.android.tasks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.listeners.ResponseListener;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class MeasurementTask extends ServerTask{

	public MeasurementTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}

	@Override
	public void runTask() {
		Session session =  (Session) getContext().getApplicationContext();
		ServerHelper serverhelper = new ServerHelper(session);
		// TODO Run ping task with list of things such as ip address and number of pings
		
		serverhelper.execute(new PingTask(getContext(),"localhost",5, getResponseListener()));
		
	}

	@Override
	public String toString() {
		return "Measurement Task";
	}

}
