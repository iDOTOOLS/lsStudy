package com.dianxinos.lockscreen_sdk.monitor;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

/**
 * listen the phone state change.
 */
public class DXPhoneStateListener extends PhoneStateListener {
    private static final String TAG = "DXPhoneStateListener";
    private Context mContext;
    private DXLockScreenMediator mDXLockScreenMediator;
    private TelephonyManager mTelephonyManager;
    public static boolean sIsRinging = false;

    public DXPhoneStateListener(Context context, DXLockScreenMediator mediator) {
        mContext = context;
        mDXLockScreenMediator = mediator;
        mTelephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    // when a calling is coming, we will receive CALL_STATE_RINGING
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            if (DXLockScreenUtils.DBG) Log.d(TAG,
                    "current state is idle, hang up or end call. notify DXLockScreenMediator");
            sIsRinging = false;
            if (!DXLockScreenUtils.isICSSDKVersion()) {
                mDXLockScreenMediator.notifyCallHangupOrRefuse();
            }
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            if (DXLockScreenUtils.DBG) Log.d(TAG, "current state is offhook, answer");
            sIsRinging = true;
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            if (DXLockScreenUtils.DBG) Log.d(TAG, "current state is ringing");
            // will send message to service to remove lockscreen view.
            if (DXLockScreenUtils.DBG) Log.d(TAG, "send PHONE_STATE_CHANGED_RINGING to handler");
            sIsRinging = true;
            mDXLockScreenMediator.notifyCallRinging();
            break;
        default:
            break;
        }
    }

    public void registerListener() {
        mTelephonyManager.listen(this,
                PhoneStateListener.LISTEN_CALL_STATE);
    }
    
    public void unregisterListener(){
        mTelephonyManager.listen(this,
                PhoneStateListener.LISTEN_NONE);
    }
}
