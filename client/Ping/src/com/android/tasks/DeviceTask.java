package com.android.tasks;

import java.util.HashMap;

import android.content.Context;

import com.android.helpers.DeviceHelper;
import com.android.listeners.ResponseListener;

public class DeviceTask extends ServerTask{

	private String deviceInfo;
	
	public DeviceTask(Context context, ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
	}

	@Override
	public void runTask() {
		try {
			DeviceHelper dh = new DeviceHelper();
			String phoneDetail = dh.runPhoneDetail(getContext());
			String networkDetail = dh.runNetworkDetail(getContext());
			
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
	}

	@Override
	public String toString() {
		return "DeviceTask";
	}
	
}
