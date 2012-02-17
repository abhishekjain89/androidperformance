package com.android.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.android.listeners.ResponseListener;


public class SignalUtil {

	private static TelephonyManager telephonyManager;
	private static MyPhoneStateListener phoneStateListener;
	private static int signalStrength;
	private static Context context;
	public static ResponseListener responseListener;
	
	public static void getSignal(ResponseListener responseListen, Context ct) {	
		responseListener = responseListen;
		context = ct;
		phoneStateListener = new MyPhoneStateListener();
		signalStrength = -1;
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		if(telephonyManager != null && phoneStateListener != null) {
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		}
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
	}
	
	private static TelephonyManager getTelephoneManager(){
		if(telephonyManager == null)
			telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager;
	}
	
	private static class MyPhoneStateListener extends PhoneStateListener{
		
		public void onSignalStrengthsChanged (SignalStrength signalStrength)
		{
			String strength = "-1";
			if (signalStrength != null) {
				if (signalStrength.isGsm()) {
					strength = "" + signalStrength.getGsmSignalStrength();
				}
				if (signalStrength.getCdmaDbm() > 0) {
					strength = signalStrength.getCdmaDbm() + "dBm ";
					strength += signalStrength.getCdmaEcio() + "Ec/lo";
				}
				else if (signalStrength.getEvdoDbm() > 0) {
					strength = signalStrength.getEvdoDbm() + "dBm ";
					strength += signalStrength.getEvdoEcio() + "Ec/lo ";
					strength += signalStrength.getEvdoSnr() + "snr";	
				}
				getTelephoneManager().listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
				responseListener.onCompleteSignal(strength);
			}
			else {
				responseListener.onCompleteSignal(strength);
			}
		}
		
		/*@Override
		public void onSignalStrengthChanged(int asu) { 
			signalStrength = asu;
			getTelephoneManager().listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
			responseListener.onCompleteSignal(signalStrength);
		}*/
	};
}
