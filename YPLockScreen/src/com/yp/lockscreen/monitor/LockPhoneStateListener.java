package com.yp.lockscreen.monitor;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.yp.lockscreen.utils.LogHelper;

public class LockPhoneStateListener extends PhoneStateListener {
    private static final String TAG = "DXPhoneStateListener";
    private Context mContext;
    private DXLockScreenMediator mDXLockScreenMediator;
    private TelephonyManager mTelephonyManager;
    public static boolean sIsRinging = false;

	public LockPhoneStateListener(Context context, DXLockScreenMediator mediator) {
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
        case TelephonyManager.CALL_STATE_IDLE://挂电话
           LogHelper.i(TAG,
                    "current state is idle, hang up or end call. notify DXLockScreenMediator");
            sIsRinging = false;
            if (DXLockScreenUtils.isICSSDKVersion()) {
                mDXLockScreenMediator.notifyCallHangupOrRefuse();
            }
            break;
            
        case TelephonyManager.CALL_STATE_OFFHOOK://接电话
        	 LogHelper.i(TAG, "current state is offhook, answer");
            sIsRinging = true;
            break;
            
        case TelephonyManager.CALL_STATE_RINGING://响铃
        	 LogHelper.i(TAG, "current state is ringing");
            // will send message to service to remove lockscreen view.
        	 LogHelper.i(TAG, "send PHONE_STATE_CHANGED_RINGING to handler");
            sIsRinging = true;
            mDXLockScreenMediator.notifyCallRinging();
            break;
            
        default:
            break;
        }
    }

    /**
     * 注册电话监听器，监听call的各种状态
     */
    public void registerListener() {
        mTelephonyManager.listen(this,
                PhoneStateListener.LISTEN_CALL_STATE);
    }
    
    /**
     * 解除call的事件监听
     */
    public void unregisterListener(){
        mTelephonyManager.listen(this,
                PhoneStateListener.LISTEN_NONE);
    }
}
