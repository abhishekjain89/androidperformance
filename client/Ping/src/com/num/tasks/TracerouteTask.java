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
	int endindex = -1;
	int startindex = -1;
	
	public TracerouteTask(Context context, Map<String, String> reqParams,
			Address dst, int endindex, ResponseListener listener) {
		super(context, reqParams, listener);
		this.dst = dst;
		this.startindex = 2;
		this.endindex = endindex;
	}
	
	public TracerouteTask(Context context, Map<String, String> reqParams,
			Address dst, int startindex, int endindex, ResponseListener listener) {
		super(context, reqParams, listener);
		this.dst = dst;
		this.startindex = startindex;
		this.endindex = endindex;
	}
	
	@Override
	public void runTask() {

		Traceroute traceroute = TracerouteHelper.traceHelp(dst.ip, startindex, endindex);
		/*traceroute.addToList(new TracerouteEntry("cc.gatech",0,1));
		traceroute.addToList(new TracerouteEntry("kacb.gatech",0,2));
		traceroute.addToList(new TracerouteEntry("oit.gatech",0,3));*/
		this.getResponseListener().onCompleteTraceroute(traceroute);
	}

	@Override
	public String toString() {
		return "Traceroute Task";
	}
}
