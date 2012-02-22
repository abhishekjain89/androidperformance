package com.ping;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import android.app.Application;

import com.ping.models.Screen;

import com.ping.utils.DeviceUtil;
import com.ping.utils.IDThreadPoolExecutor;



public class Session extends Application{
	
	DeviceUtil util = new DeviceUtil();
	
	public Session(){
		
	}
	
	public ArrayList<Screen> screenBuffer = new ArrayList<Screen>();
	
	public void AddScreen(boolean isOn){
		screenBuffer.add(new Screen(util.getUTCTime(),util.getLocalTime(),isOn));
		
	}
	

}