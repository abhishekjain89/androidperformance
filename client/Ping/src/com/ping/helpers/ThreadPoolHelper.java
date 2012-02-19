package com.ping.helpers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.ping.Session;
import com.ping.tasks.ServerTask;
import com.ping.utils.IDThreadPoolExecutor;


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
	
	public IDThreadPoolExecutor getThreadPoolExecutor(){
		return tpe;
		
	}
	public void shutdown(){
		tpe.shutdownNow();
		
	}
	
	
	
	
	
	
	
	
	

}
