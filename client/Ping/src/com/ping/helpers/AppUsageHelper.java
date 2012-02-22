package com.ping.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ping.models.Application;
import com.ping.models.Usage;
import com.ping.utils.AppUsageUtil;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;


public class AppUsageHelper {
	
	private static PackageManager mPm;
	
	private static ArrayList<String> getRunningProcesses(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService("activity");
		List<RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
	String s = processes.get(0).processName;
	ArrayList<String> runningProcessNames = new ArrayList<String>();
	
	for(RunningAppProcessInfo p: processes)
		runningProcessNames.add(p.processName);
	
	return runningProcessNames;
	}
	
	public static Usage getUsageData(Context context) {

		Usage usage = new Usage();
		List<Application> result = new ArrayList<Application>();
		usage.setApplications(result);
		
		mPm = context.getPackageManager();
		ArrayList<String> runningNames = getRunningProcesses(context);
		List<ApplicationInfo> allInfo = mPm.getInstalledApplications(0);
		
		Set<Integer> uids = new TreeSet<Integer>();
		
		long total_recv=TrafficStats.getTotalRxBytes();
		long total_sent=TrafficStats.getTotalTxBytes();
		long mobile_recv=TrafficStats.getMobileRxBytes();
		long mobile_sent=TrafficStats.getMobileTxBytes();
		
		
		for (ApplicationInfo info : allInfo) {
			Integer uid = info.uid;
			if (uids.contains(uid))
				continue;
			uids.add((Integer) uid);
			long recv = TrafficStats.getUidRxBytes(uid);
			long sent = TrafficStats.getUidTxBytes(uid);
			// remove those application which do not sent and recv data
			if (recv > 0 || sent > 0)
			{
				Application app = new Application();
				app.setName(AppUsageUtil.getAppLabel(context, uid));
				
				app.setPackageName(AppUsageUtil.getAppPkg(context,uid));
				app.setIcon(AppUsageUtil.getAppIcon(context, uid));
				app.setTotal_recv(TrafficStats.getUidRxBytes(uid));
				app.setTotal_sent(TrafficStats.getUidTxBytes(uid));				
				app.setIsRunning(runningNames.contains(AppUsageUtil.getAppPkg(context,uid)));
				
				result.add(app);
			}
		}
		
		usage.setTotal_recv(total_recv);
		usage.setTotal_sent(total_sent);
		usage.setMobile_recv(mobile_recv);
		usage.setMobile_sent(mobile_sent);
		
		return usage;
	}
	

}
