package com.yp.enstudy.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.yp.enstudy.utils.LogHelper;
import com.yp.enstudy.utils.ZipUtils;

/**
 * @author Joshua 用法： DBHelper dbHelper = new DBHelper(this);
 *         dbHelper.createDataBase(); SQLiteDatabase db =
 *         dbHelper.getWritableDatabase(); Cursor cursor = db.query()
 *         将把assets下的数据库文件直接复制到DB_PATH，但数据库文件大小限制在1M以下
 *         如果有超过1M的大文件，则需要先分割为N个小文件，然后使用copyBigDatabase()替换copyDatabase()
 */
public class DBHelper extends SQLiteOpenHelper {
    // 用户数据库文件的版本
    private static final int DB_VERSION = 1;
    // 数据库文件目标存放路径为系统默认位置，cn.arthur.examples 是你的包名
    public static String DB_PATH;
    /*
     * //如果你想把数据库文件存放在SD卡的话 private static String DB_PATH =
     * android.os.Environment.getExternalStorageDirectory().getAbsolutePath() +
     * "/arthurcn/drivertest/packfiles/";
     */
    private static String DB_NAME = "ciku_03";
    private static String ASSETS_NAME = "ciku_03";

    private SQLiteDatabase myDataBase = null;
    private final Context myContext;

    /**
     * 如果数据库文件较大，使用FileSplit分割为小于1M的小文件 分割为 hello.db.101 hello.db.102
     * hello.db.103
     */
    // 第一个文件名后缀
    private static final int ASSETS_SUFFIX_BEGIN = 101;
    // 最后一个文件名后缀
    private static final int ASSETS_SUFFIX_END = 103;
    
    public static DBHelper dbInstance;
    
    public static DBHelper getInstance(Context context){
        synchronized (DBHelper.class) {
            if(dbInstance==null){
                dbInstance = new DBHelper(context);
            }
        }
        return dbInstance;
    }

    public void setDBName(String name){
        if(!TextUtils.isEmpty(name) && !name.equals(DB_NAME)){
            synchronized (DBHelper.class) {
                dbInstance = new DBHelper(myContext, name);
            }
        }
    }

    /**
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数
     * 
     * @param context
     *            上下文对象
     * @param name
     *            数据库名称
     * @param factory
     *            一般都是null
     * @param version
     *            当前数据库的版本，值必须是整数并且是递增的状态
     */
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, null, version);
        this.myContext = context;
        DB_PATH = "/data/data/"+myContext.getPackageName()+"/databases/";
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
        DB_PATH = "/data/data/"+myContext.getPackageName()+"/databases/";
    }

    public DBHelper(Context context, String name) {
        this(context, name, DB_VERSION);
        DB_PATH = "/data/data/"+myContext.getPackageName()+"/databases/";
    }

    public DBHelper(Context context) {
        this(context, DB_PATH + DB_NAME);
        DB_PATH = "/data/data/"+myContext.getPackageName()+"/databases/";
    }

    public void createDataBase() throws IOException {
        try {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //SQLiteDatabase.openOrCreateDatabase(dbf, null);
            // 复制asseets中的db文件到DB_PATH下
            //copyDataBase();
            //解压默认数据库
            unZipDataBase();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    // 检查数据库是否有效
    public synchronized boolean checkDataBase(String dbName ) {
        SQLiteDatabase checkDB = null;
        String DB_FULL_PATH="/data/data/"+myContext.getPackageName()+"/databases/" + dbName;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (Throwable e) {
            // database doesn't exist yet.
//            e.printStackTrace();
        }
        return checkDB != null ? true : false;
    }
    
    public void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    
    public void unZipDBFile(String zipPath,String zipName){
        try {
            ZipUtils zUtils = new ZipUtils();
            File file = new File(zipPath + "/" + zipName);
            InputStream input = new FileInputStream(file);
            String outFileName = DB_PATH + zipName;
            OutputStream myOutput = new FileOutputStream(new File(outFileName));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            input.close();                
            
            File zipFile = new File(DB_PATH+zipName);
            zUtils.upZipFile(zipFile, DB_PATH);
            
            //解压完后 删除原文件
            zipFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void unZipDataBase(){
        try {
            ZipUtils zUtils = new ZipUtils();
            String [] paths = myContext.getAssets().list("library");
            for(String s:paths){
                if(checkDataBase(s.replaceAll(".zip", ""))){
                    LogHelper.d("db", s+" 已存在");
                    continue;
                }
                InputStream input = myContext.getAssets().open("library/"+s);
                String outFileName = DB_PATH + s;
                OutputStream myOutput = new FileOutputStream(new File(outFileName));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                input.close();                
                
                File zipFile = new File(DB_PATH+s);
                zUtils.upZipFile(zipFile, DB_PATH);
                
                //解压完后 删除原文件
                zipFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 复制assets下的大数据库文件时用这个
    public void copyBigDataBase() throws IOException {
        InputStream myInput;
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END + 1; i++) {
            myInput = myContext.getAssets().open(ASSETS_NAME + "." + i);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
        }
        myOutput.close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    /**
     * 该函数是在第一次创建的时候执行， 实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * 数据库表结构有变化时采用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
