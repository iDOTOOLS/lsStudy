package com.yp.lockscreen.activity;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.utils.NumberUtil;
import com.yp.enstudy.utils.StringUtil;
import com.yp.enstudy.utils.TimeUtil;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.view.WheelMain;
import com.yp.lockscreen.view.WheelView.ScrollCallback;
import com.yp.lockscreen.work.AlertReceiver;

public class RemindReviewActivity extends Activity implements OnClickListener {

	private static final String TAG = "RemindReviewActivity";

	private static final String ACTION_REMIND_REVIEW = "lock.intent.action.REMIND_REVIEW";

	private LinearLayout backLy;

	private ToggleButton isWakeTog;

	private RelativeLayout setTimeLy;

	private TextView timeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_remind_activity);
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
		backLy = (LinearLayout) findViewById(R.id.review_remind_activity_back_ly);
		backLy.setOnClickListener(this);
		isWakeTog = (ToggleButton) findViewById(R.id.review_remind_activity_tog);
		isWakeTog.setOnClickListener(this);
		setTimeLy = (RelativeLayout) findViewById(R.id.review_remind_activity_wake_time_ry);
		setTimeLy.setOnClickListener(this);
		timeText = (TextView) findViewById(R.id.review_remind_activity_wake_content_time);

		isWakeTog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				ConfigManager.setIsOpenRemind(RemindReviewActivity.this,
						isChecked);
				if (isChecked) {
					startRemind();
				} else {
					stopRemind();
				}

			}
		});
		setTimeText();
		isWakeTog.setChecked(ConfigManager.getIsOpenRemind(this));
	}

	private void setTimeText() {
		if (timeText != null) {
			timeText.setText(ConfigManager.getAlarmReViewTime(this));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.review_remind_activity_back_ly:
			finishActivity();
			break;
		case R.id.review_remind_activity_wake_time_ry:

			final Dialog remindDlog = new AlertDialog.Builder(this).create();
			remindDlog.show();
			final View dlogView = LayoutInflater.from(this).inflate(
					R.layout.dialog_remind_wheel_view, null);
			final WheelMain main = new WheelMain(dlogView);
			main.screenheight = Global.screen_Height;
			// 获取记录的时间
			String str = ConfigManager.getAlarmReViewTime(this);
			String[] times = str.split(":");

			main.initDateTimePicker(0, 0, 0, new ScrollCallback() {
				@Override
				public void scrollingFinish() {

				}
			});

			main.setCurYearItem(NumberUtil.toInt(times[0]));
			main.setCurMonthItem(NumberUtil.toInt(times[1]));
			remindDlog.getWindow().setContentView(dlogView);

			final Button okBtn = (Button) dlogView
					.findViewById(R.id.dialog_remind_wheel_view_ok_btn);
			okBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					remindDlog.dismiss();
					String time = getTimeStr(main.getwv_year(),
							main.getwv_month());
					ConfigManager.setAlarmReViewTime(RemindReviewActivity.this,
							time);
					if (ConfigManager
							.getIsOpenRemind(RemindReviewActivity.this)) {
						stopRemind();
						startRemind();
					}
					setTimeText();
				}
			});
			break;
		}
	}

	private AlarmManager am;
	private PendingIntent sender;

	private void startRemind() {

		am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(RemindReviewActivity.this,
				AlertReceiver.class);

		sender = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		String timeStr = ConfigManager.getAlarmReViewTime(this);// 获取设置的时间

		int interval = 60 * 60 * 24 * 1000;// 间隔， 每隔24H收到一次广播
		// 将String 转换成数字时间
		long timeInMillis = TimeUtil.twentyfour2Millis(timeStr);

		if (timeInMillis < System.currentTimeMillis()) {// 如果设置的时间小于当前时间，那么就在该时间点加上24小时
			timeInMillis += interval;
		}
		am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);// timeInMillis：第一次的时间点
	}

	private void stopRemind() {
		if (am != null && sender != null) {
			am.cancel(sender);
			am = null;
			sender = null;
		}

	}

	private String getTimeStr(int HH, int mm) {
		String hour = String.format("%02d", HH);
		String min = String.format("%02d", mm);
		return hour + ":" + min;
	}

	void finishActivity() {
		finish();
	}
}
