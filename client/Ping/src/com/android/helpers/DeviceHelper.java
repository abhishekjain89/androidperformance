package com.android.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import com.android.utils.DeviceUtil;

public class DeviceHelper {
	
	public String phoneDetail;
	
	public String networkDetail;
	
	public String runPhoneDetail(Context context) {
		String srvnName = context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		DeviceUtil deviceUtil = new DeviceUtil();
		
		phoneDetail = deviceUtil.getPhoneDetail(telephonyManager);
		
		return phoneDetail;
	}
	
	public String runNetworkDetail(Context context) {
		String srvnName = context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		DeviceUtil deviceUtil = new DeviceUtil();
		
		phoneDetail = deviceUtil.getNetworkDetail(telephonyManager);


		phoneDetail += "\n" + deviceUtil.getConnectivityDetail(context);
		
		return phoneDetail;
	}

}
