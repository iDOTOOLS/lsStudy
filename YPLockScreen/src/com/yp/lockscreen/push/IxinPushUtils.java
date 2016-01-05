package com.yp.lockscreen.push;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class IxinPushUtils {
	/**
	 * 获取爱心推key
	 * @param context
	 * @return
	 */
	public static int getAixinTuiKey(Context context){
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			ApplicationInfo ai = pi.applicationInfo;
			Bundle metaDatas = ai.metaData;
			return metaDatas.getInt("aixintui");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取渠道号 (umeng)
	 * @param context
	 * @return
	 */
	public static String getChannel(Context context){
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			ApplicationInfo ai = pi.applicationInfo;
			Bundle metaDatas = ai.metaData;
			String result = metaDatas.getString("UMENG_CHANNEL");
			return result != null ? result : "undefine";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "undefine";
	}
	
	/**
	 * 获取版本名称
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context){
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "0.0";
	}
}
