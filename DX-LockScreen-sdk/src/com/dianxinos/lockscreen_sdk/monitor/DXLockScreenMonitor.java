package com.dianxinos.lockscreen_sdk.monitor;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.Intents;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.ClassManager;
import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.ResourceManager;

public class DXLockScreenMonitor {
    private static final String TAG = "DXLockScreenMonitor";

    private Context mContext;
    private TelephonyManager mTelephonyManager;
    private DXLockScreenMediator mDXLockScreenMediator;
    public DXCallMessageObserver mPhoneObserver;
    public DXCallMessageObserver mMessageObserver;

    public DXLockScreenMonitor(Context context, DXLockScreenMediator mediator) {
        mContext = context;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mDXLockScreenMediator = mediator;
    }

    public void unregisterReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String strAction = intent.getAction();
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "received:" + strAction);
            }
            if (strAction == null) {
                if (DXLockScreenUtils.DBG)
                    Log.e(TAG, "there are something wrong in receiver.");
                return;
            }

            if (mDXLockScreenMediator.isStartedUnlock()) {
                if (DXLockScreenUtils.DBG)
                    Log.d(TAG, "user started to unlock not report this change:" + strAction);
                return;
            }

            // if (Intent.ACTION_TIME_TICK.equals(strAction)
            // || Intent.ACTION_TIME_CHANGED.equals(strAction)
            // || Intent.ACTION_TIMEZONE_CHANGED.equals(strAction)) {
            // mDXLockScreenMediator.notifyTimeDateUpdated();
            // } else if
            // (TelephonyIntents.Intents.SPN_STRINGS_UPDATED_ACTION.equals(strAction))
            // {
            // CharSequence plmn = getTelephonyPlmnFrom(intent);
            // CharSequence spn = getTelephonySpnFrom(intent);
            // mDXLockScreenMediator.notifySPNUpdated(plmn, spn);
            // } else if (TelephonyIntents.ACTION_SIM_STATE_CHANGED
            // .equals(strAction)) {
            // mDXLockScreenMediator.notifySimStateChanged(intent);
            // } else if (Intent.ACTION_BATTERY_CHANGED.equals(strAction)) {
            // mDXLockScreenMediator.notifyBatteryChanged(intent);
            // } else {
            // final int count = DXLockScreenUtils.ACTION_ALARM_ON_ALL.length;
            // for (int i = 0; i < count; i++) {
            // if (DXLockScreenUtils.ACTION_ALARM_ON_ALL[i].equals(strAction)) {
            // if (DXLockScreenUtils.DBG)
            // Log.d(TAG, "received ALARM_ALERT,strAction=" + strAction);
            // mDXLockScreenMediator.notifyAlarmOn();
            // break;
            // }
            // }
            // }

            if (("android.intent.action.TIME_TICK".equals(strAction)) || ("android.intent.action.TIME_SET".equals(strAction)) || ("android.intent.action.TIMEZONE_CHANGED".equals(strAction))) {
                DXLockScreenMonitor.this.mDXLockScreenMediator.notifyTimeDateUpdated();
            } else if ("android.provider.Telephony.SPN_STRINGS_UPDATED".equals(strAction)) {
                CharSequence plmn = DXLockScreenMonitor.this.getTelephonyPlmnFrom(intent);
                CharSequence spn = DXLockScreenMonitor.this.getTelephonySpnFrom(intent);
                DXLockScreenMonitor.this.mDXLockScreenMediator.notifySPNUpdated(plmn, spn);
            } else if ("android.intent.action.SIM_STATE_CHANGED".equals(strAction)) {
                DXLockScreenMonitor.this.mDXLockScreenMediator.notifySimStateChanged(intent);
            } else if ("android.intent.action.BATTERY_CHANGED".equals(strAction)) {
                DXLockScreenMonitor.this.mDXLockScreenMediator.notifyBatteryChanged(intent);
            } else {
                int count = DXLockScreenUtils.ACTION_ALARM_ON_ALL.length;
                for (int i = 0; i < count; ++i)
                    if (DXLockScreenUtils.ACTION_ALARM_ON_ALL[i].equals(strAction)) {
                        if (DXLockScreenUtils.DBG)
                            Log.d("DXLockScreenMonitor", "received ALARM_ALERT,strAction=" + strAction);
                        DXLockScreenMonitor.this.mDXLockScreenMediator.notifyAlarmOn();
                        return;
                    }
            }
        }
    };

    private CharSequence getTelephonySpnFrom(Intent intent) {
        // if (intent.getBooleanExtra(Intents.EXTRA_SHOW_SPN, false)) {
        // final String spn = intent.getStringExtra(Intents.EXTRA_SPN);
        // if (spn != null) {
        // return spn;
        // }
        // }
        // return null;
        if (intent.getBooleanExtra("showSpn", false)) {
            String spn = intent.getStringExtra("spn");
            if (spn != null) {
                return spn;
            }
        }
        return null;
    }

    private CharSequence getTelephonyPlmnFrom(Intent intent) {
        // if (intent.getBooleanExtra(Intents.EXTRA_SHOW_PLMN, false)) {
        // final String plmn = intent.getStringExtra(Intents.EXTRA_PLMN);
        // if (plmn != null) {
        // return plmn;
        // } else {
        // return
        // mContext.getResources().getText(ResourceManager.sResourceManager.getDefaultPlmnMsgId());
        // }
        // }
        // return null;
        if (intent.getBooleanExtra("showPlmn", false)) {
            String plmn = intent.getStringExtra("plmn");
            if (plmn != null) {
                return plmn;
            }
            return this.mContext.getResources().getText(ResourceManager.sResourceManager.getDefaultPlmnMsgId());
        }

        return null;
    }

    /**
     * Registers various content observers. The current implementation registers
     * only a favorites observer to keep track of the favorites applications.
     */
    // public void registerCallMessageObservers() {
    // if (DXLockScreenUtils.DBG)
    // Log.d(TAG, "registerContentObservers()");
    //
    // ContentResolver resolver = mContext.getContentResolver();
    //
    // if (mMessageObserver == null) {
    // mMessageObserver = new DXCallMessageObserver(mDXLockScreenMediator,
    // DXLockScreenUtils.TYPE_UNREAD_MESSAGE, resolver, new Uri[] {
    // Sms.CONTENT_URI, Mms.CONTENT_URI }, new String[] {
    // Sms.DATE, Mms.DATE }, new String[] {
    // "(" + Sms.TYPE + " = " + Sms.MESSAGE_TYPE_INBOX
    // + " AND " + Sms.READ + " = 0)",
    // "(" + Mms.MESSAGE_BOX + "=" + Mms.MESSAGE_BOX_INBOX
    // + " AND " + Mms.READ + "=0" + " AND ("
    // + Mms.MESSAGE_TYPE + "=" + /*
    // * PduHeaders.
    // * MESSAGE_TYPE_NOTIFICATION_IND
    // */130 + " OR "
    // + Mms.MESSAGE_TYPE + "=" + /*
    // * PduHeaders.
    // * MESSAGE_TYPE_RETRIEVE_CONF
    // */132 + "))" });
    // resolver.registerContentObserver(MmsSms.CONTENT_URI, true,
    // mMessageObserver);
    // }
    //
    // if (mPhoneObserver == null) {
    // mPhoneObserver = new DXCallMessageObserver(mDXLockScreenMediator,
    // DXLockScreenUtils.TYPE_MISSING_CALL, resolver,
    // new Uri[] { Calls.CONTENT_URI },
    // new String[] { Calls.DATE }, new String[] { "type="
    // + Calls.MISSED_TYPE + " AND new=1" });
    // resolver.registerContentObserver(Calls.CONTENT_URI, true,
    // mPhoneObserver);
    // }
    // }

    ClassManager mClassM = new ClassManager();
    public void registerCallMessageObservers() {
        if (DXLockScreenUtils.DBG) {
            Log.d("DXLockScreenMonitor", "registerContentObservers()");
        }
        ContentResolver resolver = this.mContext.getContentResolver();

        if (this.mMessageObserver == null) {
            this.mMessageObserver = new DXCallMessageObserver(this.mDXLockScreenMediator, 2, resolver, new Uri[] { mClassM.getTelephonySmSUri(), mClassM.getTelephonyMmsUri() }, new String[] { "date",
                    "date" }, new String[] { "(type = 1 AND read = 0)", "(msg_box=1 AND read=0 AND (m_type=130 OR m_type=132))" });

            resolver.registerContentObserver(mClassM.getTelePhonyMmsSmsUri(), true, this.mMessageObserver);
        }

        if (this.mPhoneObserver == null) {
            this.mPhoneObserver = new DXCallMessageObserver(this.mDXLockScreenMediator, 1, resolver, new Uri[] { mClassM.getCallLogCallUri() }, new String[] { "date" },
                    new String[] { "type=3 AND new=1" });

            resolver.registerContentObserver(mClassM.getCallLogCallUri(), true, this.mPhoneObserver);
        }
    }

    public void unregisterCallMessageObservers() {
        if (DXLockScreenUtils.DBG)
            Log.d(TAG, "unRegisterContentObservers()");

        ContentResolver resolver = mContext.getContentResolver();
        if (mMessageObserver != null) {
            resolver.unregisterContentObserver(mMessageObserver);
            mMessageObserver = null;
        }
        if (mPhoneObserver != null) {
            resolver.unregisterContentObserver(mPhoneObserver);
            mPhoneObserver = null;
        }
    }

}
