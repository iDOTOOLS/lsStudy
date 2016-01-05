package com.yp.enstudy;

import java.io.File;

import android.content.Context;

import com.yp.enstudy.http.AjaxCallBack;
import com.yp.enstudy.http.FinalHttp;
import com.yp.enstudy.http.HttpHandler;
import com.yp.enstudy.utils.DownLoadUtils;
import com.yp.enstudy.utils.LogHelper;
import com.yp.enstudy.utils.StringUtil;
import com.yp.enstudy.utils.ZipUtils;

public class DownloadManager {
    public static String CIKUURL="http://oss.tv163.com/ciku/word/";
    public static String VOICEURL= "http://oss.tv163.com/ciku/sound/";
    private Context context;
    private DownLoadUtils mUtils;
    private ZipUtils mZipUtils;
    private String downName;
    
    public DownloadManager(Context context){
        this.context = context;
    }
    
    public interface DownLoadCallBack{
        public boolean success();
        public boolean error();
        public long downSize(long size);
    }
    
    /**
     * @param name ciku_01.zip
     * @return
     */
    public boolean downloadDB(String name,final DownLoadCallBack callback){
        this.downName = name;
        mUtils = new DownLoadUtils(callback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mUtils.download(CIKUURL + downName, context)){
                    mZipUtils = new ZipUtils();
                    File file = new File(Constans.PATH_DOWNLOAD + downName);
                    if(file.exists()){
                        try {
                            String dbPath = "/data/data/"+context.getPackageName()+"/databases/";
                            mZipUtils.upZipFile(file, dbPath);
                            LogHelper.d("unzip", "解压成功!");
                            if(callback!=null) callback.success();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }         
                        file.delete();
                    }
                }else{
                    if(callback!=null) callback.error();
                }
            }
        }).start();
        return false;
    }
    
    public FinalHttp mFh = new FinalHttp();
    public HttpHandler<File> mHttpHandler;
    private String tmpPath;
    private String path;
    private File mFile;
    private File mFileRename;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean downloadVoiceByAFinal(final String name,final DownLoadCallBack callback){
        path =  Constans.PATH_DOWNLOAD + "/" + StringUtil.getNameByUrl(name);
        mFile = new File(path);
        if(!mFile.exists()){
            tmpPath = path.substring(0, path.length() - 4) + ".tmp";
        }else{
            return true;
        }
        
        mHttpHandler = mFh.download(VOICEURL+name, tmpPath,true, new AjaxCallBack(){
            @Override
            public void onLoading(long count, long current) {
                callback.downSize(current);
            }
            
            @Override
            public void onSuccess(Object t) {
                /**下载完成后 重命名*/
                mFileRename = new File(path);
                mFile = new File(tmpPath);
                mFile.renameTo(mFileRename);
                
                unZipVoice(callback,name);
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                if(callback!=null) callback.error();
            }
        });
        return false;
    }
    
    public boolean downloadVoice(final String name,final DownLoadCallBack callback){
        mUtils = new DownLoadUtils(callback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mUtils.download(VOICEURL + name, context)){
                    mZipUtils = new ZipUtils();
                    File file = new File(Constans.PATH_DOWNLOAD + name);
                    if(file.exists()){
                        try {
                            mZipUtils.upZipFile(file, Constans.PATH_AUDIO);
                            LogHelper.d("unzip", "解压成功!");
                            if(callback!=null) callback.success();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }             
                        file.delete();
                    }
                }else{
                    if(callback!=null) callback.error();
                }
            }
        }).start();
        return false;
    }
    
    /**解压*/
    public void unZipVoice(final DownLoadCallBack callback,final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mZipUtils = new ZipUtils();
                File file = new File(Constans.PATH_DOWNLOAD + name);
                if(file.exists()){
                    try {
                        mZipUtils.upZipFile(file, Constans.PATH_AUDIO);
                        LogHelper.d("unzip", "解压成功!");
                        if(callback!=null) callback.success();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }             
                    file.delete();
                }
            }
        }).start();
    }
    
    public void stop(){
        if(mUtils!=null)
            mUtils.setDownLoadStop(true);
    }
    
}
