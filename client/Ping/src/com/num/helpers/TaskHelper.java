package com.num.helpers;

import java.util.HashMap;

import android.content.Context;

import com.num.activities.FullDisplayActivity.MeasurementListener;
import com.num.listeners.BaseResponseListener;
import com.num.tasks.AllPingTask;
import com.num.tasks.ServerTask;
import com.num.tasks.WifiTask;

public class TaskHelper {
	
	public static ServerTask getTask(String key, Context context, BaseResponseListener listener){
		
		if(key.equals("latency")){
			return new AllPingTask(context, listener);
		}
		else if(key.equals("wifi")){
			return new WifiTask(context,new HashMap<String,String>(), listener);
		}
		
		return null;
	}

}
