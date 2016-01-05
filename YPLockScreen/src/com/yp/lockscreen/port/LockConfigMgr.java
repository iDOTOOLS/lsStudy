package com.yp.lockscreen.port;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.db.SharedPreferencesCompat;
import com.yp.lockscreen.utils.LogHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class LockConfigMgr {
    private static final String PREFS_FILE = "lock_config";

    public static void setLockSize(Context cxt, int size) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("lock_size", size));
    }

    public static int getLockSize(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getInt("lock_size", ConfigManager.getUnlockScreenType(cxt));
    }

    public static void setIsFirstUse(Context cxt, boolean isFirst) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("first_time_use", isFirst));
    }

    public static boolean getIsFirstUse(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean("first_time_use", true);
    }

    public static void setIsFirstSet(Context cxt, boolean isFirst) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("first_time_set", isFirst));
    }

    public static boolean getIsFirstSet(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean("first_time_set", true);
    }

    public static void setIsFirstSetLock(Context cxt, boolean isFirst) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("first_time_set_lock_count", isFirst));
    }

    public static boolean getIsFirstSetLock(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean("first_time_set_lock_count", true);
    }

    public static String getImageName(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getString("wallpager_name", null);
    }

    public static void setImageName(Context cxt, String name) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putString("wallpager_name", name));
    }

    /** 是否显示过锁屏 */
    public static void setIsShowLockScreen(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("first_show_lockscreen", true));
    }

    /** 是否显示过锁屏 */
    public static boolean getIsShowLockScreen(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean("first_show_lockscreen", false);
    }

    /****
     * 记录 解锁后最后一个单词的位置
     * @param cxt
     * @param index
     */
    public static void setLastLockWordPosition(Context cxt,int index){
        if(Global.gReViewWords!=null){
            index = index>Global.gReViewWords.size()?Global.gReViewWords.size():index;
        }
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("today_id", index));
        LogHelper.d("lockview", "解锁单词位置:"+index);
    }
    
    public static int getLastLockWordPosition(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getInt("today_id", 0);
    }
    
    /***
     * 例: 第一天:1,2,3,4,5,6,7,8,9,10        第二天:11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7,8,9,10
     * @param cxt
     * @param days
     */
    public static void setDayList(Context cxt,String days){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putString("day_list", days));
    }
    
    public static String getDayList(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getString("day_list", "");
    }
    
    /**设置是否提示过 小米适配 设置页面*/
    public static void setMiuiAdapterActivity(Context cxt, boolean showed) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("first_miui_activity", showed));
    }

    /**获得是否提示过 小米适配 设置页面*/
    public static boolean getMiuiAdapterActivity(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean("first_miui_activity", false);
    }
    
    /**application中执行初始化时间*/
    public static void setApplicationInitTime(Context cxt, long time){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putLong("application_init_time", time));
    }
    
    /**application中执行初始化时间*/
    public static long getApplicationInitTime(Context cxt) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getLong("application_init_time", 0);
    }
    
}
