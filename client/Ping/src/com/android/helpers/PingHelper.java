package com.android.helpers;

import com.android.models.Ping;
import com.android.utils.CommandLineUtil;

public class PingHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String ip_address;
	
	/**
	 * PingHelp helps run ping command by creating cmd and inputs
	 * @param p - Ping object
	 * @return
	 */
	private Ping pingHelp(Ping p)
	{
		String ip 		= p.getDstIp();
		String cmd 		= "ping";
		String options 	= "-c 5";
		String output 	= "";
		
		output = cmdUtil.runCommand(cmd, ip, options);
		// Parse output and edit p
		// p.~~~~
		return p;
	}
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	private Ping pingHelp()
	{
		Ping p 			= null;
		String ip 		= ip_address;
		String cmd 		= "ping";
		String options 	= "-c 5";
		String output 	= "";
		
		output = cmdUtil.runCommand(cmd, ip, options);
		// Parse output and edit p
		// p.~~~~
		return null;
	}
	
}