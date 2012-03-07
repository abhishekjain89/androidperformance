package com.num.tasks;

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

import com.num.Values;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.State;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.models.WifiNeighbor;
import com.num.models.WifiPreference;
import com.num.utils.DeviceUtil;
import com.num.utils.GPSUtil;
import com.num.utils.HTTPUtil;
import com.num.utils.NeighborWifiUtil;
import com.num.utils.SignalUtil;
import com.num.utils.StateUtil;
import com.num.utils.WifiUtil;
import com.num.utils.GPSUtil.LocationResult;
import com.num.utils.NeighborWifiUtil.NeighborResult;

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