package com.yp.lockscreen.work;

import com.yp.enstudy.DownloadManager.DownLoadCallBack;
import com.yp.lockscreen.port.Global;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

public class LockDownLoad {
    public static final long DOWNLOAD_SUCCESS = -1;

    public static final long DOWNLOAD_ERROR = -2;
    
    public static final int DOWNLOAD_VOICE_FLAG = 1;

    public static final int DOWNLOAD_DB_FLAG = 2;
    
    public static final int DOWNLOAD_IMG_FLAG = 3;
    
    public void download(int type,String name,Handler callbackHandler){
        this.mCallbackHandler = callbackHandler;
        switch(type){
        case DOWNLOAD_VOICE_FLAG:
            if(Build.VERSION.SDK_INT>=11){
                Global.gDownLoad.downloadVoiceByAFinal(name, new DownLoadVoiceCallBack());
            }else{
                Global.gDownLoad.downloadVoice(name, new DownLoadVoiceCallBack());                
            }
            break;
        case DOWNLOAD_DB_FLAG:
            Global.gDownLoad.downloadDB(name, new DownLoadVoiceCallBack());
            break;
        }
    }
    
    private Handler mCallbackHandler;
    private class DownLoadVoiceCallBack implements DownLoadCallBack {
        @Override
        public boolean success() {
            Message msg = mCallbackHandler.obtainMessage();
            msg.obj = DOWNLOAD_SUCCESS;
            mCallbackHandler.sendMessage(msg);
            return false;
        }

        @Override
        public boolean error() {
            Message msg = mCallbackHandler.obtainMessage();
            msg.obj = DOWNLOAD_ERROR;
            mCallbackHandler.sendMessage(msg);
            return false;
        }

        @Override
        public long downSize(long size) {
            Message msg = mCallbackHandler.obtainMessage();
            msg.obj = size;
            mCallbackHandler.sendMessage(msg);
            return 0;
        }

    }
}
