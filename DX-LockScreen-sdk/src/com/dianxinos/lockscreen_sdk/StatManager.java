package com.dianxinos.lockscreen_sdk;

import com.dianxinos.dxservice.core.DXCore;
import com.dianxinos.dxservice.stat.Constant.DataPolicy;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class StatManager {
    public static final int PKG_ADDED = 1;
    public static final int PKG_REMOVED = 2;
    public static final int PKG_CHANGED = 3;
    public final static int TYPE_DESKTOP = 1;
    public final static int TYPE_DRAWER = 2;
    public final static int TYPE_DRAWER_LIST = 3;
    private static final String TAG = "StatManager";
    private static DXCore coreservice = null;

    public static void init(Context context) {
        if (coreservice != null) {
            destroy();
        }
        coreservice = DXCore.getInstance(context);
    }

    public static void reportStart() {
        if (coreservice == null)
            return;
        if(DXLockScreenUtils.DBG){
            Log.i(TAG, "reportStart");
        }
        coreservice.reportEvent("start", 1);
    }

    public static void destroy() {
        if (coreservice != null) {
            coreservice.destroy();
            coreservice = null;
        }
    }

    public static void reportAllApps(ArrayList<String> apps, boolean realTime) {
        JSONArray jsonArray = new JSONArray(apps);
        if (coreservice == null)
            return;
        boolean resulte;
        if (realTime) {
            resulte = coreservice.reportEvent("A", 0, 0, jsonArray.toString());   // 0: DataPolicy.NONE,  0: ReportPolicy.TYPE_REALTIME
        } else {
            resulte = coreservice.reportEvent("A", 0, 1, jsonArray.toString());   // 0: DataPolicy.NONE,  1: ReportPolicy.TYPE_OFFLINE
        }
    }

    public static void reportRunningApps(ArrayList<String> apps, boolean realTime) {
        JSONArray jsonArray = new JSONArray(apps);
        if (coreservice == null)
            return;
        boolean resulte;
        if (realTime) {
            resulte = coreservice.reportEvent("G", 0, 0, jsonArray.toString());   // 0: DataPolicy.NONE,  0: ReportPolicy.TYPE_REALTIME
        } else {
            resulte = coreservice.reportEvent("G", 0, 1, jsonArray.toString());   // 0: DataPolicy.NONE,  1: ReportPolicy.TYPE_OFFLINE
        }
    }

    public static void reportCrash(JSONObject crashData) {
        if (coreservice == null)
            return;

//        coreservice.reportEvent("crash", 3, crashData);
    }

    public static void reportUpdateDialog() {
//        if (DXLockScreenUtils.DBG) {
//            Log.i(TAG, "reportUpdateDialog,coreservice=" + coreservice);
//        }
//        if (coreservice == null)
//            return;
//        coreservice.reportEvent("UI", "SD", 1);
    }

    public static void reportUpdateLater() {
//        if (DXLockScreenUtils.DBG) {
//            Log.i(TAG, "reportUpdateLater,coreservice=" + coreservice);
//        }
//        if (coreservice == null)
//            return;
//        coreservice.reportEvent("UI", "NU", 1);
    }

    public static void reportUpdateBackPressed() {
//        if (DXLockScreenUtils.DBG) {
//            Log.i(TAG, "reportUpdateBackPressed,coreservice=" + coreservice);
//        }
//        if (coreservice == null)
//            return;
//        coreservice.reportEvent("UI", "BK", 1);
    }

    public static void reportUpdateNotification() {
//        if (DXLockScreenUtils.DBG) {
//            Log.i(TAG, "reportUpdateNotification,coreservice=" + coreservice);
//        }
//        if (coreservice == null)
//            return;
//        coreservice.reportEvent("UI", "SN", 1);
    }

    public static void reportUpdateNotificationClicked() {
//        if (DXLockScreenUtils.DBG) {
//            Log.i(TAG, "reportUpdateNotificationClicked,coreservice=" + coreservice);
//        }
//        if (coreservice == null)
//            return;
//        coreservice.reportEvent("UI", "CN", 1);
    }

    public static void reportUnlockType(int unLockType) {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "reportUnlockType,unLockType=" + unLockType);
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("UT", Integer.toString(unLockType), 1);
    }

    public static void reportOpenDrawer() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "reportOpenDrawer ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("OD", 1);
    }

    public static void clickGps() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click gps button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CG", 1);
    }

    public static void clickAirplaneMode() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click air button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CAM", 1);
    }

    public static void clickWifi() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click wifi button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CW", 1);
    }

    public static void clickMuteButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click mute button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CM", 1);
    }

    public static void clickDataButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click data button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CD", 1);
    }

    public static void clickPhoneButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click phone button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CP", 1);
    }

    public static void clickMmsButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click mms button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("MM", 1);
    }

    public static void clickBrowserButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click browser button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CB", 1);
    }

    public static void clickCameraButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click camera button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CC", 1);
    }

    public static void clickContactButton() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report Click camera button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("CCB", 1);
    }

    public static void clickButton(String info) {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "report clickButton button ");
        }
        if (coreservice == null)
            return;
        coreservice.reportEvent("InsClick", DataPolicy.NONE, info);
    }
}
