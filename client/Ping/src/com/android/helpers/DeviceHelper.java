package com.android.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.models.*;
import com.android.utils.DeviceUtil;

public class DeviceHelper {

	
	public static Measurement deviceHelp(Context context){
		
		return runFullDetail(context);
	}
	
	public static Measurement runFullDetail(Context context) {

		DeviceUtil deviceUtil = new DeviceUtil();
		GPSHelper gpsH = new GPSHelper();
		Measurement info = new Measurement();
		info.setDevice(deviceUtil.getDeviceDetail(context));		
		info.setNetwork(deviceUtil.getNetworkDetail(context));		
		info.setSim(deviceUtil.getSimDetail(context));
		info.setThroughput(null); // TO BE ADDED
		info.setGps(null); // TO BE ADDED
		info.setTime(deviceUtil.getTime());
		info.setDeviceId(deviceUtil.getDeviceId(context));
		
		
		//GPS gps = gpsH.getGps(context);
		//info.setAltitude(gps.getAltitude());
		//info.setLatitude(gps.getLatitude());
		//info.setLongitude(gps.getLongitude());
		return info;
		
	}
}
