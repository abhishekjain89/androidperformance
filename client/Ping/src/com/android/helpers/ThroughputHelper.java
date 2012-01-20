package com.android.helpers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.models.Measure;
import com.android.models.Ping;
import com.android.models.Throughput;
import com.android.utils.CommandLineUtil;
import com.android.utils.ParseUtil;

public class ThroughputHelper {
	
	public static CommandLineUtil cmdUtil;
	public static String throughputOutput;
	
	
	/**
	 * Pinghelp helps run ping command by creating cmd and inputs
	 * @return
	 */
	public static Throughput pingHelp(String ip_address, String srcPort, String dstPort) {
		
		String ipDst 	= ip_address;
		String cmd 		= "netperf ";
		cmd += "-H " + ip_address;
		cmd += "-p " + dstPort + "  -- -P" + srcPort;
		String output 	= "";

		cmdUtil = new CommandLineUtil();
		output = cmdUtil.runCommand(cmd);
		
		Throughput throughput =  ParseUtil.ThroughputParser(output);
		throughput.setDstIp(ip_address);
		return throughput;
		
	}



}
