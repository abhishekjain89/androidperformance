package com.ping.utils;


import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil {
	
	public static final String SETTINGS_FILE_NAME = "com.ping";
	
	public static SharedPreferences getPreferences(Context context){
		return context.getSharedPreferences(SETTINGS_FILE_NAME, context.MODE_PRIVATE);
	}
	
	public static int getFrequency(Context context){
		return getPreferences(context).getInt("freq", 15);
	}
	
	public static boolean isPing(Activity activity){
		return getPreferences(activity).getBoolean("serv", false);
	}
	
	public static void setData(Activity activity,int freq,boolean isPing){
		Editor editor = getPreferences(activity).edit();
		editor.putBoolean("serv", isPing);
		editor.putInt("freq", freq);
		editor.commit();
	}
	
	public static void acceptConditions(Activity activity){
		Editor editor = getPreferences(activity).edit();
		
		editor.putBoolean("acceptConditions", true);
		editor.commit();
	}
	
	public static boolean isAccepted(Activity activity){
		return getPreferences(activity).contains("acceptConditions");
	}
	
	public static boolean contains(String key,Context context){
		return getPreferences(context).contains(key);
	}
	
	public static String getDataString(String str,Context context){
		return getPreferences(context).getString(str, "");
	}
	
	public static int getDataInt(String str,Context context){
		return getPreferences(context).getInt(str, -1);
	}
	
	public static void setDataInt(String key,int val,Context context){
		Editor editor = getPreferences(context).edit();
		
		editor.putInt(key,val);
		editor.commit();
	}

}
