package com.imdoon.daemonguard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Build;
import android.util.Log;
import android.app.Service;

import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.lockscreen.StaticService;

import java.io.IOException;


public class DaemonService extends Service {

	public static final String TAG = "imdoon_DaemonService";

    public DaemonWatcher mWatcher = DaemonWatcher.getInstance();
    public String packageName;
    public String serviceName;
    public boolean daemonExit = false;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static DaemonService mDaemonService;
    @Override  
    public void onCreate() {
        Log.i(TAG, "DaemonService onCreate ...");
        mDaemonService = this;
        if (!daemonExit) {
            daemonStart();
        }
        if(GlobalConfigMgr.getOpenLockScreenFlag(getApplicationContext())){
            Intent intent1 = new Intent(StaticService.action_wakeSelf);
            intent1.setPackage("com.yp.lockscreen");
            if (android.os.Build.VERSION.SDK_INT >= 12) {
                intent1.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
            }
            getApplicationContext().startService(intent1);
        }
    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.i(TAG, "DaemonService onStartCommand ...");
		super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mDaemonService = null;
        Log.i(TAG, "DaemonService onDestroy ...");
        if (!daemonExit) {
            mWatcher.disconnectToAppMonitor();
        }
	}

    private void daemonStart() {
        packageName = this.getPackageName();
        serviceName = packageName + "/" + this.getClass().getCanonicalName();
            Log.d(TAG, "PackageName: " + packageName);
            Log.d(TAG, "ServiceName: " + serviceName);

        mWatcher.init(Build.VERSION.SDK_INT, packageName, serviceName);

        mWatcher.createAppMonitor();
        mWatcher.connectToAppMonitor();
    }

//    private void daemonStop() {
//        daemonExit = mWatcher.deinit();
//    }

    public static void startGuard(Context context) {
        try {
            AssetFileUtils.copyAssetFileToFiles(context, "imdoon_core_daemon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.startService(new Intent(context, DaemonService.class));
    }

//    public static void stopGuard(Context context){
//        if(mDaemonService != null) {
//            mDaemonService.daemonStop();
//        }
//    }
}
