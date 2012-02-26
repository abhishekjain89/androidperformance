package com.ping;


public class Values{
	
	public static final int FREQUENCY_SECS = 15*60;
	
	
	public static final int THROUGHPUT_FREQ = (3600/FREQUENCY_SECS)*19; //18 hours
	
	public static final int UPLINKPORT=9912;
	public static final int UPLINK_DURATION=25000;
	public static final int DOWNLINKPORT=9710;
	public static final int DOWNLINK_DURATION=20000;
	public static final int DOWNLINK_BUFFER_SIZE=12000;
	
	public static final int TCP_HEADER_SIZE=54;
	public static final int TCP_PACKET_SIZE=1380;
	
	public static final int NORMAL_SLEEP_TIME = 1000;
	public static final int SHORT_SLEEP_TIME = 100;
	public static final int ONE_MINUTE_TIME = 60 * 1000;
	
	public static final int SERVICE_DEFAULT_FREQUENCY_MINS = 90;
	
	public static final String THROUGHPUT_SERVER_ADDRESS="ruggles.gtnoise.net";
	public static final String API_SERVER_ADDRESS="ruggles.gtnoise.net";
	
	public static final int GPS_TIMEOUT = 20000;
	public static final int SIGNALSTRENGTH_TIMEOUT = 10000;
	public static final int WIFI_TIMEOUT = 10000;
	
	public static final String UNAVAILABLE_CELLID = "65535";
	public static final String UNAVAILABLE_CELLLAC = "65535";
	
	public static final int THREADPOOL_MAX_SIZE = 10;
	public static final int THREADPOOL_KEEPALIVE_SEC = 30;
	
	public static final int[] SERVICE_FREQUENCY_MINS = {30,60,120,240};
	
	public static final String[] PING_SERVERS = {"143.215.131.173", "143.225.229.254","128.48.110.150","localhost"};
	//public static final String SERVERADDRESS="localhost";
	
	
	
}