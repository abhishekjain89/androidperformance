package com.ping.utils;

import com.ping.models.Network;
import com.ping.models.State;

import android.content.Context;

public class StateUtil {
	
	public Context context;
	Network network;
	DeviceUtil util;
	
	public StateUtil(Context c){
		this.context = c;
		util = new DeviceUtil();
		network = util.getNetworkDetail(context);
	}
	
	public boolean networkInUse(){
		return false;
	}
	
	public State createState(){
		State s  = new State();
		
		s.setNetworkType(network.getNetworkType());
		
		if(s.getNetworkType().equals("Mobile"))
			s.setCellId(network.getCellId());
		else
			s.setCellId("");
		
		s.setLocal_time(util.getLocalTime());
		s.setTime(util.getUTCTime());
		s.setDeviceid(util.getDeviceId(context));
		
		return s;
		
	}
	
	public boolean isNetworkClear(){
		
		
		//if(!network.getConnectionType().equals("Mobile")) return false;
		//if(!network.getDataActivity().equals("DATA_ACTIVITY_NONE")) return false;
		
		return true;
		
	}

}
