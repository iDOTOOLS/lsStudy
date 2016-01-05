package com.yp.enstudy.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 
 * 包含设备处理方法的工具类
 * @version 1.0.0 2011-10-10
 */
public class DeviceUtil {
	

	public static final int SDK_VERSION_1_5 = 3;

	public static final int SDK_VERSION_1_6 = 4;
	
	public static final int SDK_VERSION_2_0 = 5;
	
	public static final int SDK_VERSION_2_0_1 = 6;
	
	public static final int SDK_VERSION_2_1 = 7;
	
	public static final int SDK_VERSION_2_2 = 8;
	
	public static final int SDK_VERSION_2_3_1=9;
	
	public static final int SDK_VERSION_2_3_3=10;
	
	public static final int SDK_VERSION_3_0=11;
	
	public static final int SDK_VERSION_4_0_3=15;
	
	public static final int SDK_VERSION_4_1=16;
	/**
	 * 获得设备型号
	 * @return
	 */
	public static String getDeviceModel() {
        return Build.MODEL;
    }
	
	/**
	 * 获得国际移动设备身份码
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
        return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
	
	/**
	 * 获得国际移动用户识别码
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
        return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }
	
	/**
	 * 获得设备屏幕矩形区域范围
	 * @param context
	 * @return
	 */
	public static Rect getScreenRect(Context context) {
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        return new Rect(0, 0, w, h);
    }
	
	/**
	 * 获得设备屏幕密度
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
		return metrics.density;
	}
	
	/** 
	 * 获得系统版本
	 */
	public static String getSDKVersion(){
		return android.os.Build.VERSION.SDK;
	}
	
	/**
	 * 获得系统版本号
	 * @return
	 */
	public static int getSDKVersionInt(){
		return NumberUtil.toInt(android.os.Build.VERSION.SDK);
		//return android.os.Build.VERSION.SDK_INT;
	}
	
	/***
	 * 获得系统版本
	 */
	public static String getSDKVserionName(){
		String versoinName="未获得版本号";
		switch(NumberUtil.toInt(android.os.Build.VERSION.SDK)){
		case SDK_VERSION_1_5:
			versoinName="android_1.5";
			break;
		case SDK_VERSION_1_6:
			versoinName="android_1.6";
			break;
		case SDK_VERSION_2_0:
			versoinName="android_2.0";
			break;
		case SDK_VERSION_2_0_1:
			versoinName="android_2.0.1";
			break;
		case SDK_VERSION_2_1:
			versoinName="android_2.1";
			break;
		case SDK_VERSION_2_2:
			versoinName="android_2.2";
			break;
		case SDK_VERSION_2_3_1:
			versoinName="android_2.3.1";
			break;
		case SDK_VERSION_2_3_3:
			versoinName="android_2.3.3";
			break;
		case SDK_VERSION_3_0:
			versoinName="android_3.0";
			break;
		case SDK_VERSION_4_0_3:
			versoinName="android_4.0.3";
			break;
		case SDK_VERSION_4_1:
			versoinName="android_4.1";
			break;
		}
		return versoinName;
	}
	
	/**
	 * 获得SD卡空间总大小
	 *  @return size -1 SD卡不存在 
	 */
	public static long getSdCardCountSize(){
		long size=-1;
		String state = Environment.getExternalStorageState();		
		if (Environment.MEDIA_MOUNTED.equals(state)) {   
            File sdcardDir = Environment.getExternalStorageDirectory();   
            StatFs sf = new  StatFs(sdcardDir.getPath());   
            long  blockSize = sf.getBlockSize();   
            long  blockCount = sf.getBlockCount();   
            size=blockSize*blockCount/ 1024;
            Log.d("" ,  "block大小:" + blockSize+ ",block数目:" + blockCount+ ",总大小:" +blockSize*blockCount/ 1024 + "KB" );              
		}
		return size;
	}
	
	
	/**
	 * 获得SD卡可用空间总大小
	 *  @return size -1 SD卡不存在 
	 */
	public static long getSdCardHaveSize(){
		long size=-1;
		String state = Environment.getExternalStorageState();		
		if (Environment.MEDIA_MOUNTED.equals(state)) {   
            File sdcardDir = Environment.getExternalStorageDirectory();   
            StatFs sf = new  StatFs(sdcardDir.getPath());   
            long  blockSize = sf.getBlockSize();   
            long  availCount = sf.getAvailableBlocks();   
            size= availCount*blockSize/ 1024;             
            Log.d("" ,  "可用的block数目：:" + availCount+ ",剩余空间:" + availCount*blockSize/ 1024 + "KB" );   
		}
		return size;
	}
	
	/**
	 * 是否是模拟器
	 * @param context
	 * @return
	 */
	public static boolean isEmulator(Context context){   
	    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);   
	    String imei = tm.getDeviceId();   
	    if (imei == null || imei.equals("000000000000000")){   
	        return true;   
	    }   
	    return false;   
	}
	
    /***
     * 获取手机MAC地址
     * 
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        try{
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        }catch(Exception ex){
            ex.printStackTrace();
            return "";
        }
    }
	
	/** 关闭软键盘*/
	public static void closeInputMethod(EditText editTxt, Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(editTxt.getWindowToken(), 0);
	}
	
	/**ping */
	public static String pingHost(String str){ 
        String resault=""; 
        try { 
            // TODO: Hardcoded for now, make it UI configurable  
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " +str); 
            int status = p.waitFor(); 
            if (status == 0) { 
                resault="success"; 
            }    
            else 
            { 
                resault="faild"; 
            } 
            } catch (IOException e) { 
            } catch (InterruptedException e) { 
            } 
         
        return resault; 
    } 

	/**是否安装过谷歌地图*/
	protected boolean checkGoogleMap(Context context) {
		boolean isInstallGMap = false;
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if (p.versionName == null) { // system packages continue;
			}
			if ("com.google.android.apps.maps".equals(p.packageName)) {
				isInstallGMap = true;
				break;
			}
		}
		return isInstallGMap;
	}
	
	public static String getVersionName(Context cxt){
	    try {
            return cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return "";
	}
}



