package com.num.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.num.Values;
import com.num.helpers.DeviceHelper;
import com.num.helpers.LooperThread;
import com.num.helpers.PingHelper;
import com.num.listeners.ResponseListener;

import com.num.utils.SignalUtil;
import com.num.utils.SignalUtil.SignalResult;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class SignalStrengthTask extends ServerTask {

	boolean signalRunning = true;
	String signalValue = "";

	public SignalStrengthTask(Context context, Map<String, String> reqParams,
			ResponseListener listener) {
		super(context, reqParams, listener);
	}

	@Override
	public void runTask() {
		
		final TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
		LooperThread thread = new LooperThread(new Runnable() {
			
			public void run() {
				MyPhoneStateListener listener = new MyPhoneStateListener(manager);
				listener.listen();
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(signalRunning) {
			
			try {
				Thread.sleep(Values.NORMAL_SLEEP_TIME);
				System.out.println("waiting");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("signalValue: " + signalValue);
		getResponseListener().onCompleteSignal(signalValue);
		
	}

	@Override
	public String toString() {
		return "SignalStrength Task";
	}
	

	private class MyPhoneStateListener extends PhoneStateListener {
		private TelephonyManager manager;		
		public MyPhoneStateListener(TelephonyManager manager) {
			this.manager = manager;			
		}

		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);
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
			}
			signalValue = strength;
			signalRunning=false;
			manager.listen(this, PhoneStateListener.LISTEN_NONE);
			Looper.myLooper().quit();
		}

		public void listen() {
			manager.listen(MyPhoneStateListener.this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		}
		
	}

	
	
	

}
