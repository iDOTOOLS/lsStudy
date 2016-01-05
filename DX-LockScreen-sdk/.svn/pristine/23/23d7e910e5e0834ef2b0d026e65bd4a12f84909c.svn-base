package com.dianxinos.lockscreen_sdk;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dianxinos.DXStatService.utils.BaseInfoHelper;

public class DXLockScreenUtils {
    private static final String TAG = "DXLockScreenUtils";
    public static boolean DBG = false;
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    public static final int MSG_SCREEN_OFF = 0x123;
    public static final int MSG_SCREEN_ON = MSG_SCREEN_OFF + 1;
    public static final int MSG_CALL_RINGING = MSG_SCREEN_OFF + 2;
    public static final int MSG_ALARM_ON = MSG_SCREEN_OFF + 3;
    public static final int MSG_LOCKPASSWORD_CHANGED = MSG_SCREEN_OFF + 4;
    public static final int MSG_CALLORMESSAGE_UPDATED = MSG_SCREEN_OFF + 5;
    public static final int MSG_UNLOCK_NORMAL = MSG_SCREEN_OFF + 6;
    public static final int MSG_TIMEDATE_UPDATED = MSG_SCREEN_OFF + 7;
    public static final int MSG_SPN_UPDATED = MSG_SCREEN_OFF + 8;
    public static final int MSG_CALL_ANSWERORREFUSE = MSG_SCREEN_OFF + 9;
    public static final int MSG_SIM_STATE_CHANGNED = MSG_SCREEN_OFF + 10;
    public static final int MSG_BATTERY_CHANGNED = MSG_SCREEN_OFF + 11;
    public static final int MSG_SCREEN_LOCK = MSG_SCREEN_OFF + 12;
    public static final int MSG_CLEAR_SYSTEM_LOCK = MSG_SCREEN_OFF + 13;
    public static final int MSG_STATUSBAR_NOTIFICATION_CLICKED = MSG_SCREEN_OFF + 14;

    public static final long DELAYED_DURATION = 200;

    public static final int UNLOCK_POSITION_HOME = 0x1234;
    public static final int UNLOCK_POSITION_NEW_SMS = UNLOCK_POSITION_HOME + 1;
    public static final int UNLOCK_POSITION_VIEW_SMS = UNLOCK_POSITION_HOME + 2;
    public static final int UNLOCK_POSITION_NEW_CALL = UNLOCK_POSITION_HOME + 3;
    public static final int UNLOCK_POSITION_VIEW_CALL = UNLOCK_POSITION_HOME + 4;
    public static final int UNLOCK_POSITION_CAMERA = UNLOCK_POSITION_HOME + 5;
    public static final int UNLOCK_POSITION_DXHOT = UNLOCK_POSITION_HOME + 6;

    /**
     * 入口是否调整为点心精品（代替之前的电话入口。同时，相机代替之前的短信入口）
     */
    public static boolean SWITCH_GO_TO_DXHOT = false;

    public static final int HOVER_STATE_NONE = 0;
    public static final int HOVER_STATE_SMS = HOVER_STATE_NONE + 1;
    public static final int HOVER_STATE_HOME = HOVER_STATE_NONE + 2;
    public static final int HOVER_STATE_CALL = HOVER_STATE_NONE + 3;

    public static final int TYPE_MISSING_CALL = 1;
    public static final int TYPE_UNREAD_MESSAGE = 2;

    public static final int ICS_SDK_CURRENT_VERSION = Integer
            .valueOf(android.os.Build.VERSION.SDK);
    public static final int ICS_SDK_SMALLEST_VERSION = 14;

    public static final String[] ACTION_ALARM_ON_ALL = new String[] {
            "com.android.deskclock.ALARM_ALERT",
            "com.android.alarmclock.ALARM_ALERT",
            "com.samsung.sec.android.clockpackage.alarm.ALARM_ALERT",
            "com.motorola.blur.alarmclock.ALARM_ALERT",
            "zte.com.cn.alarmclock.ALARM_ALERT",
            "com.htc.android.worldclock.ALARM_ALERT",
            "com.sonyericsson.alarm.ALARM_ALERT",
            "com.dianxinos.clock.ALARM_ALERT",
            "com.dianxinos.clock.SLEEP_ALERT"
    };

    public static final int NODXHOME_NOTIFICATIONID = 826;

    // for SettingsObserver
    public static final long PASSWORD_PATTERN = DevicePolicyManager.PASSWORD_QUALITY_SOMETHING;
    public static final long PASSWORD_PIN = DevicePolicyManager.PASSWORD_QUALITY_NUMERIC;
    public static final long PASSWORD_NORMAL = DevicePolicyManager.PASSWORD_QUALITY_ALPHABETIC;
    public static final int PASSWORD_IS_PATTERN = 1;
    public static final String PASSWORDTYPE_COLUMN = "lockscreen.password_type";
    public static final String PATTERN_AUTOLOCK_COLUMEN = "lock_pattern_autolock";
    public static final String PASSWORDTYPE_PATTERNSTR = "pattern";
    public static final String PASSWORDTYPE_PINSTR = "pin";
    public static final String PASSWORDTYPE_NORMALSTR = "normal";
    public static final String PASSWORDTYPE_NONESTR = "none";

    // for DXOperatingCMLockScreen.
    protected static final int CMLOCKSCREEN_DISABLED = 1;
    protected static final int CMLOCKSCREEN_ENABLED = 0;
    protected static final int CMLOCKSCREENONSECURITY_DISABLED = 1;
    protected static final int CMLOCKSCREENONSECURITY_ENABLED = 0;
    protected static final int CMLOCKSCREEN_UNKNOWN = -1;
    protected static final String LOCKSCREEN_DISABLE_ON_SECURITY = "lockscreen_disable_on_security";
    protected static final String LOCKSCREEN_DISABLED = "lockscreen_disabled";
    public final static String DISABLE_ICSLOCKSCREEN_KEY = "lockscreen.disabled";
    public final static String SLIDE_ENABLE = "slide_enable";
    public final static long ICS_LOCKSCREEN_DISABLED_DEFVAL = 0;
    public final static int ICS_DISABLED_DIANXIN_LOCKSCREEN = 1;
    public final static int ICS_ENABLED_DIANXIN_LOCKSCREEN = 0;

    // for ThemePreferenceActivity
    public final static String DXHOME_PACKAGE_NAME = "com.dianxinos.dxhome";
    public final static String PACKAGE_NAME_ELEGANT = "com.dianxinos.dxlauncher";
    public final static String DXLAUNCHER_PACKAGE_NAME = "com.android.launcher";
    public static final String LOCK_SCREEN_SERVICE_NAME = ".LockScreenService";
    public static final boolean TEST_URL = DBG;
    public final static String PACKAGE_NAME_ELEGANT_NEW = "com.dianxinos.launcher";
    public final static String PACKAGE_NAME_PRO_NEW = "com.dianxinos.home";
    /**
     * when dxhome was installed,the theme should uninstall itself?
     */
    public final static boolean SWITCH_UNINSTALL_SELF = false;
    public static final String DXHOME_LAUNCHER_NAME = "com.dianxinos.launcher2.Launcher";
    public static final String EXTRA_IS_OPEN_THIS_LOCKSCREEN = "is_open_this_lockscreen";
    public static final String EXTRA_PACKAGENAME = "packageName";
    public static final String PRE_WORLD_SHARE = "launcher_w";
    public static final int MODE = Context.MODE_WORLD_READABLE
            + Context.MODE_WORLD_WRITEABLE;
    public static final String KEY_CHOOSE_LOCK_SCREEN = "pref_key_choose_lockscreen";
    public static final String LOCK_SCREEN_NO_PKG_NAME = "no_lock_screen";
    public static final int FLAG_START_FROM_DXHOME = 1;
    private static final String sLcFile = "lc.txt";
    /**
     * pro版早已监听此action.用于打开或关闭某一锁习
     */
    public static final String ACTION_OPEN_CLOSE_LOCKSCREEN = "dianxinos.intent.action.ACTION_OPEN_CLOSE_LOCKSCREEN";
    /**
     * 锁屏2.0才支持的action.用于启动或关闭自己。可用此action来判断是否为2.0锁屏
     */
    public static final String ACTION_OPEN_CLOSE_LOCKSCREEN_V2_0 = "dianxinos.intent.action.ACTION_OPEN_CLOSE_LOCKSCREEN_For_lockscreen";
    private static final String ACTION_DX_LAUNCHER = "com.dianxinos.dxhome.LAUNCHER";
    private static final String ACTION_DXHOME_START = "com.dianxinos.dxhome.main";
    public static final int EXTRA_START_DXHOME_FROM_LOCKSCREEN = 1;
    public static final String EXTRA_START_DXHOME_FROM = "START_DXHOME_FROM";
    public static final String ACTION_STATUSBARNOTIFICATIONCLICKED = "android.dianxinos.intent.action.statusbarnotificationclicked";
    public static final String ACTION_DXLOCKSCREENLOCKED = "android.dianxinos.intent.action.lockscreen_locked";
    public static final String ACTION_DXLOCKSCREENUNLOCKED = "android.dianxinos.intent.action.lockscreen_unlocked";

    /**
     * 锁屏内部，用于预览这个锁屏的界面
     */
    public static final String ACTION_LOCKSCREEN_PREVIEW_IN_LOCKSCREEN = "dianxinos.intent.action.LockScreenPreview";
    /**
     * 支持锁屏本地管理：所有锁屏预览
     */
    public static final String ACTION_LOCKSCREEN_PREVIEW_ACTIVITY = "dianxinos.intent.action.LockScreenPreviewsActivity";
    /**
     * 支持锁屏本地管理：详细信息
     */
    public static final String ACTION_LOCKSCREEN_DETAILS_ACTIVITY = "dianxinos.intent.action.LockScreenDetails";
    private static final String ACTION_DXHOME_LAUNCHER = "com.dianxinos.intent.action.HOME";

    public static final String KEY_LOCKSCREEN_OPEN = "LockOpen";
    public static final String KEY_LOCKSCREEN_USE = "LockUse";
    public static final String KEY_LOCKSCREEN_INSTALL = "LockInsSu";

    public static final String KEY_THEME_OPEN = "ThemeOpen";
    public static final String KEY_THEME_USE = "ThemeUse";
    public static final String KEY_THEME_INSTALL = "ThemeInsSu";

    private static final String PREF_LAUNCHER_DXHOME_ON_LOCK = "pref_launcher_dxhome_on_lock";
    public static String getLC(Context context) {
        String lc = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(sLcFile));
            BufferedReader bufReader = new BufferedReader(inputReader);
            lc = bufReader.readLine();
            lc = lc.trim();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return lc;
    }

    /**
     * check is dx public home installed
     * 
     * @param context
     * @return if dx public home is install,return true
     */
    public static boolean isDXPublicHomeInstalled(Context context) {
        try {
            Intent intent = new Intent();
            final PackageManager packageManager = context.getPackageManager();
            intent.setClassName(DXLockScreenUtils.DXHOME_PACKAGE_NAME,
                    DXLockScreenUtils.DXHOME_LAUNCHER_NAME);
            List<ResolveInfo> apps = packageManager.queryIntentActivities(
                    intent, 0);
            return apps != null && apps.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * check is dxhome and dx project launcher installed
     * 
     * @param context
     * @return if is public dxhome or project dxlaucnher,return true,else return
     *         false
     */
    public static boolean isDXLauncherInstalled(Context context) {
        try {
            if (isDXPublicHomeInstalled(context))
                return true;
            Intent intent = new Intent();
            intent.setAction(DXLockScreenUtils.ACTION_DX_LAUNCHER);
            final PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> apps = packageManager.queryIntentActivities(
                    intent, 0);
            return apps != null && apps.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDxhomeLockScreenPkgName(Context context) {
        try {
            Context dxhomeContext = context.createPackageContext(
                    DXLockScreenUtils.DXHOME_PACKAGE_NAME,
                    Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPre = dxhomeContext.getSharedPreferences(
                    DXLockScreenUtils.PRE_WORLD_SHARE, DXLockScreenUtils.MODE);
            if (sharedPre != null) {
                String pkgName = sharedPre.getString(
                        DXLockScreenUtils.KEY_CHOOSE_LOCK_SCREEN,
                        DXLockScreenUtils.LOCK_SCREEN_NO_PKG_NAME);
                if (DXLockScreenUtils.DBG)
                    Log.i(TAG, "getDxhomeLockScreenPkgName = " + pkgName);
                return pkgName;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return DXLockScreenUtils.LOCK_SCREEN_NO_PKG_NAME;
    }

    /**
     * is DxHome(include public dxhome and project dxlauncher) Support
     * LockScreen
     * 
     * @param context
     * @return true if dxhome supported lockscreen
     */
    public static boolean isDxHomeSupportLockScreen(Context context) {
        Intent intent = new Intent(
                DXLockScreenUtils.ACTION_OPEN_CLOSE_LOCKSCREEN);
        intent.setPackage(DXLockScreenUtils.DXHOME_PACKAGE_NAME);
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryBroadcastReceivers(intent,
                0);
        if (list != null && list.size() > 0)
            return true;
        intent.setPackage(DXLockScreenUtils.DXLAUNCHER_PACKAGE_NAME);
        list = packageManager.queryBroadcastReceivers(intent, 0);
        return list != null && list.size() > 0;
    }

    public static boolean isServiceRunning(Context context) {
        return isServiceRunning(context, context.getPackageName());
    }

    public static boolean isServiceRunning(Context context, String pkgName) {
        try {
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningServiceInfo> runningServices = manager
                    .getRunningServices(30);
            final int count = runningServices.size();
            for (int i = 0; i < count; i++) {
                if (runningServices.get(i).service.getPackageName().equals(
                        pkgName)) {
                    return true;
                }
            }
            // if the top 30 has not the service,get all running service again
            runningServices = manager.getRunningServices(Integer.MAX_VALUE);
            final int size = runningServices.size();
            for (int i = 0; i < size; i++) {
                if (runningServices.get(i).service.getPackageName().equals(
                        pkgName)) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            return false;
        }
        return false;
    }

    public static String getVersionNameCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return info.versionName + "_" + info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return info.versionCode;
        } catch (Exception e) {
        }
        return -1;
    }

    public static boolean isSDCardMounted() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static int getScreenHeight(Context context) {
        if (sScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenHeight = dm.heightPixels;
        }
        return sScreenHeight;
    }

    public static int getScreenWidth(Context context) {
        if (sScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenWidth = dm.widthPixels;
        }
        return sScreenWidth;
    }

    private static Boolean isDXROM = null;

    /**
     * check is Running On DXRom
     * 
     * @return true if running on DXROM.else return false
     */
    public static boolean checkIsRunningOnRom() {
        if (isDXROM == null) {
            String CONFIG_FILE = "/system/etc/dianxin_config.xml";
            try {
                File configFile = new File(CONFIG_FILE);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(configFile);
                Element rootNode = doc.getDocumentElement();
                isDXROM = getConfigVersionValue(rootNode, "version", false);
            } catch (Exception e) {
                return false;
            }
        }
        return isDXROM;
    }

    private static boolean getConfigVersionValue(Element rootNode,
            String tagName, boolean defValue) {
        String VALUE_ATTR_NAME = "value";
        NodeList nodeList = rootNode.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element hwNode = (Element) nodeList.item(0);
            String value = hwNode.getAttribute(VALUE_ATTR_NAME);
            if (value != null) {
                if (value.startsWith("DXROM") || value.startsWith("DXSimple")) {
                    return true;
                }
            }
        }
        return defValue;
    }

    public static boolean isICSSDKVersion() {
        return (DXLockScreenUtils.ICS_SDK_CURRENT_VERSION >= DXLockScreenUtils.ICS_SDK_SMALLEST_VERSION) ? true
                : false;
    }

    protected static boolean isScreenOn(Context context){
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
    
    public static Bitmap createBackgroundByCustom(String path) {
        Bitmap background;
        FileInputStream input = null;
        try {
            input = new FileInputStream(path);
        } catch (Exception e) {
            return null;
        }

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        opt.inJustDecodeBounds = false;
        opt.inDensity = DisplayMetrics.DENSITY_LOW;
        try {
            background = BitmapFactory.decodeStream(input, null, opt);
        } finally {
        }

        try {
            input.close();
        } catch (IOException e) {
        }
        return background;
    }

    static private float sDensity;

    static public float getScreenDensity(Context context) {
        if (sDensity == 0f) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sDensity = dm.density;
        }
        return sDensity;
    }

    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    public static void setEnvironment(String env) {
        if ("prod".equals(env)) {
            DBG = false;
        } else if ("test".equals(env)) {
            DBG = true;
        } else {
            DBG = false;
        }
    }

    public static boolean isPkgActivityInstalledByAction(Context context, String pkgName,
            String action) {
        try {
            Intent intent = new Intent(action);
            if(pkgName!=null){
                intent.setPackage(pkgName);
            }
            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(intent, 0);
            return apps != null && apps.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据action，查询所有含有此action的包名，如无，则返回一个长度为0的数组
     * 
     * @param context
     * @param action
     * @return
     */
    public static ArrayList<String> queryActivitysByAction(Context context,
            String action) {
        ArrayList<String> pkgs = new ArrayList<String>();
        try {
            Intent intent = new Intent(action);
            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(intent, 0);
            if (apps != null) {
                for (int i = 0; i < apps.size(); i++) {
                    pkgs.add(apps.get(i).activityInfo.packageName);
                }
            }
        } catch (Exception e) {
        }
        return pkgs;
    }

    /**
     * 获取所有安装了的点心桌面
     * @param context
     * @return
     */
    public static ArrayList<String> queryAllDXhomes(Context context) {
        ArrayList<String> pkgs = queryActivitysByAction(context, ACTION_DXHOME_LAUNCHER);
        if (!pkgs.contains(DXHOME_PACKAGE_NAME) && isPkgInstalled(context, DXHOME_PACKAGE_NAME)) {
            pkgs.add(DXHOME_PACKAGE_NAME);
        }
        if (!pkgs.contains(PACKAGE_NAME_ELEGANT) && isPkgInstalled(context, PACKAGE_NAME_ELEGANT)) {
            pkgs.add(PACKAGE_NAME_ELEGANT);
        }
        return pkgs;
    }

    /**
     * start dxhome
     * @param context
     */
    public static void startDXHomeSafely(Context context) {
        try {
            Intent i = new Intent();
            i.setClassName(DXHOME_PACKAGE_NAME, DXHOME_LAUNCHER_NAME);
            i.putExtra(EXTRA_START_DXHOME_FROM, EXTRA_START_DXHOME_FROM_LOCKSCREEN);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (Exception e) {
            try {
                Intent i = new Intent(ACTION_DXHOME_START);
                i.putExtra(EXTRA_START_DXHOME_FROM, EXTRA_START_DXHOME_FROM_LOCKSCREEN);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                context.startActivity(i);
            } catch (Exception e1) {
            }
        }
    }

    /**
     * download By Browser
     * 
     * @param context
     * @param uri
     * @param missidSDCardMsg:if missidSDCardMsg!=null,when sdcard is
     *            missed,show toast of missidSDCardMsg
     */
    public static void downloadByBrowser(Context context, Uri uri, String missidSDCardMsg) {
        try {
            if (isSDCardMounted()) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri);
                context.startActivity(i);
            } else {
                if (missidSDCardMsg != null) {
                    Toast.makeText(context, missidSDCardMsg, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param context
     * @param url
     * @param missidSDCardMsg:if missidSDCardMsg!=null,when sdcard is
     *            missed,show toast of missidSDCardMsg
     */
    public static void downloadByBrowser(Context context, String url, String missidSDCardMsg) {
        try {
            downloadByBrowser(context, Uri.parse(url), missidSDCardMsg);
        } catch (Exception e) {
        }
    }

    public static boolean isContainsPreviewActivity(Context context, String pkgName) {
        return isPkgActivityInstalledByAction(context, pkgName,
                ACTION_LOCKSCREEN_PREVIEW_ACTIVITY);
    }

    public static boolean isPkgInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        android.content.pm.ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static ResolveInfo getElegantMainActivity(Context context) {
        try {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setPackage(PACKAGE_NAME_ELEGANT);
            return context.getPackageManager().resolveActivity(i, 0);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 打开或关闭一锁屏（发broadcast，分别给pro和2.0锁屏发）
     * 
     * @param context
     * @param pkgName
     * @param isOpenThisLockScreen
     */
    public static void openOrCloseLockScreen(Context context, String pkgName,
            boolean isOpenThisLockScreen) {
        Intent intent = new Intent(ACTION_OPEN_CLOSE_LOCKSCREEN);
        intent.putExtra(EXTRA_IS_OPEN_THIS_LOCKSCREEN, isOpenThisLockScreen);
        intent.putExtra(EXTRA_PACKAGENAME, pkgName);
        context.sendBroadcast(intent);

        intent = new Intent(ACTION_OPEN_CLOSE_LOCKSCREEN_V2_0);
        intent.putExtra(EXTRA_IS_OPEN_THIS_LOCKSCREEN, isOpenThisLockScreen);
        intent.putExtra(EXTRA_PACKAGENAME, pkgName);
        context.sendBroadcast(intent);

        // startService,因为4.0以上的机器中如果从未启动，则收不到broadcast
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            try {
                intent = new Intent();
                intent.setClassName(pkgName, pkgName
                        + LOCK_SCREEN_SERVICE_NAME);
                context.startService(intent);
            } catch (Exception e) {
            }
        }
    }

    public  static void sortAppsByInstallTime(Context context, List<String> pkgNames) {
        final int appSize = pkgNames.size();
        Map<CharSequence, Long> installedTimeApps = new HashMap<CharSequence, Long>();
        for (int i = 0; i < appSize; i++) {
            installedTimeApps.put(pkgNames.get(i), getPackageInstallTime(context,
                    pkgNames.get(i).toString()));
        }
        // insert sort:inverted sequence
        for (int i = 0; i < appSize - 1; i++) {
            int minIndex = i;
            // find maxIndex
            for (int j = i + 1; j < appSize; j++) {
                if (installedTimeApps.get(pkgNames.get(j)) > installedTimeApps
                        .get(pkgNames.get(minIndex))) {
                    minIndex = j;
                }
            }
            // swap
            if (minIndex != i) {
                String temp = pkgNames.get(i);
                pkgNames.set(i, pkgNames.get(minIndex));
                pkgNames.set(minIndex, temp);
            }
        }
    }

    public static long getPackageInstallTime(Context context, String packageName) {
        long installTime = 0;
        if (Build.VERSION.SDK_INT < 9) {
            try {
                File apkDir = new File("/data/data/" + packageName);
                if (apkDir.exists()) {
                    installTime = apkDir.lastModified();
                }
            } catch (Exception e) {
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo pkgInfo = packageManager.getPackageInfo(packageName,
                        PackageManager.GET_META_DATA);
                installTime = pkgInfo.firstInstallTime;
            } catch (NameNotFoundException e) {
                try {
                    File apkDir = new File("/data/data/" + packageName);
                    if (apkDir.exists()) {
                        installTime = apkDir.lastModified();
                    }
                } catch (Exception e2) {
                }
            } catch (Exception e) {
            }
        }
        return installTime;
    }

    /**
     * clear reference in InputMethodManager
     */
    public static void destroyIMM() {
        // clear reference in InputMethodManager
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            Class<?> clsIMM;
            clsIMM = cl.loadClass("android.view.inputmethod.InputMethodManager");

            Field fieldInstance = clsIMM.getDeclaredField("mInstance");
            fieldInstance.setAccessible(true);
            Object instance = fieldInstance.get(null);

            Field fieldView = clsIMM.getDeclaredField("mCurRootView");
            fieldView.setAccessible(true);
            fieldView.set(instance, null);

            fieldView = clsIMM.getDeclaredField("mServedView");
            fieldView.setAccessible(true);
            fieldView.set(instance, null);

            fieldView = clsIMM.getDeclaredField("mNextServedView");
            fieldView.setAccessible(true);
            fieldView.set(instance, null);

        } catch (ClassNotFoundException e) {
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (NullPointerException e) {
        }
    }
    public static void destroyView(View view) {
        destroyViewInternal(view, view);
    }

    private static void destroyViewInternal(View view, View root) {
        if (view == null)
            return;

        // set view.mContext = null, mParent = null
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            Class<?> clsView;
            clsView = cl.loadClass("android.view.View");

            if (view != root) {
                Field fieldContext = clsView.getDeclaredField("mContext");
                fieldContext.setAccessible(true);
                fieldContext.set(view, null);
            }

            Field fieldParent = clsView.getDeclaredField("mParent");
            fieldParent.setAccessible(true);
            fieldParent.set(view, null);
        } catch (ClassNotFoundException e) {
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (NullPointerException e) {
        }

        try {
            view.setVisibility(View.GONE);
            view.destroyDrawingCache();

            if (view.getBackground() != null) {
                view.setBackgroundDrawable(null);
            }

            view.setAnimation(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnTouchListener(null);
            if (!(view instanceof AdapterView))
                view.setOnClickListener(null);

            if ((view instanceof ViewGroup) && !(view instanceof AdapterView)) {
                ViewGroup vg = (ViewGroup) view;
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = vg.getChildAt(i);
                    if (v != null)
                        destroyViewInternal(v, root);
                }
                vg.removeAllViews();
            } else if (view instanceof AdapterView) {
                AdapterView<?> av = (AdapterView<?>) view;
                av.setAdapter(null);
                av.setAnimation(null);
                av.setOnItemClickListener(null);
                av.setOnItemSelectedListener(null);
                av.setOnItemLongClickListener(null);
            }
        } catch (Exception e) {
            // silently discard all errors
        }
    }

    public static String appendUrlPostfix(Context context, String url) {
        return appendUrlPostfix(context, url, null);
    }

    public static String appendUrlPostfix(Context context, String url, String uninstallPkgName) {
        return url + "?lc=" + BaseInfoHelper.getLcFromAssets(context) + "&pkg="
                + context.getPackageName()
                + (uninstallPkgName == null ? "" : "&unipkg=" + uninstallPkgName);
    }

    /**
     * 获取当前运行的task top Activity
     * @return
     */
    public static ComponentName getTopActivity(Context context){
        ActivityManager aManager = (ActivityManager)context
                .getSystemService(Context.ACTIVITY_SERVICE);
        RunningTaskInfo tinfo = aManager.getRunningTasks(Integer.MAX_VALUE).get(0);
       return tinfo.topActivity;
    }

    /**
     * 获取所有安装的桌面软件 包括 系统默认 点心桌面 第三方桌面
     * @return
     */
    public static List<ResolveInfo> getDeskInstalls(Context context){
        Intent homeintent = new Intent();
        homeintent.setAction(Intent.ACTION_MAIN);
        homeintent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(
                homeintent, PackageManager.MATCH_DEFAULT_ONLY);
        return list;
    }

    public static void launchDXHomeOnLock(Context context) {
        if (DXLockScreenUtils.isPkgInstalled(context, DXLockScreenUtils.DXHOME_PACKAGE_NAME)) {
            if (isDefaultLauncher(context)) {
                return;
            }
            SharedPreferences sp = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_WORLD_READABLE);
            boolean launch = sp.getBoolean(PREF_LAUNCHER_DXHOME_ON_LOCK, false);

            if(DXLockScreenUtils.DBG){
                Log.i(TAG, "launcher=" + launch);
            }
            if (!launch) {
                ComponentName component = DXLockScreenUtils.getTopActivity(context);
                if(DXLockScreenUtils.DBG){
                    Log.i(TAG, "component=" + component);
                }
                String packageName = component.getPackageName();
                if(DXLockScreenUtils.DBG){
                    Log.i(TAG, "packageName=" + packageName);
                }
                if (!DXLockScreenUtils.DXHOME_PACKAGE_NAME.equals(packageName)) {
                    boolean launcherDXHome = false;
                    if (context.getPackageName().equals(packageName)) {
                        launcherDXHome = true;
                    }

                    List<ResolveInfo> list = DXLockScreenUtils.getDeskInstalls(context);
                    ResolveInfo dxhomeInfo = null;
                    for (ResolveInfo rinfo : list) {
                        if (rinfo.activityInfo.packageName.equals(packageName)) {
                            launcherDXHome = true;
                        } else if (DXLockScreenUtils.DXHOME_PACKAGE_NAME.equals(rinfo.activityInfo.packageName)) {
                            dxhomeInfo = rinfo;
                        }
                    }

                    if(DXLockScreenUtils.DBG){
                        Log.i(TAG, "launcherDXHome=" + launcherDXHome + ", dxhomeInfo=" + dxhomeInfo);
                    }
                    if (launcherDXHome && dxhomeInfo != null) {
                        startActivityWithResolveInfo(context, dxhomeInfo);
                        sp.edit().putBoolean(PREF_LAUNCHER_DXHOME_ON_LOCK, true).commit();
                    }
                }
            }
        }
    }

    private static void startActivityWithResolveInfo(Context context, ResolveInfo dxhomeInfo) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(dxhomeInfo.activityInfo.packageName, dxhomeInfo.activityInfo.name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDXHome(Context context) {
        List<ResolveInfo> list = DXLockScreenUtils.getDeskInstalls(context);
        ResolveInfo dxhomeInfo = null;
        for (ResolveInfo info : list) {
            if (DXLockScreenUtils.DXHOME_PACKAGE_NAME.equals(info.activityInfo.packageName)) {
                dxhomeInfo = info;
                break;
            }
        }

        if (DXLockScreenUtils.DBG) {
            Log.i("LockscreenApplication", "dxhomeInfo=" + dxhomeInfo);
        }
        if (dxhomeInfo != null) {
            DXLockScreenUtils.startActivityWithResolveInfo(context, dxhomeInfo);
        }
    }

    public static boolean isDefaultLauncher(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ComponentName> prefActList = new LinkedList<ComponentName>();
            List<IntentFilter> filterList = new LinkedList<IntentFilter>();
            pm.getPreferredActivities(filterList, prefActList, DXHOME_PACKAGE_NAME);
            if(prefActList.size() <= 0) return false;
            for(IntentFilter filter : filterList) {
                if(filter.hasAction(Intent.ACTION_MAIN) && filter.hasCategory(Intent.CATEGORY_HOME)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable e) {
            return  false;
        }
    }
}
