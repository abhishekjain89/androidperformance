package com.android.tasks;

import java.util.Map;

import android.content.Context;

import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.Ping;



/**
 * @author abhishekjain
 *
 */
public class PingTask extends ServerTask {

	public PingTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}


	@Override
	public void runTask() {
		
		//Session session = (Session)getContext().getApplicationContext();
		
		try {
		
			Ping p = PingHelper.pingHelp("localhost", "5");	
		
			getResponseListener().onCompletePing(p);
		
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}
	
	public String toString() {
		return "PingTask";
	}

}
