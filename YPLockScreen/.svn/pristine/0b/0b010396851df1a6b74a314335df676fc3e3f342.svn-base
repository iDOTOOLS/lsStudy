package com.yp.lockscreen.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.lockscreen.R;
import com.yp.lockscreen.StudyManager;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.LogHelper;

public class StudyPlaneActivity extends Activity implements OnClickListener {

	private static final String TAG = "StudyPlaneActivity";

	public static final String REQUEST_NAME = "tempCikuName";
	
	public static final String REQUEST_CN_NAME = "tempCnName";

	private RelativeLayout cikuRy;

	private RelativeLayout orderRy;

	private RelativeLayout dayWordRy;

	private LayoutInflater mInflater;

	private int wordOrderId;

	private Button backBtn;

	private LinearLayout backLy;

	private Button OkBtn;

	private String tempCikuName;
	
	private String tempCnName; 

	private int tempOrderType;

	private int tempNewsCount;
	
	private Context mCxt;
	
	private TextView cikuText;
	
	private TextView orderText;
	
	private TextView newText;
	
	private boolean isFirst = false;

	
	// private boolean tempIsChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study_plane_activity);
//		isFirst = savedInstanceState.getBoolean("is_first");
		mCxt = this;
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
			wakeToSave();
		}
		return super.onKeyDown(keyCode, event);
	}

	void initView() {

		mInflater = LayoutInflater.from(this);
		tempCikuName = ConfigManager.getCurCiku(this);
		tempCnName = Global.gCurTableName.ciku_name;
		tempOrderType = ConfigManager.getOrderType(mCxt);
		tempNewsCount = ConfigManager.getWordEveryDayNum(mCxt);
		OkBtn = (Button) findViewById(R.id.study_plan_activity_ok_btn);
		OkBtn.setOnClickListener(this);
		cikuRy = (RelativeLayout) findViewById(R.id.study_plane_activity_ciku_ry);
		cikuRy.setOnClickListener(this);
		orderRy = (RelativeLayout) findViewById(R.id.study_plane_activity_order_ry);
		orderRy.setOnClickListener(this);
		dayWordRy = (RelativeLayout) findViewById(R.id.study_plane_activity_new_ry);
		dayWordRy.setOnClickListener(this);

		backLy = (LinearLayout) findViewById(R.id.shudy_plan_activity_ly);
		backLy.setOnClickListener(this);
		
		cikuText = (TextView)findViewById(R.id.study_plane_activity_ciku_text);
		orderText = (TextView)findViewById(R.id.study_plane_activity_order_text);
		newText = (TextView)findViewById(R.id.study_plane_activity_new_text);
		
		setCikuDefultText();
		setNewDefultText();
		setOrderDefultText();
	}

	void finishActivity() {
		LockConfigMgr.setIsFirstSet(mCxt, false);
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			tempCikuName = bundle.getString(REQUEST_NAME);
			tempCnName = bundle.getString(REQUEST_CN_NAME);
			setCikuDefultText();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.study_plane_activity_ciku_ry:
			Bundle nameBundle = new Bundle();
			nameBundle.putString(REQUEST_NAME, tempCikuName);
			nameBundle.putString(REQUEST_CN_NAME, tempCnName);
			Intent cikuIntent = new Intent(this, CikuListActivity.class);
			cikuIntent.putExtras(nameBundle);
			startActivityForResult(cikuIntent, 0);
			break;
		case R.id.study_plane_activity_order_ry:

			final Dialog dialog = new AlertDialog.Builder(StudyPlaneActivity.this).create();
			dialog.show();
			LinearLayout dialogLy = (LinearLayout) mInflater.inflate(R.layout.dialog_word_order, null);
			dialog.getWindow().setContentView(dialogLy);
			final RadioGroup mOrderGroup = (RadioGroup) dialogLy
					.findViewById(R.id.dialog_word_order_radiobtn);
			switch (tempOrderType) {
			case 0:
				mOrderGroup.check(R.id.dialog_word_order_first);
				break;
			case 1:
				mOrderGroup.check(R.id.dialog_word_order_second);
				break;
			case 2:
				mOrderGroup.check(R.id.dialog_word_order_third);
				break;
			}

			Button orderBtn = (Button) dialogLy.findViewById(R.id.dialog_word_order_ok);
			orderBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					wordOrderId = mOrderGroup.getCheckedRadioButtonId();
					LogHelper.i(TAG, "wordOrderId:" + wordOrderId);
					switch (wordOrderId) {
					case R.id.dialog_word_order_first:
						tempOrderType = 0;
						break;
					case R.id.dialog_word_order_second:
						tempOrderType = 1;
						break;
					case R.id.dialog_word_order_third:
						tempOrderType = 2;
						break;
					}
					dialog.dismiss();
					setOrderDefultText();
				}
			});

			break;
		case R.id.study_plane_activity_new_ry:
			final Dialog countDialog = new AlertDialog.Builder(StudyPlaneActivity.this).create();
			countDialog.show();
			RelativeLayout countRy = (RelativeLayout) mInflater.inflate(R.layout.dialog_days_new_word, null);
			countDialog.getWindow().setContentView(countRy);
			final RadioGroup countGroup = (RadioGroup) countRy.findViewById(R.id.dialog_days_new_word_radiobtn);
			switch (tempNewsCount) {
			case 10:
				countGroup.check(R.id.dialog_days_new_word_first);
				break;
			case 20:
				countGroup.check(R.id.dialog_days_new_word_second);
				break;
			case 30:
				countGroup.check(R.id.dialog_days_new_word_third);
				break;
			case -1:
				countGroup.check(R.id.dialog_days_new_word_four);
			}

			Button countOkBtn = (Button) countRy.findViewById(R.id.days_new_word_ok_btn);
			countOkBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int countRadioId = countGroup.getCheckedRadioButtonId();
					LogHelper.i(TAG, "countRadioId:" + countRadioId);

					switch (countRadioId) {
					case R.id.dialog_days_new_word_first:
						tempNewsCount = 10;
						break;
					case R.id.dialog_days_new_word_second:
						tempNewsCount = 20;
						break;
					case R.id.dialog_days_new_word_third:
						tempNewsCount = 30;
						break;
					case R.id.dialog_days_new_word_four:
						tempNewsCount = -1;
						break;
					}
					countDialog.dismiss();
					setNewDefultText();
				}
			});
			break;

		case R.id.study_plan_activity_ok_btn:
			wake2Change();
			break;
		case R.id.shudy_plan_activity_ly:
			wakeToSave();
			break;

		}
	}

	private boolean isNeedDialog() {
		if ((!tempCikuName.equals(ConfigManager.getCurCiku(this)))
				|| tempNewsCount != ConfigManager.getWordEveryDayNum(mCxt)
				|| tempOrderType != ConfigManager.getOrderType(mCxt)) {
			return true;
		} else {
			return false;
		}
	}

	StudyManager mSm = new StudyManager();
	/**
	 * 点击保存
	 */
	private void wake2Change() {
		if (LockConfigMgr.getIsFirstSet(mCxt)) {
			resetStudyData();
			finishActivity();
			return;
		}
		if (isNeedDialog()) {
			final Dialog changeDialog = new AlertDialog.Builder(this).create();
			changeDialog.show();
			View v = LayoutInflater.from(this).inflate(R.layout.dialog_change_plan, null);
			changeDialog.getWindow().setContentView(v);
			Button okBtnView = (Button) v.findViewById(R.id.dialog_change_plan_ok_btn);
			Button cnlBtn = (Button) v.findViewById(R.id.dialog_change_plan_btn);
			okBtnView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					resetStudyData();
					changeDialog.dismiss();
					finishActivity();
				}
			});
			cnlBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					changeDialog.dismiss();
				}
			});

		} else {
			finishActivity();
		}
	}

	/**重置学习计划*/
	private void resetStudyData(){
	    ConfigManager.saveCurCiku(StudyPlaneActivity.this,tempCikuName);
        ConfigManager.setOrderType(StudyPlaneActivity.this,tempOrderType);
        ConfigManager.setWordEveryDayNum(StudyPlaneActivity.this,tempNewsCount);
        LockConfigMgr.setLockSize(StudyPlaneActivity.this,0);
        // 更新
        Global.updataNoRememberList();
        Global.updataRemember();
        Global.updataReviewlsit();
        Global.updataCurTableName();
        Global.gWordData.setDBName(tempCikuName);
        mSm.resetDayListData(mCxt);
	}
	
	/**
	 * 点击back键，
	 */
	private void wakeToSave() {

		if (isNeedDialog()) {
			final Dialog saveDialog = new AlertDialog.Builder(this).create();
			saveDialog.show();
			View v = LayoutInflater.from(this).inflate(
					R.layout.dialog_save_plan, null);
			saveDialog.getWindow().setContentView(v);
			Button okBtnView = (Button) v
					.findViewById(R.id.dialog_save_plan_ok_btn);
			Button cnlBtn = (Button) v.findViewById(R.id.dialog_save_plan_btn);
			okBtnView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					saveDialog.dismiss();
					wake2Change();

				}
			});
			cnlBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					saveDialog.dismiss();
					finishActivity();
				}
			});

		} else {
			finishActivity();
		}

	}
	
	private void setCikuDefultText(){
		
		cikuText.setText(tempCnName);
		
	}
	
	private void setOrderDefultText(){
		if (newText != null) {
			String buffer = "";
			switch (tempOrderType) {
			case 0:
				buffer =getString(R.string.order_normal);
				break;
			case 1:
				buffer = getString(R.string.order_random);
				break;
			case 2:
				buffer = getString(R.string.order_reverse);
				break;
			}
			orderText.setText(buffer);
		}
	}
	
	private void setNewDefultText(){
		if (newText != null) {
			String buffer = "";
			switch (tempNewsCount) {
			case -1:
				buffer = getString(R.string.infinite_mode);
				break;
			case 10:
				buffer = "10";
				break;
			case 20:
				buffer = "20";
				break;
			case 30:
				buffer = "30";
				break;
			}
			newText.setText(buffer);
		}
		
		/**
		 * 		switch (ConfigManager.getWordEveryDayNum(mActivity)) {
		case -1:
			buffer.append("每日新词无限个");
			break;
		case 10:
			buffer.append("每日新词10个");
			break;
		case 20:
			buffer.append("每日新词20个");
			break;
		case 30:
			buffer.append("每日新词30个");
			break;

		default:
			break;
		}
		 * 
		 * */
	}
}
