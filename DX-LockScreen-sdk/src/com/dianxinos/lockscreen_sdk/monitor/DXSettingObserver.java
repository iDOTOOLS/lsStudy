
package com.dianxinos.lockscreen_sdk.monitor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

public class DXSettingObserver extends ContentObserver {
    private static final String TAG = "SettingObserver";

    private String passwordTypestr;
    private ContentResolver mContentResolver;
    private DXLockScreenMediator mDXLockScreenMediator;

    public DXSettingObserver(Context context, DXLockScreenMediator mediator) {
        super(new Handler());
        mDXLockScreenMediator = mediator;
        mContentResolver = context.getContentResolver();
        mContentResolver.registerContentObserver(Settings.Secure
                .getUriFor(DXLockScreenUtils.PASSWORDTYPE_COLUMN), false,
                this);
        mContentResolver.registerContentObserver(Settings.Secure
                .getUriFor(DXLockScreenUtils.PATTERN_AUTOLOCK_COLUMEN),
                false, this);
        mContentResolver.registerContentObserver(Settings.Secure
                .getUriFor(DXLockScreenUtils.SLIDE_ENABLE),
                false, this);
        if (DXLockScreenUtils.isICSSDKVersion()) {
            mContentResolver.registerContentObserver(Settings.Secure
                    .getUriFor(DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY), false, this);
        }
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        try {
            long passwordType = Settings.Secure.getLong(mContentResolver,
                    DXLockScreenUtils.PASSWORDTYPE_COLUMN);
            int patternAutolock = Settings.Secure.getInt(mContentResolver,
                    DXLockScreenUtils.PATTERN_AUTOLOCK_COLUMEN);
            int icsLockscreenDisabled = DXLockScreenUtils.ICS_ENABLED_DIANXIN_LOCKSCREEN;
            if (DXLockScreenUtils.isICSSDKVersion()) {
                icsLockscreenDisabled = Settings.Secure.getInt(mContentResolver,
                        DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY,
                        DXLockScreenUtils.ICS_DISABLED_DIANXIN_LOCKSCREEN);
            }
//            if (icsLockscreenDisabled == DXLockScreenUtils.ICS_ENABLED_DIANXIN_LOCKSCREEN){
//                Settings.Secure.Int(mContentResolver,
//                        DXLockScreenUtils.DISABLE_ICSLOCKSCREEN_KEY,
//                        DXLockScreenUtils.ICS_DISABLED_DIANXIN_LOCKSCREEN);
//            }

            if (passwordType == DXLockScreenUtils.PASSWORD_PATTERN) {
                if (DXLockScreenUtils.PASSWORD_IS_PATTERN == patternAutolock) {
                    passwordTypestr = DXLockScreenUtils.PASSWORDTYPE_PATTERNSTR;
                } else {
                    passwordTypestr = DXLockScreenUtils.PASSWORDTYPE_NONESTR;
                }
            } else if (DXLockScreenUtils.PASSWORD_PIN == passwordType) {
                passwordTypestr = DXLockScreenUtils.PASSWORDTYPE_PINSTR;
            } else if (DXLockScreenUtils.PASSWORD_NORMAL == passwordType) {
                passwordTypestr = DXLockScreenUtils.PASSWORDTYPE_NORMALSTR;
            }

            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "your passowrd type is:" + passwordTypestr + ";icsLockscreenDisabled:"
                        + icsLockscreenDisabled);
            }
            mDXLockScreenMediator
                    .notifyLockPasswordChanged(passwordTypestr, icsLockscreenDisabled);
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}
