package com.num.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.num.tasks.ServerTask;

public class IDThreadPoolExecutor extends ThreadPoolExecutor{
	
	public IDThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	
	public void runTask(ServerTask task)
	{
		this.execute(task);
		
		
	}

}
