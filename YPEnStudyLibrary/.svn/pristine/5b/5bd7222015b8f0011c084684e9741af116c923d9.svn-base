package com.yp.enstudy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.yp.enstudy.Constans;
import com.yp.enstudy.DownloadManager.DownLoadCallBack;

import android.content.Context;
import android.text.TextUtils;
public class DownLoadUtils {
    private DownLoadCallBack mCallback;
    private boolean isDownLoadStop = false;
    public DownLoadUtils(DownLoadCallBack callback) {
        mCallback = callback;
        isDownLoadStop = false;
    }
    
    public void setDownLoadStop(boolean isDownLoadStop) {
        this.isDownLoadStop = isDownLoadStop;
    }

    private File mFile;
    private String tmpPath;
    private File mFileRename;
    private int downLoadFileSize;
    private long refeshProgressTime;
    public boolean download(String url,Context context) {
        boolean downError = false;
        String path = "";
        int fileSize = 0;
        try {
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept-Encoding", "");
            conn.connect();
            InputStream is = conn.getInputStream();
            // 如果不是带有APK下载的原始地址 则请求原始地址 此原始地址 只能在conn.connect()
            // conn.getInputStream()之后 才能获得到
//            if (url.lastIndexOf(".apk") < 0) {
            path = Constans.PATH_DOWNLOAD + "/" + StringUtil.getNameByUrl(conn.getURL().toString());
//            }
            
            
            fileSize = conn.getContentLength();// 根据响应获取文件大小

            if (DeviceUtil.getSdCardHaveSize() == -1) {
                // errorMsg = "sd卡不存在";
                return false;
            } else if (DeviceUtil.getSdCardHaveSize() < (fileSize / 1024)) { // 存储空间小于下载文件 不去下载
                return false;
            }

            if (fileSize <= 0)
                throw new RuntimeException("无法获知文件大小 ");
            if (is == null)
                throw new RuntimeException("stream is null");

            try {
                mFile = new File(path);
                if (!mFile.exists()) {      //如果不存在 先命名为临时文件
                    tmpPath = path.substring(0, path.length() - 4) + ".tmp";
                    
                } else {
                    return true;
                }
                LogHelper.d("wifi_download", mFile.getName()+" 下载开始");
                FileOutputStream fos = new FileOutputStream(tmpPath);
                // 把数据存入路径+文件名
                byte buf[] = new byte[1024*100];    //100K
                downLoadFileSize = 0;
                do {
                    try {
                        int numread = is.read(buf);
                        if (numread == -1) {
                            break;
                        }
                        fos.write(buf, 0, numread);
                        downLoadFileSize += numread;
                        
                        if(isDownLoadStop)
                            break;
                        
                        if((System.currentTimeMillis()-refeshProgressTime)>2000){
                            mCallback.downSize(downLoadFileSize);
                            refeshProgressTime = System.currentTimeMillis();
                        }
                    } catch (Exception e) {
                        downError = true;
                        break;
                    }
                } while (true && !isDownLoadStop);
                
                if(isDownLoadStop){
                    return true;
                }
                
                if(!downError){
                    LogHelper.d("download", mFile.getName()+" 下载完成");
                    /**下载完成后 重命名*/
                    mFileRename = new File(path);
                    mFile = new File(tmpPath);
                    mFile.renameTo(mFileRename);
                    return true;
                }else{
                    return false;                    
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                LogHelper.d("download", mFile.getName()+"下载失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**获得真实下载地址   非线程*/
    public String getDonwloadRealUrl(String url){
        try {
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Accept-Encoding", "");
            conn.connect();
            InputStream is = conn.getInputStream();
            // 如果不是带有APK下载的原始地址 则请求原始地址 此原始地址 只能在conn.connect()
            // conn.getInputStream()之后 才能获得到
            return conn.getURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}