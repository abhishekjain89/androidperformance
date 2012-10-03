package com.num.tasks;

import java.util.Map;

import android.content.Context;

import com.num.listeners.ResponseListener;
import com.num.models.Wifi;
import com.num.utils.NeighborWifiUtil;
import com.num.utils.WifiUtil;

public class WifiTask extends ServerTask{

	public WifiTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runTask() {
		
		WifiUtil w = new WifiUtil();
		Wifi wifiList = new Wifi();
		try{
		wifiList = w.getWifi(this.getContext());
		} catch (Exception e) {
			 
		}
		
		this.getResponseListener().onCompleteWifi(wifiList);
		
	}

	@Override
	public String toString() {
		return "Wifi Task";
	}

}
