
package com.dotools.utils;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dotools.utils.DevicesUtils_vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FullScreenHelperActivity extends Activity {
    public static final String TAG ="FullScreenHelperActivity";
    public static FullScreenHelperActivity sActivity;
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
        
        if(DTLockScreenUtils.mStatusBarHeight == 0){
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doFullScreen(true);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return false;
    }

    private void doFullScreen(boolean flag) {
        Window window = getWindow();
        if (flag) {
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "-doFullScreen "+flag);
            }
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            window.setAttributes(attrs);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            if (Build.VERSION.SDK_INT >= 19) {
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            }
//            if(DevicesUtils_vk.hasVertualKey()) {
//                WindowManager.LayoutParams params = window.getAttributes();  
//                params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;  
//                window.setAttributes(params); 
//            }
        } else {
            if (DXLockScreenUtils.DBG) {
                Log.d(TAG, "-do quit FullScreen ");
            }
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(attrs);
            // 取消全屏设置
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            if (Build.VERSION.SDK_INT >= 19) {
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doFullScreen(false);
        sActivity = null;
    }

    public static void doLockScreen(final Context ct) {
        sFlagCreate = true;
        Intent intent = new Intent(ct, FullScreenHelperActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ct.startActivity(intent);
    }

    public static void doUnLockScreen(Context ct) {
        sFlagCreate = false;
        if (sActivity != null) {
            sActivity.doFullScreen(false);
            sActivity.finish();
            sActivity = null;
        }
    }

}
