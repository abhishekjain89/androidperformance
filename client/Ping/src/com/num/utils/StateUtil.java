package com.num.utils;

import com.num.models.Network;
import com.num.models.State;

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

	public boolean networkAvailable(){
		if(network.getConnectionType()==null) return true;
		return false;
	}
	
	public State createState(){
		State s  = new State();

		try{


			s.setNetworkType(network.getConnectionType());

			if(s.getNetworkType().equals("Mobile"))
				s.setCellId(network.getCellId());
			else
				s.setCellId("");

			s.setLocal_time(util.getLocalTime());
			s.setTime(util.getUTCTime());
			s.setDeviceid(util.getDeviceId(context));
		}
		catch (Exception e){
			return null;
		}
		return s;

	}

	public boolean isNetworkClear(){


		//if(!network.getConnectionType().equals("Mobile")) return false;
		//if(!network.getDataActivity().equals("DATA_ACTIVITY_NONE")) return false;

		return true;

	}

}
