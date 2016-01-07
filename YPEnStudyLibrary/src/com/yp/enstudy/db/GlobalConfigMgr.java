package com.yp.enstudy.db;


import android.content.Context;
import android.content.SharedPreferences;

public class GlobalConfigMgr {
    private static final String PREFS_FILE = "global_config";
    /**每日新词数*/
    private static int wordEveryDayNum=10;
    /**排序*/
    private static int orderType = 0;
    /**解锁方式 1(默认)   3    5   10*/
    private static int unlockCount = 1;
    /**复习提醒时间（默认21:00）*/
    private static String alarmReViewTime = "21:00";
    /**中文释义*/
    private static boolean isExplain;
    /**解锁震动*/
    private static boolean isUnlockVibration;
    /**例句(测试)*
     * 0 不显示      1 显示英文     2 显示中英文*/
    private static int sentenceType;
    /**锁定HOME键*/
    private static boolean isLockHome =false;

    public static long getFirstStudyTime(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getLong("first_study", 0);
    }
    
    public static void setFirstStudyTime(Context cxt,long time){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putLong("first_study", time));
    }
    
    public static int getWordEveryDayNum(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getInt("everyday_num", wordEveryDayNum);
    }
    
    public static void setWordEveryDayNum(Context cxt,int count){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("everyday_num", count));
    }
    
    public static int getOrderType(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getInt("order_type", orderType);
    }
    
    public static void setOrderType(Context cxt,int count){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("order_type", count));
    }
    
    public static int getUnlockScreenType(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getInt("unlock_count_type", unlockCount);
    }
    
    public static void setUnlockScreenType(Context cxt,int count){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("unlock_count_type", count));
    }
    
    public static String getAlarmReViewTime(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getString("alarm_review_time",alarmReViewTime);
    }
    
    public static void setAlarmReViewTime(Context cxt,String time){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putString("alarm_review_time", time));
    }
    
    public static boolean getExplain(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getBoolean("explain",isExplain);
    }
    
    public static void setExplain(Context cxt,boolean flag){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("explain", flag));
    }
    
    public static boolean getUnlockVibration(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getBoolean("unlock_vibration",isUnlockVibration);
    }
    
    public static void setUnlockVibration(Context cxt,boolean flag){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("unlock_vibration", flag));
    }
    
    public static int getSentenceType(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getInt("sentence",sentenceType);
    }
    
    public static void setSentenceType(Context cxt,int type){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("sentence", type));
    }
    
    public static boolean getLockHome(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getBoolean("lock_home",isLockHome);
    }
    
    public static void setLockHome(Context cxt,boolean flag){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("lock_home", flag));
    }
    
    public static String getCurCiKu(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getString("cur_ciku",DBConstans.CUR_WORD_LIBRARY_NAME);
    }
    
    public static void setCurCiKu(Context cxt,String cikuName){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putString("cur_ciku", cikuName));
    }
    
    public static int getWallFlag(Context cxt){
    	SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getInt("wall_flag", -1);
    }
    public static void setWallFlag(Context cxt,int flag){
    	SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putInt("wall_flag", flag));
    }
    
    private static Boolean vOpenLockscreen = null;
    public static boolean getOpenLockScreenFlag(Context cxt){
        if(vOpenLockscreen == null) {
        	SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        	vOpenLockscreen = prefs.getBoolean("is_open_lock_flag", true);
        }
        return vOpenLockscreen;
    }
    
    public static void setOpenLockScreenFlag(Context cxt,boolean flag){
        if(vOpenLockscreen != null || vOpenLockscreen.booleanValue() != flag) {
            vOpenLockscreen = flag;
        	SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        	SharedPreferencesCompat.apply(prefs.edit().putBoolean("is_open_lock_flag", flag));
        }
    }
    
    public static boolean getIsOpenRemind(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return prefs.getBoolean("is_open_remind", false);
    }
    
    public static void setIsOpenRemind(Context cxt,boolean isOpen){
        SharedPreferences prefs = cxt.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        SharedPreferencesCompat.apply(prefs.edit().putBoolean("is_open_remind", isOpen));
    }
    
}
