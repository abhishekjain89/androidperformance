package com.android.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUsageUtil {

	public static String getAppLabel(Context c, int uid) {
		PackageManager pm = c.getPackageManager();
		String label = null;
		try {

			String name = pm.getPackagesForUid(uid)[0];
			label = (String) pm.getApplicationLabel(getAppInfo(c,name));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			label = "unknown(uid=" + uid + ")";
		}
		return label;
	}
	
	public static String getAppPkg(Context c, int uid) throws NullPointerException
	{
		PackageManager pm = c.getPackageManager();
		String name = pm.getPackagesForUid(uid)[0];
		return name;
	}
	
	private static ApplicationInfo getAppInfo(Context c, String name) throws NameNotFoundException
	{
		PackageManager pm = c.getPackageManager();
		return pm.getApplicationInfo(name, PackageManager.GET_UNINSTALLED_PACKAGES);
	}
	
}
