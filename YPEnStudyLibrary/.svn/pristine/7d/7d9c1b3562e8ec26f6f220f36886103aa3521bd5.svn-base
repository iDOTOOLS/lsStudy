package com.yp.enstudy;

import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.os.Environment;
import com.yp.enstudy.bean.Record;
import com.yp.enstudy.bean.TableName;
import com.yp.enstudy.bean.Word;
import com.yp.enstudy.db.DBConstans;
import com.yp.enstudy.db.DBHelper;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.enstudy.db.StudyDao;
import com.yp.enstudy.utils.DeviceUtil;
import com.yp.enstudy.utils.LogHelper;
import com.yp.enstudy.utils.MD5;
import com.yp.enstudy.utils.TimeUtil;

public class WordData{
    private Context mContext;
    private StudyDao mDao;
    public WordData(Context context){
        mContext = context;
        mDao = new StudyDao(mContext);
        initDefaultWordDB();
    }
    
    public void initDefaultWordDB(){
        if(mDao!=null){
            mDao.initDefaultDB();
        }
    }
    
    /**设置切换词库*/
    public void setDBName(String dbName){
        if(mDao!=null)
            mDao.setDBName(dbName);
    }
    
    /**坚持使用时间*/
    public int getStudyDayTime(){
        long firstStudyTime = 0;
        firstStudyTime = GlobalConfigMgr.getFirstStudyTime(mContext);
        if(firstStudyTime==0){
            GlobalConfigMgr.setFirstStudyTime(mContext, firstStudyTime=System.currentTimeMillis()/1000);
        }
        return (int)(System.currentTimeMillis()/1000-firstStudyTime)/(60*60*24);
    }
    
    /**当前选择词库数据*/
    public TableName getCurWordLibraryData(){
        try {
            Constans.CUR_LIBRARY_DATA = mDao.queryTableName(ConfigManager.getCurCiku(mContext)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constans.CUR_LIBRARY_DATA;
    }
    
    /**生词列表*/
    public ArrayList<Word> getNoRemember(){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            list = mDao.queryAllWordLibrary(Constans.TYPE_NO_REMEMBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**已掌握单词列表*/
    public ArrayList<Word> getRemember(){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            list = mDao.queryAllWordLibrary(Constans.TYPE_REMEMBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 获得 复习 列表
     * count 新词数量
     * orderType 0 正    1随机   2倒
     * */
    public ArrayList<Word> getReviewList(int count,int orderType){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            list = mDao.queryRecordWordLibrary(orderType, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 获得 复习 列表
     * 起始 id   1时 起始ID为2
     * count 新词数量
     * orderType 0 正    1随机   2倒
     * */
    public ArrayList<Word> getReviewList(int startId,int count,int orderType){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            list = mDao.queryRecordWordLibrary(orderType, startId,count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 获得 复习 列表  包含已掌握的
     * 起始 id   1时 起始ID为2
     * count 新词数量
     * orderType 0 正    1随机   2倒
     * */
    public ArrayList<Word> getStudyWordGroup(int startId,int count,int orderType){
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            list = mDao.queryWordLibrary(orderType, startId,count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    /**学习记录*/
    public ArrayList<Record> getRecordAllList(){
        ArrayList<Record> list = new ArrayList<Record>();
        try {
            list = mDao.queryAllRecordTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**取得当天学习记录*/
    public Record getTodayRecord(){
        Record vo = new Record();
        try {
            vo = mDao.queryLastRecordTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
    
    /**每天第一次使用 时 添加使用记录  执行 则 返回 true   否则 false*/
    public boolean addEveryDayFirstUseRecord(){
        try {
            if(mDao!=null){
                Record last = mDao.queryLastRecordTime();
                if(!TimeUtil.checkSameDay(last.record_time, System.currentTimeMillis()/1000)){        //如果是同一天不执行任何添加
                    Record today = new Record();
                    today.record_date = TimeUtil.getYearTime(System.currentTimeMillis()/1000);
                    today.record_time = System.currentTimeMillis()/1000;
                    today.review = 0;
                    today.record_days = last.record_days + 1;
                    today.record_count = 0;
                    today.record_words = 0;
                    addRecord(today);
                    System.out.println("addRecord");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**添加使用记录*/
    public void addRecord(Record record){
        if(record!=null)
            mDao.addRecord(record);
    }
    
    /**解锁次数+1*/
    public void updateTodayUnlockCount(){
        if(mDao!=null)
            mDao.updateLockCount();
    }
    
    /**更新 今天复习单词数*/
    public void updateTodayReViewWordCount(int count){
        if(mDao!=null){
            mDao.updateReviewWordCount(count);
        }
    }
    
    /**单词 标记为 已掌握*/
    public void setRememberWord(String word){
        if(mDao!=null){
            mDao.updateWordRemember(word, 2);
        }
    }
    /**移除 已掌握*/
    public void removeRememberWord(String word){
        if(mDao!=null){
            mDao.updateWordRemember(word, 0);
        }
    }
    
    /**单词 标记为 生词*/
    public void setNoRememberWord(String word){
        if(mDao!=null){
            mDao.updateWordRemember(word, 1);
        }
    }
    /**移除 生词表*/
    public void removeNoRememberWord(String word){
        if(mDao!=null){
            mDao.updateWordRemember(word, 0);
        }
    }
    
    /**查询所有词库列表信息*/
    public ArrayList<TableName> queryAllTableName(){
        ArrayList<TableName> list = new ArrayList<TableName>();
        try {
            if(mDao!=null){
                list = mDao.queryTableName(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void updateReviewState(boolean flag){
        if(mDao!=null)
            mDao.updateReviewState(flag);
    }
    
    public String getVoicePathByWord(String word){
        return Constans.PATH_AUDIO + File.separator + ConfigManager.getCurCiku(mContext) + File.separator + getVoiceNameByWord(word);
    }
    
    public String getVoiceNameByWord(String word){
//      System.out.println(CipherUtility.encrypt("wyzlovelww1314","consistent"));
//      System.out.println(MD5.getMessageDigest(("consistent"+"wyzlovelww1314").getBytes()));//5b60eccf8caeb5aeac46b664053a8f38
        String key = "wyzlovelww1314";
        return MD5.getMessageDigest((word+key).getBytes());
    }
    
    public void initBaseDir(){
        String basePath = "";
        long availableSDCardSpace = DeviceUtil.getSdCardHaveSize();// 获取SD卡可用空间
        if (availableSDCardSpace != -1L) {// 如果存在SD卡
            basePath = Environment.getExternalStorageDirectory() + File.separator;
        } else if (DeviceUtil.getSdCardHaveSize() != -1) {
            basePath = mContext.getFilesDir().getPath() + File.separator ;
        } else {            // sd卡不存在
            basePath = mContext.getFilesDir().getPath() + File.separator ;
        }
        
        File basePathFile = new File(basePath);
        if(!basePathFile.exists()){
            basePathFile.mkdirs();
        }
        
        File downloadPathFile = new File(basePath + Constans.PATH_DOWNLOAD);
        if(!downloadPathFile.exists()){
            downloadPathFile.mkdirs();
        }
        
        File voicePathFile = new File(basePath + Constans.PATH_AUDIO);
        if(!voicePathFile.exists()){
            voicePathFile.mkdirs();
        }
    }
    
    /**验证数据库是否存在*/
    public boolean checkDB(String dbName){
        try {
            DBHelper  helper = new DBHelper(mContext);
            return helper.checkDataBase(dbName);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }
    
    /**验证是否存在 词库语音包
     * cikuName  ciku_01*/
    public boolean checkVoice(String cikuName){
        try {
            File[] files = new File(Constans.PATH_AUDIO,cikuName).listFiles();
            if(files!=null && files.length>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    /**初始化当前选择的词库*/
    public void initCurCikuName(){
        DBConstans.CUR_WORD_LIBRARY_NAME = ConfigManager.getCurCiku(mContext);
        LogHelper.d("WordData", "当前词库:"+DBConstans.CUR_WORD_LIBRARY_NAME);
    }
}
