package com.yp.lockscreen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.utils.RomUtils;

public class RunReadMeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_readme_activity);
        
        TextView miui5 = (TextView)findViewById(R.id.miui5_txt);
        miui5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RomUtils.openMiui5PermissionActivity(RunReadMeActivity.this);
            }
        });
        
        TextView miui5_2 = (TextView)findViewById(R.id.miui5_txt2);
        miui5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RomUtils.isMIUI()){
                    RomUtils.openMiuiPermissionAutoBoot(RunReadMeActivity.this);
                }
            }
        });
        
        TextView miui6 = (TextView)findViewById(R.id.miui6_txt);
        miui6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RomUtils.openMiui6PermissionActivity(RunReadMeActivity.this);
            }
        });
        
        TextView miui6_2 = (TextView)findViewById(R.id.miui6_txt2);
        miui6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RomUtils.openMiui6SafeCenterActivity(RunReadMeActivity.this);
            }
        });
        
        LinearLayout backLayout = (LinearLayout)findViewById(R.id.back_layout);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
