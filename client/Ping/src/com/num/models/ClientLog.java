package com.num.models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;
import com.num.Values;
import com.num.helpers.ThreadPoolHelper;
import com.num.tasks.LogTask;
import com.num.utils.DeviceUtil;
import com.num.utils.SHA1Util;

public class ClientLog implements Model {
	static ThreadPoolHelper serverhelper;
	String deviceid = "";
	String text = "";
	String tag = "";
	int value = 0;
	String time = "";

	public JSONObject toJSON() {

		JSONObject obj = new JSONObject();
		try {

			obj.putOpt("deviceid", deviceid);
			obj.putOpt("text", text);
			obj.putOpt("tag", tag);
			obj.putOpt("value", value);
			obj.putOpt("time", time);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;

	}
	
	public static void log(Context context, String text,String tag) {
		log(context,text,tag,0);
	}
	
	public static void log(Context context, Exception e,String tag) {
		log(context,stackToString(e),tag,0);
	}

	public static void log(Context context, String text, String tag, int value) {

		try {

			ClientLog log = new ClientLog();

			DeviceUtil deviceUtil = new DeviceUtil();
			log.setDeviceid(SHA1Util.SHA1(deviceUtil.getDeviceId(context)));
			log.setTime(deviceUtil.getUTCTime());
			log.setText(text);
			log.setTag(tag);
			log.setValue(value);

			if (serverhelper == null) {
				serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,
						Values.THREADPOOL_KEEPALIVE_SEC);
			}
			serverhelper.execute(new LogTask(context, log));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return "ClientLog";
	}

	public ArrayList<Row> getDisplayData(Context context) {
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("First", "Second"));
		return data;
	}

	public int getIcon() {
		return R.drawable.png;
	}
	
	private static String stackToString(Exception err) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		err.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}

}
