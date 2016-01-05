package com.yp.lockscreen.view;

import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.view.WheelView.ScrollCallback;

import android.view.View;

public class WheelMain {

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	public int screenheight;
	private boolean hasSelectTime;
	private static int START_YEAR = 0, END_YEAR = 23;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelMain(View view) {
		super();
		this.view = view;
		hasSelectTime = false;
		setView(view);
	}

	public WheelMain(View view, boolean hasSelectTime) {
		super();
		this.view = view;
		this.hasSelectTime = hasSelectTime;
		setView(view);
	}

	public void initDateTimePicker(int year, int month, int day,
			ScrollCallback scrollbaCallback) {
		this.initDateTimePicker(0, 0, 0, day, day, scrollbaCallback);
	}

	public void initDateTimePicker(int year, int month, int day, int h, int m,
			ScrollCallback scrollbaCallback) {

		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(00, 23, "%02d"));
		wv_year.setCyclic(true);
		if (Global.language.contains("en")) {//判断当前语言
			wv_year.setLabel("H");
		}else {
			wv_year.setLabel("时");
		}
		wv_year.setCurrentItem(21);
		wv_year.setCallback(scrollbaCallback);

		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(00, 59, "%02d"));
		wv_month.setCyclic(true);
		if (Global.language.contains("en")) {
			wv_month.setLabel("m");
		}else {
			wv_month.setLabel("分");
		}
		wv_month.setCallback(scrollbaCallback);
		wv_month.setCurrentItem(0);

	}

	public void setCurYearItem(int i) {
		wv_year.setCurrentItem(i);
	}

	public void setCurMonthItem(int i) {
		wv_month.setCurrentItem(i);
	}

	public int getwv_year() {
		return wv_year.getCurrentItem();
	}

	public int getwv_month() {
		return wv_month.getCurrentItem();
	}
}
