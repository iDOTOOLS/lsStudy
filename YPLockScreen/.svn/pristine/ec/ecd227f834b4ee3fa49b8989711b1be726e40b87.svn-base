package com.yp.lockscreen.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * 
 * 包含设备处理方法的工具类
 * 
 * @version 1.0.0 2011-10-10
 */
public class DeviceUtil {
    /**
     * 获得设备型号
     * 
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static String getSDKVserionName(){
        return Build.VERSION.RELEASE;
    }
    
    /**
     * 获得国际移动设备身份码
     * 
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        return ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获得国际移动用户识别码
     * 
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        return ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获得设备屏幕矩形区域范围
     * 
     * @param context
     * @return
     */
    public static Rect getScreenRect(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        return new Rect(0, 0, w, h);
    }

    /**
     * 获得设备屏幕密度
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return metrics.density;
    }

    /**
     * 获得系统版本
     */
    public static String getSDKVersion() {
        return android.os.Build.VERSION.SDK;
    }


    /**
     * 获得SD卡空间总大小  返回单位  KB
     * 
     * @return size -1 SD卡不存在
     */
    public static long getSdCardCountSize() {
        long size = -1;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            size = blockSize * blockCount / 1024;
            Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount
                    + ",总大小:" + blockSize * blockCount / 1024 + "KB");
        }
        return size;
    }
    
    /**
     * 获得指定文件夹总大小 返回单位KB
     * @return size -1 SD卡不存在
     * */
    public static long getFileCountSize(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileCountSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        Log.d("", "目录大小:" + size / 1024 + "KB");
        return size/1024;
    }

    /**
     * 获得SD卡可用空间总大小
     * 
     * @return size -1 SD卡不存在 单位 K
     */
    public static long getSdCardHaveSize() {
        long size = -1;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            size = availCount * blockSize / 1024;
            Log.d("", "可用的block数目：:" + availCount + ",剩余空间:" + availCount
                    * blockSize / 1024 + "KB");
        }
        return size;
    }

    /**
     * 是否是模拟器
     * 
     * @param context
     * @return
     */
    public static boolean isEmulator(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null || imei.equals("000000000000000")) {
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
            return null;
        }
    }

    /** 关闭软键盘 */
    public static void closeInputMethod(EditText editTxt, Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTxt.getWindowToken(), 0);
    }

    /** ping */
    public static String pingHost(String str) {
        String resault = "";
        try {
            // TODO: Hardcoded for now, make it UI configurable
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + str);
            int status = p.waitFor();
            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }

        return resault;
    }

    /** 定位服务 是否开启 true 开启 false 未开启 */
    public static boolean checkLocationEnable(Context context) {
        boolean flag = true;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        final boolean networkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//        if (!gpsEnabled && !networkEnabled) {
//            new EnableGpsDialogFragment().show(
//                    ((FragmentActivity) context).getSupportFragmentManager(),
//                    "dialog");
//            flag = false;
//        }
        return flag;
    }

    /** 返回系统语言 */
    public static String getLocalLanguage() {
        String strs="";
        try{
            Locale locale = Locale.getDefault();
            strs+=locale.getLanguage();
            if(!TextUtils.isEmpty(locale.getCountry())){
                strs+="-"+locale.getCountry();
            }
//            LogUtil.print("language:"+strs);
        }catch(Exception ex){
            ex.printStackTrace();
            strs="zh-CN";
        }
         return strs;
//        return locale.getLanguage() + "-" + "hans";
    }

    /** 是否安装过谷歌地图 */
    protected boolean checkGoogleMap(Context context) {
        boolean isInstallGMap = false;
        List<PackageInfo> packs = context.getPackageManager()
                .getInstalledPackages(0);
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

    @TargetApi(11)
    public static void enableStrictMode() {
        if (DeviceUtil.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
                    .detectNetwork();
            // .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
                    .detectAll();
            // .penaltyLog();

            if (DeviceUtil.hasHoneycomb()) {
                // threadPolicyBuilder.penaltyFlashScreen(); //闪红提示
                // vmPolicyBuilder
                // .setClassInstanceLimit(ImageGridActivity.class, 1)
                // .setClassInstanceLimit(ImageDetailActivity.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
        return false;
    }
    
    public static void simulateKey(final int KeyCode) {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    
    /**
     * 判断桌面是否已添加快捷方式
     * 
     * @param cx
     * @param titleName
     *            快捷方式名称
     * @return
     */
    public static boolean hasShortcut(Context cx) {
        boolean result = false;
        // 获取当前应用名称
        String title = null;
        try {
            final PackageManager pm = cx.getPackageManager();
            title = pm.getApplicationLabel(
                    pm.getApplicationInfo(cx.getPackageName(),
                            PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }

        final String uriStr;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,
                "title=?", new String[] { title }, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }
    
    
    /**手动缩回通知栏*/
    public static void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;

            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    
    /**
     * 验证服务是否启动
     * serviceName   包名+服务名
     * */
    public static boolean isServiceWorked(Context context,String serviceName) {
        ActivityManager myManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
