package com.num.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.num.models.*;
import com.num.utils.BatteryUtil;
import com.num.utils.DeviceUtil;
import com.num.utils.StateUtil;

public class DeviceHelper {


	public static Measurement deviceHelp(Context context, Measurement info){

		return runFullDetail(context, info);
	}

	public static Measurement runFullDetail(Context context, Measurement info) {

		DeviceUtil deviceUtil = new DeviceUtil();
		StateUtil util = new StateUtil(context);
		State state = util.createState();
		info.setDevice(deviceUtil.getDeviceDetail(context,info));
		info.setNetwork(deviceUtil.getNetworkDetail(context));		
		info.setSim(deviceUtil.getSimDetail(context));
		info.setTime(deviceUtil.getUTCTime());//
		info.setLocalTime(deviceUtil.getLocalTime());//
		info.setDeviceId(deviceUtil.getDeviceId(context));//
		info.setState(state);

		return info;

	}
}
