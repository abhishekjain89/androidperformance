package com.android;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import android.app.Application;

import com.android.utils.IDThreadPoolExecutor;



public class Session extends Application {
	
	private IDThreadPoolExecutor tpe;
	
	
	public Session(){
			
	tpe = new IDThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
	
	}
	
	public IDThreadPoolExecutor getThreadPoolExecutor()
	{
		return  tpe;
	}
	

}