package com.android.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.android.listeners.ResponseListener;
import com.android.models.WifiNeighbor;

public class NeighborWifiUtil {
	

	private static Context context;
	public static ResponseListener responseListener;
    public static WifiManager mainWifi;
    public static WifiReceiver receiverWifi;
    public static List<ScanResult> wifiList;

	
	public void getNeighborWifi(ResponseListener rl, Context ct) {
		responseListener = rl;
		context = ct;

		mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();
		context.registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		mainWifi.startScan();
	}
	

    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
        	wifiList = mainWifi.getScanResults();

        	responseListener.onCompleteWifi(wifiList);
        }
    }
}
