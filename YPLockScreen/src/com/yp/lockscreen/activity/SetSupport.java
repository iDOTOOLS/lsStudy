package com.yp.lockscreen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;

public class SetSupport extends Activity{

	private LinearLayout backLy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_support_activity);
		backLy = (LinearLayout)findViewById(R.id.set_support_activity_back_ly);
		backLy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SetSupport.this.finish();
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
