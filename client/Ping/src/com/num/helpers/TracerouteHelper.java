package com.num.helpers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.util.Log;

import com.num.models.Measure;
import com.num.models.Ping;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.utils.CommandLineUtil;
import com.num.utils.ParseUtil;

public class TracerouteHelper {
	
	public static CommandLineUtil cmdUtil;
	public static final String SERVER_ADDRESS = "cc.gatech.edu";

	public static TracerouteEntry TraceHelp(String ipDst, int index)
	{
		
		String cmd 		= "ping";
		String options 	= "-c "+1 + " -t "+index;
		String output 	= "";
		cmdUtil = new CommandLineUtil();
		Log.d("TraceWorker", ""+options);
		output = cmdUtil.runCommand(cmd, ipDst, options);
		//result += (output + "\n");
		
		TracerouteEntry hop = parseResult(output, index);
		return hop;
	}
	
	static TracerouteEntry parseResult(String result, int index)
	{
		String ipAddr = "";
		String ipName="";
		String ipBits = "";
		boolean found = false;
		String parsedResult= "";
		int pos;
		String rtt = "";
		//int hop = startindex;
		for(pos=0; pos<result.length(); pos++)
		{
			parsedResult = "";
			if(result.charAt(pos)=='F')
			{
				found = true;
			}
			if(found==true){
				pos+=5;
				
				while(result.charAt(pos)!=' ')
				{
					parsedResult += result.charAt(pos);
					pos++;
				}
				break;
			}
		}
		if(found==true)
		{
			try {
				ipAddr=InetAddress.getByName(parsedResult).toString();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ipAddr=parsedResult;
			}
			
			if(ipAddr.indexOf('/')!=-1)
			{
				ipName = ipAddr.substring(0,ipAddr.indexOf('/'));
				ipBits = ipAddr.substring(ipAddr.indexOf('/')+1);				
			}
			else
			{
				ipBits = ipAddr;
			}
			//rtt = getHopRTT(parsedResult);
			Log.d("TraceHelp","Parsed result "+ parsedResult);
			return new TracerouteEntry(ipBits,ipName, ""+rtt, index);
		}
		else
		{
			return new TracerouteEntry("***","*", "*", index);
		}
	}
	
	static String getHopRTT(String dst)
	{
		String ipDst 	= dst;
		String cmd 		= "ping";
		String options 	= "-c 3";
		String output 	= "";
		double average;
		cmdUtil = new CommandLineUtil();

		output = cmdUtil.runCommand(cmd, ipDst, options);
		Measure ping_measurement = ParseUtil.PingParser(output);
		average = ping_measurement.getAverage();
		if(average!=-1)
			return ""+average;
		else
			return "*";
		
	}

}
