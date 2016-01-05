package com.yp.lockscreen.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.yp.lockscreen.StaticService;

public class SwitchReceiver extends BroadcastReceiver {

    private static final String TAG = "SwitchReceiver";
    private static final String SWITCH_PREF_NAME = "SwitchReceiverPreferences";
    private static final String PREF_KEY_IS_OPEN = "isOpen";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (DXLockScreenUtils.ACTION_OPEN_CLOSE_LOCKSCREEN_V2_0.equals(action)) {
            String packageName = intent.getStringExtra(DXLockScreenUtils.EXTRA_PACKAGENAME);// 获取包名
            boolean isOpen = intent.getBooleanExtra(DXLockScreenUtils.EXTRA_IS_OPEN_THIS_LOCKSCREEN, false);// 判断这个lock是否处于锁屏状态
            Log.i(TAG, "onReceive ACTION_OPEN_CLOSE_LOCKSCREEN_V2_0 packageName=" + packageName + ",isOpen=" + isOpen);
            if (isOpen) {
                if (context.getPackageName().equals(packageName)) {
                    Intent i = new Intent();
                    i.setClass(context, StaticService.class);
                    context.startService(i);// 启动service
                    saveSwitchPref(context, true);
                } else {
                    Intent i = new Intent();
                    i.setClass(context, StaticService.class);
                    context.stopService(i);
                    saveSwitchPref(context, false);
                }
            } else {
                if (context.getPackageName().equals(packageName)) {
                    Intent i = new Intent();
                    i.setClass(context, StaticService.class);
                    context.stopService(i);
                    saveSwitchPref(context, false);

                }
            }
        }
    }

    /**
     * 保存一个值到SwitchReceiverPreferencescom.dianxinos.lockscreen_threenpoint
     * 
     * @param context
     * @param isOpen
     */
    private void saveSwitchPref(Context context, boolean isOpen) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SWITCH_PREF_NAME + context.getPackageName(), DXLockScreenUtils.MODE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_IS_OPEN, isOpen);
        editor.commit();
    }
}
