package com.yp.lockscreen.activity;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SetShareIdActivity extends Activity{
	private LinearLayout backLy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_shareid_activity);
		backLy = (LinearLayout)findViewById(R.id.set_shareid_activity_back_ly);
		backLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SetShareIdActivity.this.finish();
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
