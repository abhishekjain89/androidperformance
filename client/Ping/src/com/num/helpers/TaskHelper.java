package com.num.helpers;

import java.util.HashMap;

import android.content.Context;

import com.num.activities.FullDisplayActivity.MeasurementListener;
import com.num.listeners.BaseResponseListener;
import com.num.tasks.AllPingTask;
import com.num.tasks.BatteryTask;
import com.num.tasks.ServerTask;
import com.num.tasks.ThroughputTask;
import com.num.tasks.UsageTask;
import com.num.tasks.WifiTask;

public class TaskHelper {
	
	public static ServerTask getTask(String key, Context context, BaseResponseListener listener){
		
		if(key.equals("latency")){
			return new AllPingTask(context, listener);
		}
		else if(key.equals("wifi")){
			return new WifiTask(context,new HashMap<String,String>(), listener);
		}
		else if(key.equals("battery")){
			return new BatteryTask(context,new HashMap<String,String>(), listener);
		}
		else if(key.equals("throughput")){
			return new ThroughputTask(context,new HashMap<String,String>(), listener);
		}
		else if(key.equals("usage")){
			return new UsageTask(context,new HashMap<String,String>(),true, listener);
		}
		
		
		return null;
	}

}
