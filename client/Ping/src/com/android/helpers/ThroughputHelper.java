package com.android.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.models.Link;
import com.android.models.Measure;
import com.android.models.Ping;
import com.android.models.Throughput;
import com.android.utils.CommandLineUtil;
import com.android.utils.ParseUtil;
import com.android.utils.ThroughputUtil;

public class ThroughputHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String throughputOutput;
	
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Throughput getThroughput() {
		
		Throughput t = new Throughput();
		
		try {
			Link up=ThroughputUtil.uplinkmeasurement();
			t.setUpLink(up);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Link down=ThroughputUtil.downlinkmeasurement();
			t.setDownLink(down);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
		
	}



}
