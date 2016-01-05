package com.yp.lockscreen;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.bean.Record;
import com.yp.enstudy.bean.Word;
import com.yp.enstudy.db.DBConstans;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.enstudy.utils.TimeUtil;
import com.yp.lockscreen.bean.ReciteInfoVO;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.Ebbinghaus;
import com.yp.lockscreen.utils.LogHelper;
import com.yp.lockscreen.utils.SerializableUtils;

public class StudyManager {
    private static String TAG = StudyManager.class.getName();

    /**获得 锁屏 界面 中 复习模式单词列表*/
    public ArrayList<Word> getReviewModelInLockScreen(Context cxt){
        if(Global.gReViewWords==null) return null;
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            int lockWordPosition = LockConfigMgr.getLastLockWordPosition(cxt);
            int reviewCountSize = getReviewCountSize();
            lockWordPosition = lockWordPosition>=reviewCountSize?0:lockWordPosition;         //超出范围 从0开始计数
            for(int i=lockWordPosition-1;i>=0;i--){
                if(Global.gReViewWords.get(i).remember!=2)
                    list.add(0,Global.gReViewWords.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**获得 锁屏 界面 中 解锁模式单词列表*/
    public ArrayList<Word> getLockModelInLockScreen(Context cxt){
        if(Global.gReViewWords==null) return null;
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            int lockWordPosition = LockConfigMgr.getLastLockWordPosition(cxt);
            int reviewCountSize = getReviewCountSize();
            lockWordPosition = lockWordPosition>=reviewCountSize?0:lockWordPosition;        //超出范围 从0开始计数
            int typeSize = ConfigManager.getUnlockScreenType(cxt);     //1,3,5,10
            for(int i=lockWordPosition;i<(lockWordPosition + typeSize);i++){
                if(i<Global.gReViewWords.size() && Global.gReViewWords.get(i).remember!=2)
                    list.add(Global.gReViewWords.get(i));
            }
            int listSize = list.size();
            if(listSize<typeSize && typeSize<=Global.gReViewWords.size()){           //如果不足解锁数 则从零开始添加  且 不允许超出设置的解锁次数
                for(int i=0;i<typeSize-listSize;i++){
                    if(Global.gReViewWords.get(i).remember!=2)
                        list.add(Global.gReViewWords.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void initNextGroupReivewWords(){
        initNextDayList(Global.gContext);
        initDayListForWord();
    }
    
    public int getReviewCountSize(){
        int size = Global.gReViewWords.size();
        return size;
    }
    
    public void initNextDayList(Context cxt){
        int everyDayNum = ConfigManager.getWordEveryDayNum(cxt);     //10,20,30
        String dayList = LockConfigMgr.getDayList(cxt);
        String[] strs = dayList.split(",");
        int lenght = strs.length;
        String tmpDayList ="";
        int lastInt = Integer.valueOf(strs[everyDayNum-1]);
        for(int i=1;i<=everyDayNum;i++){
            tmpDayList += (lastInt+i)+",";
        }
        dayList = tmpDayList + dayList;
        strs = dayList.split(",");
        dayList = "";
        for(int j=0;j<lenght;j++){
            dayList += strs[j] + ",";
        }
        dayList = dayList.substring(0, dayList.length()-1);
        LockConfigMgr.setDayList(cxt, dayList);
    }
    
    /**初始化 每天单词列表*/
    public void initDayListData(Context cxt){
        try {
            int everyDayNum = ConfigManager.getWordEveryDayNum(cxt);     //10,20,30
            String dayList = LockConfigMgr.getDayList(cxt);
            String tmpDayList ="";
            if(TextUtils.isEmpty(dayList)){     //第一次写入
                for(int i=1;i<=everyDayNum;i++){
                    if(i<everyDayNum){
                        tmpDayList += i+",";
                    }else{
                        tmpDayList +=i;
                    }
                }
            }else{
                String[] strs = dayList.split(",");
                int lastInt = Integer.valueOf(strs[everyDayNum-1]);
                for(int i=1;i<=everyDayNum;i++){
                    tmpDayList += (lastInt+i)+",";
                }
            }
            dayList = tmpDayList + dayList;
            
            LogHelper.d(TAG, "save before dayList:"+dayList);
            
//            addEbbinghausPlan(dayList);
            
            saveDayList(cxt, dayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Ebbinghaus mEbbingHaus = Ebbinghaus.getInstance();
    public void addEbbinghausPlan(String dayList){
        String[] strs = dayList.split(",");
        int[] ids = new int[strs.length];
        for(int i=0;i<strs.length;i++){
            ids[i] = Integer.valueOf(strs[i]);
        }
        mEbbingHaus.addWord2EbbinghausPlan(ids);
        saveEbbinghausPlan(mEbbingHaus.getIdInfoPair());
        if(LogHelper.flag)
            mEbbingHaus.showPlan();
    }
    
    public boolean saveEbbinghausPlan(Map<Integer, ReciteInfoVO> maps){
        if(maps!=null){
            SerializableUtils.inputSerializableFile(maps, "ebbinghausplan", Global.gContext);
            return true;
        }
        return false;
    }
    
    public Map<Integer, ReciteInfoVO> getEbbinghausPlan(){
        return (Map<Integer, ReciteInfoVO>) SerializableUtils.readSerializableFile("ebbinghausplan", Global.gContext);
    } 
    
    /**根据每天最大复习数 重新计算dayList*/
    public String logicDayListByMaxWord(String dayList,int everyWordNum,Context cxt){
        try {
            String[] strs = dayList.split(",");
            int max = getMaxWordsByEveryStudyNews(everyWordNum);
            String resetDayList = "";
            if(strs.length>max){
                for(int i=0;i<max;i++){
                    resetDayList +=strs[i]+",";
                }
                 return resetDayList.substring(0,resetDayList.length()-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**根据每天新增学习得到 最大的复习数*/
    public int getMaxWordsByEveryStudyNews(int everyWordNum){
        return everyWordNum * 4;
    }

    /**重置dayList*/
    public void resetDayListData(Context cxt){
        try {
            int typeSize = ConfigManager.getWordEveryDayNum(cxt);    
            String dayList="";
            for(int i=1;i<=typeSize;i++){
                if(i<typeSize){
                    dayList += i+",";
                }else{
                    dayList +=i;
                }
            }
            saveDayList(cxt, dayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**保存daylist      内部逻辑带有重新计算方法  如果单独使用 setDayList 可能会数据有误*/
    public void saveDayList(Context cxt,String dayList){
        try {
            int everyDayNum = ConfigManager.getWordEveryDayNum(cxt);
            String resetDayList = logicDayListByMaxWord(dayList,everyDayNum,cxt);
            
            if(!TextUtils.isEmpty(resetDayList))
                dayList = resetDayList;
            
            LockConfigMgr.setDayList(cxt, dayList);
            LogHelper.d(TAG, "save after  dayList:"+dayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**初始化这一天 单词学习数据*/
    public boolean initDayListForWord(){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            String dayList = LockConfigMgr.getDayList(Global.gContext);
            int everyDayNum = ConfigManager.getWordEveryDayNum(Global.gContext);
            if(!TextUtils.isEmpty(dayList)){
                String[] dayStrs = dayList.split(",");
                int startId=0;
                if(dayStrs.length>=everyDayNum){
                    startId = Integer.valueOf(dayStrs[dayStrs.length-everyDayNum]) -1;
                }
                list = Global.gWordData.getStudyWordGroup(startId,dayStrs.length,ConfigManager.getOrderType(Global.gContext));
                
                if(list!=null && list.size()>0){
                    Global.gReViewWords = new ArrayList<Word>();
                    Global.gReViewWords.addAll(list);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**验证是否是新的学习一天*/
    public boolean checkNewStudyDay(){
        try {
            Record vo = Global.gWordData.getTodayRecord();
            if(!TimeUtil.checkSameDay(vo.record_time, System.currentTimeMillis()/1000)){        
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    /**获得今天应该复习的单词数*/
    public int getTodayStudyNum(Context cxt){
        int num=10;
        try {
            String dayList = LockConfigMgr.getDayList(cxt);
            if(TextUtils.isEmpty(dayList)){
                num=0;
            }else{
                String[] strs = dayList.split(",");
                num = strs.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
    
    /**缓存开始复习中的 未记住单词*/
    public void cacheNoRememberWord(ArrayList<Word> mNoRememberWords,Context context){
        try {
            if(mNoRememberWords!=null && mNoRememberWords.size()>0){
                /**缓存未记住的单词*/
                SerializableUtils.inputSerializableFile(mNoRememberWords, "norememberwords", context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**清除 开始复习中的 未记住单词*/
    public void clearNoRememberWord(Context context){
        try {
            SerializableUtils.delSerializableFile("norememberwords", context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
