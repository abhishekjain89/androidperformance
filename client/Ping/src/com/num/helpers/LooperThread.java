package com.num.helpers;

import android.os.Handler;
import android.os.Looper;

public class LooperThread extends Thread {
	public Handler mHandler;
	private Runnable r;
	private Looper looper;

	public LooperThread(Runnable r) {
		this.r = r;
	}

	public void run() {
		Looper.prepare();
		looper = Looper.myLooper();
		mHandler = new Handler();
		mHandler.post(r);
		Looper.loop();
	}
	
	public void quit() {
		looper.quit();
	}
}
