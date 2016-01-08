
package com.dotools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

public class FullScreenHelperNoDisplayActivity extends Activity {
    private static final String TAG = "FullScreenHelperNoDisplayActivity";
    public static FullScreenHelperNoDisplayActivity sActivity;
    public static boolean sFlagCreate = true;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sFlagCreate) {
            doFullScreen(true);
            View view = new View(this);
            view.setVisibility(View.INVISIBLE);
            setContentView(view);
            sActivity = this;
        } else {
            finish();
        }
        
        DTLockScreenUtils.disableKeyguard(this);
        
        if (DTLockScreenUtils.mStatusBarHeight == 0) {
            DTLockScreenUtils.mStatusBarHeight = DTLockScreenUtils.getStatusHeight(this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!sFlagCreate) {
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doFullScreen(true);
    }

    private void doFullScreen(boolean flag) {
        if (flag) {
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "-doFullScreen ");
            }
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
           attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            if (Build.VERSION.SDK_INT >= 19) {
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            }
        } else {
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "-do quit FullScreen ");
            }
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            // 取消全屏设置
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sActivity = null;
    }

    public static void doLockScreen(final Context ct) {
        Intent intent = new Intent(ct, FullScreenHelperNoDisplayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ct.startActivity(intent);
    }

    public static void doUnLockScreen(Context ct) {
        if (sActivity != null) {
            sActivity.doFullScreen(false);
            sActivity.finish();
            sActivity = null;
        }
    }

}
