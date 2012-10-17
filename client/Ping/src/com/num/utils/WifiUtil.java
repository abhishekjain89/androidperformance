package com.num.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.num.Values;
import com.num.activities.RunActivity.MeasurementListener;
import com.num.models.Wifi;
import com.num.models.WifiNeighbor;
import com.num.models.WifiPreference;
import com.num.utils.NeighborWifiUtil.NeighborResult;

public class WifiUtil {
	
	boolean notDone = false;
	boolean isWIFI = false;
	Wifi wifi;

	public Wifi getWifi(Context context) {
		
		Values session = (Values) context.getApplicationContext();
		
		wifi = getWifiDetail(context);
		while(notDone){
			try {
				Thread.sleep(session.SHORT_SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		NeighborWifiUtil neighborWifiUtil = new NeighborWifiUtil();
		neighborWifiUtil.getNeighborWifi(context,neighborResult  );
		if (isWIFI) {
			notDone = true;		
		}
		while(notDone){
			try {
				Thread.sleep(session.SHORT_SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return wifi;
		
	}


	public NeighborResult neighborResult = new NeighborResult(){
		@Override
		public void gotNeighbor(List<ScanResult> wifiList){
			try{
				
			
			ArrayList<WifiNeighbor> neighbors = new ArrayList<WifiNeighbor>();
			ArrayList<WifiPreference> prefers = wifi.getPreference();
			for (int i = 0; i < wifiList.size(); i++) {
				WifiNeighbor n = new WifiNeighbor();
				String bssid = wifiList.get(i).BSSID;
				String capability = wifiList.get(i).capabilities;
				int frequency = wifiList.get(i).frequency;
				int signalLevel = wifiList.get(i).level;
				String ssid = wifiList.get(i).SSID;

				n.setCapability(capability);
				n.setMacAddress(bssid);
				n.setFrequency(frequency);
				n.setSignalLevel(signalLevel);
				n.setSSID(ssid);
				n.setPreferred(false);
				if (ssid.equalsIgnoreCase(wifi.getSsid())) {
					n.setConnected(true);
				}
				else {
					n.setConnected(false);
				}
				for (int j = 0; j < prefers.size(); j++) {
					if (ssid.equalsIgnoreCase(prefers.get(j).getSsid())) {
						n.setPreferred(true);
						break;
					}
				}
				neighbors.add(n);
			}
			wifi.setNeighbors(neighbors);	
			notDone = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public Wifi getWifiDetail(Context context) {
		String service = Context.CONNECTIVITY_SERVICE;
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		isWIFI = false;		
		
		Wifi wifiDetail = new Wifi();
		if (activeNetwork == null) {
			isWIFI = false;
		}
		else {
			int connectionType = activeNetwork.getType();
			switch (connectionType) {
				case (ConnectivityManager.TYPE_MOBILE) : 
					isWIFI = false;
					break;
			  	case (ConnectivityManager.TYPE_WIFI) : 
			  		isWIFI = true;
			  		break;
			  	default: 
			  		break;
			}
		}
		wifiDetail.setWifi(isWIFI);
		if (!isWIFI) {		
			notDone = false;
			return wifiDetail;
		}
		else {
			String srvc = Context.WIFI_SERVICE;
			WifiManager wifi = (WifiManager)context.getSystemService(srvc);
			WifiInfo info = wifi.getConnectionInfo();
			if (info.getBSSID() != null) {
				int strength = WifiManager.calculateSignalLevel(info.getRssi(), 11);
				
				int ipAddress = info.getIpAddress();
				int speed = info.getLinkSpeed();
				int networkId = info.getNetworkId();
				int rssi = info.getRssi();
				String macAddress = info.getMacAddress();
				String ssid = info.getSSID();		
		    	//ssid = ssid.substring(1, ssid.length() - 1);
				SupplicantState supState = info.getSupplicantState();
				String detailedInfo = supState.toString();
				String units = WifiInfo.LINK_SPEED_UNITS;
				//String cSummary = String.format("Connected to %s at %s%s. Strength %s", ssid, speed, units, strength);
				wifiDetail.setDetailedInfo(detailedInfo);
				wifiDetail.setIpAddress(ipAddress);
				wifiDetail.setMacAddress(macAddress);
				wifiDetail.setNetworkId(networkId);
				wifiDetail.setRssi(rssi);
				wifiDetail.setSpeed(speed);
				wifiDetail.setSsid(ssid);
				wifiDetail.setStrength(strength);
				wifiDetail.setUnits(units);
				wifiDetail.setPreferred(false);
			} 
		    // List available networks
		    ArrayList<WifiPreference> preference = new ArrayList<WifiPreference>();
			List<WifiConfiguration> configs = wifi.getConfiguredNetworks();
		    
		    for (int i = 0; i < configs.size(); i++) {
		    	WifiPreference wifiPref = new WifiPreference();
		    	
		    	String ssid = configs.get(i).SSID;
		    	ssid = ssid.substring(1, ssid.length() - 1);
		    	if (ssid.equalsIgnoreCase(wifiDetail.getSsid())) {
		    		wifiPref.setConnected(true);
		    		wifiDetail.setPreferred(true);
		    	}
		    	else {
		    		wifiPref.setConnected(false);
		    	}
		    	
		    	String authAlgorithm = configs.get(i).allowedAuthAlgorithms.toString();
		    	String groupCiphers = configs.get(i).allowedGroupCiphers.toString();
		    	String pairwiseCiphers = configs.get(i).allowedPairwiseCiphers.toString();
		    	String protocols = configs.get(i).allowedProtocols.toString();		    	
		    	String bssid = configs.get(i).BSSID;
		    	int networkid = configs.get(i).networkId;
		    	int priority = configs.get(i).priority;
		    	int status = configs.get(i).status;
		    	
		    	wifiPref.setAuthAlgorithms(authAlgorithm);
		    	wifiPref.setBssid(bssid);
		    	wifiPref.setGroupCiphers(groupCiphers);
		    	wifiPref.setPairwiseCiphers(pairwiseCiphers);
		    	wifiPref.setPriority(priority);
		    	wifiPref.setSsid(ssid);
		    	wifiPref.setProtocols(protocols);
		    	wifiPref.setNetworkid(networkid);
		    	wifiPref.setStatus(status);

		    	preference.add(wifiPref);
		    }
		    wifiDetail.setPreference(preference);
		    
		}
		notDone = false;
		return wifiDetail;
	}

}
