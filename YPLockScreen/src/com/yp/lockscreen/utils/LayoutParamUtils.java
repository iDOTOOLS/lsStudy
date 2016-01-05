package com.yp.lockscreen.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.widget.LinearLayout.LayoutParams;

import com.yp.lockscreen.LockScreenApplication;
import com.yp.lockscreen.port.Global;

public class LayoutParamUtils {

	public static int getViewWidth(int width) {
		switch (Global.screen_width) {
		case 320:
			return width / 2;
		case 480:
			return (int) (Float.valueOf(width / 2) * 1.5);
		case 540:
			return (int) (Float.valueOf(width / 2) * 1.6875);
		case 640:
			return width;
		case 600:
			return (int) (Float.valueOf(width / 2) * 1.875);
		case 720:
			return (int) (Float.valueOf(width / 2) * 2.25);
		case 800:
			return (int) (Float.valueOf(width / 2) * 2.5);
		case 1080:
			return (int) (Float.valueOf(width / 2) * 3.4);
		case 1280:
			return (int) (Float.valueOf(width / 2) * 4);
		default:
			if (Global.screen_width < 320) {
				return (int) (width / 4f);
			} else {
				return (int) (Float.valueOf(width / 2) * (width / 320f));
			}
		}
	}

	public static int getViewHeight(int height) {
		switch (Global.screen_width) {
		case 320:
			return height / 2;
		case 480:
			return (int) (Float.valueOf(height / 2) * 1.5);
		case 540:
			return (int) (Float.valueOf(height / 2) * 1.6875);
		case 640:
			return height;
		case 600:
			return (int) (Float.valueOf(height / 2) * 1.875);
		case 720:
			return (int) (Float.valueOf(height / 2) * 2.25);
		case 800:
			return (int) (Float.valueOf(height / 2) * 2.5);
		case 1080:
			return (int) (Float.valueOf(height / 2) * 3.4);
		case 1280:
			return (int) (Float.valueOf(height / 2) * 4);
		default:
			if (Global.screen_width < 320) {
				return (int) (height / 2.5f);
			} else {
				return (int) (Float.valueOf(height / 2) * (height / 320f));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public static int getHomeBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 100;
		case 480:
			return 150;
		case 540:
			return 169;
		case 640:
			return 200;
		case 600:
			return 187;
		case 720:
			return 225;
		case 800:
			return 250;
		default:
			return (int) (Global.screen_width / 3.2f);
		}
	}

	public static int getHomeBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 52;
		case 480:
			return 78;
		case 540:
			return 88;
		case 640:
			return 104;
		case 600:
			return 98;
		case 720:
			return 117;
		case 800:
			return 130;
		case 1080:
			return 177;
		default:
			return (int) (Global.screen_width / 6.1f);
		}
	}

	public static int getDyMsgWidth() {
		switch (Global.screen_width) {
		case 320:
			return 110;
		case 480:
			return 165;
		case 540:
			return 186;
		case 640:
			return 220;
		case 600:
			return 206;
		case 720:
			return 248;
		case 800:
			return 275;
		default:
			return (int) (Global.screen_width / 2.9);
		}
	}

	public static int getDyMsgHeight() {
		switch (Global.screen_width) {
		case 320:
			return 52;
		case 480:
			return 78;
		case 540:
			return 88;
		case 640:
			return 104;
		case 600:
			return 98;
		case 720:
			return 117;
		case 800:
			return 130;
		default:
			return (int) (Global.screen_width / 6.1f);
		}
	}

	public static int getDy_TopWidth() {
		switch (Global.screen_width) {
		case 320:
			return 160;
		case 480:
			return 240;
		case 540:
			return 270;
		case 640:
			return 320;
		case 600:
			return 300;
		case 720:
			return 360;
		case 800:
			return 400;
		default:
			return Global.screen_width / 2;
		}
	}

	public static int getDy_TopHeight() {
		switch (Global.screen_width) {
		case 320:
			return 50;
		case 480:
			return 75;
		case 540:
			return 85;
		case 640:
			return 100;
		case 600:
			return 94;
		case 720:
			return 113;
		case 800:
			return 125;
		default:
			return (int) (Global.screen_width / 6.4f);
		}
	}

	public static int getBackBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 70;
		case 480:
			return 105;
		case 540:
			return 118;
		case 640:
			return 140;
		case 600:
			return 132;
		case 720:
			return 158;
		case 800:
			return 175;
		case 1080:
			return 245;
		default:
			return (int) (Global.screen_width / 4.6);
		}
	}

	public static int getBackBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 52;
		case 480:
			return 78;
		case 540:
			return 88;
		case 640:
			return 104;
		case 600:
			return 98;
		case 720:
			return 117;
		case 800:
			return 130;
		default:
			return (int) (Global.screen_width / 6.1f);
		}
	}

	public static int getMiddleBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 180;
		case 480:
			return 270;
		case 540:
			return 304;
		case 640:
			return 360;
		case 600:
			return 338;
		case 720:
			return 405;
		case 800:
			return 450;
		case 1080:
			return 589;
		default:
			return Global.screen_width / 2;
		}
	}

	public static int getMiddleBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 52;
		case 480:
			return 78;
		case 540:
			return 88;
		case 640:
			return 104;
		case 600:
			return 98;
		case 720:
			return 117;
		case 800:
			return 130;
		default:
			return (int) (Global.screen_width / 6.1f);
		}
	}

	public static int getCrameBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 98;
		case 480:
			return 147;
		case 540:
			return 163;
		case 640:
			return 196;
		case 600:
			return 184;
		case 720:
			return 221;
		case 800:
			return 245;
		default:
			return (int) (Global.screen_width / 3.2f);
		}
	}

	public static int getCrameBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 49;
		case 480:
			return 73;
		case 540:
			return 83;
		case 640:
			return 98;
		case 600:
			return 92;
		case 720:
			return 111;
		case 800:
			return 123;
		default:
			return (int) (Global.screen_width / 6.5f);
		}
	}

	public static int getZhanBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 41;
		case 480:
			return 62;
		case 540:
			return 70;
		case 640:
			return 82;
		case 600:
			return 77;
		case 720:
			return 93;
		case 800:
			return 103;
		default:
			return (int) (Global.screen_width / 8);
		}
	}

	public static int getZhanBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 41;
		case 480:
			return 62;
		case 540:
			return 70;
		case 640:
			return 82;
		case 600:
			return 77;
		case 720:
			return 93;
		case 800:
			return 103;
		default:
			return Global.screen_width / 8;
		}
	}

	public static int getAddBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 50;
		case 480:
			return 75;
		case 540:
			return 85;
		case 640:
			return 100;
		case 600:
			return 94;
		case 720:
			return 113;
		case 800:
			return 125;
		default:
			return (int) (Global.screen_width / 6.4f);
		}
	}

	public static int getAddBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 41;
		case 480:
			return 62;
		case 540:
			return 70;
		case 640:
			return 82;
		case 600:
			return 77;
		case 720:
			return 93;
		case 800:
			return 103;
		default:
			return Global.screen_width / 8;
		}
	}

	public static int getMsgBtnWidth() {
		switch (Global.screen_width) {
		case 320:
			return 47;
		case 480:
			return 71;
		case 540:
			return 80;
		case 640:
			return 94;
		case 600:
			return 89;
		case 720:
			return 106;
		case 800:
			return 118;
		default:
			return Global.screen_width / 7;
		}
	}

	public static int getMsgBtnHeight() {
		switch (Global.screen_width) {
		case 320:
			return 47;
		case 480:
			return 71;
		case 540:
			return 80;
		case 640:
			return 94;
		case 600:
			return 89;
		case 720:
			return 106;
		case 800:
			return 118;
		default:
			return Global.screen_width / 7;
		}
	}

	public static int getLogoBtnBgWidth() {
		switch (Global.screen_width) {
		case 320:
			return 290;
		case 480:
			return 435;
		case 540:
			return 490;
		case 640:
			return 580;
		case 600:
			return 544;
		case 720:
			return 653;
		case 800:
			return 725;
		default:
			return (int) (Global.screen_width * 0.9f);
		}
	}

	public static int getLogoBtnBgHeight() {
		switch (Global.screen_width) {
		case 320:
			return 58;
		case 480:
			return 87;
		case 540:
			return 98;
		case 640:
			return 116;
		case 600:
			return 109;
		case 720:
			return 131;
		case 800:
			return 145;
		default:
			return Global.screen_width / 5;
		}
	}

	public static int getLogoBigBgWidth() {
		switch (Global.screen_width) {
		case 320:
			return 320;
		case 480:
			return 480;
		case 540:
			return 540;
		case 640:
			return 640;
		case 600:
			return 600;
		case 720:
			return 720;
		case 800:
			return 800;
		default:
			return Global.screen_width;
		}
	}

	public static int getLogoBigBgHeight() {
		switch (Global.screen_width) {
		case 320:
			return 476;
		case 480:
			return 713;
		case 540:
			return 804;
		case 640:
			return 952;
		case 600:
			return 893;
		case 720:
			return 1071;
		case 800:
			return 1190;
		default:
			return Global.screen_width;
		}
	}

	public static int getMidleMengCengWidth() {
		switch (Global.screen_width) {
		case 320:
			return 320;
		case 480:
			return 480;
		case 540:
			return 540;
		case 640:
			return 640;
		case 600:
			return 600;
		case 720:
			return 720;
		case 800:
			return 800;
		default:
			return Global.screen_width;
		}
	}

	public static int getMidleMengCengHeight() {
		switch (Global.screen_width) {
		case 320:
			return 60;
		case 480:
			return 90;
		case 540:
			return 102;
		case 640:
			return 120;
		case 600:
			return 113;
		case 720:
			return 135;
		case 800:
			return 150;
		default:
			return (int) (Global.screen_width / 5.3f);
		}
	}

	public static int getTopTitleWidth() {
		switch (Global.screen_width) {
		case 320:
			return 320;
		case 480:
			return 480;
		case 540:
			return 540;
		case 640:
			return 640;
		case 600:
			return 600;
		case 720:
			return 720;
		case 800:
			return 800;
		default:
			return Global.screen_width;
		}
	}

	public static int getTopTitleHeight() {
		switch (Global.screen_width) {
		case 320:
			return 50;
		case 480:
			return 75;
		case 540:
			return 85;
		case 640:
			return 100;
		case 600:
			return 94;
		case 720:
			return 113;
		case 800:
			return 125;
		default:
			return (int) (Global.screen_width / 6.4f);
		}
	}

	public static int getCrameMengCengWidth() {
		switch (Global.screen_width) {
		case 320:
			return 320;
		case 480:
			return 480;
		case 540:
			return 540;
		case 640:
			return 640;
		case 600:
			return 600;
		case 720:
			return 720;
		case 800:
			return 800;
		default:
			return Global.screen_width;
		}
	}

	public static int getCrameMengCengHeight() {
		switch (Global.screen_width) {
		case 320:
			return 124;
		case 480:
			return 185;
		case 540:
			return 210;
		case 640:
			return 248;
		case 600:
			return 233;
		case 720:
			return 279;
		case 800:
			return 310;
		default:
			return (int) (Global.screen_width / 2.6f);
		}
	}

	public static int getPhotoInfoSendMsgWidth() {
		switch (Global.screen_width) {
		case 320:
			return 280;
		case 480:
			return 420;
		case 540:
			return 473;
		case 640:
			return 560;
		case 600:
			return 525;
		case 720:
			return 630;
		case 800:
			return 780;
		default:
			return (int) (Global.screen_width / 1.1f);
		}
	}

	public static int getPhotoInfoSendMsgHeight() {
		switch (Global.screen_width) {
		case 320:
			return 35;
		case 480:
			return 53;
		case 540:
			return 59;
		case 640:
			return 70;
		case 600:
			return 66;
		case 720:
			return 79;
		case 800:
			return 88;
		default:
			return Global.screen_width / 9;
		}
	}

	public static int getHomeASDlideButtonHeight() {
		switch (Global.screen_width) {
		case 320:
			return 98;
		case 480:
			return 147;
		case 540:
			return 165;
		case 640:
			return 197;
		case 600:
			return 184;
		case 720:
			return 222;
		case 800:
			return 245;
		default:
			return Global.screen_width / 3;
		}
	}

	public static int getHomeADSlideWidth() {
		return Global.screen_width;
	}

	public static int getHomeADSlideHeight() {
		switch (Global.screen_width) {
		case 320:
			return 153;
		case 480:
			return 230;
		case 540:
			return 258;
		case 640:
			return 306;
		case 600:
			return 287;
		case 720:
			return 344;
		case 800:
			return 382;
		default:
			return Global.screen_width / 2;
		}
	}

	public static int getHomeTableWidth() {
		switch (Global.screen_width) {
		case 320:
			return 157;
		case 480:
			return 236;
		case 540:
			return 265;
		case 640:
			return 315;
		case 600:
			return 296;
		case 720:
			return 355;
		case 800:
			return 394;
		default:
			return Global.screen_width / 2;
		}
	}

	public static int getHomeTableHeight() {
		switch (Global.screen_width) {
		case 320:
			return 154;
		case 480:
			return 231;
		case 540:
			return 259;
		case 640:
			return 306;
		case 600:
			return 288;
		case 720:
			return 345;
		case 800:
			return 383;
		default:
			return Global.screen_width / 2;
		}
	}

	/**
	 * 获取布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getViewLayoutParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getViewWidth(0),
				getViewHeight(0));
		return params;
	}

	/**
	 * 获取首页home按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getHomeButtonLayoutParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getHomeBtnWidth(),
				getHomeBtnHeight());
		return params;
	}

	/**
	 * 获取首页dynamic&&message按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getDynamicMsgLayoutParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getDyMsgWidth(),
				getDyMsgHeight());
		return params;
	}

	/**
	 * 获取首页dynamicTop按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getDynamicTopParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getDy_TopWidth(),
				getDy_TopHeight());
		return params;
	}

	/**
	 * 获取每一页两边按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getBackBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getBackBtnWidth(),
				getBackBtnHeight());
		return params;
	}

	/**
	 * 获取每一页中间按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getMiddleBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getMiddleBtnWidth(),
				getMiddleBtnHeight());
		return params;
	}

	/**
	 * 获取相机按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getCrameBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getCrameBtnWidth(),
				getCrameBtnHeight());
		return params;
	}

	/**
	 * 获取赞按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getZanBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getZhanBtnWidth(),
				getZhanBtnHeight());
		return params;
	}

	/**
	 * 获取加关注按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getAddBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getAddBtnWidth(),
				getAddBtnHeight());
		return params;
	}

	/**
	 * 获取消息提示按钮布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getMsgBtnParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getMsgBtnWidth(),
				getMsgBtnHeight());
		return params;
	}

	/**
	 * 获取Logo页按钮背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getLogoBtnBgParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getLogoBtnBgWidth(),
				getLogoBtnBgHeight());
		return params;
	}

	/**
	 * 获取Logo背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getLogoBigBgParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getLogoBigBgWidth(),
				getLogoBigBgHeight());
		return params;
	}

	/**
	 * 获取MidleMengCeng背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getMidleMengCengParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getMidleMengCengWidth(),
				getMidleMengCengHeight());
		return params;
	}

	/**
	 * 获取TopTitle背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getTopTitleParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getTopTitleWidth(),
				getTopTitleHeight());
		return params;
	}

	/**
	 * 获取CrameMengCeng背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getCrameMengCengParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getCrameMengCengWidth(),
				getCrameMengCengHeight());
		return params;
	}

	/**
	 * 获取PhotoInfoSendMsg背景布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getPhotoInfoSendMsgParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getPhotoInfoSendMsgWidth(),
				getPhotoInfoSendMsgHeight());
		return params;
	}

	/**
	 * 获取首页大图广告布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getHomeADSlideLayoutParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getHomeADSlideWidth(),
				getHomeADSlideHeight());
		return params;
	}

	/**
	 * 获取首页表格布局
	 * 
	 * @param context
	 * @return
	 */
	public static LayoutParams getHomeTableLayoutParmas(Activity context) {
		if (Global.screen_width == 0)
			LockScreenApplication.getScreenSize();
		LayoutParams params = new LayoutParams(getHomeTableWidth(),
				getHomeTableHeight());
		return params;
	}

	/** DP 转 相素 */
	public static int dip2px(float dpValue) {
		final float scale = Global.gContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px(像素) 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = Global.gContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/** 获得状态栏高度 */
	public static int getStatusHeight(Context context) {
		Rect frame = new Rect();
		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}
}
