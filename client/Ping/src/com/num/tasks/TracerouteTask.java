package com.num.tasks;

import java.util.List;
import java.util.Map;

import android.content.Context;


import com.num.helpers.TracerouteHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;

/*
 * Traceroute Task 
 * set tasks to run and gives ip address to tracroute and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class TracerouteTask extends ServerTask {
	Address dst;
	int index = -1;
	
	
	public TracerouteTask(Context context, Map<String, String> reqParams,
			Address dst, int index, ResponseListener listener) {
		super(context, reqParams, listener);
		this.dst = dst;
		this.index = index;
		
	}
	
		
	@Override
	public void runTask() {


		TracerouteEntry traceroute = TracerouteHelper.TraceHelp(dst.ip, index);
		this.getResponseListener().onCompleteTracerouteHop(traceroute);
	}

	@Override
	public String toString() {
		return "Traceroute Task";
	}
}
