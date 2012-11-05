package com.num.helpers;

import android.util.Log;

import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.utils.CommandLineUtil;

public class TracerouteHelper {
	
	public static CommandLineUtil cmdUtil;
	public static final String SERVER_ADDRESS = "cc.gatech.edu";
	public static Traceroute traceHelp(String ipDst, int startindex, int endindex)
	{
		String result = null;
		String cmd 		= "ping";
		String options 	= "-c "+1 + " -t ";
		String output 	= "";
		cmdUtil = new CommandLineUtil();
		String temp;
		for(int i = startindex; i<endindex;i++)
		{
			temp = options+i;
			Log.d("TraceWorker", ""+temp);
			output = cmdUtil.runCommand(cmd, ipDst, temp);
			result += (output + "\n");
		}
		Traceroute trace = new Traceroute();
		parseResult(result, trace, startindex);
		return trace;
	}
	
	static void parseResult(String result, Traceroute trace, int startindex)
	{
		boolean found = false;
		String parsedResult= "";
		int pos;
		int count = 0;
		int hop = startindex;
		for(pos=0; pos<result.length(); pos++)
		{
			parsedResult = "";
			if(result.charAt(pos)=='F')
			{
				found = true;
			}
			if(result.charAt(pos)=='\n')
			{
				count++;
			}
			if(found==true){
				pos+=5;
				Log.d("TraceWorker", "" + count);
				if(count>7)
				{
					
					trace.addToList(new TracerouteEntry("Hop details missing", 0.0, hop));;
					hop++;
				}
				count=0;
				while(result.charAt(pos)!=' ')
				{
					parsedResult += result.charAt(pos);
					pos++;
				}
				trace.addToList(new TracerouteEntry(parsedResult, 0.0, hop));
				found = false;
				hop++;
			}
		}
	}
	

}
