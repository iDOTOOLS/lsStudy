package com.yp.lockscreen.utils;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import com.yp.enstudy.utils.LogHelper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class ShareUtils {
    private Context mContext;
    public Activity mActivity;
    private int mExtarFlag = 0x00;

    public ShareUtils(Context context) {
        this.mContext = context;
    }

    /**调用系统分享到Sina*/
    public void shareTextToSinaBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.sina.weibo")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    /**调用系统分享到QQ空间*/
    public void shareTextToQZoneBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.qzone")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    /**调用系统分享到 微信朋友圈*/
    public void shareTextToFriendGroupBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.tencent.mm")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    /**调用系统分享到FaceBook*/
    public void shareUrlToFaceBookBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.facebook.katana")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    /**调用系统分享到Twitter*/
    public void shareUrlToTwitterBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.twitter.android")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    public void shareUrl2MailBySystem(String title){
        int i=0;
        for(ResolveInfo appInfo : getShareTargets()){
            if(appInfo.activityInfo.packageName.equals("com.android.email")){
                CreateShareText(title,appInfo);
                i++;
                break;
            }
        }
        if(i==0)
            Toast.makeText(mContext, "没有安装", Toast.LENGTH_LONG).show();
      }
    
    

    private String encode(String url) {
        try {
            String str1 = url.substring(0, url.indexOf("?param=") + 7);
            String str2 = url.substring(url.indexOf("?param=") + 7,
                    url.length());
            return str1 + URLEncoder.encode(str2, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
    
    /** 获得支持ACTION_SEND的应用列表 */
    private List<ResolveInfo> getShareTargets() {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = mContext.getPackageManager();
        return pm.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }
    
    public void CreateShareBmp(String msg,ResolveInfo appInfo,File file){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName,appInfo.activityInfo.name));
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));    
        mContext.startActivity(shareIntent);
    }
    
    public void CreateShareText(String msg,ResolveInfo appInfo){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName,appInfo.activityInfo.name));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        mContext.startActivity(shareIntent);
    }
    
    /**
     * 默认分享列表
     * **/
    public void CreateShare(String title,String content ,Activity activity) {  
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");   
        intent.putExtra(Intent.EXTRA_SUBJECT, title);   
        intent.putExtra(Intent.EXTRA_TEXT, content);    
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
        activity.startActivity(Intent.createChooser(intent, title));
    }
}
