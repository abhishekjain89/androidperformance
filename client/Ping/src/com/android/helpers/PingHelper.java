package com.android.helpers;

import com.android.models.Measurement;
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
	public static Ping pingHelp(String ip_address, String count) {
		Ping p 			= null;
		String ip 		= ip_address;
		String cmd 		= "ping";
		String options 	= "-c " + count;
		String output 	= "";

		cmdUtil = new CommandLineUtil();
		output = cmdUtil.runCommand(cmd, ip, options);
		pingOutput = output;
		
		Measurement ping_measurement = ParseUtil.PingParser(output);
		
		
		return null;
	}

	public static String getPingOutput() {
		return pingOutput;
	}

	
}