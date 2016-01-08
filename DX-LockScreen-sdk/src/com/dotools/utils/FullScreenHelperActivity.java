
package com.dotools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FullScreenHelperActivity extends Activity {
    public static final String TAG ="FullScreenHelperActivity";
    public static FullScreenHelperActivity sActivity;
    public static boolean sFlagCreate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sFlagCreate) {
            View view = new View(this);
            view.setVisibility(View.INVISIBLE);
            setContentView(view);
            sActivity = this;
        } else {
            finish();
        }
        DTLockScreenUtils.disableKeyguard(this);
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
    protected void onDestroy() {
        super.onDestroy();
        sActivity = null;
    }

    public static void doStartActivity(final Context ct){
        sFlagCreate = true;
        Intent intent = new Intent(ct, FullScreenHelperActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ct.startActivity(intent);
    }
    
    public static void doFinishActivity(){
        sFlagCreate = false;
        if (sActivity != null) {
            sActivity.finish();
            sActivity = null;
        }
    }

}
