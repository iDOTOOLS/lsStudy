package com.dianxinos.lockscreen_sdk.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

public class DXScreenOnOffReceiver extends BroadcastReceiver {
    private static final String TAG = "DXScreenOnOffReceiver";
    private Context mContext;
    private TelephonyManager mTelephonyManager;
    private DXLockScreenMediator mDXLockScreenMediator;

    public DXScreenOnOffReceiver(Context context, DXLockScreenMediator mediator) {
        mContext = context;
        mTelephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        mDXLockScreenMediator = mediator;
    }

    public void onReceive(Context context, Intent intent) {
        String strAction = intent.getAction();
        if (strAction == null) {
            if (DXLockScreenUtils.DBG) Log.e(TAG, "there are something wrong in receiver.");
            return;
        }
        if (Intent.ACTION_SCREEN_OFF.equals(strAction)
                && TelephonyManager.CALL_STATE_IDLE == mTelephonyManager
                        .getCallState()) {
            mDXLockScreenMediator.notifyScreenOff();
        } else if(Intent.ACTION_SCREEN_ON.equals(strAction)
                && TelephonyManager.CALL_STATE_IDLE == mTelephonyManager
                .getCallState()){
            mDXLockScreenMediator.notifyScreenOn();
        } else if(DXLockScreenUtils.ACTION_STATUSBARNOTIFICATIONCLICKED.equals(strAction)) {
            mDXLockScreenMediator.notifyStatusbarNotificationClicked();
        }
    }

    public void registerScreenOffReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(DXLockScreenUtils.ACTION_STATUSBARNOTIFICATIONCLICKED);
        mContext.registerReceiver(this, filter);
    }

    public void unregisterScreenOffReceiver() {
        mContext.unregisterReceiver(this);
    }
}
