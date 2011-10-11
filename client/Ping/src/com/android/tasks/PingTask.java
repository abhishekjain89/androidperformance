package com.android.tasks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.helpers.PingHelper;
import com.android.listeners.ResponseListener;
import com.android.models.Ping;



/**
 * @author abhishekjain
 *
 */
public class PingTask extends ServerTask {
	
	private String ipaddress;
	private int count;

	public PingTask(Context context, String ipaddress, int count,
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		this.ipaddress = ipaddress;
		this.count = count;
	}

	@Override
	public void runTask() {
		
		//Session session = (Session)getContext().getApplicationContext();
		
		try {
		
			Ping p = PingHelper.pingHelp(getIpaddress(), getCount());	
		
			getResponseListener().onCompletePing(p);

			// Call task that sends data to back end
			
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}


	public String toString() {
		return "PingTask";
	}
	
	public String getIpaddress() {
		return ipaddress;
	}


	public int getCount() {
		return count;
	}

}
