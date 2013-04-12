package com.num;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;

import com.num.database.DatabasePicker;
import com.num.database.datasource.DataSource;
import com.num.database.datasource.ThroughputDataSource;
import com.num.models.Address;
import com.num.models.Buffer;
import com.num.models.MainModel;
import com.num.models.Screen;
import com.num.utils.DeviceUtil;
import com.num.utils.HTTPUtil;
import com.num.utils.PreferencesUtil;

public class Values extends Application{

	public boolean DEBUG = false;
	
	public  static int FREQUENCY_SECS = 15*60;

	DeviceUtil util = new DeviceUtil();

	public  int THROUGHPUT_FREQ = (3600/FREQUENCY_SECS)*19; //19 hours

	public  int UPLINKPORT=10020;
	public  static int UPLINK_DURATION=15000;
	public  static int DOWNLINKPORT=10030;
	public  static int DOWNLINK_DURATION=20000;
	public  static int DOWNLINK_BUFFER_SIZE=50000;

	public  static int TCP_HEADER_SIZE=54;
	public  static int TCP_PACKET_SIZE=1380;

	public  static int NORMAL_SLEEP_TIME = 1000;
	public  static int SHORT_SLEEP_TIME = 100;
	public  static int ONE_MINUTE_TIME = 60 * 1000;
	
	public DatabasePicker picker;
	
	public HashMap<String,MainModel> dataStore = new HashMap<String,MainModel>();

	public  String THROUGHPUT_SERVER_ADDRESS="ruggles.gtnoise.net";
	public String SA_THROUGHPUT_SERVER_ADDRESS = "measurements-bismark.cs.uct.ac.za";
	public  String API_SERVER_ADDRESS="https://ruggles.gtnoise.net";
	
	public static final String LOSS_SERVER_ADDRESS = "ruggles.gtnoise.net";
	public static final int LOSS_PORT = 8888;
	public static final int LOSS_THRESHOLD = 10000;
	public static final int LOSS_TOTAL = 500;

	public  int GPS_TIMEOUT = 20000;
	public  int SIGNALSTRENGTH_TIMEOUT = 10000;
	public  int WIFI_TIMEOUT = 10000;

	public  String UNAVAILABLE_CELLID = "65535";
	public  String UNAVAILABLE_CELLLAC = "65535";

	public static int THREADPOOL_MAX_SIZE = 10;
	public static int THREADPOOL_KEEPALIVE_SEC = 30;

	public ThroughputDataSource datasource;

	public ArrayList<Address> PING_SERVERS;
	public static Address PING_SEQUENCE_ADDRESS = new Address("143.215.131.173", "Atlanta, GA", "ping");
	public static float PING_WARMUP_SEQUENCE_GAP = 0.2f;
	public static int PING_WARMUP_SEQUENCE_TOTAL = 20;
	public static float PING_SEQUENCE_VERSION= 1;
	public static float PING_COOLDOWN_TIME = 20;
	public static float[] PING_SEQUENCE_GAPS = new float[]{0.5f,1,1.5f,2,3,4,5,7.5f,10};
	

	public Buffer unsentMeasurements;

	public void savePreferences(){

 		//PreferencesUtil.setDataInt("throughput", throughput,this);
	}
	
	public DatabasePicker createPicker(DataSource dataSource) {
		picker = new DatabasePicker(dataSource);
		return picker;
	}
	
	public DatabasePicker getPicker() {
		return picker;
	}

	public Values(){

		unsentMeasurements = new Buffer();

		PING_SERVERS = new ArrayList<Address>();
		PING_SERVERS.add(new Address("143.215.131.173", "Atlanta, GA", "ping"));
		//PING_SERVERS.add(new Address("143.225.229.254", "Napoli, ITALY", "ping"));
		PING_SERVERS.add(new Address("128.48.110.150", "Oakland, CA", "ping"));
		PING_SERVERS.add(new Address("www.facebook.com", "Facebook", "ping"));
		PING_SERVERS.add(new Address("www.google.com", "Google", "ping"));
		PING_SERVERS.add(new Address("143.215.131.173", "Atlanta, GA", "firsthop"));
		//PING_SERVERS.add(new Address("143.225.229.254", "Napoli, ITALY", "firsthop"));
		PING_SERVERS.add(new Address("128.48.110.150", "Oakland, CA", "firsthop"));
		PING_SERVERS.add(new Address("www.facebook.com", "Facebook", "firsthop"));
		PING_SERVERS.add(new Address("www.google.com", "Google", "firsthop"));

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

	public ArrayList<Address> getPingServers(Context context){
		
		HTTPUtil http = new HTTPUtil();
		String response = "";
		int exception = 0;
		try {
			response = http.request(new HashMap<String,String>(), "GET", "values", "", "");
		} catch (FileNotFoundException e1) {
			exception = 1;
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			exception = 1;
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			exception = 1;
			e1.printStackTrace();
		} catch (IOException e1) {
			exception = 1;
			e1.printStackTrace();
		}
		if(exception==1)
		{
			return PING_SERVERS;
		}
		DeviceUtil device = new DeviceUtil();
		String country = device.getNetworkCountry(context);
		if(country == null)
		{
			country = "xx";
		}
		ArrayList<Address> DYN_PING_SERVERS = null;
		boolean specialcountry = false;
		try {
			JSONObject obj = new JSONObject(response);
			JSONObject pingObj = obj.getJSONObject("values").getJSONObject("ping_servers").getJSONObject("servers");
			DYN_PING_SERVERS = new ArrayList<Address>();
			JSONArray countryArray = obj.getJSONObject("values").getJSONArray("countries");
			
			for(int i =0; i<countryArray.length(); i++)
			{
				if(country.equals(countryArray.getJSONObject(i).getString("code")))
				{
					specialcountry = true;
					break;
				}
			}
			JSONArray pingArray = null;
			if(specialcountry)
			{
				try{
					pingArray = pingObj.getJSONArray(country);
				}
				catch(JSONException e)
				{
					pingArray = pingObj.getJSONArray("default");
				}
			}
			else
			{
				pingArray = pingObj.getJSONArray("default");
			}
			for(int i=0;i<pingArray.length();i++){
				JSONObject pingObject = pingArray.getJSONObject(i);
				Address address = new Address(pingObject.getString("ipaddress"),pingObject.getString("tag"), "ping");
				DYN_PING_SERVERS.add(address);
				Address address2 = new Address(pingObject.getString("ipaddress"),pingObject.getString("tag"), "firsthop");
				DYN_PING_SERVERS.add(address2);
			}


		} catch (JSONException e) {
			e.printStackTrace();
			return PING_SERVERS;
		}
		return DYN_PING_SERVERS;
		//return PING_SERVERS;
	}

	public void loadValues(){
		/*

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
		*/
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