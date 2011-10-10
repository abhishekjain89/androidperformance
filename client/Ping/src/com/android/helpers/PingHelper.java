package com.android.helpers;

import com.android.models.Ping;
import com.android.utils.CommandLineUtil;
import com.android.utils.ParseUtil;

public class PingHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String ip_address = "localhost";
	public static String pingOutput;
	
	/**
	 * PingHelp helps run ping command by creating cmd and inputs
	 * @param p - Ping object
	 * @return
	 */
	public static Ping pingHelp(Ping p)
	{
		String ip 		= p.getDstIp();
		String cmd 		= "ping";
		String options 	= "-c 5";
		String output 	= "";
		cmdUtil = new CommandLineUtil();
		output = cmdUtil.runCommand(cmd, ip, options);
		pingOutput = output;
		// Parse output and edit p
		// p.~~~~
		return p;
	}
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Ping pingHelp()
	{
		Ping p 			= null;
		String ip 		= ip_address;
		String cmd 		= "ping";
		String options 	= "-c 5";
		String output 	= "";

		cmdUtil = new CommandLineUtil();
		output = cmdUtil.runCommand(cmd, ip, options);
		pingOutput = output;
		
		Ping ping_output = ParseUtil.PingParser(output);
		
		
		return ping_output;
	}

	public static String getPingOutput() {
		return pingOutput;
	}

	
}