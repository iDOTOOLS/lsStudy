package com.yp.lockscreen;

import android.content.Context;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.DXLockScreenViewManager;
import com.dianxinos.lockscreen_sdk.LockScreenBaseService;
import com.dianxinos.lockscreen_sdk.ResourceManager;
import com.yp.lockscreen.monitor.LockPhoneStateListener;

public class StaticService extends LockScreenBaseService {

	@Override
	public void checkUpdate() {

	}

	@Override
	public DXLockScreenViewManager createLockScreenViewManager(Context context, DXLockScreenMediator mediator) {
		LockPhoneStateListener listener = new LockPhoneStateListener(context, mediator);
		listener.registerListener();
		return new SubLockScreenViewManager(context, mediator);
	}

	@Override
	public void onCreate() {
		DXLockScreenUtils.DBG = true;
		super.onCreate();

		ClassicResourceManager rm = new ClassicResourceManager();
		rm.setContext(this.getApplicationContext());
		ResourceManager.setResourceManager(rm);// 将这个对象赋值给自己的一个变量，以便以后调用
		DXLockScreenUtils.SWITCH_GO_TO_DXHOT = false;
		
		try {
		    stopForeground(true);         //关闭 service 服务显示在通知栏上
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}