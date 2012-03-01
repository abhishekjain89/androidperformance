package com.ping.tasks;

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

import com.ping.Values;
import com.ping.helpers.ThreadPoolHelper;
import com.ping.listeners.BaseResponseListener;
import com.ping.listeners.FakeListener;
import com.ping.listeners.ResponseListener;
import com.ping.models.Battery;
import com.ping.models.Device;
import com.ping.models.GPS;
import com.ping.models.Measurement;
import com.ping.models.Network;
import com.ping.models.Ping;
import com.ping.models.Sim;
import com.ping.models.State;
import com.ping.models.Throughput;
import com.ping.models.Usage;
import com.ping.models.Wifi;
import com.ping.models.WifiNeighbor;
import com.ping.models.WifiPreference;
import com.ping.utils.DeviceUtil;
import com.ping.utils.GPSUtil;
import com.ping.utils.HTTPUtil;
import com.ping.utils.NeighborWifiUtil;
import com.ping.utils.SignalUtil;
import com.ping.utils.StateUtil;
import com.ping.utils.WifiUtil;
import com.ping.utils.GPSUtil.LocationResult;
import com.ping.utils.NeighborWifiUtil.NeighborResult;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class ParameterTask extends ServerTask{

	ThreadPoolHelper serverhelper;


	public ParameterTask(Context context,
			ResponseListener listener) {
		super(context, new HashMap<String,String>(), listener);

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(getValues().THREADPOOL_MAX_SIZE,getValues().THREADPOOL_KEEPALIVE_SEC);
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

			StateUtil util = new StateUtil(getContext());
			State state = util.createState();
			
			if(state.getCellId().equals("65535")){
				getResponseListener().onComplete("true");
				return;
			}
			

			Log.v(toString(),state.toJSON().toString());
			String output = http.request(this.getReqParams(), "POST", "parameter_check", "", state.toJSON().toString());

			if(output.contains("1"))
				getResponseListener().onComplete("true");
			else
				getResponseListener().onFail("true");


		} catch (Exception e) {

			getResponseListener().onComplete("true");
			e.printStackTrace();
		}


	}

	@Override
	public String toString() {
		return "Paramater Task";
	}
}