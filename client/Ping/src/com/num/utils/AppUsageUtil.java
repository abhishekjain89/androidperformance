package com.num.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class AppUsageUtil {

	public static String getAppLabel(Context c, int uid) {
		PackageManager pm = c.getPackageManager();
		String label = null;
		try {

			String name = pm.getPackagesForUid(uid)[0];
			label = (String) pm.getApplicationLabel(getAppInfo(c,name));
		} catch (NameNotFoundException e) {
			// 
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
	
	public static Drawable getAppIcon(Context c, int uid)
	{
		PackageManager pm = c.getPackageManager();
		

		Drawable icon = null;
		try {

			String name = pm.getPackagesForUid(uid)[0];
			
			icon = (Drawable) getAppInfo(c,name).loadIcon(pm);
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			icon = null;
		}
		return icon;
	}
	
}
