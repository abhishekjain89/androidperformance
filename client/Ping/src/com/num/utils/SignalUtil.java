package com.num.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

public class SignalUtil {

	private static TelephonyManager telephonyManager;
	private static MyPhoneStateListener phoneStateListener;
	private static int signalStrength;
	private static Context context;
	public static SignalResult signalResult;
	
	public static void getSignal(SignalResult signalR, Context ct) {	
		signalResult = signalR;
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
				//responseListener.onCompleteSignal(strength);
				signalResult.gotSignal(strength);
			}
			else {
				//responseListener.onCompleteSignal(strength);
				signalResult.gotSignal(strength);

			}
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		}
		
		public void onSignalStrengthChanged(int asu) { 
			try {
				signalStrength = asu;
				getTelephoneManager().listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
				//responseListener.onCompleteSignal("" + signalStrength);
				signalResult.gotSignal("" + signalStrength);
				telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

    public static abstract class SignalResult {
        public abstract void gotSignal(String signal);
          
    }
}
