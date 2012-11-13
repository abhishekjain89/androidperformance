package com.num.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.num.database.datasource.LatencyDataSource;
import com.num.helpers.PingHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.LastMile;
import com.num.models.Ping;
import com.num.models.PingData;
import com.num.models.WarmupExperiment;
import com.num.utils.DeviceUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class WarmupSequenceTask extends ServerTask {
	
	public WarmupSequenceTask(Context context, ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);		
	}

	@Override
	public void runTask() {
		
		WarmupExperiment experiment = new WarmupExperiment();
		
		PingHelper.warmupSequenceHelp(experiment);
		
		getResponseListener().onCompleteWarmupExperiment(experiment);
		
		
	}

	@Override
	public String toString() {
		return "WarmupSequence Task";
	}
}
