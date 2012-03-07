package com.num.utils;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.num.Values;
import com.num.listeners.ResponseListener;

public class NeighborWifiUtil {


	private static Context context;
	public static ResponseListener responseListener;
	public static WifiManager mainWifi;
	public static WifiReceiver receiverWifi;
	public static List<ScanResult> wifiList;
	public static NeighborResult neighborResult;
	public boolean isRunning = true;

	public void getNeighborWifi(Context ct, NeighborResult nr) {
		context = ct;
		neighborResult = nr;
		isRunning = true;
		mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();

		try{
			context.registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		}
		catch(Exception e){
			context.unregisterReceiver(receiverWifi);
			context.registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		}
		mainWifi.startScan();
		/*
		while(isRunning){
			try {
				Thread.sleep(Values.SHORT_SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//receiverWifi.abortBroadcast();*/
		//context.unregisterReceiver(receiverWifi);
	}


	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			wifiList = mainWifi.getScanResults();
			neighborResult.gotNeighbor(wifiList);
			isRunning=false;
			try{
				context.unregisterReceiver(receiverWifi);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//mainWifi.disconnect();

		}
	}

	public static abstract class NeighborResult {
		public abstract void gotNeighbor(List<ScanResult> wifiList);

	}
}
