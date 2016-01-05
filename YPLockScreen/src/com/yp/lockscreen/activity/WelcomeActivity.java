package com.yp.lockscreen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.work.WelcomeAdapter;

public class WelcomeActivity extends FragmentActivity {

	private ViewPager vp;
	private RadioGroup radGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	void initView() {
		vp = (ViewPager) findViewById(R.id.activity_welcome_viewpager);
		vp.setAdapter(new WelcomeAdapter(getSupportFragmentManager()));
		radGroup = (RadioGroup) findViewById(R.id.welcome_activity_rdg);
		radGroup.check(R.id.welcome_activity_rdg_1);
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					radGroup.check(R.id.welcome_activity_rdg_1);
					break;
				case 1:
					radGroup.check(R.id.welcome_activity_rdg_2);
					break;
				case 2:
					radGroup.check(R.id.welcome_activity_rdg_3);
					break;
				case 3:
					radGroup.check(R.id.welcome_activity_rdg_4);
					break;
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
