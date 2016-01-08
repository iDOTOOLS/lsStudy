package com.yp.lockscreen;

import java.io.File;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dotools.utils.Utilities;
import com.imdoon.daemonguard.DaemonService;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.Constans;
import com.yp.enstudy.DownloadManager;
import com.yp.enstudy.WordData;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.DeviceUtil;
import com.yp.lockscreen.utils.LogHelper;

public class LockScreenApplication extends Application {

    private String getProcessName() {
        final int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();
        String myProcessName = getProcessName();
        if (myProcessName.equals("com.yp.lockscreen")) {
            Utilities.initEnvironment(this);
            DXLockScreenUtils.DBG = true;
            preLoadInfo();
            getScreenSize();
            initBaseDir();
            initImageLoader(Global.gContext);
            Global.language = DeviceUtil.getLocalLanguage();
            
            wakeUpLockService();
        }
        if (!myProcessName.equals("com.yp.lockscreen:dae")) {
            DaemonService.startGuard(getApplicationContext());
        }
    }
    

    public void wakeUpLockService() {
        if (!DeviceUtil.isServiceWorked(getApplicationContext(), "com.yp.lockscreen.StaticService")) {
            LogHelper.d("", "锁屏服务 StaticService 停止 重新激活.");
            DXLockScreenUtils.openOrCloseLockScreen(this, this.getPackageName(), ConfigManager.isUseLockScreen(getApplicationContext()));
        }
    }

    public final static long MINTIME = 10 * 60 * 1000;
    public static long lastTime = 0;

    void preLoadInfo() {
        LogHelper.d("application", "preLoadInfo function");
        Global.gContext = this.getApplicationContext();

        Global.gWordData = new WordData(Global.gContext);

        Global.gWordData.initCurCikuName();

        Global.gDownLoad = new DownloadManager(Global.gContext);

        Global.gUnknownWords = Global.updataNoRememberList();

        Global.gGraspWords = Global.updataRemember();

        Global.gReViewWords = Global.updataReviewlsit();

        Global.updataCurTableName();
    }

    public static void getScreenSize() {
        Global.screen_Height = Global.gContext.getResources().getDisplayMetrics().heightPixels;
        Global.screen_width = Global.gContext.getResources().getDisplayMetrics().widthPixels;
    }

    private void initBaseDir() {
        long availableSDCardSpace = DeviceUtil.getSdCardHaveSize();
        // 获取SD卡可用空间
        if (availableSDCardSpace != -1L) {// 如果存在SD卡
            Global.BASE_PATH = Environment.getExternalStorageDirectory() + File.separator + "EnLockScreen";
        } else if (DeviceUtil.getSdCardHaveSize() != -1) {
            Global.BASE_PATH = getFilesDir().getPath() + File.separator + "EnLockScreen";
        } else { // sd卡不存在
            Global.BASE_PATH = getFilesDir().getPath() + File.separator + "EnLockScreen";
        }

        Global.AUDIO_PATH = Global.BASE_PATH + File.separator + "audio/";
        Global.DOWNLOAD_PATH = Global.BASE_PATH + File.separator + "download/";
        Global.TEMP_PATH = Global.BASE_PATH + File.separator + "temp/";
        Global.IMAGE_PATH = Global.BASE_PATH + File.separator + "image/";

        Constans.PATH_AUDIO = Global.AUDIO_PATH;
        Constans.PATH_DOWNLOAD = Global.DOWNLOAD_PATH;
        Constans.PATH_IMG = Global.IMAGE_PATH;
        Constans.PATH_TEMP = Global.TEMP_PATH;

        File basePathFile = new File(Global.BASE_PATH);
        if (!basePathFile.exists()) {
            basePathFile.mkdirs();
        }
        File audioPathFile = new File(Global.AUDIO_PATH);
        if (!audioPathFile.exists()) {
            audioPathFile.mkdirs();
        }
        File downloadPathFile = new File(Global.DOWNLOAD_PATH);
        if (!downloadPathFile.exists()) {
            downloadPathFile.mkdirs();
        }

        File tempPathFile = new File(Global.TEMP_PATH);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }

        File imgPathFile = new File(Global.IMAGE_PATH);
        if (!imgPathFile.exists()) {
            imgPathFile.mkdirs();
        }
    }

    /**
     * 初始化图片加载类配置信息
     * 
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
                .denyCacheImageMultipleSizesInMemory()// 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
                .diskCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
