package com.num.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.num.database.datasource.ApplicationDataSource;
import com.num.database.datasource.LatencyDataSource;
import com.num.helpers.AppUsageHelper;
import com.num.helpers.DeviceHelper;
import com.num.helpers.PingHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;
import com.num.models.Application;
import com.num.models.ClientLog;
import com.num.models.Measurement;
import com.num.models.Ping;
import com.num.models.Usage;
import com.num.utils.DeviceUtil;
import com.num.utils.HTTPUtil;
import com.num.utils.SDCardFileReader;
import com.num.utils.SHA1Util;

public class LogTask extends ServerTask{
	ClientLog log;
	
	public LogTask(Context context, ClientLog log) {
		super(context, new HashMap<String, String>(), new FakeListener());
		this.log = log;
	}

	@Override
	public void runTask() {
		HTTPUtil http = new HTTPUtil();
		try {
			SDCardFileReader.saveData("log_last.txt",log.toJSON().toString());
			http.request(new HashMap<String,String>(), "POST", "log", "", log.toJSON().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Log Task";
	}
	

}
