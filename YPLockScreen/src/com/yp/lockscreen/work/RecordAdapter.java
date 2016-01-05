package com.yp.lockscreen.work;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yp.enstudy.bean.Record;
import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.ReviewActivity;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.ShareUtils;
import com.yp.lockscreen.utils.StringUtils;

public class RecordAdapter extends BaseAdapter {

	private Context			mContext;
	private List<Record>	mList;

	private LayoutInflater	mInflater;

	public RecordAdapter(Context mContext, List<Record> mList) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = mInflater.inflate(R.layout.record_listview_item, null);
			holder.daysText = (TextView) v.findViewById(R.id.record_list_item_days_text);
			holder.dayText = (TextView) v.findViewById(R.id.record_list_item_days_text_unit);
			holder.ciUnitTxt = (TextView) v.findViewById(R.id.record_list_item_cishu_unit_txt);
			holder.numUnitTxt = (TextView) v.findViewById(R.id.record_list_item_num_unit_txt);
			holder.shareText = (TextView) v.findViewById(R.id.record_list_item_share_text);
			holder.timeText = (TextView) v.findViewById(R.id.record_list_item_time_text);
			holder.unlockPer = (TextView) v.findViewById(R.id.record_list_item_unlock_per_text);
			holder.unlockTimes = (TextView) v.findViewById(R.id.record_list_item_unlock_time_text);
			holder.wordCount = (TextView) v.findViewById(R.id.record_list_item_review_text);
			holder.behaviorImg = (ImageView) v.findViewById(R.id.record_list_item_time_behavior_img);
			holder.driverLine = (ImageView) v.findViewById(R.id.record_list_item_driver_line_img);
			holder.shareLy = (LinearLayout) v.findViewById(R.id.record_list_item_share_ly);
			holder.daysLy = (LinearLayout) v.findViewById(R.id.record_list_item_days_ly);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();

		}
		final Record record = mList.get(position);
		if (position == 0) {
			holder.driverLine.setVisibility(View.GONE);
			holder.timeText.setText(R.string.today);
			holder.shareLy.setVisibility(View.VISIBLE);
			holder.shareText.setText(R.string.share);
			holder.shareLy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ShareUtils shareUtils = new ShareUtils(mContext);
					shareUtils.CreateShare(mContext.getString(R.string.app_name), getShareString(record), (Activity) mContext);

				}
			});
		} else {
			holder.driverLine.setVisibility(View.VISIBLE);
			holder.timeText.setText(record.record_date + "");
			if (Global.language.contains("en")) {
				holder.timeText.setText(StringUtils.timeCn2En(record.record_date + ""));
			} else {
				holder.timeText.setText(record.record_date + "");
			}

			holder.shareLy.setVisibility(View.GONE);
			holder.shareLy.setOnClickListener(null);

		}
		if (record.record_count > 0 && record.review == 1) {
			holder.behaviorImg.setVisibility(View.VISIBLE);
		} else {
			holder.behaviorImg.setVisibility(View.GONE);
		}
		

		if (Global.language.contains("en")) {
			holder.daysText.setText(" "+record.record_days + " ");
			holder.unlockPer.setText(" "+getUnlockPer(record.record_count)+ " ");
			holder.unlockTimes.setText(" "+record.record_count + " ");
			if (record.review == 1) {
				holder.wordCount.setText(" "+record.record_words + " ");
			} else if (record.review == 0) {
				holder.wordCount.setText(" 0 ");
			}
			if (record.record_days > 1) {
				holder.dayText.setText(mContext.getString(R.string.review_text_days));
			} else {
				holder.dayText.setText(mContext.getString(R.string.review_text_day));
			}
			if (record.record_count > 1) {
				holder.ciUnitTxt.setText(mContext.getString(R.string.times));
			} else {
				holder.ciUnitTxt.setText(mContext.getString(R.string.time));
			}
			if (record.record_words > 1) {
				holder.numUnitTxt.setText(mContext.getString(R.string.s_words));
			} else {
				holder.numUnitTxt.setText(mContext.getString(R.string.s_word));
			}
		} else {
			holder.daysText.setText(record.record_days + "");
			holder.unlockPer.setText(getUnlockPer(record.record_count)+ "");
			holder.unlockTimes.setText(record.record_count + "");
			holder.dayText.setText(mContext.getString(R.string.review_text_day));
			holder.ciUnitTxt.setText(mContext.getString(R.string.times));
			holder.numUnitTxt.setText(mContext.getString(R.string.s_word));
			if (record.review == 1) {
				holder.wordCount.setText(record.record_words + "");
			} else if (record.review == 0) {
				holder.wordCount.setText("0");
			}
		}

		

		if (position == 0) {
			holder.shareText.setVisibility(View.VISIBLE);
		} else {
			holder.shareText.setVisibility(View.GONE);
		}
		return v;
	}

	private class ViewHolder {

		/**
		 * 使用第几天
		 */
		TextView		daysText;

		/**
		 * 单位：天
		 */
		TextView		dayText;
		/**
		 * 单位：次数
		 */
		TextView		ciUnitTxt;
		/**
		 * 单位：个数
		 */
		TextView		numUnitTxt;
		/**
		 * 解锁次数
		 */
		TextView		unlockTimes;
		/**
		 * 解锁超过多少人
		 */
		TextView		unlockPer;
		/**
		 * 复习单词数
		 */
		TextView		wordCount;
		/**
		 * 分享
		 */
		TextView		shareText;
		/**
		 * 时间
		 */
		TextView		timeText;
		/**
		 * 奖章图片
		 */
		ImageView		behaviorImg;
		/**
		 * 分割线
		 */
		ImageView		driverLine;
		/**
		 * 分享ly
		 */
		LinearLayout	shareLy;
		/**
		 * 
		 */
		LinearLayout	daysLy;

	}

	private String getShareString(Record record) {
		StringBuffer s = new StringBuffer();
		if (Global.language.contains("en")) {
			s.append(mContext.getString(R.string.share_text1));
			s.append(record.record_days);
			if (record.record_days > 1) {
				s.append(mContext.getString(R.string.share_text2s));
			} else {
				s.append(mContext.getString(R.string.share_text2));
			}
			s.append(record.record_count);
			if (record.record_count > 1) {
				s.append(mContext.getString(R.string.share_text3s));
			} else {
				s.append(mContext.getString(R.string.share_text3));
			}
			s.append(record.record_words);
			if (record.record_count > 1) {
				s.append(mContext.getString(R.string.share_text4s));
			} else {
				s.append(mContext.getString(R.string.share_text4));
			}
			s.append(mContext.getString(R.string.share_text4));
		} else {
			s.append(mContext.getString(R.string.share_text1));
			s.append(record.record_days);
			s.append(mContext.getString(R.string.share_text2));
			s.append(record.record_count);
			s.append(mContext.getString(R.string.share_text3));
			s.append(record.record_words);
			s.append(mContext.getString(R.string.share_text4));
		}

		return s.toString();
	}

	String getUnlockPer(int count) {
		if (count == 0) {
			return "0%";
		} else if (0 < count && count < 5) {
			return "5%";
		} else if (5 < count && count < 10) {
			return "10%";
		} else if (10 <= count && count < 20) {
			return "15%";
		} else if (20 <= count && count < 30) {
			return "25%";
		} else if (30 <= count && count < 40) {
			return "45%";
		} else if (40 <= count && count < 50) {
			return "50%";
		} else if (50 <= count && count < 60) {
			return "70%";
		} else if (60 <= count && count < 70) {
			return "80%";
		} else {
			return "87%";
		}
	}
}
