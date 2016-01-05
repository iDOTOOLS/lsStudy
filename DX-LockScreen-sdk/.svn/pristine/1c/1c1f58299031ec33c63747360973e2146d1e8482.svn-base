
package com.dianxinos.lockscreen_sdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

/**
 * @author ninghai
 */
class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private static final String STACK_TRACE = "stackTrace";

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;

    public static final String ACTION_APPLICATION_DETAILS_SETTINGS = "android.settings.APPLICATION_DETAILS_SETTINGS";

    public static String sVersionCode = "";

    public CrashHandler() {
    }

    public void init(String versionCode, Context context) {
        if (DXLockScreenUtils.DBG)
            Log.e(TAG, "handleException,init,versionCode=" + versionCode);
        if (versionCode != null) {
            sVersionCode = versionCode;
        }
        mContext = context;
        try {
            UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

            if (defaultHandler != this) {
                mDefaultHandler = defaultHandler;
                Thread.setDefaultUncaughtExceptionHandler(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while setting the default uncaught exception handler", e);
        }
    }

    public void destroy() {
        if (DXLockScreenUtils.DBG)
            Log.e(TAG, "handleException,destroy");
        try {
            if (mDefaultHandler != null) {
                Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
                mDefaultHandler = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while setting the default uncaught exception handler", e);
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            handleException(throwable);

            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, throwable);
            }
        } catch (Throwable e) {
            // should never happen
            Log.e(TAG, "Unexpected error", e);
        }
    }

    private void handleException(Throwable throwable) {
        if (DXLockScreenUtils.DBG)
            Log.e(TAG, "handleException,throwable=" + throwable);
        if (throwable == null) {
            return;
        }
        try {
            JSONObject crashData = new JSONObject();

            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            throwable.printStackTrace(printWriter);

            // If the exception was thrown in a background thread inside
            // AsyncTask, then the actual exception can be found with getCause
            // only one level deep
            Throwable cause = throwable.getCause();
            if (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String log = "VersionName = " + sVersionCode + "\n Device Info = " + Build.PRODUCT
                    + "/" + Build.MODEL + "\n Exception info = \n" + result.toString();
            outputLogtoFile(log);
            crashData.put(STACK_TRACE, log);

            Log.e(TAG, "VersionName = " + sVersionCode + "\n Device Info = " + Build.PRODUCT + "/" 
                    + Build.MODEL + "\n Exception info = \n" + result.toString());
            // send the event
            StatManager.reportCrash(crashData);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving stack trace", e);
        }
    }

    public static void outputLogtoFile(String log) {
        String ROOT_SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/DX-Log/";
        String LOG_FILENAME = ROOT_SD_PATH + "Log.txt";
        if (DXLockScreenUtils.isSDCardMounted()) {
            PrintWriter out = null;
            try {
                File dir = new File(ROOT_SD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                out = new PrintWriter(new FileOutputStream(LOG_FILENAME, true), true);
                out.println();
                out.println(log);
            } catch (Exception e) {
                Log.w("ZQX", "There are something wrong when output log file.", e);
            } finally {
                if (out != null)
                    out.close();
            }
        }
    }
}
