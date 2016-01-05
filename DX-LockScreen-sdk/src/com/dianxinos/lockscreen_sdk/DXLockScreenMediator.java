package com.dianxinos.lockscreen_sdk;

import java.lang.reflect.Method;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

public class DXLockScreenMediator {
    private static final String TAG = "DXLockScreenMediator";

    private boolean mIsDianXinLockScreenDisabled = false;
    private LockScreenBaseService mLockScreenService;
    private DXLockScreenViewManager mDXLockScreenViewManager;
    private DXOperatingCMLockScreen mDXOperatingCMLockScreen;
    private String mLockPasswordStr = DXLockScreenUtils.PASSWORDTYPE_NONESTR;

    private CharSequence mPlmn;
    private CharSequence mSpn;
    private boolean mScreenIsLockedBeforeCallRinging = false;

    private ClassManager  classM;

    public DXLockScreenMediator(LockScreenBaseService service) {
        mLockScreenService = service;
        if (DXLockScreenUtils.checkIsRunningOnRom()) {
            mDXOperatingCMLockScreen = new DXOperatingCMLockScreen(mLockScreenService);
        }
        try {
            classM = new ClassManager();
            classM.LockPatternNewInstance(mLockScreenService);
//            mLockPatternUtils = new LockPatternUtils(mLockScreenService);
        } catch (Throwable e) {
        }
    }
    
    public void setViewManager(DXLockScreenViewManager dXLockScreenViewManager){
        mDXLockScreenViewManager = dXLockScreenViewManager;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
            case DXLockScreenUtils.MSG_SCREEN_OFF:
                handleNotifyScreenOff();
                break;
            case DXLockScreenUtils.MSG_SCREEN_ON:
                handleNotifyScreenOn();
                break;
            case DXLockScreenUtils.MSG_UNLOCK_NORMAL:
                handleNotifyUnlockNormal(msg.arg1);
                break;
            case DXLockScreenUtils.MSG_TIMEDATE_UPDATED:
                handleNotifyTimeDateUpdated();
                break;
            case DXLockScreenUtils.MSG_CALL_RINGING:
                handleNotifyCallRinging();
                break;
            case DXLockScreenUtils.MSG_CALL_ANSWERORREFUSE:
                handleNotifyCallHangupOrRefuse();
                break;
            case DXLockScreenUtils.MSG_ALARM_ON:
                handleNotifyAlarmOn();
                break;
            case DXLockScreenUtils.MSG_LOCKPASSWORD_CHANGED:
                handleNotifyLockPasswordChanged();
                break;
            case DXLockScreenUtils.MSG_CLEAR_SYSTEM_LOCK:
                handleNotifyClearSystemLock();
                break;
            case DXLockScreenUtils.MSG_CALLORMESSAGE_UPDATED:
                handleNotifyCallMessageUpdated(msg.arg1, msg.arg2);
                break;
            case DXLockScreenUtils.MSG_SPN_UPDATED:
                handleNotifySPNUpdated();
                break;
            case DXLockScreenUtils.MSG_SIM_STATE_CHANGNED:
                handleNotifySimStateChanged((Intent) msg.obj);
                break;
            case DXLockScreenUtils.MSG_BATTERY_CHANGNED:
                handleNotifyBatteryChanged((Intent) msg.obj);
                break;
            case DXLockScreenUtils.MSG_SCREEN_LOCK:
                handleLockScreen();
                break;
            case DXLockScreenUtils.MSG_STATUSBAR_NOTIFICATION_CLICKED:
                handleNotifyStatusbarNotificationClicked();
                break;
            default:
                break;
            }
        }
    };

    private void handleNotifyScreenOff() {
        try {
            if (DXLockScreenUtils.checkIsRunningOnRom()) {
                if (DXLockScreenUtils.DBG) {
                    Log.d(TAG,
                            "mLockPatternUtils.isSecure():" + classM.LockPattern_IsSecure()
                                    + "isDianxinLockScreenDisabled:" + mIsDianXinLockScreenDisabled);
                }
                // If current situation is safe or user set no LockScreen, we should
                // disable DianXin LockScreen
                if (isDisabledDianXinLockScreen()) {
                    return;
                }
                mDXOperatingCMLockScreen.checkLockScreenDisableOnSecurity();
            }
            mDXLockScreenViewManager.handleScreenTurnedOff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleLockScreen() {
        mLockScreenService.handleLockScreen();
    }

    private void handleNotifyScreenOn() {
        if (DXLockScreenUtils.checkIsRunningOnRom()
                && isDisabledDianXinLockScreen()) {
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG,
                        "No need to unlock Lockscreen");
            }
            return;
        }
        mDXLockScreenViewManager.handleScreenTurnedOn();
    }

    private void handleNotifyCallRinging() {
        mDXLockScreenViewManager.unlock();
    }

    private void handleNotifyCallHangupOrRefuse() {
        if (DXLockScreenUtils.DBG) Log.d(TAG, "handleNotifyCallAnswerOrRefuse to show lockscreen");
        mDXLockScreenViewManager.lock();
    }

    private void handleNotifyAlarmOn() {
        mDXLockScreenViewManager.unlock();
    }

    private void handleNotifyLockPasswordChanged() {
        if (DXLockScreenUtils.DBG) {
            Log.e(TAG, "handleNotifyLockPasswordChanged ...");
        }
        handleNotifyClearSystemLock();
        if (DXLockScreenUtils.checkIsRunningOnRom()) {
            new Thread(){
                @Override
                public void run() {
                    mDXOperatingCMLockScreen.checkCMLockscreenState(mLockPasswordStr);
                }
            }.start();
        }
    }

    private void handleNotifyClearSystemLock() {
        if (DXLockScreenUtils.DBG) {
            Log.e(TAG, "handleNotifyClearSystemLock ...");
        }
        if (DXLockScreenUtils.checkIsRunningOnRom()) {
            if (mDXOperatingCMLockScreen.ismStartFirstTime()) {
                mDXOperatingCMLockScreen.initDefaultCMValue();
            }
        } else {
            DXKeyGuardLocker.getInstance(mLockScreenService).getNewKeyGuard();
            DXKeyGuardLocker.getInstance(mLockScreenService).disableKeyGuard();
        }
    }

    private void handleNotifyCallMessageUpdated(int unreadType, int unreadNum) {
        mDXLockScreenViewManager
                .handleCallMessageUpdated(unreadType, unreadNum);
    }

    private void handleNotifyUnlockNormal(int unlockToApp) {
        mDXLockScreenViewManager.handleUnlockScreen(unlockToApp);
    }

    private void handleNotifyTimeDateUpdated() {
        mDXLockScreenViewManager.handleTimeDateUpdated();
    }

    public void handleNotifySPNUpdated() {
        mDXLockScreenViewManager.handleSPNUpdated(mPlmn, mSpn);
    }

    private void handleNotifySimStateChanged(Intent intent) {
        mDXLockScreenViewManager.handSimStateChanged(intent);
    }

    private void handleNotifyBatteryChanged(Intent intent) {
        mDXLockScreenViewManager.handleBatteryChanged(intent);
    }

    private void handleNotifyStatusbarNotificationClicked(){
        mDXLockScreenViewManager.unlock();
    }

    public void notifyScreenOff() {
        // if we are in rom, we should check cm lockscreen
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_SCREEN_OFF));
    }

    public void notifyScreenOn() {
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_SCREEN_ON));
    }

    public void notifyCallRinging() {
        mScreenIsLockedBeforeCallRinging = mDXLockScreenViewManager
                .isLockScreenLocked();
        if (mScreenIsLockedBeforeCallRinging) {
            mHandler.sendMessage(mHandler
                    .obtainMessage(DXLockScreenUtils.MSG_CALL_RINGING));
        }
    }

    public void notifyCallHangupOrRefuse() {
        if (mScreenIsLockedBeforeCallRinging) {
            mScreenIsLockedBeforeCallRinging = false;
            mHandler.sendMessage(mHandler
                    .obtainMessage(DXLockScreenUtils.MSG_CALL_ANSWERORREFUSE));
        }
    }

    public void notifyAlarmOn() {
        if (DXLockScreenUtils.DBG) Log.d(TAG, "notifyAlarm on");
        if (mDXLockScreenViewManager.isLockScreenLocked()) {
            mHandler.sendMessage(mHandler
                    .obtainMessage(DXLockScreenUtils.MSG_ALARM_ON));
        }
    }

    public void notifyLockPasswordChanged(String passwordTypestr, int isICSLockScreenDisabled) {
        mLockPasswordStr = passwordTypestr;
//        mIsDianXinLockScreenDisabled = (isICSLockScreenDisabled == DXLockScreenUtils.ICS_DISABLED_DIANXIN_LOCKSCREEN) ? true
//                : false;
        try{
            int isSlide = Settings.Secure.getInt(mLockScreenService.getContentResolver(), DXLockScreenUtils.SLIDE_ENABLE, 1);
            if (isSlide == 1){                  //slide enable.
                mIsDianXinLockScreenDisabled = false;
            }
            else                               //slide disable.
                mIsDianXinLockScreenDisabled = true;
        }
        catch (Exception e){
        }
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "notifyLockPasswordChanged: isICSLockScreenDisabled:"
                    + isICSLockScreenDisabled
                    + ";mIsDianXinLockScreenDisabled:" + mIsDianXinLockScreenDisabled);
        }
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_LOCKPASSWORD_CHANGED));
    }

    public void notifyClearSystemLock() {
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_CLEAR_SYSTEM_LOCK));
    }

    public void notifyCallMessageUpdated(final int unreadType,
            final int unreadNum) {
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "in notifyCallMessageUpdated(), isDXLockScreenLocked():"
                    + mDXLockScreenViewManager.isLockScreenLocked());
        }
        if (mDXLockScreenViewManager.isLockScreenLocked()) {
            Message msg = new Message();
            msg.what = DXLockScreenUtils.MSG_CALLORMESSAGE_UPDATED;
            msg.arg1 = unreadType;
            msg.arg2 = unreadNum;
            mHandler.sendMessage(msg);
        }
    }

    public void notifyUnlockNormal(int unlockToApp) {
        mHandler.sendMessage(mHandler.obtainMessage(
                DXLockScreenUtils.MSG_UNLOCK_NORMAL, unlockToApp, -1));
    }

    public void notifyTimeDateUpdated() {
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_TIMEDATE_UPDATED));
    }

    public void notifySPNUpdated(CharSequence plmn, CharSequence spn) {
        mPlmn = plmn;
        mSpn = spn;
        mHandler.sendMessage(mHandler
                .obtainMessage(DXLockScreenUtils.MSG_SPN_UPDATED));
    }

    public void notifySimStateChanged(Intent intent) {
        mHandler.sendMessage(mHandler.obtainMessage(
                DXLockScreenUtils.MSG_SIM_STATE_CHANGNED, intent));
    }

    public void notifyBatteryChanged(Intent intent) {
        mHandler.sendMessage(mHandler.obtainMessage(
                DXLockScreenUtils.MSG_BATTERY_CHANGNED, intent));
    }

    public boolean isStartedUnlock() {
        return mDXLockScreenViewManager.checkHasStartedUnlock();
    }

    public void restoreCMLockScreenDefaultValue() {
        mDXOperatingCMLockScreen.resoreDefaultCMValue();
    }

    public void notifyScreenLock() {
        mHandler.sendEmptyMessage(DXLockScreenUtils.MSG_SCREEN_LOCK);
    }

    public void notifyStatusbarNotificationClicked() {
        mHandler.sendMessage(mHandler.obtainMessage(
                DXLockScreenUtils.MSG_STATUSBAR_NOTIFICATION_CLICKED));
    }

    private boolean isDisabledDianXinLockScreen() {
        try {
            if (DXLockScreenUtils.isICSSDKVersion()
                    && ((classM.LockPattern!=null && classM.LockPattern_IsSecure()) || mIsDianXinLockScreenDisabled)) {
                return true;
            } else {
                // On CM2.3.7, We will disable DianXin LockScreen when the system is
                // secure.
                if (classM.LockPattern!=null && classM.LockPattern_IsSecure()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
