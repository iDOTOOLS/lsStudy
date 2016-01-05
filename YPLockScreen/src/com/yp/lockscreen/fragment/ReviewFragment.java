package com.yp.lockscreen.fragment;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.WordData;
import com.yp.enstudy.bean.Record;
import com.yp.enstudy.bean.TableName;
import com.yp.enstudy.db.DBConstans;
import com.yp.lockscreen.R;
import com.yp.lockscreen.StudyManager;
import com.yp.lockscreen.activity.ReviewActivity;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.LogHelper;

public class ReviewFragment extends Fragment {
	private static final String	TAG	= "ReviewFragment";

	public ReviewFragment() {
		super();
	}

	private View		retView;

	private TextView	reviewCount;
	private TextView	unLockCount;
	private TextView	keeepDay;
	private TextView	cetCount;
	private TextView	masterCount;
	private TextView	lockUnit;
	private TextView	useUnit;
	private TextView	countUnit;
	private TextView	handleUnit;

	private TextView	bookName;

	private Activity	mActivity;

	private Button		startBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogHelper.i(TAG, "onCreate");
		mActivity = this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogHelper.i(TAG, "onCreateView");

		retView = inflater.inflate(R.layout.review_fragment, null);
		initView(retView);
		setViewData(Global.gWordData);
		return retView;
	}

	void initView(View v) {
		countUnit = (TextView) v.findViewById(R.id.review_count_text);
		
		lockUnit = (TextView) v.findViewById(R.id.item_lock_unit_text);
		useUnit = (TextView) v.findViewById(R.id.item_use_unit_text);
		handleUnit = (TextView) v.findViewById(R.id.item_handle_unit_text);

		reviewCount = (TextView) v.findViewById(R.id.review_goal_text);
		unLockCount = (TextView) v.findViewById(R.id.gridview_item_count_text);
		keeepDay = (TextView) v.findViewById(R.id.gridview_item2_count_text);
		cetCount = (TextView) v.findViewById(R.id.gridview_item3_count_text);
		masterCount = (TextView) v.findViewById(R.id.gridview_item4_count_text);
		bookName = (TextView) v.findViewById(R.id.gridview_item3_name_text);

		startBtn = (Button) v.findViewById(R.id.review_start_btn);
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ReviewActivity.class);
				mActivity.startActivity(intent);
			}
		});
	}

	private StudyManager	mSm	= new StudyManager();

	void setViewData(WordData data) {
		if (data != null) {
			// reviewCount.setText(data.getReviewList(ConfigManager.getWordEveryDayNum(mActivity),
			// 1).size()+"");
			reviewCount.setText(mSm.getTodayStudyNum(getActivity()) + "");
			Record record = data.getTodayRecord();
			unLockCount.setText(record.record_count + "");
			keeepDay.setText(record.record_days + "");
			TableName tableName = Global.gCurTableName;
			masterCount.setText(data.getRemember().size() + "");
			bookName.setText(tableName.ciku_name);
			cetCount.setText(tableName.word_count + "");
			masterCount.setText(data.getRemember().size() + "");
			if (Global.language.contains("en")) {
				if (mSm.getTodayStudyNum(getActivity()) >1) {
					countUnit.setText(getString(R.string.share_text4s));
				}else {
					countUnit.setText(getString(R.string.share_text4));
				}
				if (record.record_count > 1) {
					lockUnit.setText(getString(R.string.times));
				}else {
					lockUnit.setText(getString(R.string.time));
				}
				if (record.record_days > 1) {
					useUnit.setText(getString(R.string.review_text_days));
				}else {
					useUnit.setText(getString(R.string.review_text_day));
				}
				if (data.getRemember().size() > 1) {
					handleUnit.setText(getString(R.string.share_text4s));
				}else {
					handleUnit.setText(getString(R.string.share_text4));
				}
			}else {
				lockUnit.setText(getString(R.string.times));
				useUnit.setText(getString(R.string.review_text_day));
				handleUnit.setText(getString(R.string.review_text_wrodcount));
				countUnit.setText(getString(R.string.word));
			}
		}
	}

	public void notifyDatachanged() {
		setViewData(Global.gWordData);
	}

	@Override
	public void onPause() {

		super.onPause();
	}
}
