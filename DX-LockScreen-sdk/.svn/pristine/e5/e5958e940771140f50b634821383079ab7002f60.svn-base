package com.dianxinos.lockscreen_sdk.monitor;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class DXCallMessageObserver extends ContentObserver {

    private static final String TAG = "CallMessageObserver";

    private DXLockScreenMediator mDXLockScreenMediator;
    private Uri[] mUri;
    private String[] mProjection;
    private String[] mSelection;
    private ContentResolver mContentResolver;
    private int mUnreadType;

    public DXCallMessageObserver(DXLockScreenMediator mediator, int type,
            ContentResolver resolver, Uri[] uri, String[] projection,
            String[] selection) {
        super(new Handler());
        mDXLockScreenMediator = mediator;
        mUnreadType = type;
        mContentResolver = resolver;
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        onChange(false);
    }

    @Override
    public void onChange(boolean selfChange) {
//        queryDBAndUpdateView();
    }

    protected void queryDBAndUpdateView() {
        new Thread() {
            @Override
            public void run() {
                int unreadNum = 0;
                int relatedUriCount = mUri.length;
                if (DXLockScreenUtils.DBG) {
                    Log.d(TAG, "query message database started");
                }
                for (int i = 0; i < relatedUriCount; i++) {
                    Cursor cursor = mContentResolver.query(mUri[i],
                            new String[] { mProjection[i] }, mSelection[i],
                            null, null);
                    if (cursor != null) {
                        unreadNum += cursor.getCount();
                        cursor.close();
                    }
                }
                if (DXLockScreenUtils.DBG) {
                    Log.d(TAG, "query message database ended, mUnreadType:" + mUnreadType + ";unreadNum:" + unreadNum);
                }

                mDXLockScreenMediator.notifyCallMessageUpdated(mUnreadType,
                        unreadNum);
            }
        }.start();
    }
}
