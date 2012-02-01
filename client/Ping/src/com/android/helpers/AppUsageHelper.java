package com.android.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.android.models.Application;
import com.android.models.Usage;
import com.android.utils.AppUsageUtil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;


public class AppUsageHelper {
	
	private static PackageManager mPm;
	
	public static Usage getUsageData(Context context) {
		Usage usage = new Usage();
		List<Application> result = new ArrayList<Application>();
		usage.setApplications(result);
		
		mPm = context.getPackageManager();
		
		List<ApplicationInfo> allInfo = mPm.getInstalledApplications(0);
		
		Set<Integer> uids = new TreeSet<Integer>();
		
		long total_recv=0;
		long total_sent=0;
		
		
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
				
				app.setTotal_recv(TrafficStats.getUidRxBytes(uid));
				app.setTotal_sent(TrafficStats.getUidTxBytes(uid));
				total_recv+=app.getTotal_recv();
				total_sent+=app.getTotal_sent();
				result.add(app);
			}
		}
		
		usage.setTotal_recv(total_recv);
		usage.setTotal_sent(total_sent);
		
		return usage;
	}
	

}