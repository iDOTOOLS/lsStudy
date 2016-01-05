package com.yp.lockscreen.activity;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.bean.TableName;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.work.CikuListAdapter;

public class CikuListActivity extends Activity {

	private Button button;
	private LinearLayout backLy;

	private ListView cikuList;

	private ArrayList<TableName> mList;
	
	private String curCikuName;
	
	private CikuListAdapter adapter;
	
	private String curCnName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		curCikuName = getIntent().getExtras().getString(StudyPlaneActivity.REQUEST_NAME);
		curCnName = getIntent().getExtras().getString(StudyPlaneActivity.REQUEST_CN_NAME);
		setContentView(R.layout.ciku_list_activity);
		initView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishActivity();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	void initView() {

		button = (Button) findViewById(R.id.ciku_list_activity_back_btn);
		backLy = (LinearLayout) findViewById(R.id.ciku_list_activity_back_ly);
		backLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		cikuList = (ListView) findViewById(R.id.ciku_list_activity_lsitview);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		mList = Global.gWordData.queryAllTableName();
		for (int i = 0; i < mList.size(); i++) {
			
			TableName name = mList.get(i);
			if (Global.gWordData.checkDB(name.name)) {
				name.isDownLoad = true;
			}else {
				name.isDownLoad = false;
			}
		}
		Collections.sort(mList);
		adapter = new CikuListAdapter(this, mList, curCikuName,curCnName);
		cikuList.setAdapter(adapter);
	}

	void finishActivity() {
		curCikuName = adapter.getCurDBName();
		curCnName = adapter.getCurCnName();
		Intent intent = new Intent(this, StudyPlaneActivity.class);  
        Bundle bundle = new Bundle();  
        bundle.putString(StudyPlaneActivity.REQUEST_NAME, curCikuName);  
        bundle.putString(StudyPlaneActivity.REQUEST_CN_NAME, curCnName);  
        intent.putExtras(bundle);  
        setResult(RESULT_OK, intent); 
		this.finish();
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
