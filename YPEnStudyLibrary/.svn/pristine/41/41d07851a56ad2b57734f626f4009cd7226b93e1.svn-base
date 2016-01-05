package com.yp.enstudy;

import com.yp.enstudy.DownloadManager.DownLoadCallBack;
import com.yp.enstudy.utils.LogHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        WordData data = new WordData(this);
        data.initBaseDir();
//      data.getStudyDan("size:"+list.size());
//      System.out.println(checkDataBase("ciku_03"));
//      System.out.println(checkDataBase("ciku_06"));
        DownloadManager downloadManager = new DownloadManager(this);
        
//        downloadManager.downloadDB("ciku_21.zip",mDownloadCallBack);
//        downloadManager.downloadVoice("ciku_08.zip", mDownloadCallBack);
//        System.out.println("checkDB:"+data.checkVoice("ciku_01"));;
    }
    
    DownLoadCallBack mDownloadCallBack = new DownLoadCallBack() {
        @Override
        public boolean success() {
            LogHelper.d("download", "下载成功");
            return false;
        }
        @Override
        public boolean error() {
            LogHelper.d("download", "下载失败");
            return false;
        }
        @Override
        public long downSize(long size) {
            System.out.println("down size:" + size+"");
            return 0;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
