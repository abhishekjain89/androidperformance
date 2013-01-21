package com.num.helpers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.num.Values;
import com.num.models.Ipdv;
import com.num.models.IpdvUnit;
import com.num.models.Loss;

import android.util.Log;

public class LossHelper
{
	public static double losspercentage;
	public int total;
	static Loss l;
	public static Ipdv ipdv;
	
	
	
	private static
	DatagramPacket
	getRequestPacket() 
	{
		String request = "00002";
		byte[] start_request_array = request.getBytes();
		DatagramPacket request_packet = new DatagramPacket(start_request_array, start_request_array.length);
		return request_packet;
	}
	
	
	public static Loss getLoss()
	{
		l = new Loss();
		DatagramSocket clientSocket = null;
		byte[] receive_data = new byte[1024];
		DatagramPacket receive_packet = new DatagramPacket(receive_data, receive_data.length);
		//List<Long> ddTList = new ArrayList<Long>();
		ipdv = new Ipdv();
		try 
		{
			
			clientSocket = new DatagramSocket();
			clientSocket.connect(InetAddress.getByName(Values.LOSS_SERVER_ADDRESS), Values.LOSS_PORT);
			clientSocket.send(getRequestPacket());
			//receive_data = new byte[PACKET_SIZE];
			//DatagramPacket receive_packet = new DatagramPacket(receive_data, receive_data.length);
			clientSocket.receive(receive_packet);
			String received = new String(receive_packet.getData(),0,receive_packet.getLength());
			Log.d("Loss Test Worker", "received port"+received);
			clientSocket.close();
			clientSocket = null;
			clientSocket = new DatagramSocket();
			clientSocket.connect(InetAddress.getByName(Values.LOSS_SERVER_ADDRESS), Integer.parseInt(received));
			int count = 0;
			clientSocket.setSoTimeout(120000);
			clientSocket.send(getRequestPacket());
			int seq_count = 0;
			long dT1 = 0;
			long dT2 = 0;
			long n = 0;
			
			while(!received.equals("terminate"))
			{
				clientSocket.receive(receive_packet);
				long timestamp = new Date().getTime();//System.currentTimeMillis();
				received = null;
				String seq_number = (new String(receive_packet.getData(),0,4));
				received = new String(receive_packet.getData(),4,receive_packet.getLength()-4);
				
				long rec_timestamp;
				int sequence_number;
				
				
				try
				{
					sequence_number = Integer.parseInt(seq_number);
					rec_timestamp = Long.parseLong(received);
					if(timestamp - rec_timestamp>Values.LOSS_THRESHOLD)
					{
						count++;
						continue;
					}
					if(sequence_number%10==9)
					{
						dT1 = timestamp-rec_timestamp;
						n = sequence_number;
					}
					else if(sequence_number==(n+1))
					{
						seq_count++;
						dT2 = timestamp-rec_timestamp;
						//Log.d("Loss Test Worker", "dT2");
						ipdv.addToList(new IpdvUnit(seq_count++,(dT2 - dT1)));
						n = 0;
						dT1 = dT2 = 0;
					}
					
				}
				catch (NumberFormatException e)
				{
					
					continue;
					
				}
				Log.d("Loss Test Worker", "Seq number = "+sequence_number);
				Log.d("Loss Test Worker", "Time Diff = "+ (timestamp-rec_timestamp));
			}
			
			Log.d("Loss Test Worker", ""+count);
			received = null;
			losspercentage = ((double)count)/1000*100;
			/*for(Long item: ddTList)
			{
				Log.d("Loss Test Worker", "ipdv: "+item);
			}*/
			l.setLosspercentage(losspercentage);
			l.setLost(count);
			l.setTotal(Values.LOSS_TOTAL);
			l.setIpdv(ipdv);
			
		}
		
		
		catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SocketTimeoutException e)
		{
			e.printStackTrace();
			Log.d("Loss Test Worker", "Socket timed out. Loss = 100%");
			l.setLosspercentage(100);
			l.setLost(0);
			l.setTotal(Values.LOSS_TOTAL);
			
					
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		/*catch (InterruptedException e) {
		}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		finally
		{
			if(clientSocket!=null)
			{
				clientSocket.close();
			}
			clientSocket = null;
			receive_data = null;
		}
		return l;
	}
}