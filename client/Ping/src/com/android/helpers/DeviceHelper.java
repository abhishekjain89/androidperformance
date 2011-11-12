package com.android.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.models.Device;
import com.android.utils.DeviceUtil;

public class DeviceHelper {

	
	public static Device deviceHelp(Context context){
		
		//String phoneDetail = runPhoneDetail(context);
		//String networkDetail = runNetworkDetail(context);
		//return new Device(phoneDetail,networkDetail);
		
		return runFullDetail(context);
	}
	
	public static Device runFullDetail(Context context) {
		DeviceUtil deviceUtil = new DeviceUtil();
		return deviceUtil.getFullDetail(context);
	}
	
	public static String runPhoneDetail(Context context) {
		String srvnName = context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		DeviceUtil deviceUtil = new DeviceUtil();
		
		return deviceUtil.getPhoneDetail(telephonyManager);
		
	}
	
	public static String runNetworkDetail(Context context) {
		String srvnName = context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		DeviceUtil deviceUtil = new DeviceUtil();
		
		String phoneDetail = deviceUtil.getNetworkDetail(telephonyManager);


		phoneDetail += "\n" + deviceUtil.getConnectivityDetail(context);
		
		return phoneDetail;
	}

}
