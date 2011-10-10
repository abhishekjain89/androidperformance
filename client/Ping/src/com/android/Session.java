package com.android;

import com.android.utils.IDThreadPoolExecutor;

import android.app.Application;



public class Session extends Application {
	
	private static IDThreadPoolExecutor tpe;


	public IDThreadPoolExecutor getThreadPoolExecutor()
	{
		return  tpe;
	}
	

}