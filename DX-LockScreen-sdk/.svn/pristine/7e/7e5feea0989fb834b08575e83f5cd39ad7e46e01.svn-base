
package com.dianxinos.lockscreen_sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

//import com.android.internal.widget.LockPatternUtils;

public class DXOperatingCMLockScreen {
    private static final String TAG = "OperatingCMLockScreen";

    private Context mContext;
    private ContentResolver mContentResolver;
    private boolean mStartFirstTime = true;

    public boolean ismStartFirstTime() {
        return mStartFirstTime;
    }

    public void setmStartFirstTime(boolean mStartFirstTime) {
        this.mStartFirstTime = mStartFirstTime;
    }

    private boolean mLockPatternChanged = false;
    private boolean mLockIsSeure = false;
    private String mOldPasswordTypeStr = DXLockScreenUtils.PASSWORDTYPE_NONESTR;

    public DXOperatingCMLockScreen(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
        try {
            classM.LockPatternNewInstance(mContext);
//            mLockPatternUtils = new LockPatternUtils(mContext);
        } catch (Exception e) {
        }
    }

    ClassManager classM = new ClassManager();
    public void checkCMLockscreenState(String passwordTypeStr) {
        boolean isICSSlideChanged = false;
        if (DXLockScreenUtils.isICSSDKVersion()) {
            long ics_lockscreen_disabled_val =
                    Settings.Secure.getLong(mContentResolver,
                            DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY, -1);
            if (DXLockScreenUtils.ICS_LOCKSCREEN_DISABLED_DEFVAL != ics_lockscreen_disabled_val) {
                Log.d(TAG,
                        "in checkCMLockscreenState()-->the user has changed to slide unlock lockscreen.");
                isICSSlideChanged = true;
            }
        }

        if (!mOldPasswordTypeStr.equals(passwordTypeStr) ||
                isICSSlideChanged) {
            // if (!mOldPasswordTypeStr.equals(passwordTypeStr)) {
            mOldPasswordTypeStr = passwordTypeStr;
            mLockPatternChanged = true;
        }
        if (passwordTypeStr
                .equals(DXLockScreenUtils.PASSWORDTYPE_NONESTR)) {
            mLockIsSeure = false;
        } else {
            mLockIsSeure = true;
        }
        int isCMLockScreenDisabled = getSystemSettingValue(
                DXLockScreenUtils.LOCKSCREEN_DISABLED,
                DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN);
        int isCMLockscreenDisabledOnSecurity = getSystemSettingValue(
                DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN);

        if (DXLockScreenUtils.DBG) {
            Log.d(TAG, "Settings.System.LOCKSCREEN_DISABLED value:"
                    + isCMLockScreenDisabled);
            Log.d(TAG,
                    "Settings.System.LOCKSCREEN_DISABLE_ON_SECURITY value:"
                            + isCMLockscreenDisabledOnSecurity);
            Log.d(TAG, "mLockPatternChanged:" + mLockPatternChanged
                    + ";mLockIsSecure:" + mLockIsSeure
                    + ";mStartFirstTime:" + mStartFirstTime);
        }
        
        if ((mStartFirstTime && (classM.LockPattern != null && !classM.LockPattern_IsSecure()))
                || (!mStartFirstTime && mLockPatternChanged && !mLockIsSeure)) {
            if (DXLockScreenUtils.isICSSDKVersion()) {
                Log.d(TAG, "disabled ICS system lockscreen.");
//                setICSLockscreenDisabled();
            } else {
                if (DXLockScreenUtils.CMLOCKSCREEN_DISABLED != isCMLockScreenDisabled) {
                    // disable cm lockscreen
                    setSystemSettingValue(
                            DXLockScreenUtils.LOCKSCREEN_DISABLED,
                            DXLockScreenUtils.CMLOCKSCREEN_DISABLED);
                }
            }
        } else if (!mStartFirstTime && !mLockPatternChanged
                && !mLockIsSeure) {
            Log.d(TAG, "do nothing");
        } else {
            // user set the password, we do the following things
            // 1.enable cm lock logic handle by set
            // LockScreenUtils.LOCKSCREEN_DISABLED as 0
            // 2.discable cm lock display by set
            // LockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY as 1.
            if (DXLockScreenUtils.CMLOCKSCREEN_DISABLED != isCMLockscreenDisabledOnSecurity) {
                setSystemSettingValue(
                        DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                        DXLockScreenUtils.CMLOCKSCREENONSECURITY_DISABLED);
            }
            if (DXLockScreenUtils.CMLOCKSCREEN_DISABLED == isCMLockScreenDisabled) {
                setSystemSettingValue(
                        DXLockScreenUtils.LOCKSCREEN_DISABLED,
                        DXLockScreenUtils.CMLOCKSCREEN_ENABLED);
            }
        }
        if (DXLockScreenUtils.DBG) {
            Log.d(TAG,
                    "Settings.System.LOCKSCREEN_DISABLED value:"
                            + getSystemSettingValue(
                                    DXLockScreenUtils.LOCKSCREEN_DISABLED,
                                    DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN));
            Log.d(TAG,
                    "Settings.System.LOCKSCREEN_DISABLE_ON_SECURITY value:"
                            + getSystemSettingValue(
                                    DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                                    DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN));
        }
        // when we finish the checking. reset mLockPatternChanged as
        // false
        mLockPatternChanged = false;
        isICSSlideChanged = false;
        if (mStartFirstTime) {
            setmStartFirstTime(false);
        }
    }

    public void checkLockScreenDisableOnSecurity() {
        if (classM.LockPattern != null && classM.LockPattern_IsSecure()) {
            int isCMLockscreenDisabledOnSecurity = getSystemSettingValue(
                    DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                    DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN);
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG,
                        "checkLockscreenDisableOnSecurity()-->isCMLockscreenDisabledOnSecurity"
                                + isCMLockscreenDisabledOnSecurity);
            }
            if (isCMLockscreenDisabledOnSecurity != DXLockScreenUtils.CMLOCKSCREEN_DISABLED) {
                setSystemSettingValue(
                        DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                        DXLockScreenUtils.CMLOCKSCREENONSECURITY_DISABLED);
            }
        } else {
            if (DXLockScreenUtils.isICSSDKVersion()) {
                long isICSLockscreenDisabled =
                        Settings.Secure.getLong(mContentResolver,
                                DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY, -1);
                if (DXLockScreenUtils.CMLOCKSCREEN_DISABLED != isICSLockscreenDisabled) {
                    Log.d(TAG,
                            "in checkLockScreenDisableOnSecurity()-->disabled ICS system lockscreen.");
//                    setICSLockscreenDisabled();
                }
            } else {
                int isCMLockscreenDisabled = getSystemSettingValue(
                        DXLockScreenUtils.LOCKSCREEN_DISABLED,
                        DXLockScreenUtils.CMLOCKSCREEN_UNKNOWN);
                if (DXLockScreenUtils.DBG) {
                    Log.d(TAG,
                            "checkLockscreenDisableOnSecurity()-->isCMLockscreenDisabled"
                                    + isCMLockscreenDisabled);
                }
                if (isCMLockscreenDisabled != DXLockScreenUtils.CMLOCKSCREEN_DISABLED) {
                    setSystemSettingValue(DXLockScreenUtils.LOCKSCREEN_DISABLED,
                            DXLockScreenUtils.CMLOCKSCREEN_DISABLED);
                }
            }
        }
    }

    public void initDefaultCMValue() {
        if (DXLockScreenUtils.isICSSDKVersion() &&
                !classM.LockPattern_IsSecure()) {
//            setICSLockscreenDisabled();
        } else {
            setSystemSettingValue(DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                    DXLockScreenUtils.CMLOCKSCREEN_DISABLED);
            setSystemSettingValue(DXLockScreenUtils.LOCKSCREEN_DISABLED,
                    DXLockScreenUtils.CMLOCKSCREEN_DISABLED);
        }
    }

//    public void setICSLockscreenDisabled() {
//        Settings.Secure.putLong(mContentResolver, DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY,
//                DXLockScreenUtils.CMLOCKSCREEN_DISABLED);
//    }

    public void resoreDefaultCMValue() {
        setSystemSettingValue(DXLockScreenUtils.LOCKSCREEN_DISABLE_ON_SECURITY,
                DXLockScreenUtils.CMLOCKSCREEN_ENABLED);
        setSystemSettingValue(DXLockScreenUtils.LOCKSCREEN_DISABLED,
                DXLockScreenUtils.CMLOCKSCREEN_ENABLED);
    }

    private void setSystemSettingValue(final String key, final int value) {
        new Thread() {
            @Override
            public void run() {
                Settings.System.putInt(mContentResolver, key, value);
            }
        }.start();
    }

    private int getSystemSettingValue(String key, int defaultValue) {
        return Settings.System.getInt(mContentResolver, key, defaultValue);
    }

}
