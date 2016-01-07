package com.yp.lockscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.DXLockScreenViewManager;
import com.dianxinos.lockscreen_sdk.LockScreenBaseService;
import com.dianxinos.lockscreen_sdk.ResourceManager;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.lockscreen.monitor.LockPhoneStateListener;

public class StaticService extends LockScreenBaseService {

    public static final String action_wakeSelf = "com.dotools.fls.wakeSelf";
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("StaticService", "onStartCommand:"+intent.getAction());
        Context applicationContext = this.getApplicationContext();
        if(GlobalConfigMgr.getOpenLockScreenFlag(applicationContext)) {
            AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC,  System.currentTimeMillis() + 15000, getP1(applicationContext));
        }
        return super.onStartCommand(intent, flags, startId);

    }
    
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
        Log.i("StaticService", "onCreate");
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
        Log.i("StaticService", "onDestroy");
        Context ct = getApplicationContext();
        if (GlobalConfigMgr.getOpenLockScreenFlag(ct)) {
            try {
                Intent i = new Intent();
                i.setClass(ct, StaticService.class);
                ct.startService(i);// 启动service
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

	

    private PendingIntent pend1 ;

    private PendingIntent getP1(Context ct) {
        if (pend1 == null) {
            pend1 = getTimer(ct, action_wakeSelf);
        }
        return pend1;
    }
    

    public static PendingIntent getTimer(Context ct,String action) {
        Intent intent1 = new Intent();
        intent1.setPackage("com.yp.lockscreen");
        intent1.setAction(action);
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent1.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        PendingIntent pi1 = PendingIntent.getService(ct, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi1;
    }
}