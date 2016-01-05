package com.yp.lockscreen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.utils.RomUtils;

public class InitSettingActivity extends Activity {
    private ImageView img;
    private ImageView img_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.init_setting_activity);
        
        img = (ImageView)findViewById(R.id.img);
        img_auto = (ImageView)findViewById(R.id.img_auto);
        if(RomUtils.isMIUI() && RomUtils.getRom().equals(RomUtils.ROM_MIUI_V6)){
            img.setBackgroundResource(R.drawable.minu6_runreadme);
            img_auto.setBackgroundResource(R.drawable.minu6_readme2);
        }
        
        Button open = (Button)findViewById(R.id.open_btn);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RomUtils.isMIUI()){
                    RomUtils.openMiuiPermissionActivity(InitSettingActivity.this);
                }
            }
        });
        
        Button open_auto = (Button)findViewById(R.id.open_auto_btn);
        open_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RomUtils.isMIUI()){
                    RomUtils.openMiuiPermissionAutoBoot(InitSettingActivity.this);
                }
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
