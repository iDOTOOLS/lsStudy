package com.dianxinos.lockscreen_sdk;

import com.dianxinos.backend.DXBackendConfig;
import com.dianxinos.lockscreen_sdk.monitor.DXPhoneStateListener;
import com.dianxinos.lockscreen_sdk.monitor.DXScreenOnOffReceiver;
import com.dianxinos.lockscreen_sdk.monitor.DXSettingObserver;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public abstract class LockScreenBaseService extends Service {
    private static final String TAG = "LockScreenBaseService";
    private static int mIsDianXinLockScreenDisabled = DXLockScreenUtils.ICS_ENABLED_DIANXIN_LOCKSCREEN;

    private Context mContext;
    private DXLockScreenMediator mDXLockScreenMediator;
    private DXLockScreenViewManager mDXLockScreenViewManager;
    private DXScreenOnOffReceiver mDXScreenOffReceiver;
    private DXPhoneStateListener mDXPhoneStateListener;
    private DXSettingObserver mDXSettingObserver;
    private CrashHandler mCrashHandler = new CrashHandler();

    public static AudioManager sAudioManager;

    BroadcastReceiver mUserPresentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            mDXLockScreenMediator.notifyClearSystemLock();
        }
    };

    /**
     * check update from server
     */
    public abstract void checkUpdate();
    
    public abstract DXLockScreenViewManager createLockScreenViewManager(Context context,
            DXLockScreenMediator lockScreenMediator);

    @Override
    public void onCreate() {
        super.onCreate();
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "LockScreenBaseService has been created");
        }
        DXBackendConfig.init();
        init();
        mDXLockScreenMediator
                .notifyLockPasswordChanged(DXLockScreenUtils.PASSWORDTYPE_NONESTR, mIsDianXinLockScreenDisabled);
        if (DXLockScreenUtils.checkIsRunningOnRom()) {
            mDXLockScreenMediator.notifyScreenOff();
        }
        checkUpdate();
        sAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "LockScreenBaseService onDestroy");
        }
        cleanUp();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void init() {
        mContext = getApplicationContext();
        // set the high priority service.
        Notification notifyForeground = new Notification();
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(100, notifyForeground);
        }
        try {
            mIsDianXinLockScreenDisabled = Settings.Secure.getInt(mContext.getContentResolver(),
                    DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY, DXLockScreenUtils.ICS_ENABLED_DIANXIN_LOCKSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCrashHandler.init(DXLockScreenUtils.getVersionNameCode(mContext),
                mContext);
        mDXLockScreenMediator = new DXLockScreenMediator(this);
        mDXLockScreenViewManager = createLockScreenViewManager(mContext,mDXLockScreenMediator);
        mDXLockScreenMediator.setViewManager(mDXLockScreenViewManager);
        mDXScreenOffReceiver = new DXScreenOnOffReceiver(mContext,
                mDXLockScreenMediator);
        mDXPhoneStateListener = new DXPhoneStateListener(mContext,
                mDXLockScreenMediator);

        //start to register observer, listener and receiver.
        mDXSettingObserver = new DXSettingObserver(mContext,
                mDXLockScreenMediator);
        mDXScreenOffReceiver.registerScreenOffReceiver();
        mDXPhoneStateListener.registerListener();
        registerReceiver(mUserPresentReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));

        StatManager.init(mContext);
    }

    private void cleanUp() {
        stopForeground(true);
        if (mDXScreenOffReceiver != null) {
            mDXScreenOffReceiver.unregisterScreenOffReceiver();
        }
        unregisterReceiver(mUserPresentReceiver);

        if (mDXPhoneStateListener != null) {
            mDXPhoneStateListener.unregisterListener();
        }

        if (mDXSettingObserver != null) {
            mDXSettingObserver = null;
        }

        if (mCrashHandler != null) {
            mCrashHandler.destroy();
        }

        if (DXLockScreenUtils.checkIsRunningOnRom()) {
            //we are running on rom, we should not restore system locskcreen.
            //mDXLockScreenMediator.restoreCMLockScreenDefaultValue();
        } else {
            DXKeyGuardLocker.getInstance(mContext).getNewKeyGuard();
            DXKeyGuardLocker.getInstance(mContext).reenableKeyGuard();
        }
    }

    /**
     * 锁屏时触发
     */
    public void handleLockScreen() {
        StatManager.reportStart();
    }

}
