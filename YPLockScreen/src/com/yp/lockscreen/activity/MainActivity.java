package com.yp.lockscreen.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.lockscreen.R;
import com.yp.lockscreen.StudyManager;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.work.VPFragmentAdapter;

public class MainActivity extends FragmentActivity implements OnClickListener {

    private LinearLayout reviewLayout;
    private TextView reviewText;
    private ImageView reviewImg;

    private LinearLayout recordLayout;
    private TextView recordText;
    private ImageView recordImg;

    private LinearLayout wordLayout;
    private TextView wordText;
    private ImageView wordImg;

    private LinearLayout settingLayout;
    private TextView settingText;
    private ImageView settingImg;
    private StudyManager mStudyManager;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudyManager = new StudyManager();
        initWordData();
        startLockService(); // 初始化服务
        setContentView(R.layout.activity_main);
        MIUIAlertMsg();
        initViews();// 初始化Views
    }

    public void initWordData() {
        Global.gWordData.initCurCikuName();
        if (Global.gWordData.addEveryDayFirstUseRecord()) { // 如果做为新的一天 添加过数据
                                                            // 则返回true
            mStudyManager.initDayListData(this);
            mStudyManager.clearNoRememberWord(this); // 初始化新一天数据时清除之前 开始复习
                                                     // 当中未记住的缓存数据
        }
        mStudyManager.initDayListForWord();
    }

    private void MIUIAlertMsg() {
        if (Build.MANUFACTURER.equals("Xiaomi") && !LockConfigMgr.getMiuiAdapterActivity(Global.gContext)) {
            startActivity(new Intent(this, InitSettingActivity.class));
            LockConfigMgr.setMiuiAdapterActivity(Global.gContext, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (LockConfigMgr.getIsFirstUse(this)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
        if (LockConfigMgr.getIsFirstSetLock(this) && !LockConfigMgr.getIsFirstSet(this)) {
            mVp.setCurrentItem(3);
        }
        if (mVpAdapter != null) {
            mVpAdapter.notifyFragmentsChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private VPFragmentAdapter mVpAdapter;

    void initViews() {
        reviewLayout = (LinearLayout) findViewById(R.id.bottom_indicator_review);
        reviewText = (TextView) findViewById(R.id.indicator_review_text);
        reviewImg = (ImageView) findViewById(R.id.indicator_review_img);
        reviewLayout.setOnClickListener(this);

        recordLayout = (LinearLayout) findViewById(R.id.bottom_indicator_record);
        recordText = (TextView) findViewById(R.id.indicator_record_text);
        recordImg = (ImageView) findViewById(R.id.indicator_record_img);
        recordLayout.setOnClickListener(this);

        wordLayout = (LinearLayout) findViewById(R.id.bottom_indicator_word);
        wordText = (TextView) findViewById(R.id.indicator_word_text);
        wordImg = (ImageView) findViewById(R.id.indicator_word_img);
        wordLayout.setOnClickListener(this);

        settingLayout = (LinearLayout) findViewById(R.id.bottom_indicator_setting);
        settingText = (TextView) findViewById(R.id.indicator_setting_text);
        settingImg = (ImageView) findViewById(R.id.indicator_setting_img);
        settingLayout.setOnClickListener(this);

        mVp = (ViewPager) findViewById(R.id.word_main_vp);
        mVp.setOnPageChangeListener(new VpchangListener());
        mVpAdapter = new VPFragmentAdapter(getSupportFragmentManager());
        mVp.setAdapter(mVpAdapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
        case R.id.bottom_indicator_review:
            mVp.setCurrentItem(0);
            resetBottom();
            bottomChange(0);
            break;
        case R.id.bottom_indicator_record:
            mVp.setCurrentItem(1);
            resetBottom();
            bottomChange(1);
            break;

        case R.id.bottom_indicator_word:
            mVp.setCurrentItem(2);
            resetBottom();
            bottomChange(2);
            break;
        case R.id.bottom_indicator_setting:
            mVp.setCurrentItem(3);
            resetBottom();
            bottomChange(3);
            break;
        }
    }

    /**
     * 重新恢复normal状态
     */
    private void resetBottom() {
        reviewImg.setImageResource(R.drawable.review_norma);
        reviewText.setTextColor(0xff888888);

        recordImg.setImageResource(R.drawable.record_normal);
        recordText.setTextColor(0xff888888);

        wordImg.setImageResource(R.drawable.word_norma);
        wordText.setTextColor(0xff888888);

        settingImg.setImageResource(R.drawable.setting_normal);
        settingText.setTextColor(0xff888888);
    }

    /**
     * 根据给定的id，改变选中底部状态
     * 
     * @param id
     */
    private void bottomChange(int id) {

        resetBottom();
        switch (id) {
        case 0:
            // MobclickAgent.onEvent(this, "weatherView");
            reviewImg.setImageResource(R.drawable.review_pressed);
            reviewText.setTextColor(0xff0a8be4);
            break;
        case 1:
            // MobclickAgent.onEvent(this, "seniorityView");
            recordImg.setImageResource(R.drawable.record_pressed);
            recordText.setTextColor(0xff0a8be4);
            break;
        case 2:
            // MobclickAgent.onEvent(this, "cityView");
            wordImg.setImageResource(R.drawable.word_pressed);
            wordText.setTextColor(0xff0a8be4);
            break;
        case 3:
            // MobclickAgent.onEvent(this, "toolView");
            settingImg.setImageResource(R.drawable.setting_pressed);
            settingText.setTextColor(0xff0a8be4);
            break;
        }
    }

    class VpchangListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            resetBottom();
            bottomChange(arg0);
        }
    }

    void startLockService() {
        DXLockScreenUtils.openOrCloseLockScreen(this, this.getPackageName(), ConfigManager.isUseLockScreen(this));
    }

}
