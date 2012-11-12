package com.num.helpers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


import android.app.Activity;
import android.util.Log;

import com.num.Values;
import com.num.tasks.ServerTask;
import com.num.utils.IDThreadPoolExecutor;


public class ThreadPoolHelper {
	
	private String TAG = "ServerHelper";
	private IDThreadPoolExecutor tpe;

	
	public ThreadPoolHelper(int poolSize,int keepAlive){
		
		tpe = new IDThreadPoolExecutor(poolSize, poolSize, keepAlive, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
	}

	
	public void execute(ServerTask task)
	{
		
		tpe.runTask(task);
	}
	
	public void executeOnUIThread(Activity activity, ServerTask task)
	{		
		activity.runOnUiThread(task);
	}
	
	public IDThreadPoolExecutor getThreadPoolExecutor(){
		return tpe;
		
	}
	public void shutdown(){
		tpe.shutdownNow();		
	}
	
	public void waitOnTasks() {
		try {
			Thread.sleep(Values.SHORT_SLEEP_TIME);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(getThreadPoolExecutor().getActiveCount()>0){
			try {
				Thread.sleep(Values.SHORT_SLEEP_TIME);
			
			} catch (InterruptedException e) {
				shutdown();
				return;
			}
		}
	}
	
	
	public void waitOnTasks(int seconds) {
		
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		
		try {
			Thread.sleep(Values.SHORT_SLEEP_TIME);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(getThreadPoolExecutor().getActiveCount()>0 && end-start<seconds*1000){
			try {
				Thread.sleep(Values.SHORT_SLEEP_TIME);
			
			} catch (InterruptedException e) {
				shutdown();
				return;
			}
			
			end = System.currentTimeMillis(); 
		}
	}
	
	
	
	
	
	
	
	
	

}
