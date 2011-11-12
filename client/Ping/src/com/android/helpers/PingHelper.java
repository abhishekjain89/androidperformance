package com.android.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.models.Measure;
import com.android.models.Ping;
import com.android.utils.CommandLineUtil;
import com.android.utils.ParseUtil;

public class PingHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String pingOutput;
	
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Ping pingHelp(String ip_address, int count) {
		Ping p 			= null;
		String ipDst 	= ip_address;
		String cmd 		= "ping";
		String options 	= "-c " + count;
		String output 	= "";

		cmdUtil = new CommandLineUtil();
		output = cmdUtil.runCommand(cmd, ipDst, options);
		pingOutput = output;
		
		Measure ping_measurement = ParseUtil.PingParser(output);

		Socket conn;
		String ipSrc = "";
		try {
			conn = new Socket("www.google.com", 80);
			ipSrc = conn.getLocalAddress().toString(); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		p=new Ping(ipSrc, ipDst ,ping_measurement);
		
		
		return p;
	}

	public static String getPingOutput() {
		return pingOutput;
	}

	
}