package com.num.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Message;

import com.num.Values;
import com.num.database.datasource.ThroughputDataSource;
import com.num.helpers.LossHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.helpers.ThroughputHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.Loss;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.utils.DeviceUtil;

public class LossTask extends ServerTask{
	
	ThreadPoolHelper serverhelper;
	
	public LossTask(Context context, Map<String, String> reqParams, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,
				Values.THREADPOOL_KEEPALIVE_SEC);
	}

	@Override
	public void runTask() {
		
		try {
			
			Loss l = LossHelper.getLoss();			
			
			getResponseListener().onCompleteLoss(l);
			//serverhelper.execute(new MeasurementTask(getContext(), t, true, new FakeListener()));
			
			
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}

	@Override
	public String toString() {
		return "LossTask";
	}
	
}
