package com.android.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.Values;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.FakeListener;
import com.android.listeners.ResponseListener;
import com.android.models.Battery;
import com.android.models.Device;
import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.models.Network;
import com.android.models.Ping;
import com.android.models.Sim;
import com.android.models.Throughput;
import com.android.models.Usage;
import com.android.models.Wifi;
import com.android.models.WifiNeighbor;
import com.android.models.WifiPreference;
import com.android.utils.GPSUtil;
import com.android.utils.GPSUtil.LocationResult;
import com.android.utils.HTTPUtil;
import com.android.utils.NeighborWifiUtil;
import com.android.utils.NeighborWifiUtil.NeighborResult;
import com.android.utils.SignalUtil;
import com.android.utils.WifiUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class SummaryTask extends ServerTask{
	
	ThreadPoolHelper serverhelper;
	
	
	public SummaryTask(Context context,
			ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);
		
		ThreadPoolHelper serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,Values.THREADPOOL_KEEPALIVE_SEC);
	}
	
	public void killAll(){
		try{
		serverhelper.shutdown();
		}
		catch(Exception e){
			
		}
	}
	
	public void runTask() {

		HTTPUtil http = new HTTPUtil();
		
		try {
			String output = http.request(this.getReqParams(), "POST", "summary", "", "".toString());
			
			getResponseListener().onCompleteSummary(new JSONObject(output));

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public String toString() {
		return "Summary Task";
	}
}