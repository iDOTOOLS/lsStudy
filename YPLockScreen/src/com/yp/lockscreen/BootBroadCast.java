package com.yp.lockscreen;

import java.net.URI;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.yp.enstudy.ConfigManager;
import com.yp.lockscreen.utils.LogHelper;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BootBroadCast extends BroadcastReceiver {
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            LogHelper.d("", "android.intent.action.BOOT_COMPLETED");
            DXLockScreenUtils.openOrCloseLockScreen(mContext, mContext.getPackageName(), ConfigManager.isUseLockScreen(mContext));
        } else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (wifiInfo.isConnected()) {
                LogHelper.d("", "android.net.conn.CONNECTIVITY_CHANGE");
            }
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {

        } else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = URI.create(intent.getDataString()).getSchemeSpecificPart();
            // 如果被卸载的应用 接收到自身被卸载的广播 则不执行更新配置文件操作

        }else if(intent.getAction().equals("android.intent.action.BATTERY_CHANGED")){
            LogHelper.d("BootBroadCast", "android.intent.action.BATTERY_CHANGED");
            DXLockScreenUtils.openOrCloseLockScreen(mContext, mContext.getPackageName(), ConfigManager.isUseLockScreen(mContext));
        }
    }
}
