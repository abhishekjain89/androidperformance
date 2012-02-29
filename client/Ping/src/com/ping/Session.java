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
	public int gps_count=0;
	public int throughput_count = 0;
	
	public Session(){
		
	}
	
	public ArrayList<Screen> screenBuffer = new ArrayList<Screen>();
	
	public void AddScreen(boolean isOn){
		screenBuffer.add(new Screen(util.getUTCTime(),util.getLocalTime(),isOn));
		
	}
	
	public void incrementGPS(){
		gps_count++;
		gps_count%=4;
	}
	
	public void decrementGPS(){
		gps_count--;
		gps_count%=4;
	}
	
	public void incrementThroughput(){
		throughput_count++;
		throughput_count%=Values.THROUGHPUT_FREQ;
	}
	
	public void decrementThroughput(){
		throughput_count--;
		throughput_count%=Values.THROUGHPUT_FREQ;
	}
	
	public boolean doGPS(){
		return gps_count==0;
	}
	
	public boolean doThroughput(){
		return throughput_count==0;
	}
	
	

}