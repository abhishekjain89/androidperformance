package com.ping.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Random;

import com.ping.Values;
import com.ping.models.Link;



public class ThroughputUtil {
	
	public static String generateRandom()
	{
		Random number=new Random();
		StringBuilder message=new StringBuilder();
		for(int i=0;i<1405;i++)
		{
			message.append('a'+number.nextInt(52));
		}
		return message.toString();
	}
	
	public static Link uplinkmeasurement() throws UnknownHostException, IOException
	{
		String serveraddress=Values.THROUGHPUT_SERVER_ADDRESS;
		SocketAddress serversocket = new InetSocketAddress(serveraddress,Values.UPLINKPORT);
		Socket uplinkclient=new Socket();
		uplinkclient.connect(serversocket);

		DataInputStream in = new DataInputStream(uplinkclient.getInputStream());
		DataOutputStream out = new DataOutputStream(uplinkclient.getOutputStream());
		Link link = new Link();
		System.out.println("Starting uplink test:");
		String buf= generateRandom();
		byte[] message = buf.getBytes();
		long throughput=0;
		long tensecthroughput=0;
		long start = System.currentTimeMillis();
		//System.out.println(start);
		long end = System.currentTimeMillis();
		long intermediate = System.currentTimeMillis();
		long count=0;
		long tenseccount=0;
		int flag=0;
		do
		{
			out.write(message);
			end = System.currentTimeMillis();
			if(end-start>=Values.UPLINK_DURATION/2){
				if(flag==0){intermediate= System.currentTimeMillis();
				flag=1;
				}
				tenseccount++;
			}

			count++;
		}while(end-start<=Values.UPLINK_DURATION);
		throughput=count*((long)message.length+(54*3))/(end-start)*8;
		System.out.println("Message length: "+message.length);
		System.out.println("Intermediate: "+intermediate);
		link.setCount(count);
		link.setMessage_size(message.length+(54*3));
		link.setTime(end-start);
		link.setDstIp(Values.THROUGHPUT_SERVER_ADDRESS);
		link.setDstPort(Values.UPLINKPORT+"");
		System.out.println(link.toJSON());
		//tensecthroughput = tenseccount*((long)message.length+(54*3))/((end-intermediate)*8);
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e1)
		{
			e1.printStackTrace();
		}
		
		System.out.println("Uplink test complete");
		System.out.println("Overall throughput: "+throughput + "kbps");
		
		out.close();
		in.close();
		uplinkclient.close();
		
		return link;
	}

	public static Link downlinkmeasurement() throws IOException
	{
		String serveraddress=Values.THROUGHPUT_SERVER_ADDRESS;
		SocketAddress serversocket = new InetSocketAddress(serveraddress,Values.DOWNLINKPORT);
		Socket downlinkclient=new Socket();
		downlinkclient.connect(serversocket);

		DataInputStream in = new DataInputStream(downlinkclient.getInputStream());
		DataOutputStream out = new DataOutputStream(downlinkclient.getOutputStream());
		System.out.println("Starting downlink test:");
		Link link = new Link();
		out.flush();
		try {
			Thread.sleep(Values.NORMAL_SLEEP_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int messagebytes=0;
		int totalbytes=0;
		int count=0;
		long start=System.currentTimeMillis();
		long end=System.currentTimeMillis();
		byte[] buffer=new byte[Values.DOWNLINK_BUFFER_SIZE];
		do
		{
			messagebytes=in.read(buffer, 0, Values.DOWNLINK_BUFFER_SIZE);
			count++;
			if(messagebytes<=0)
				break;
			//System.out.println(messagebytes);
			totalbytes+=messagebytes;
			end=System.currentTimeMillis();
		}while(true);
		link.setCount(1);
		link.setMessage_size(totalbytes*((Values.TCP_PACKET_SIZE+Values.TCP_HEADER_SIZE)/(Values.TCP_PACKET_SIZE)));
		link.setTime(end-start);
		link.setDstIp(serveraddress);
		link.setDstPort(Values.DOWNLINKPORT+"");
		System.out.println("Downlink test complete");
		System.out.println("Packets received " + count);
		if(end-start>0) System.out.println("Throughput: "+ totalbytes*8/(int)(end-start)+ " kbps");
		System.out.println(link.toJSON());
		out.close();
		in.close();
		downlinkclient.close();
		return link;
	}

}
