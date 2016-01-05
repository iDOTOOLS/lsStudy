package com.yp.lockscreen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.lockscreen.R;
import com.yp.lockscreen.utils.MarketUtils;

public class MoreSetActivity extends Activity implements OnClickListener {

    private LinearLayout backLy;

    private ToggleButton wordLockbtn;

    private RelativeLayout shareIDLy;

    private RelativeLayout abortLy;

    private MoreSetActivity mCxt;

    private RelativeLayout goodRy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_more_activity);
        mCxt = this;
        initViews();
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

    void initViews() {
        backLy = (LinearLayout) findViewById(R.id.set_more_activity_back_ly);
        backLy.setOnClickListener(this);
        wordLockbtn = (ToggleButton) findViewById(R.id.set_more_activity_toggle);
        wordLockbtn.setChecked(ConfigManager.isUseLockScreen(this));
        shareIDLy = (RelativeLayout) findViewById(R.id.set_more_activity_share_id_ry);
        shareIDLy.setOnClickListener(this);
        abortLy = (RelativeLayout) findViewById(R.id.set_more_activity_about_ry);
        abortLy.setOnClickListener(this);
        goodRy = (RelativeLayout) findViewById(R.id.abort_activity_good_ry);
        goodRy.setOnClickListener(this);
        wordLockbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigManager.setLockSwitch(MoreSetActivity.this, isChecked);
                DXLockScreenUtils.openOrCloseLockScreen(MoreSetActivity.this, MoreSetActivity.this.getPackageName(), isChecked);
                if(!isChecked){
                    MobclickAgent.onEvent(mCxt, "lockscreen_close");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.set_more_activity_back_ly:
            finishActivity();

            break;
        case R.id.abort_activity_good_ry:
            MarketUtils.appRank(MoreSetActivity.this);
            break;
        case R.id.set_more_activity_share_id_ry:

            Intent shareIntent = new Intent(MoreSetActivity.this, SetShareIdActivity.class);
            MoreSetActivity.this.startActivity(shareIntent);

            break;
        case R.id.set_more_activity_about_ry:
            Intent abortIntent = new Intent(MoreSetActivity.this, AbortActivity.class);
            MoreSetActivity.this.startActivity(abortIntent);

            break;
        }
    }

    private void inflaterToast(Context cxt, String str) {
        // View view =
        // LayoutInflater.from(cxt).inflate(R.layout.toast_view,null);
        // TextView text = (TextView)view.findViewById(R.id.toast_view_text);
        // text.setText(str);
        // Toast updataToast = new Toast(cxt);
        // updataToast.setGravity(Gravity.CENTER, 0, 0);
        // updataToast.setDuration(Toast.LENGTH_SHORT);
        // updataToast.setView(view);
        // updataToast.show();
        Toast.makeText(cxt, str, Toast.LENGTH_LONG).show();
    }

    private void finishActivity() {
        this.finish();
    }
}
