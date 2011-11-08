package com.android.services;

import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.Session;
import com.android.helpers.ServerHelper;
import com.android.tasks.MeasurementTask;

public class PerformanceService extends Service{

	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;

	public void onCreate() {
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	

}
