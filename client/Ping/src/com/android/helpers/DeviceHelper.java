package com.android.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.models.GPS;
import com.android.models.Measurement;
import com.android.utils.DeviceUtil;

public class DeviceHelper {

	
	public static Measurement deviceHelp(Context context){
		
		return runFullDetail(context);
	}
	
	public static Measurement runFullDetail(Context context) {

		DeviceUtil deviceUtil = new DeviceUtil();
		GPSHelper gpsH = new GPSHelper();
		Measurement info = new Measurement();
		info = deviceUtil.getFullDetail(context);
		GPS gps = gpsH.getGps(context);
		info.setAltitude(gps.getAltitude());
		info.setLatitude(gps.getLatitude());
		info.setLongitude(gps.getLongitude());
		return info;
		
	}
}
