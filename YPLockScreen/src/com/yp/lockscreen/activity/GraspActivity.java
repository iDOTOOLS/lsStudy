package com.yp.lockscreen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.work.WordBookListAdapter;

public class GraspActivity extends Activity {

	private Button backBtn;

	private ListView mListView;
	
	private LinearLayout backLy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grasp_word_activity);
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
	void initView() {

		backBtn = (Button) findViewById(R.id.grasp_word_back_btn);
		backLy = (LinearLayout)findViewById(R.id.grasp_word_activity_back_ly);
		backLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				finishActivity();
			}
		});
		
		mListView = (ListView) findViewById(R.id.grasp_word_lsit);
		mListView.setAdapter(new WordBookListAdapter(this, Global.gGraspWords,WordBookListAdapter.WORD_GRASP_TYPE));
		
	}
	
	void finishActivity(){
		
		this.finish();
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
	}

	
}
