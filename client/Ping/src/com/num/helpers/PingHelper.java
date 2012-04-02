package com.num.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.num.models.Address;
import com.num.models.Measure;
import com.num.models.Ping;
import com.num.utils.CommandLineUtil;
import com.num.utils.ParseUtil;

public class PingHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String pingOutput;
	
	public static Ping firstHopHelp(Address address, int count) {

		//Ping p=new Ping(ipSrc, address ,ping_measurement, "firsthop");
		return null;
	}
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Ping pingHelp(Address address, int count, String type) {
		Ping p 			= null;
		String ipDst 	= address.getIp();
		String cmd 		= "ping";
		String options 	= "-c " + count;
		String output 	= "";

		cmdUtil = new CommandLineUtil();

		if (type.equals("firsthop")) {
			int hopCount = 1;
			options += " -t " + hopCount;
			output = cmdUtil.runCommand(cmd, ipDst, options);
		}
		else {
			output = cmdUtil.runCommand(cmd, ipDst, options);
		}
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
		if (type.equals("firsthop")) {
			p=new Ping(ipSrc, address ,ping_measurement, type);			
		}
		else {
			p=new Ping(ipSrc, address ,ping_measurement, "ping");
		}
		
		
		return p;
	}

	public static String getPingOutput() {
		return pingOutput;
	}

	
}