package com.dianxinos.lockscreen_sdk;

//import com.android.internal.telephony.IccCard;
//import com.android.internal.telephony.TelephonyIntents;
//import android.provider.Telephony.Intents;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.dianxinos.lockscreen_sdk.monitor.DXLockScreenMonitor;
import com.dianxinos.lockscreen_sdk.monitor.DXPhoneStateListener;
import com.dianxinos.lockscreen_sdk.views.DXLockScreenSDKBaseView;
import com.dotools.utils.DTLockScreenUtils;
import com.dotools.utils.DevicesUtils;
import com.dotools.utils.DevicesUtils_vk;
import com.dotools.utils.FullScreenHelperActivity;
import com.dotools.utils.FullScreenHelperNoDisplayActivity;
import com.dotools.utils.UiUtils;
import com.dotools.utils.Utilities;

import java.util.ArrayList;

/**
 * 管理LockScreenView的一个抽象类，必须继承并且实现相应接口。
 * 并可根据需要重写相关protected的接口，如needBatteryChangedReceiver，
 * 如果您的锁屏不需要电量变化通知，则重写使多返回false。
 */
public abstract class DXLockScreenViewManager {
    private static final String ACTION_DXHOT_MAIN_ACTIVITY = "com.dianxinos.dxhot.action.MAIN";

    private static final String SIM_STATUS_NETWORK_LOCKED = "NetworkLocked";

    private static final String SIM_STATUS_NO_SERVICE = "NoService";

    private static final String SIM_STATUS_SIM_MISSING_LOCKED = "SimMissingLocked";

    private static final String SIM_STATUS_SIM_PUK_LOCKED = "SimPukLocked";

    private static final String SIM_STATUS_SIM_LOCKED = "SimLocked";

    private static final String SIM_STATUS_NORMAL = "Normal";

    private static final String SIM_STATUS_SIM_MISSING = "SimMissing";

    private static final String TAG = "DXLockScreenViewManager";

    protected Context mContext;
    protected DXLockScreenSDKBaseView mDXLockScreenView;
    protected DXLockScreenMediator mDXLockScreenMediator;
    protected DXLockScreenMonitor mDXLockScreenMonitor;
    protected WindowManager mWindowManager;

    private CharSequence mPlmn = "";
    private CharSequence mSpn = "";
    private String mSimStatus = SIM_STATUS_NORMAL;
    private CharSequence mCarrierStr = "";

    protected boolean mDXLockScreenLocked = false;

    public static String[] sModlesNotSupportSimState = new String[] {
            "C8650"
    };

    /**
     * 是否处于锁屏状态
     * @return
     */
    public boolean isLockScreenLocked() {
        return mDXLockScreenLocked;
    }

    /**
     * 返回用户自定义的锁屏view。
     * 抽象方法，必须重写。
     * @param context
     * @param mediator
     * @return
     */
    public abstract DXLockScreenSDKBaseView createLockScreenView(Context context,
            DXLockScreenMediator mediator);

    /**
     * 锁屏时触发。继承类可根据需要处理的事件，重写此方法。
     */
    protected void onLock(){
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG,
                    "DXLockScreenUtils.isDXLauncherInstalled(mContext)="
                            + DXLockScreenUtils.isDXLauncherInstalled(mContext));
        }
        DXLockScreenUtils.launchDXHomeOnLock(mContext);
    }

    /**
     * 解锁时触发。继承类可根据需要处理的事件，重写此方法。
     */
    protected void onUnlock(){
    }

    /**
     * 是否需要运营商字符串，继承类可根据需要重写。
     * 如果您的锁屏不需要运营商信息，则重写使多返回false
     * @return 默认返回true。
     */
    protected boolean needSpnStringReceiver() {
        return true;
    }

    /**
     * 是否需要SIM卡状态，继承类可根据需要重写。
     * 如果您的锁屏不需要SIM卡信息，则重写使多返回false
     * @return 默认返回true。
     */
    protected boolean needSimStateReceiver() {
        return true;
    }

    /**
     * 是否需要时间日期，继承类可根据需要重写。
     * 如果您的锁屏不需要时间日期变化通知，则重写使多返回false
     * @return 默认返回true。
     */
    protected boolean needTimeReceiver() {
        return true;
    }

    /**
     * 是否需要电量信息，继承类可根据需要重写。
     * 如果您的锁屏不需要电量变化通知，则重写使多返回false
     * @return 默认返回true。
     */
    protected boolean needBatteryChangedReceiver() {
        return true;
    }

    /**
     * 是否需要未接电话与短信，继承类可根据需要重写
     * @return 默认返回true。
     */
    protected boolean needCallMessageObservers() {
        return true;
    }

    private void setmDXLockScreenLocked(boolean mDXLockScreenLocked) {
        this.mDXLockScreenLocked = mDXLockScreenLocked;
    }

    public DXLockScreenViewManager(Context context,
            DXLockScreenMediator mediator) {
        mContext = context;
        mDXLockScreenMediator = mediator;
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
    }

    private void createDXLockScreenView() {
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "createDXLockScreenView() mDXLockScreenView:"
                    + mDXLockScreenView);
        }

        if (mDXLockScreenView == null) {
            mDXLockScreenView = createLockScreenView(mContext, mDXLockScreenMediator);
            // TestMemoryLeak.addObjectMonitor(mDXLockScreenView,
            // "mDXLockScreenView");
            mDXLockScreenMonitor = new DXLockScreenMonitor(mContext,
                    mDXLockScreenMediator);
        }
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "end createDXLockScreenView() mDXLockScreenView:"
                    + mDXLockScreenView);
        }
    }
    
    private void registerViewObserversAndReceivers() {
        if(needCallMessageObservers()){
            mDXLockScreenMonitor.registerCallMessageObservers();
        }
        IntentFilter filter = new IntentFilter();
        if(needTimeReceiver()){
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        }
        if(needBatteryChangedReceiver()){
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        }
        if(needSimStateReceiver() && isSupportSimState(mContext)){
//            filter.addAction(Telephony.Intents.ACTION_SIM_STATE_CHANGED);
            filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        }
        if(needSpnStringReceiver() && isSupportSimState(mContext)){
//            filter.addAction(Telephony.Intents.SPN_STRINGS_UPDATED_ACTION);
            filter.addAction("android.provider.Telephony.SPN_STRINGS_UPDATED");
        }
        final int count = DXLockScreenUtils.ACTION_ALARM_ON_ALL.length;
        for (int i = 0; i < count; i++) {
            filter.addAction(DXLockScreenUtils.ACTION_ALARM_ON_ALL[i]);
        }
        Intent intent = mContext.registerReceiver(mDXLockScreenMonitor.mReceiver, filter);
        if (intent != null && Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())
                && needBatteryChangedReceiver()) {
            mDXLockScreenMediator.notifyBatteryChanged(intent);
        }
    }

    public static boolean isSupportSimState(Context context) {
        for (String model : sModlesNotSupportSimState) {
            if (model.equals(Build.MODEL))
                return false;
        }
        return true;
    }

    private void unregisterViewObserversAndReceivers() {
        mDXLockScreenMonitor.unregisterCallMessageObservers();
        mDXLockScreenMonitor.unregisterReceiver();
    }

    protected void lock() {
        if (DXLockScreenUtils.DBG) {
            Log.i(TAG, "DXPhoneStateListener.sIsRinging=" + DXPhoneStateListener.sIsRinging
                    + ",isLockScreenLocked()=" + isLockScreenLocked() + ",mDXLockScreenView="
                    + mDXLockScreenView);
        }
        try{
            if (DXPhoneStateListener.sIsRinging)
                return;
            if (isLockScreenLocked())
                return;
            createDXLockScreenView();
            
//            if (Build.VERSION.SDK_INT >= 19 && DevicesUtils_vk.hasVertualKey()) {// |
//                // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                // 有导航条并且版本在19以上
//                mDXLockScreenView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LOW_PROFILE);
//            } else if (Build.VERSION.SDK_INT >= 14) {
//                mDXLockScreenView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//            }
            
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            if (DXLockScreenUtils.isICSSDKVersion()) {
                Log.d(TAG, "Current platform is ics");
                params.type = WindowManager.LayoutParams.TYPE_PHONE;// TYPE_PHONE(not
                                                                    // top);//TYPE_STATUS_BAR(permision)
            } else {
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            }
            params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            params.flags |= LayoutParams.FLAG_DISMISS_KEYGUARD;
//            params.flags |= LayoutParams.FLAG_FULLSCREEN;
//            params.flags |= LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//            params.flags |= LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            params.flags |= LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            params.format = PixelFormat.RGBA_8888;
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST;
            params.gravity = Gravity.TOP | Gravity.START;
//            if (VERSION.SDK_INT >= 19) {
//                params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            }
//            if (DevicesUtils_vk.hasVertualKey()) {
//                params.height = UiUtils.getScreenHeightPixelsTotal();
//            } else {
             params.height = WindowManager.LayoutParams.MATCH_PARENT;
            //}
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.x = 0;
            params.y = 0;
            params.flags |= LayoutParams.FLAG_HARDWARE_ACCELERATED;
            if (DevicesUtils.isLenovoDevice()) {
                FullScreenHelperNoDisplayActivity.doStartActivity(mContext);
            } else {
                FullScreenHelperActivity.doStartActivity(mContext);
            }
            hanldeLiveWallpaper(params);
//            mDXLockScreenView.setLayoutParams(params);
            if (mDXLockScreenView != null && !isLockScreenLocked()) {
                mWindowManager.addView(mDXLockScreenView, params);
            }
//            params.type = WindowManager.LayoutParams.TYPE_KEYGUARD;
//            mDXLockScreenView.setLayoutParams(params);
//            mWindowManager.updateViewLayout(mDXLockScreenView, params);

            if (DXLockScreenUtils.isScreenOn(mContext)) {
                if (DXLockScreenUtils.DBG) {
                    Log.d(TAG,
                            "because now the screen is on, we call DXLockScreenView.onresume()");
                }
                mDXLockScreenView.onResume();
            }
            setmDXLockScreenLocked(true);
            registerViewObserversAndReceivers();
            mDXLockScreenMediator.notifyScreenLock();
            sendDXLockScreenLockedIntent();

            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "showing DX lockscreen");
            }
            onLock();
        }catch (Exception e) {
            Log.e(TAG, "Lock failed!\n" + e);
        }
    }

    protected void hanldeLiveWallpaper(LayoutParams params) {
    }

    protected void handleUnlockScreen(int position) {
        if (DXLockScreenUtils.DBG)
            Log.i(TAG, "handleUnlockScreen position = position");
        Intent unlockScreenIntent = new Intent(Intent.ACTION_MAIN);
        unlockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        boolean startActivityFlag = true;

        if (position == DXLockScreenUtils.UNLOCK_POSITION_NEW_SMS
                || position == DXLockScreenUtils.UNLOCK_POSITION_VIEW_SMS) {
            if (DXLockScreenUtils.DBG)
                Log.i(TAG, "unlock type = UNLOCK_POSITION_NEW_SMS");
            unlockScreenIntent.setType("vnd.android.cursor.dir/mms");
            unlockScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            unlockScreenIntent.addCategory(Intent.CATEGORY_DEFAULT);
        } else if (position == DXLockScreenUtils.UNLOCK_POSITION_NEW_CALL
                || position == DXLockScreenUtils.UNLOCK_POSITION_VIEW_CALL) {
            if (DXLockScreenUtils.DBG)
                Log.i(TAG, "unlock type = UNLOCK_POSITION_NEW_CALL");
            unlockScreenIntent.setAction("android.intent.action.CALL_BUTTON");
        } else if (position == DXLockScreenUtils.UNLOCK_POSITION_CAMERA) {
            unlockScreenIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        } else if (position == DXLockScreenUtils.UNLOCK_POSITION_DXHOT) {
            unlockScreenIntent.setAction(ACTION_DXHOT_MAIN_ACTIVITY);
            unlockScreenIntent.addCategory(Intent.CATEGORY_DEFAULT);
            // if has install home which contains LockScreenManager(pro or elegant),start dxhome instead.
            ArrayList<String> dxHomes = DXLockScreenUtils.queryActivitysByAction(mContext, ACTION_DXHOT_MAIN_ACTIVITY);
            if (dxHomes.size() > 0) {
                DXLockScreenUtils.sortAppsByInstallTime(mContext, dxHomes);
                unlockScreenIntent.setPackage(dxHomes.get(0));
            }
        } else {
            startActivityFlag = false;
        }

        if (startActivityFlag) {
            try {
                mContext.startActivity(unlockScreenIntent);
            } catch (Exception e) {
            }
        }
        StatManager.reportUnlockType(position);

        // for removing lockscreen view.
        unlock();
    }

    protected void unlock() {
        try {
            setmDXLockScreenLocked(false);
            if (mDXLockScreenView != null) {
                unregisterViewObserversAndReceivers();
                mDXLockScreenView.onPause();
            }
        } catch (Exception e) {
            Log.e(TAG, "removed LockScreenView throw exception");
        } finally {
            try {
                if (DevicesUtils.isLenovoDevice()) {
                    FullScreenHelperNoDisplayActivity.doFinishActivity();
                } else {
                    FullScreenHelperActivity.doFinishActivity();
                }
                mWindowManager.removeView(mDXLockScreenView);
                mDXLockScreenView.destroy();
                DXLockScreenUtils.destroyView(mDXLockScreenView);
                mDXLockScreenView = null;
                DXLockScreenUtils.destroyIMM();
                mDXLockScreenMediator.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        System.gc();
                    }
                }, 2000);
                sendDXLockScreenUnLockedIntent();
            } catch (Exception e) {
                Log.e(TAG, "Unlock failed!\n" + e);
            }
        }
        onUnlock();
    }

    protected void handleScreenTurnedOff() {
        if (mDXLockScreenView != null) {
            mDXLockScreenView.onPause();
        } else {
            lock();
        }
    }

    protected void handleScreenTurnedOn() {
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "received scree on event.");
        }
        if (mDXLockScreenView != null) {
            mDXLockScreenView.onResume();
        }
    }

    protected void handleCallMessageUpdated(final int unreadType,
            final int unreadNum) {
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "update lockscreenview using unreadnum:" + unreadNum
                    + "; unreadType:" + unreadType);
        }
        mDXLockScreenView.updateCallSmsCount(unreadType, unreadNum);
    }

    protected void handleTimeDateUpdated() {
        if (isLockScreenLocked()) {
            mDXLockScreenView.updateTimeAndDate();
        }
    }

    protected void handleSPNUpdated(CharSequence plmn, CharSequence spn) {
        mPlmn = plmn;
        mSpn = spn;
        setCarrierString();
        if (isLockScreenLocked()) {
            mDXLockScreenView.updateCarrierInfo(mCarrierStr);
        }
    }

    protected void handSimStateChanged(Intent intent) {
        setSimStatus(intent);
        setCarrierString();
        if (isLockScreenLocked()) {
            mDXLockScreenView.updateCarrierInfo(mCarrierStr);
        }
    }

    protected void handleBatteryChanged(Intent intent) {
        final int pluggedInStatus = intent.getIntExtra("status",
                BatteryManager.BATTERY_STATUS_UNKNOWN);
        int batteryLevel = intent.getIntExtra("level", 0);
        final boolean pluggedIn = isPluggedIn(pluggedInStatus);
        batteryLevel = Math.max(batteryLevel, 0);
        batteryLevel = Math.min(batteryLevel, 100);
        if (isLockScreenLocked()) {
            mDXLockScreenView.updateBatteryStatus(pluggedIn, batteryLevel);
        }
    }

    private boolean isPluggedIn(int status) {
        return status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;
    }

    private void setSimStatus(Intent intent) {
//        String stateExtra = intent.getStringExtra(IccCard.INTENT_KEY_ICC_STATE);
        String stateExtra = intent.getStringExtra("ss");
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "setSimStatus stateExtra:" + stateExtra);
        }

//        if (IccCard.INTENT_VALUE_ICC_ABSENT.equals(stateExtra)) {
//            mSimStatus = SIM_STATUS_SIM_MISSING;
//        } else if (IccCard.INTENT_VALUE_ICC_READY.equals(stateExtra)
//                || IccCard.INTENT_VALUE_ICC_LOADED.equals(stateExtra)) {
//            mSimStatus = SIM_STATUS_NORMAL;
//        } else if (IccCard.INTENT_VALUE_ICC_LOCKED.equals(stateExtra)) {
//            final String lockedReason = intent
//                    .getStringExtra(IccCard.INTENT_KEY_LOCKED_REASON);
//            if (IccCard.INTENT_VALUE_LOCKED_ON_PIN.equals(lockedReason)) {
//                mSimStatus = SIM_STATUS_SIM_LOCKED;
//            } else if (IccCard.INTENT_VALUE_LOCKED_ON_PUK.equals(lockedReason)) {
//                mSimStatus = SIM_STATUS_SIM_PUK_LOCKED;
//            } else {
//                mSimStatus = SIM_STATUS_SIM_MISSING;
//            }
//        } else if (IccCard.INTENT_VALUE_LOCKED_NETWORK.equals(stateExtra)) {
//            mSimStatus = SIM_STATUS_SIM_MISSING_LOCKED;
//        } else {
//            mSimStatus = SIM_STATUS_NO_SERVICE;
//        }
        
        if ("ABSENT".equals(stateExtra)) {
            this.mSimStatus = "SimMissing";
          } else if (("READY".equals(stateExtra)) || ("LOADED".equals(stateExtra)))
          {
            this.mSimStatus = "Normal";
          } else if ("LOCKED".equals(stateExtra)) {
            String lockedReason = intent.getStringExtra("reason");

            if ("PIN".equals(lockedReason))
              this.mSimStatus = "SimLocked";
            else if ("PUK".equals(lockedReason))
              this.mSimStatus = "SimPukLocked";
            else
              this.mSimStatus = "SimMissing";
          }
          else if ("NETWORK".equals(stateExtra)) {
            this.mSimStatus = "SimMissingLocked";
          } else {
            this.mSimStatus = "NoService";
          }
    }

    /**
     * Update CarrierView to match the current status.
     */
    private void setCarrierString() {
        mCarrierStr = "";
        if (mSimStatus.equals(SIM_STATUS_NORMAL)) {
            mCarrierStr = handlePlmnSpn(mPlmn, mSpn);
        } else if (mSimStatus.equals(SIM_STATUS_NETWORK_LOCKED)) {
            mCarrierStr = handlePlmnSpn(
                    mPlmn,
                    mContext.getText(ResourceManager.sResourceManager.getNetWorkLockedMsgID()));//R.string.lockscreen_network_locked_message
        } else if (mSimStatus.equals(SIM_STATUS_SIM_MISSING)) {
            mCarrierStr = mContext
                    .getText(ResourceManager.sResourceManager.getSimMissingMsgID());//R.string.lockscreen_missing_sim_message_short
        } else if (mSimStatus.equals(SIM_STATUS_SIM_MISSING_LOCKED)) {
            mCarrierStr = handlePlmnSpn(
                    mPlmn,
                    mContext.getText(ResourceManager.sResourceManager.getSimMissingMsgID()));//R.string.lockscreen_missing_sim_message_short
        } else if (mSimStatus.equals(SIM_STATUS_SIM_LOCKED)) {
            mCarrierStr = handlePlmnSpn(mPlmn,
                    mContext.getText(ResourceManager.sResourceManager.getSimLockedMsgID()));//R.string.lockscreen_sim_locked_message
        } else if (mSimStatus.equals(SIM_STATUS_SIM_PUK_LOCKED)) {
            mCarrierStr = handlePlmnSpn(
                    mPlmn,
                    mContext.getText(ResourceManager.sResourceManager.getSimPukLockedMsgID()));//R.string.lockscreen_sim_puk_locked_message
        } else if (mSimStatus.equals(SIM_STATUS_NO_SERVICE)) {
            mCarrierStr = mContext.getText(ResourceManager.sResourceManager.getCarrierDefaultMsgID());//R.string.lockscreen_carrier_default
        }
    }

    static CharSequence handlePlmnSpn(CharSequence telephonyPlmn,
            CharSequence telephonySpn) {
        if (telephonyPlmn != null && telephonySpn == null) {
            return telephonyPlmn;
        } else if (telephonyPlmn != null && telephonySpn != null) {
            return telephonyPlmn + "|" + telephonySpn;
        } else if (telephonyPlmn == null && telephonySpn != null) {
            return telephonySpn;
        } else {
            return "";
        }
    }

    /**
     * @return if is unlock,return true.
     */
    public boolean checkHasStartedUnlock() {
        return !isLockScreenLocked() || mDXLockScreenView == null;
    }

    protected void sendDXLockScreenLockedIntent(){
        Intent intent = new Intent();
        intent.setAction(DXLockScreenUtils.ACTION_DXLOCKSCREENLOCKED);
        mContext.sendBroadcast(intent);
        if (DXLockScreenUtils.DBG)
            Log.e(TAG, "sendDXLockScreenLockedIntent:");
    }

    protected void sendDXLockScreenUnLockedIntent(){
        Intent intent = new Intent();
        intent.setAction(DXLockScreenUtils.ACTION_DXLOCKSCREENUNLOCKED);
        mContext.sendBroadcast(intent);
        if (DXLockScreenUtils.DBG)
            Log.e(TAG, "sendDXLockScreenUnLockedIntent:");
    }
}
