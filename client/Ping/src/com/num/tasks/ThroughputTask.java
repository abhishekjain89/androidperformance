package com.num.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.num.helpers.ThroughputHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Throughput;
import com.num.utils.ThroughputData;
import com.num.utils.ThroughputDataSource;

public class ThroughputTask extends ServerTask{

	
	
	public ThroughputTask(Context context, Map<String, String> reqParams, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		
	}

	@Override
	public void runTask() {
		
		try {
			
			Throughput t = ThroughputHelper.getThroughput(getContext());
			getResponseListener().onCompleteThroughput(t);
			String downlink = "" + t.downLink.speedInBits();
			String uplink = "" + t.upLink.speedInBits();
			ThroughputDataSource datasource = new ThroughputDataSource(getContext());
			datasource.open();

			List<ThroughputData> values = datasource.getAllThroughputData();
			ThroughputData td = datasource.createThroughput(downlink, uplink);
			values = datasource.getAllThroughputData();
			datasource.close();
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}

	@Override
	public String toString() {
		return "ThroughputTask";
	}

	
}
