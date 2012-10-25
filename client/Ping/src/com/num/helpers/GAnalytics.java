package com.num.helpers;

import com.google.android.apps.analytics.easytracking.EasyTracker;

public class GAnalytics {
	
	public static String MEASUREMENT = "Measurement";
	public static String DATABASE = "Database";
	public static String GRAPH = "Graph";
	public static String ACTION = "Action";	
	public static String NOTIFICATION = "Notification";
	
	public static void log(String category,String action){
		log(category,action,null,0);
	}
	
	public static void log(String category,String action,String label){
		log(category,action,label,0);
	}
	
	public static void log(String category,String action,String label,int value){
		try{
		EasyTracker.getTracker().trackEvent(category,action,label,value);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	

}
