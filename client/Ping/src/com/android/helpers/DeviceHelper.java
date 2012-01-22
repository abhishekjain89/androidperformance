package com.android.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.utils.DeviceUtil;

public class DeviceHelper {

	
	public static Measurement deviceHelp(Context context){
		
		//String phoneDetail = runPhoneDetail(context);
		//String networkDetail = runNetworkDetail(context);
		//return new Device(phoneDetail,networkDetail);
		
		return runFullDetail(context);
	}
	
	public static Measurement runFullDetail(Context context) {
		/*
		DeviceUtil deviceUtil = new DeviceUtil();
		GPSHelper gpsH = new GPSHelper();
		Info dev = new Info();
		dev = deviceUtil.getFullDetail(context);
		GPS gps = gpsH.getGps(context);
		dev.setAltitude(gps.getAltitude());
		dev.setLatitude(gps.getLatitude());
		dev.setLongitude(gps.getLongitude());
		return dev;
		*/
		DeviceUtil deviceUtil = new DeviceUtil();
		GPSHelper gpsH = new GPSHelper();
		Measurement info = new Measurement();
		GPS gps = gpsH.getGps(context);
		info = deviceUtil.getFullDetail(context);
//		if (gps.getLatitude() == ("Not Found"))	{
//			info.setAltitude(GPS.getOldAltitude());
//			info.setLatitude(GPS.getOldLatitude());
//			info.setLongitude(GPS.getOldLongitude());
//		}
//		else {
			info.setAltitude(gps.getAltitude());
			info.setLatitude(gps.getLatitude());
			info.setLongitude(gps.getLongitude());
//		}
		return info;
		
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
