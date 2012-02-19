package com.ping.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ping.models.Link;
import com.ping.models.Measure;
import com.ping.models.Ping;
import com.ping.models.Throughput;
import com.ping.utils.CommandLineUtil;
import com.ping.utils.ParseUtil;
import com.ping.utils.ThroughputUtil;

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
