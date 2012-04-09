package com.num.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;

import com.num.models.Link;
import com.num.models.Measure;
import com.num.models.Ping;
import com.num.models.Throughput;
import com.num.utils.CommandLineUtil;
import com.num.utils.ParseUtil;
import com.num.utils.ThroughputUtil;

public class ThroughputHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String throughputOutput;
	
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Throughput getThroughput(Context context) {
		
		Throughput t = new Throughput();
		
		try {
			Link up=ThroughputUtil.uplinkmeasurement(context);
			t.setUpLink(up);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Link down=ThroughputUtil.downlinkmeasurement(context);
			t.setDownLink(down);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TRIGGER DB
		
		return t;
		
	}



}
