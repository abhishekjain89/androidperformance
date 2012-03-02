package com.ping;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;

import com.ping.models.*;
import com.ping.utils.DeviceUtil;

public class Values extends Application{
	
	public  int FREQUENCY_SECS = 15*60;
	
	DeviceUtil util = new DeviceUtil();
	public int gps_count=0;
	public int throughput_count = 0;
	
	public  int THROUGHPUT_FREQ = (3600/FREQUENCY_SECS)*19; //19 hours
	
	public  int UPLINKPORT=9912;
	public  int UPLINK_DURATION=25000;
	public  int DOWNLINKPORT=9710;
	public  int DOWNLINK_DURATION=20000;
	public  int DOWNLINK_BUFFER_SIZE=12000;
	
	public  int TCP_HEADER_SIZE=54;
	public  int TCP_PACKET_SIZE=1380;
	
	public  int NORMAL_SLEEP_TIME = 1000;
	public  int SHORT_SLEEP_TIME = 100;
	public  int ONE_MINUTE_TIME = 60 * 1000;
	
	
	
	public  String THROUGHPUT_SERVER_ADDRESS="ruggles.gtnoise.net";
	public  String API_SERVER_ADDRESS="ruggles.gtnoise.net";
	
	public  int GPS_TIMEOUT = 20000;
	public  int SIGNALSTRENGTH_TIMEOUT = 10000;
	public  int WIFI_TIMEOUT = 10000;
	
	public  String UNAVAILABLE_CELLID = "65535";
	public  String UNAVAILABLE_CELLLAC = "65535";
	
	public  int THREADPOOL_MAX_SIZE = 10;
	public  int THREADPOOL_KEEPALIVE_SEC = 30;
	
	
	public ArrayList<Address> PING_SERVERS;
	
	public Values(){
		PING_SERVERS = new ArrayList<Address>();
		PING_SERVERS.add(new Address("143.215.131.173", "Atlanta, GA"));
		PING_SERVERS.add(new Address("143.225.229.254", "Napoli, ITALY"));
		PING_SERVERS.add(new Address("128.48.110.150", "Oakland, CA"));
		PING_SERVERS.add(new Address("localhost", "localhost"));
		
		PING_SERVERS.add(new Address("www.facebook.com", "Facebook"));
		
	}
	
	public ArrayList<Address> getPingServers(){
		return PING_SERVERS;
	}
	
	public void insertValues(JSONObject obj){
		try {
			FREQUENCY_SECS = obj.getInt("frequency_secs");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			THROUGHPUT_FREQ = obj.getInt("throughput_freq");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			UPLINKPORT = obj.getInt("uplink_port");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			UPLINK_DURATION = obj.getInt("uplink_duration");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			DOWNLINKPORT = obj.getInt("downlink_port");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			DOWNLINK_DURATION = obj.getInt("downlink_duration");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			TCP_HEADER_SIZE = obj.getInt("tcp_headersize");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			TCP_PACKET_SIZE = obj.getInt("tcp_packetsize");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		

		try {
			THROUGHPUT_SERVER_ADDRESS = obj.getString("throughput_server_address");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			API_SERVER_ADDRESS = obj.getString("api_server_address");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		try {
			SIGNALSTRENGTH_TIMEOUT = obj.getInt("signalstrength_timeout");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			WIFI_TIMEOUT = obj.getInt("wifi_timeout");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			UNAVAILABLE_CELLID = obj.getString("unavailable_cellid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			UNAVAILABLE_CELLLAC = obj.getString("unavailable_celllac");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			THREADPOOL_MAX_SIZE = obj.getInt("threadpool_max_size");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			THREADPOOL_KEEPALIVE_SEC = obj.getInt("threadpool_keepalive_sec");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			JSONArray pingArray = obj.getJSONObject("ping_servers").getJSONArray("servers");
			PING_SERVERS = new ArrayList<Address>();
			
			for(int i=0;i<pingArray.length();i++){
				JSONObject pingObj = pingArray.getJSONObject(i);
				Address address = new Address(pingObj.getString("ipaddress"),pingObj.getString("tag"));
				PING_SERVERS.add(address);
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public String getTag(){
		return "Values";
	}
	

	public ArrayList<Screen> screenBuffer = new ArrayList<Screen>();
	
	public void AddScreen(boolean isOn){
		screenBuffer.add(new Screen(util.getUTCTime(),util.getLocalTime(),isOn));
		
	}
	
	public void incrementGPS(){
		gps_count++;
		gps_count%=4;
	}
	
	public void decrementGPS(){
		gps_count--;
		gps_count%=4;
	}
	
	public void incrementThroughput(){
		throughput_count++;
		throughput_count%=THROUGHPUT_FREQ;
	}
	
	public void decrementThroughput(){
		throughput_count--;
		throughput_count%=THROUGHPUT_FREQ;
	}
	
	public boolean doGPS(){
		return gps_count==0;
	}
	
	public boolean doThroughput(){
		return throughput_count==0;
	}
	
}