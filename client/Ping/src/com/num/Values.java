package com.num;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;

import com.num.models.*;
import com.num.utils.DeviceUtil;
import com.num.utils.PreferencesUtil;

public class Values extends Application{

	public  int FREQUENCY_SECS = 15*60;

	DeviceUtil util = new DeviceUtil();

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

	public HashMap<String,MainModel> dataStore = new HashMap<String,MainModel>();


	public  String THROUGHPUT_SERVER_ADDRESS="ruggles.gtnoise.net";
	public  String API_SERVER_ADDRESS="https://ruggles.gtnoise.net";

	public  int GPS_TIMEOUT = 20000;
	public  int SIGNALSTRENGTH_TIMEOUT = 10000;
	public  int WIFI_TIMEOUT = 10000;

	public  String UNAVAILABLE_CELLID = "65535";
	public  String UNAVAILABLE_CELLLAC = "65535";

	public  int THREADPOOL_MAX_SIZE = 10;
	public  int THREADPOOL_KEEPALIVE_SEC = 30;


	public ArrayList<Address> PING_SERVERS;

	public Buffer unsentMeasurements;

	public void savePreferences(){

		//PreferencesUtil.setDataInt("throughput", throughput,this);
	}

	public Values(){

		unsentMeasurements = new Buffer();

		PING_SERVERS = new ArrayList<Address>();
		PING_SERVERS.add(new Address("143.215.131.173", "Atlanta, GA", "ping"));
		PING_SERVERS.add(new Address("143.225.229.254", "Napoli, ITALY", "ping"));
		PING_SERVERS.add(new Address("128.48.110.150", "Oakland, CA", "ping"));
		
		PING_SERVERS.add(new Address("www.facebook.com", "Facebook", "ping"));

	}
	public void initDataStore(){
		dataStore = new HashMap<String,MainModel>();
	}

	public void storeModel(MainModel m){
		dataStore.put(m.getTitle(), m);
	}

	public MainModel getModel(String key){
		return dataStore.get(key);
	}

	public ArrayList<Address> getPingServers(){
		return PING_SERVERS;
	}

	public void loadValues(){


		try {
			FREQUENCY_SECS = Integer.parseInt(PreferencesUtil.getDataString("frequency_secs",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			THROUGHPUT_FREQ = Integer.parseInt(PreferencesUtil.getDataString("throughput_freq",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			UPLINKPORT = Integer.parseInt(PreferencesUtil.getDataString("uplink_port",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			UPLINK_DURATION = Integer.parseInt(PreferencesUtil.getDataString("uplink_duration",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DOWNLINKPORT = Integer.parseInt(PreferencesUtil.getDataString("downlink_port",this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DOWNLINK_DURATION = Integer.parseInt(PreferencesUtil.getDataString("downlink_duration",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			TCP_HEADER_SIZE = Integer.parseInt(PreferencesUtil.getDataString("tcp_headersize","",this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			TCP_PACKET_SIZE = Integer.parseInt(PreferencesUtil.getDataString("tcp_packetsize",this));
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			
			THROUGHPUT_SERVER_ADDRESS = PreferencesUtil.getDataString("throughput_server_address",THROUGHPUT_SERVER_ADDRESS,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			API_SERVER_ADDRESS = PreferencesUtil.getDataString("api_server_address",API_SERVER_ADDRESS,this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			SIGNALSTRENGTH_TIMEOUT = Integer.parseInt(PreferencesUtil.getDataString("signalstrength_timeout",this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			WIFI_TIMEOUT = Integer.parseInt(PreferencesUtil.getDataString("wifi_timeout",this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			UNAVAILABLE_CELLID = PreferencesUtil.getDataString("unavailable_cellid",UNAVAILABLE_CELLID,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			UNAVAILABLE_CELLLAC = PreferencesUtil.getDataString("unavailable_celllac",UNAVAILABLE_CELLLAC,this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			THREADPOOL_MAX_SIZE = Integer.parseInt(PreferencesUtil.getDataString("threadpool_max_size",this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			THREADPOOL_KEEPALIVE_SEC = Integer.parseInt(PreferencesUtil.getDataString("threadpool_keepalive_sec",this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertValues(JSONObject obj){
		Iterator<String> iterate = obj.keys();

		while(iterate.hasNext()){
			String key = iterate.next();

			try {
				String value = obj.getString(key);
				PreferencesUtil.setDataString(key, value,this);

			} catch (JSONException e) {

			}

		}

		try {
			JSONArray pingArray = obj.getJSONObject("ping_servers").getJSONArray("servers");
			PING_SERVERS = new ArrayList<Address>();

			for(int i=0;i<pingArray.length();i++){
				JSONObject pingObj = pingArray.getJSONObject(i);
				Address address = new Address(pingObj.getString("ipaddress"),pingObj.getString("tag"), "ping");
				PING_SERVERS.add(address);
				Address address2 = new Address(pingObj.getString("ipaddress"),pingObj.getString("tag"), "firsthop");
				PING_SERVERS.add(address2);
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


	public void incrementThroughput(){
		int throughput_count = getThroughput()+1;
		throughput_count%=THROUGHPUT_FREQ;
		setThroughput(throughput_count);
	}

	public void decrementThroughput(){
		int throughput_count = getThroughput()-1;
		throughput_count%=THROUGHPUT_FREQ;
		setThroughput(throughput_count);
	}



	public boolean doThroughput(){
		return getThroughput()==0;
	}

	public int getThroughput(){
		return PreferencesUtil.getDataInt("throughput", this);
	}

	public void setThroughput(int throughput){
		PreferencesUtil.setDataInt("throughput", throughput,this);
	}
	
}