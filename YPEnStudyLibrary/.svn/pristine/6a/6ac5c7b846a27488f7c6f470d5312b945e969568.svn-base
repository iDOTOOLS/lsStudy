package com.yp.enstudy.db;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.bean.Record;
import com.yp.enstudy.bean.TableName;
import com.yp.enstudy.bean.Word;
import com.yp.enstudy.utils.TimeUtil;

public class StudyDao {
    private DBHelper mHelper;
    private Context mContext;
    private String DB_PATH;
    
    
    public StudyDao(Context context){
        mContext = context;
        mHelper = DBHelper.getInstance(mContext);
        DB_PATH = "/data/data/"+mContext.getPackageName()+"/databases/";
    }
    
    public void setDBName(String dbName){
        mHelper = new DBHelper(mContext, dbName);
        DBConstans.CUR_WORD_LIBRARY_NAME = dbName;
        ConfigManager.saveCurCiku(mContext, dbName);
    }
    
    public void initDB(){
        try {
            mHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    /**查询 table_name*/
    public synchronized ArrayList<TableName>  queryTableName(String name){
        ArrayList<TableName> tableList = new ArrayList<TableName>();
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READONLY);
//            db = mHelper.getReadableDatabase();
            Cursor cursor;
            if(TextUtils.isEmpty(name))
                cursor = db.rawQuery("select * from " + DBConstans.TABLE_NAME,null);
            else
                cursor = db.rawQuery("select * from " + DBConstans.TABLE_NAME + " where name=?",new String[]{name});
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    TableName vo = new TableName();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.name = cursor.getString(cursor.getColumnIndex("name"));
                    vo.ciku_name = cursor.getString(cursor.getColumnIndex("ciku_name"));
                    vo.ciku_size = cursor.getInt(cursor.getColumnIndex("ciku_size"));
                    vo.word_count = cursor.getInt(cursor.getColumnIndex("word_count"));
                    vo.audio_size = cursor.getInt(cursor.getColumnIndex("audio_size"));
                    vo.audio_url = cursor.getString(cursor.getColumnIndex("audio_url"));
                    vo.extrs1 =cursor.getString(cursor.getColumnIndex("extrs1"));
                    vo.extrs2 =cursor.getString(cursor.getColumnIndex("extrs2"));
                    vo.flag1 = cursor.getInt(cursor.getColumnIndex("flag1"));
                    vo.flag2 = cursor.getInt(cursor.getColumnIndex("flag2"));
                    vo.remembered = cursor.getInt(cursor.getColumnIndex("remembered"));
                    vo.sequence = cursor.getInt(cursor.getColumnIndex("sequence"));
                    vo.type = cursor.getString(cursor.getColumnIndex("type"));
                    vo.type_id = cursor.getInt(cursor.getColumnIndex("type_id"));
                    vo.url = cursor.getString(cursor.getColumnIndex("url"));
                    tableList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return tableList;
    }
    
    /**查询学习记录*/
    public synchronized ArrayList<Record> queryAllRecordTime(){
        ArrayList<Record> list = new ArrayList<Record>();
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery("select * from record order by _id desc", null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Record record = new Record();
                    record._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    record.record_count = cursor.getInt(cursor.getColumnIndex("record_count"));
                    record.record_date = cursor.getString(cursor.getColumnIndex("record_date"));
                    record.record_days = cursor.getInt(cursor.getColumnIndex("record_days"));
                    record.record_time = cursor.getInt(cursor.getColumnIndex("record_time"));
                    record.record_words = cursor.getInt(cursor.getColumnIndex("record_words"));
                    record.review = cursor.getInt(cursor.getColumnIndex("review"));
                    list.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return list;
    }
    
    /**查询最后学习记录*/
    public synchronized Record queryLastRecordTime(){
        Record record = new Record();
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery("select * from record order by _id desc", null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                record._id = cursor.getInt(cursor.getColumnIndex("_id"));
                record.record_count = cursor.getInt(cursor.getColumnIndex("record_count"));
                record.record_date = cursor.getString(cursor.getColumnIndex("record_date"));
                record.record_days = cursor.getInt(cursor.getColumnIndex("record_days"));
                record.record_time = cursor.getInt(cursor.getColumnIndex("record_time"));
                record.record_words = cursor.getInt(cursor.getColumnIndex("record_words"));
                record.review = cursor.getInt(cursor.getColumnIndex("review"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return record;
    }
    
    /**添加 记录*/
    public synchronized void addRecord(Record record){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READWRITE);
            ContentValues cv = new ContentValues();
            cv.put("record_date", record.record_date);
            cv.put("record_count", record.record_count);
            cv.put("record_days", record.record_days);
            cv.put("record_time", record.record_time);
            cv.put("record_words", record.record_words);
            cv.put("review", record.review);
            db.insert("record", null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
    }
    
    /**更新 今天要复习 单词数*/
    public synchronized void updateReviewWordCount(int count){
        SQLiteDatabase db =null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("update record SET record_words="+count+" WHERE record_date='" + TimeUtil.getYearTime(System.currentTimeMillis() / 1000) + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
    }
    
    /**更新 状态 今天是否复习过*/
    public synchronized void updateReviewState(boolean flag){
        SQLiteDatabase db =null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("update record SET review="+(flag?1:0)+" WHERE record_date='" + TimeUtil.getYearTime(System.currentTimeMillis() / 1000) + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
    }
    
    /**更新 单词 学习状态   1 生词   2已掌握*/
    public synchronized void updateWordRemember(String word,int remember){
        SQLiteDatabase db =null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("update word SET remember=" + remember + " where word='" + word + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
    }
    
    /**解锁 次数 +1*/
    public synchronized void updateLockCount(){
        SQLiteDatabase db =null;
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH+DBConstans.DB_BEIDANCI, null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("update record SET record_count=record_count+1 WHERE record_date='" + TimeUtil.getYearTime(System.currentTimeMillis() / 1000) + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
    }
    
    /**
     * 查询词库单词
     * rememberType  "1"  生词    "2"  已掌握    null全部
     * */
    public synchronized ArrayList<Word> queryAllWordLibrary(String rememberType){
        ArrayList<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = null;
        try {
//            db = mHelper.getReadableDatabase();
            db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = null;
            if(TextUtils.isEmpty(rememberType))
                cursor = db.rawQuery("select * from word", null);
            else
                cursor = db.rawQuery("select * from word where remember=?", new String[]{rememberType});
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Word vo = new Word();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                    vo.en = cursor.getString(cursor.getColumnIndex("en"));
                    vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                    vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                    vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                    vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                    vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                    vo.word = cursor.getString(cursor.getColumnIndex("word"));
                    wordList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return wordList;
    }
    

    /*  根据 起始位置 与 数量 查询词库  
    * startId  起始ID 
    * orderType 0 正序 1 随机   2倒序
    * count 取出的数量      -1 ALL
    * */
   public synchronized ArrayList<Word> queryWordLibrary(int orderType,int startId,int count){
       ArrayList<Word> wordList = new ArrayList<Word>();
       SQLiteDatabase db = null;
       try {
           db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
           Cursor cursor = null;
           String orderStrs = getOrderStrByType(orderType);
           String countStr = "";
           if(startId>=0 && count >0){
               countStr = " limit " + startId + "," + count;
           }
           cursor = db.rawQuery("select * from word order by " + orderStrs + countStr,null);
           
           if(cursor.getCount()>0){
               while(cursor.moveToNext()){
                   Word vo = new Word();
                   vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                   vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                   vo.en = cursor.getString(cursor.getColumnIndex("en"));
                   vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                   vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                   vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                   vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                   vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                   vo.word = cursor.getString(cursor.getColumnIndex("word"));
                   wordList.add(vo);
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           if(db!=null){
               db.close();
               db=null;
           }
       }
       return wordList;
   }
    
    /**
     * 查询词库单词
     * rememberType  "1"  生词    "2"  已掌握    null全部
     * orderType 0 正序 1 随机   2倒序
     * count 取出的数量    -1  ALL
     * */
    public synchronized ArrayList<Word> queryWordLibrary(String rememberType,int orderType,int count){
        ArrayList<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = null;
        try {
//            db = mHelper.getReadableDatabase();
            db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = null;
            String orderStrs = getOrderStrByType(orderType);
            String counts = count>0?"limit "+count:"";
            if(TextUtils.isEmpty(rememberType))
                cursor = db.rawQuery("select * from word order by " + orderStrs + counts, null);
            else
                cursor = db.rawQuery("select * from word where remember=? order by " + orderStrs + counts, new String[]{rememberType});
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Word vo = new Word();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                    vo.en = cursor.getString(cursor.getColumnIndex("en"));
                    vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                    vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                    vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                    vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                    vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                    vo.word = cursor.getString(cursor.getColumnIndex("word"));
                    wordList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return wordList;
    }
    
    /**
     * 查询词库  生词 列表
     * rememberType  "1"  生词    "2"  已掌握    null全部
     * orderType 0 正序 1 随机   2倒序
     * count 取出的数量      -1 ALL
     * */
    public synchronized ArrayList<Word> queryNoRememberLibrary(int orderType,int count){
        ArrayList<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = null;
        try {
//            db = mHelper.getReadableDatabase();
            db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = null;
            String orderStrs = getOrderStrByType(orderType);
            String counts = count>0?"limit "+count:"";
            cursor = db.rawQuery("select * from word where remember = 1 order by " + orderStrs + counts,null);
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Word vo = new Word();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                    vo.en = cursor.getString(cursor.getColumnIndex("en"));
                    vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                    vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                    vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                    vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                    vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                    vo.word = cursor.getString(cursor.getColumnIndex("word"));
                    wordList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return wordList;
    }
    
    /**
     * 查询词库  remember <>2   列表
     * rememberType  "1"  生词    "2"  已掌握    null全部
     * orderType 0 正序 1 随机   2倒序
     * count 取出的数量      -1 ALL
     * */
    public synchronized ArrayList<Word> queryRecordWordLibrary(int orderType,int count){
        ArrayList<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = null;
        try {
//            db = mHelper.getReadableDatabase();
            db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = null;
            String orderStrs = getOrderStrByType(orderType);
            String counts = count>0?" limit "+count:"";
            cursor = db.rawQuery("select * from word where remember<>2 order by " + orderStrs + counts,null);
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Word vo = new Word();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                    vo.en = cursor.getString(cursor.getColumnIndex("en"));
                    vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                    vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                    vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                    vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                    vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                    vo.word = cursor.getString(cursor.getColumnIndex("word"));
                    wordList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return wordList;
    }
    
    /**
     * 查询词库  remember <>2   列表
     * rememberType  "1"  生词    "2"  已掌握    null全部
     * orderType 0 正序 1 随机   2倒序
     * count 取出的数量      -1 ALL
     * */
    public synchronized ArrayList<Word> queryRecordWordLibrary(int orderType,int startId,int count){
        ArrayList<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = null;
        try {
//            db = mHelper.getReadableDatabase();
            db = SQLiteDatabase.openDatabase(DB_PATH + DBConstans.CUR_WORD_LIBRARY_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = null;
            String orderStrs = getOrderStrByType(orderType);
            String countStr = "";
            if(startId>=0 && count >0){
                countStr = " limit " + startId + "," + count;
            }
            cursor = db.rawQuery("select * from word where remember<>2 order by " + orderStrs + countStr,null);
            
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Word vo = new Word();
                    vo._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    vo.cn = cursor.getString(cursor.getColumnIndex("cn"));
                    vo.en = cursor.getString(cursor.getColumnIndex("en"));
                    vo.interpretation = cursor.getString(cursor.getColumnIndex("interpretation"));
                    vo.ps = cursor.getString(cursor.getColumnIndex("ps"));
                    vo.random_id = cursor.getInt(cursor.getColumnIndex("random_id"));
                    vo.remember = cursor.getInt(cursor.getColumnIndex("remember"));
                    vo.reverse_id = cursor.getInt(cursor.getColumnIndex("reverse_id"));
                    vo.word = cursor.getString(cursor.getColumnIndex("word"));
                    wordList.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(db!=null){
                db.close();
                db=null;
            }
        }
        return wordList;
    }
    
    /**
     *  orderType 0 正序 1 随机   2倒序 
     * */
    public String getOrderStrByType(int type){
        String orderField="";
        switch(type){
        case 0:
            orderField="_id asc";
            break;
        case 1:
            orderField = "random_id asc";
            break;
        case 2:
            orderField = "reverse_id asc";
            break;
        }
        return orderField;
    }
    
    /**初始化 默认数据库*/
    public void initDefaultDB(){
        try {
            if(mHelper!=null){
                mHelper.createDataBase();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
