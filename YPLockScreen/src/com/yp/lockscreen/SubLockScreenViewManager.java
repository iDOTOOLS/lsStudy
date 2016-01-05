package com.yp.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.DXLockScreenViewManager;
import com.dianxinos.lockscreen_sdk.StatManager;
import com.dianxinos.lockscreen_sdk.monitor.DXPhoneStateListener;
import com.dianxinos.lockscreen_sdk.views.DXLockScreenSDKBaseView;
import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.view.WordLockView;

public class SubLockScreenViewManager extends DXLockScreenViewManager {

	public SubLockScreenViewManager(Context context, DXLockScreenMediator mediator) {
		super(context, mediator);
	}

	@Override
	public DXLockScreenSDKBaseView createLockScreenView(Context context, DXLockScreenMediator mediator) {
		WordLockView lockView = new WordLockView(context, mediator);
		MobclickAgent.onResume(context);
		return lockView;
	}

	@Override
	protected void hanldeLiveWallpaper(LayoutParams params) {
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.flags = WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
		super.hanldeLiveWallpaper(params);
	}

	@Override
	protected void handleUnlockScreen(int position) {

		Intent unlockScreenIntent = new Intent(Intent.ACTION_MAIN);
		unlockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		boolean startActivityFlag = true;

		if (position == DXLockScreenUtils.UNLOCK_POSITION_NEW_SMS || position == DXLockScreenUtils.UNLOCK_POSITION_VIEW_SMS) {// ���Ž���

			unlockScreenIntent.setType("vnd.android.cursor.dir/mms");
			unlockScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			unlockScreenIntent.addCategory(Intent.CATEGORY_DEFAULT);

		} else if (position == DXLockScreenUtils.UNLOCK_POSITION_NEW_CALL || position == DXLockScreenUtils.UNLOCK_POSITION_VIEW_CALL) {// �绰����

			unlockScreenIntent.setAction("android.intent.action.CALL_BUTTON");

		} else if (position == DXLockScreenUtils.UNLOCK_POSITION_CAMERA) {// ������

			unlockScreenIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		} else if (position == DXLockScreenUtils.UNLOCK_POSITION_DXHOT) {// ����hot����

			unlockScreenIntent.setAction("com.dianxinos.dxhot.action.MAIN");
			unlockScreenIntent.addCategory(Intent.CATEGORY_DEFAULT);
			// if has install home which contains LockScreenManager(pro or
			// elegant),start dxhome instead.
			// ArrayList<String> dxHomes =
			// DXLockScreenUtils.queryActivitysByAction(mContext,
			// "com.dianxinos.dxhot.action.MAIN");
			unlockScreenIntent.setAction("android.intent.action.CALL_BUTTON");
			// if (dxHomes.size() > 0) {
			// DXLockScreenUtils.sortAppsByInstallTime(mContext, dxHomes);
			// unlockScreenIntent.setPackage(dxHomes.get(0));
			// }
		} else {

			startActivityFlag = false;

		}

		if (startActivityFlag) {
			try {
				mContext.startActivity(unlockScreenIntent);
			} catch (Exception e) {

			}
		}
		StatManager.reportUnlockType(position);// 统计

		// for removing lockscreen view.
		unlock();

        MobclickAgent.onPause(mContext);
	}

	@Override
	protected boolean needCallMessageObservers() {
		return true;
	}
}
