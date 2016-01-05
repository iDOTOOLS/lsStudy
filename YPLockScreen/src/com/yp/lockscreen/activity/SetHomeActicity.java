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
import android.widget.ToggleButton;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.lockscreen.R;

public class SetHomeActicity extends Activity implements OnClickListener {

	private LinearLayout backly;
	// private RelativeLayout

	private RelativeLayout lockHomeRy;

	private RelativeLayout chooseHomeRy;

	private ToggleButton homeToggle;

	private Context mCxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_home_activity);
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
		backly = (LinearLayout) findViewById(R.id.set_home_activity_back_ly);
		backly.setOnClickListener(this);
		lockHomeRy = (RelativeLayout) findViewById(R.id.set_home_activity_unlock_home_ry);
		lockHomeRy.setOnClickListener(this);
		chooseHomeRy = (RelativeLayout) findViewById(R.id.set_home_activity_choose_home_ry);
		chooseHomeRy.setOnClickListener(this);

		homeToggle = (ToggleButton) findViewById(R.id.set_home_activity_unlock_home_toggle);
		homeToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				ConfigManager.setLockHome(mCxt, isChecked);
			}
		});
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		switch (id) {
		case R.id.set_home_activity_back_ly:
			finishActivity();
			break;
		case R.id.set_home_activity_choose_home_ry:
			Intent intent = new Intent("android.intent.category.HOME");
			startActivity(intent);
			break;
		case R.id.set_home_activity_unlock_home_ry:
			homeToggle.setChecked(!ConfigManager.isLockHome(this));
			break;
		}
	}
	
	void finishActivity(){
		this.finish();
	}

}
