package com.yp.lockscreen.fragment;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.MainActivity;
import com.yp.enstudy.utils.TimeUtil;
import com.yp.lockscreen.DownloadVoice;
import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.MoreSetActivity;
import com.yp.lockscreen.activity.RemindReviewActivity;
import com.yp.lockscreen.activity.ReviewActivity;
import com.yp.lockscreen.activity.RunReadMeActivity;
import com.yp.lockscreen.activity.SetHomeActicity;
import com.yp.lockscreen.activity.SetSupport;
import com.yp.lockscreen.activity.SetWallpaper;
import com.yp.lockscreen.activity.StudyPlaneActivity;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.LogHelper;
import com.yp.lockscreen.utils.NetworkUtils;
import com.yp.lockscreen.utils.ShareUtils;
import com.yp.lockscreen.work.VoiceDownLoadManger;

public class SettingFragment extends Fragment implements OnClickListener {
	private static final String TAG = "SettingFragment";
	private View retView;
	/**
	 * 学习计划
	 */
	private RelativeLayout planeRy;
	/**
	 * 解锁方式
	 */
	private RelativeLayout unlockRy;
	/**
	 * 复习提醒
	 */
	private RelativeLayout wakeRy;
	/**
	 * 例句
	 */
	private RelativeLayout egRy;
	/**
	 * 下载语音包
	 */
	private RelativeLayout downloadRy;

	/**
	 * 锁屏壁纸
	 */
	private RelativeLayout wallpaperRy;

	/**稳定运行说明*/
	private RelativeLayout runReadMeRy;
	
	/**
	 * 锁定home键
	 */
	private RelativeLayout homeRy;

	/**
	 * 支持开发者
	 */
	private RelativeLayout supportRy;

	/**
	 * 推荐好友
	 */
	private RelativeLayout recommendRy;
	/**
	 * 更多设置
	 */
	private RelativeLayout moreRy;

	private Activity mActivity;
	
	private LayoutInflater mInflater;

	private ToggleButton isOpenChinese;

	private ToggleButton isUnlockVibration;

	private TextView planText;
	private TextView unLockText;
	private TextView reviewText;
	private TextView egText;
	private TextView downloadSoundText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogHelper.i(TAG, "onCreate");
		mActivity = this.getActivity();
		mInflater = LayoutInflater.from(mActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogHelper.i(TAG, "onCreateView");
		retView = inflater.inflate(R.layout.setting_fragment, null);
		initView(retView);
		return retView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (LockConfigMgr.getIsFirstSetLock(mActivity)) {
			showLockType();
			LockConfigMgr.setIsFirstSetLock(mActivity, false);
		}
		setDefultValue();
	}

	void initView(View v) {

		planeRy = (RelativeLayout) v.findViewById(R.id.set_study_plan_ry);
		planeRy.setOnClickListener(this);

		unlockRy = (RelativeLayout) v.findViewById(R.id.set_unlock_style_ry);
		unlockRy.setOnClickListener(this);

		wakeRy = (RelativeLayout) v.findViewById(R.id.set_review_remind_ry);
		wakeRy.setOnClickListener(this);

		egRy = (RelativeLayout) v.findViewById(R.id.set_eg_ry);
		egRy.setOnClickListener(this);

		downloadRy = (RelativeLayout) v.findViewById(R.id.set_download_ry);
		downloadRy.setOnClickListener(this);

		wallpaperRy = (RelativeLayout) v.findViewById(R.id.set_lock_paper_ry);
		wallpaperRy.setOnClickListener(this);
		
	      runReadMeRy = (RelativeLayout) v.findViewById(R.id.set_runreadme_paper_ry);
	      runReadMeRy.setOnClickListener(this);

		homeRy = (RelativeLayout) v.findViewById(R.id.set_home_ry);
		homeRy.setOnClickListener(this);

		supportRy = (RelativeLayout) v.findViewById(R.id.set_support_ry);
		supportRy.setOnClickListener(this);

		recommendRy = (RelativeLayout) v.findViewById(R.id.set_recommend_ry);
		recommendRy.setOnClickListener(this);

		moreRy = (RelativeLayout) v.findViewById(R.id.set_more_set_ry);
		moreRy.setOnClickListener(this);

		planText = (TextView) v.findViewById(R.id.set_study_plan_cont_text);
		unLockText = (TextView) v
				.findViewById(R.id.set_unlock_style_content_text);
		reviewText = (TextView) v
				.findViewById(R.id.set_review_remind_content_text);
		egText = (TextView) v.findViewById(R.id.set_eg_content_text);
		downloadSoundText = (TextView) v
				.findViewById(R.id.set_download_content_text);

		isOpenChinese = (ToggleButton) v
				.findViewById(R.id.set_cn_translate_toggle);
		isUnlockVibration = (ToggleButton) v
				.findViewById(R.id.set_lock_shake_toggle);

		isOpenChinese.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				ConfigManager.setExplain(mActivity, isChecked);

			}
		});
		isUnlockVibration
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						ConfigManager.setUnlockVibration(mActivity, isChecked);

					}
				});

		setDefultValue();
	}
	
	private void setDefultValue(){
		isOpenChinese.setChecked(ConfigManager.isExplain(mActivity));
		isUnlockVibration
				.setChecked(ConfigManager.isUnlockVibration(mActivity));
		
		setPlanText();
		setUnlockText();
		setReviewText();
		setegText();
		setDownloadSoundText();
	}
	

	String shareContent="";
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.set_study_plan_ry:
			
			Intent planIntent = new Intent(mActivity, StudyPlaneActivity.class);
			startActivity(planIntent);

			break;
		case R.id.set_unlock_style_ry:
			showLockType();
			break;
		case R.id.set_review_remind_ry:
			Intent remindIntent = new Intent(mActivity,
					RemindReviewActivity.class);
			startActivity(remindIntent);
			break;
		case R.id.set_eg_ry:

			final Dialog egDialog = new AlertDialog.Builder(mActivity).create();
			egDialog.show();
			LinearLayout egRy = (LinearLayout) mInflater.inflate(
					R.layout.dialog_is_show_eg, null);
			egDialog.getWindow().setContentView(egRy);
			final RadioGroup egGroup = (RadioGroup) egRy
					.findViewById(R.id.dialog_is_show_eg_rg);
			switch (ConfigManager.getSentenceType(mActivity)) {
			case 0:
				egGroup.check(R.id.dialog_is_show_eg_first);
				break;
			case 1:
				egGroup.check(R.id.dialog_is_show_eg_second);
				break;
			case 2:
				egGroup.check(R.id.dialog_is_show_eg_third);
				break;
			}

			Button egBtn = (Button) egRy
					.findViewById(R.id.dialog_is_show_eg_ok_btn);
			egBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int unlockRadioId = egGroup.getCheckedRadioButtonId();
					switch (unlockRadioId) {
					case R.id.dialog_is_show_eg_first:
						ConfigManager.setSentenceType(mActivity, 0);
						break;
					case R.id.dialog_is_show_eg_second:
						ConfigManager.setSentenceType(mActivity, 1);
						break;
					case R.id.dialog_is_show_eg_third:
						ConfigManager.setSentenceType(mActivity, 2);
						break;
					}
					egDialog.dismiss();
					setegText();
				}
			});

			break;
		case R.id.set_download_ry:
			String NAME = ConfigManager.getCurCiku(mActivity);

			String PATH = Global.AUDIO_PATH + File.pathSeparator + NAME;

			File file = new File(PATH);
			if (Global.gWordData.checkVoice(ConfigManager.getCurCiku(mActivity))) {
				Toast.makeText(mActivity, R.string.file_already, Toast.LENGTH_SHORT).show();
			} else if(!NetworkUtils.isNetworkAvaialble(getActivity())){
                Toast.makeText(mActivity, R.string.netword_error, Toast.LENGTH_SHORT).show();
            }else {
                if(DownloadVoice.isDownLoading){
                    Toast.makeText(mActivity, R.string.have_task_downloading, Toast.LENGTH_SHORT).show();
                }else{
                    new VoiceDownLoadManger().startDownLoad(mActivity);
                }
			}

			break;
		case R.id.set_runreadme_paper_ry:
		    Intent runReadMeIntent = new Intent(mActivity, RunReadMeActivity.class);
		    startActivity(runReadMeIntent);
		    break;
		case R.id.set_lock_paper_ry:
			Intent wallpaperIntent = new Intent(mActivity, SetWallpaper.class);
			startActivity(wallpaperIntent);
			break;
		case R.id.set_home_ry:
			Intent homeIntent = new Intent(mActivity, SetHomeActicity.class);
			startActivity(homeIntent);
			break;
		case R.id.set_support_ry:
			Intent supportIntent = new Intent(mActivity, SetSupport.class);
			startActivity(supportIntent);
			break;
		case R.id.set_recommend_ry:
			final ShareUtils shareUtils = new ShareUtils(mActivity);
			shareContent = getString(R.string.share_friends, getActivity().getPackageName());
			if (Global.language.contains("en")) {
	            shareUtils.CreateShare(getString(R.string.review_title_text), shareContent, getActivity());
			}else {
				final Dialog recomDialog = new AlertDialog.Builder(mActivity).create();
				recomDialog.show();
				LinearLayout recomRy = (LinearLayout) mInflater.inflate(R.layout.dialog_recommend, null);
				recomDialog.getWindow().setContentView(recomRy);

				LinearLayout weixinLy = (LinearLayout) recomRy.findViewById(R.id.dialog_recommend_weixin);
				LinearLayout weiboLy = (LinearLayout) recomRy.findViewById(R.id.dialog_recommend_weibo);
				LinearLayout emailLy = (LinearLayout) recomRy.findViewById(R.id.dialog_recommend_email);

				
				weixinLy.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						recomDialog.dismiss();
						shareUtils.shareTextToFriendGroupBySystem(shareContent);
					}
				});

				weiboLy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						recomDialog.dismiss();
						shareUtils.shareTextToSinaBySystem(shareContent);
					}
				});
				emailLy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						recomDialog.dismiss();
						shareUtils.shareUrl2MailBySystem(shareContent);
					}
				});
			}
			

			break;
		case R.id.set_more_set_ry:
			Intent moretIntent = new Intent(mActivity, MoreSetActivity.class);
			startActivity(moretIntent);
			break;
		}
	}

	void showLockType() {
		final Dialog unlockDialog = new AlertDialog.Builder(mActivity).create();
		unlockDialog.show();
		LinearLayout unlockRy = (LinearLayout) mInflater.inflate(
				R.layout.dialog_unlock_method, null);
		unlockDialog.getWindow().setContentView(unlockRy);
		final RadioGroup unLockGroup = (RadioGroup) unlockRy
				.findViewById(R.id.dialog_unlock_method_radiobtn);

		switch (ConfigManager.getUnlockScreenType(mActivity)) {
		case 1:
			unLockGroup.check(R.id.dialog_unlock_method_radiobtn_first);
			break;
		case 3:
			unLockGroup.check(R.id.dialog_unlock_method_radiobtn_second);
			break;
		case 5:
			unLockGroup.check(R.id.dialog_unlock_method_radiobtn_third);
			break;
		case 10:
			unLockGroup.check(R.id.dialog_unlock_method_radiobtn_four);
			break;

		default:
			break;
		}

		Button unLockBtn = (Button) unlockRy
				.findViewById(R.id.dialog_unlock_method_ok_btn);
		unLockBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int unlockRadioId = unLockGroup.getCheckedRadioButtonId();
				switch (unlockRadioId) {
				case R.id.dialog_unlock_method_radiobtn_first:
					ConfigManager.setUnlockScreenType(mActivity, 1);
					break;
				case R.id.dialog_unlock_method_radiobtn_second:
					ConfigManager.setUnlockScreenType(mActivity, 3);
					break;
				case R.id.dialog_unlock_method_radiobtn_third:
					ConfigManager.setUnlockScreenType(mActivity, 5);
					break;
				case R.id.dialog_unlock_method_radiobtn_four:
					ConfigManager.setUnlockScreenType(mActivity, 10);
					break;
				}
				unlockDialog.dismiss();
				setUnlockText();
			}
		});

	}

	// private TextView planText;
	// private TextView unLockText;
	// private TextView reviewText;
	// private TextView egText;
	// private TextView downloadSoundText;

	private void setPlanText() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Global.gCurTableName.ciku_name+" ");
		switch (ConfigManager.getOrderType(mActivity)) {
		case 0:
			buffer.append(getString(R.string.order_normal));
			break;

		case 1:
			buffer.append(getString(R.string.order_random));
			break;
			
		case 2:
			buffer.append(getString(R.string.order_reverse));
			break;
		}
		switch (ConfigManager.getWordEveryDayNum(mActivity)) {
		case -1:
			buffer.append(getString(R.string.infiniti_words_every_day));
			break;
		case 10:
			buffer.append(getString(R.string.ten_words_every_day));
			break;
		case 20:
			buffer.append(getString(R.string.twenty_words_every_day));
			break;
		case 30:
			buffer.append(getString(R.string.thirty_words_every_day));
			break;

		default:
			break;
		}
		
		if (planText != null) {
			planText.setText(buffer);
		}
		buffer = null;
	}

	private void setUnlockText() {
		String s = null;
		switch (ConfigManager.getUnlockScreenType(mActivity)) {
		case 1:
			s = getString(R.string.one_word_unlock);
			break;
		case 3:
			s = getString(R.string.three_word_unlock);
			break;
		case 5:
			s = getString(R.string.five_word_unlock);
			break;
		case 10:
			s = getString(R.string.ten_word_unlock);
			break;
		}
		
		if (unLockText != null) {
			unLockText.setText(s);
		}
		s = null;
	}

	private void setReviewText() {
		if (reviewText != null) {
			reviewText.setText(ConfigManager.getAlarmReViewTime(mActivity));
		}
	}

	private void setegText() {
		String s = null;
		
		
		switch (ConfigManager.getSentenceType(mActivity)) {
		case 0:
			s = getString(R.string.dont_show);
			break;
		case 1:
			s = getString(R.string.only_show_english);
			break;
		case 2:
			s = getString(R.string.show_zh_en);
			break;
		}
		if (egText != null) {
			egText.setText(s);
		}
	}

	private void setDownloadSoundText() {
		//TODO  下载完之后要回调
		if (downloadSoundText != null) {
			if (Global.gWordData.checkVoice(ConfigManager.getCurCiku(mActivity))) {
				downloadSoundText.setText(getString(R.string.downloaded));
			} else {
				downloadSoundText.setText(getString(R.string.un_download));
			}
		}
	}
}
