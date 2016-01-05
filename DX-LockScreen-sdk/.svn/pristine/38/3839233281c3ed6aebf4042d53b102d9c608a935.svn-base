package com.dianxinos.backend;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

import android.util.Log;


public class DXBackendConfig {
    
    private static final String TAG = "DXBackendConfig";

    public static void init(){
        try {
//            DXStatsService.setEnvironment("prod");
//            AppUpdate.setEnvironment("prod");
            DXLockScreenUtils.setEnvironment("prod");
//            DXStatsService.setEnvironment("test");
//            AppUpdate.setEnvironment("test");
//            DXLockScreenUtils.setEnvironment("test");
        } catch (Exception e) {
            Log.w(TAG,e);
        }
    }

}
