package com.yp.enstudy;

import android.content.Context;

import com.yp.enstudy.db.DBConstans;
import com.yp.enstudy.db.GlobalConfigMgr;

public class ConfigManager {

    
    public static boolean isLockHome(Context cxt) {
        return GlobalConfigMgr.getLockHome(cxt);
    }
    /**锁定HOME键  开关*/
    public static void setLockHome(Context cxt,boolean flag) {
        GlobalConfigMgr.setLockHome(cxt, flag);
    }
    
    
    /**例句(测试)
     * @param cxt
     * @return 0 不显示      1 显示英文     2 显示中英文
     */
    public static int getSentenceType(Context cxt) {
        return GlobalConfigMgr.getSentenceType(cxt);
    }
    /**例句(测试)*
     * 0 不显示      1 显示英文     2 显示中英文*/
    public static void setSentenceType(Context cxt,int sentenceType) {
        GlobalConfigMgr.setSentenceType(cxt, sentenceType);
    }
    
    /**解锁振动*/
    public static boolean isUnlockVibration(Context cxt) {
        return GlobalConfigMgr.getUnlockVibration(cxt);
    }
    /**解锁振动*/
    public static void setUnlockVibration(Context cxt,boolean flag) {
        GlobalConfigMgr.setUnlockVibration(cxt, flag);
    }
    
    /**中文释义 开关*/
    public static boolean isExplain(Context cxt) {
        return GlobalConfigMgr.getExplain(cxt);
    }
    /**中文释义 开关*/
    public static void setExplain(Context cxt,boolean flag) {
        GlobalConfigMgr.setExplain(cxt, flag);
    }
    
    /**获取提醒复习时间  “默认：21:00”*/
    public static String getAlarmReViewTime(Context cxt) {
        return GlobalConfigMgr.getAlarmReViewTime(cxt);
    }
    /**设置提醒复习时间  "21:00" */
    public static void setAlarmReViewTime(Context cxt,String time) {
        GlobalConfigMgr.setAlarmReViewTime(cxt, time);
    }
    
    
    /**解锁方式 1(默认)   3    5   10*/
    public static int getUnlockScreenType(Context cxt) {
        return GlobalConfigMgr.getUnlockScreenType(cxt);
    }
    /**解锁方式 1(默认)   3    5   10*/
    public static void setUnlockScreenType(Context cxt,int unlockCount) {
        GlobalConfigMgr.setUnlockScreenType(cxt, unlockCount);
    }

    /**设置每日新词数*/
    public static int getWordEveryDayNum(Context context) {
        return GlobalConfigMgr.getWordEveryDayNum(context);
    }
    /**设置每日新词数*/
    public static void setWordEveryDayNum(Context context,int wordEveryDayNum) {
        GlobalConfigMgr.setWordEveryDayNum(context, wordEveryDayNum);
    }

    /**得到排序
     * 0正序(默认)   1随机   2倒序*/
    public static int getOrderType(Context context) {
        return GlobalConfigMgr.getOrderType(context);
    }
    /**设置排序
     * 0正序(默认)   1随机   2倒序*/
    public static void setOrderType(Context context,int orderType) {
        GlobalConfigMgr.setOrderType(context, orderType);
    }
    
    /**设置当前词库*/
    public static void saveCurCiku(Context cxt,String ciku){
        DBConstans.CUR_WORD_LIBRARY_NAME = ciku;
        GlobalConfigMgr.setCurCiKu(cxt, ciku);
    }
    
    /**取得 当前词库*/
    public static String getCurCiku(Context cxt) {
        return GlobalConfigMgr.getCurCiKu(cxt);
    }
    
    /**判断是否使用单词锁屏 */
    public static boolean isUseLockScreen(Context cxt) {
    	return GlobalConfigMgr.getOpenLockScreenFlag(cxt);
    }
    
    /**
     * 设置是否使用锁屏单词
     * @param cxt
     * @param flag 
     */
    public static void setLockSwitch(Context cxt,boolean flag) {
    	GlobalConfigMgr.setOpenLockScreenFlag(cxt, flag);
    }
    
    /**
     * 设置是否开启定时提醒
     * 
     * @param cxt
     * @param flag 
     */
    public static void setIsOpenRemind(Context cxt,boolean flag){
    	GlobalConfigMgr.setIsOpenRemind(cxt, flag);
    }
    /**
     * 判断是否开启定时提醒
     * @param cxt
     * @return
     */
    public static boolean getIsOpenRemind(Context cxt){
    	return GlobalConfigMgr.getIsOpenRemind(cxt);
    }
    
}
