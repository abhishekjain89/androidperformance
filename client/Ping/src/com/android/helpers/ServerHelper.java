package com.android.helpers;

import com.android.Session;
import com.android.tasks.ServerTask;


public class ServerHelper {
	
	private String TAG = "ServerHelper";
	
	private Session session;
	
	public ServerHelper(Session s){
		
		session = s;
	}

	
	public void execute(ServerTask task)
	{
		
		session.getThreadPoolExecutor().runTask(task);
	}
	
	
	
	
	
	

}
