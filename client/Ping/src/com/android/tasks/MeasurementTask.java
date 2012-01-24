package com.android.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.Session;
import com.android.activities.RunActivity.MeasurementListener;
import com.android.helpers.DeviceHelper;
import com.android.helpers.PingHelper;
import com.android.helpers.ThreadPoolHelper;
import com.android.listeners.BaseResponseListener;
import com.android.listeners.ResponseListener;
import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Ping;
import com.android.utils.HTTPUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class MeasurementTask extends ServerTask{

	public MeasurementTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}
	Measurement measurement; 
	ArrayList<Ping> pings = new ArrayList<Ping>();
	
	@Override
	public void runTask() {
		
		
		// TODO Run ping task with list of things such as ip address and number of pings	

		ThreadPoolHelper serverhelper = new ThreadPoolHelper(10,30);
		
		String[] dstIps = {"143.215.131.173", "143.225.229.254","128.48.110.150","localhost"};
			
		for(int i=0;i<dstIps.length;i++)
			serverhelper.execute(new PingTask(getContext(),new HashMap<String,String>(), dstIps[i], 5, new MeasurementListener()));
		serverhelper.execute(new DeviceTask(getContext(),new HashMap<String,String>(), new MeasurementListener()));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while(serverhelper.getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			Log.v(this.toString(), "left: " + serverhelper.getThreadPoolExecutor().getActiveCount() + " pings: " + pings.size());
		}
		
		
		
		measurement.setPings(pings);
		getResponseListener().onCompleteMeasurement(measurement);
		
		JSONObject object = measurement.toJSON();
		
		HTTPUtil http = new HTTPUtil();
		
		try {
			String output = http.request(this.getReqParams(), "POST", "measurement", "", object.toString());
			System.out.println(object.toString());
			System.out.println(output);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "Measurement Task";
	}
	
	
	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			Message msg=Message.obtain(pingHandler, 0, response);
			pingHandler.sendMessage(msg);		
		}
		
		public void onComplete(String response) {
		
		}

		public void onCompleteMeasurement(Measurement response) {
			Message msg=Message.obtain(measurementHandler, 0, response);
			measurementHandler.sendMessage(msg);
			
		}

		public void onCompleteDevice(Device response) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	private Handler pingHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				Ping p=(Ping)msg.obj;
				pings.add(p);
				if(getResponseListener() != null)
				{
					getResponseListener().onCompletePing(p);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Handler measurementHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				measurement=(Measurement)msg.obj;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}
