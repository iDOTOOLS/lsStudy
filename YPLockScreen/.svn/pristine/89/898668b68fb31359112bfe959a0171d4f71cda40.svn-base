package com.yp.lockscreen.work;

import com.yp.enstudy.utils.LogHelper;
import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {

	/**
	 * 指定的通知
	 */
	private static final int Notification_ID_BASE = 110;
	
	private Notification notification;

	private NotificationManager notyMgr;

	private PendingIntent jumpIntent;
	
	private Context mCxt;

	@Override
	public void onReceive(Context context, Intent intent) {

		mCxt = context;
		notyMgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		init();

	}
	void init(){
		
		Intent intent = new Intent(mCxt, MainActivity.class);  
		jumpIntent = PendingIntent.getActivity(mCxt, 0, intent, 0);
		
		notification = new Notification();

		notification.icon = R.drawable.icon;
		notification.tickerText = mCxt.getString(R.string.review_time);
		notification.defaults |= Notification.DEFAULT_SOUND;  
		notification.defaults |= Notification.DEFAULT_VIBRATE;  
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(mCxt, mCxt.getString(R.string.app_name), mCxt.getString(R.string.review_time), jumpIntent);
		
		notyMgr.notify(Notification_ID_BASE,notification);
		
	}

}
