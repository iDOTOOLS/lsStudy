package com.yp.lockscreen.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;


public class LogHelper {
    public static final String  TAG = "YP_LockScreen";
    public static boolean flag = true;

    public static void d(String subTag, String msg) {
        if(flag)
            Log.d(TAG, getLogMsg(subTag, msg));
    }

    public static void i(String subTag, String msg) {
        if(flag)
            Log.i(TAG, getLogMsg(subTag, msg));
    }

    public static void w(String subTag, String msg) {
        if(flag)
            Log.w(TAG, getLogMsg(subTag, msg));
    }

    public static void w(String subTag, String msg, Throwable e) {
        if(flag)
            Log.w(TAG, getLogMsg(subTag, msg + " Exception: " + getExceptionMsg(e)));
    }

    public static void e(String subTag, String msg) {
        if(flag)
            Log.e(TAG, getLogMsg(subTag, msg));
    }

    public static void e(String subTag, String msg, Throwable e) {
        if(flag)
            Log.e(TAG, getLogMsg(subTag, msg + " Exception: " + getExceptionMsg(e)));
    }

    private static String getLogMsg(String subTag, String msg) {
        return "[" + subTag + "] " + msg;
    }

    private static String getExceptionMsg(Throwable e) {
        StringWriter sw = new StringWriter(1024);
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }
}
