package com.yp.lockscreen;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.work.LockDownLoad;
import com.yp.lockscreen.work.LockDownLoadThread;

public class DownloadVoice extends IntentService {

	private RemoteViews notifyView;
	private NotificationCompat.Builder mBuilder;
	private NotificationManager mNotiManager;
	public static int downID = 1;
	private Notification mNotification;
	private static boolean isDownLoadStop = false;
	public static boolean isDownLoading = false;
//	private LockDownLoadThread mDownThread;
	private LockDownLoad mDownUtils = new LockDownLoad();;

	private int fileSize;
	private int downLoadFileSize;

	public DownloadVoice(){
		super("download apk");
	}
	
	public DownloadVoice(String name) {
		super(name);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notifyView = new RemoteViews(getPackageName(),R.layout.notify_custom_download);
		mBuilder = new NotificationCompat.Builder(this);
		notifyView.setProgressBar(R.id.progress, fileSize, downLoadFileSize,false);
//		notifyView.setViewVisibility(R.id.del_img, View.GONE);
//		notifyView.setViewVisibility(R.id.retry_btn, View.GONE);
		notifyView.setViewVisibility(R.id.progress, View.VISIBLE);
		notifyView.setViewVisibility(R.id.content_txt, View.GONE);
		notifyView.setTextViewText(R.id.title, getText(R.string.downloading));
		mBuilder.setAutoCancel(false);
		mBuilder.setContent(notifyView);
		mBuilder.setSmallIcon(R.drawable.icon);
		mBuilder.setTicker(getString(R.string.app_name));
		mNotiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT < 14) {
			PendingIntent intent = PendingIntent.getActivity(this, 0,getLaunchIntent(DownloadVoice.this), 0);
			Notification noti0 = new Notification(R.drawable.icon,"com.yp.lockscreen", System.currentTimeMillis());
			noti0.tickerText = getString(R.string.app_name);
			noti0.flags = Notification.FLAG_NO_CLEAR;
			noti0.contentView = notifyView;
			noti0.contentIntent = intent;
			mNotiManager.notify(downID, noti0);
		} else {
			mNotification = mBuilder.build();
			mNotification.flags = Notification.FLAG_NO_CLEAR;
			mNotiManager.notify(downID, mNotification);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			isDownLoadStop = intent.getBooleanExtra("isStop", false);
		}
		String name = intent.getStringExtra("name");
		fileSize = intent.getIntExtra("file_size", 0);

		if (isDownLoadStop) {
			mNotiManager.cancel(downID);
//			if (mDownThread != null) {
//				mDownThread.interrupt();
//			}
			isDownLoading = false;
		} else {
		    mDownUtils.download(LockDownLoadThread.DOWNLOAD_VOICE_FLAG, name,mHandler);
//			if (mDownThread == null) {
//				isDownLoading = true;
//				mDownThread = new LockDownLoadThread(name, mHandler,LockDownLoadThread.DOWNLOAD_VOICE_FLAG);
//				mDownThread.start();
//				Toast.makeText(DownloadVoice.this, "开始下载...", Toast.LENGTH_LONG).show();
//			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			long back = (Long) msg.obj;
			if (back == LockDownLoadThread.DOWNLOAD_SUCCESS) {

				if (isDownLoadStop)
					return;
				notifyView.setProgressBar(R.id.progress, fileSize,downLoadFileSize, false);
//				notifyView.setViewVisibility(R.id.del_img, View.GONE);
//				notifyView.setViewVisibility(R.id.retry_btn, View.GONE);
				notifyView.setViewVisibility(R.id.progress, View.GONE);
				notifyView.setViewVisibility(R.id.content_txt, View.VISIBLE);
				notifyView.setTextViewText(R.id.title, getString(R.string.download_success));
				mBuilder.setAutoCancel(true);
				mBuilder.setContent(notifyView);
				mBuilder.setSmallIcon(R.drawable.icon);
				mBuilder.setContentIntent(PendingIntent.getActivity(DownloadVoice.this, 0, getLaunchIntent(DownloadVoice.this), 0));
				mNotiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				if (Build.VERSION.SDK_INT < 14) {
					PendingIntent intent = PendingIntent.getActivity(DownloadVoice.this, 0,getLaunchIntent(DownloadVoice.this), 0);
					Notification noti0 = new Notification(R.drawable.icon, "com.yp.lockscreen",System.currentTimeMillis());
					noti0.flags = Notification.FLAG_AUTO_CANCEL;
					noti0.contentView = notifyView;
					noti0.contentIntent = intent;
					mNotiManager.notify(downID, noti0);
				} else {
					mNotification = mBuilder.build();
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotiManager.notify(downID, mNotification);
				}
				isDownLoading = false;

			} else if (back == LockDownLoadThread.DOWNLOAD_ERROR) {
				isDownLoading = false;
				showErrorNotifcation();
				Toast.makeText(DownloadVoice.this, getString(R.string.download_error_retry), Toast.LENGTH_LONG).show();
			} else if (back > 0) {
				if (isDownLoadStop)
					return;
				downLoadFileSize = (int)back;
				notifyView.setProgressBar(R.id.progress, fileSize,downLoadFileSize, false);
//				notifyView.setViewVisibility(R.id.del_img, View.GONE);
//				notifyView.setViewVisibility(R.id.retry_btn, View.GONE);
				notifyView.setViewVisibility(R.id.progress, View.VISIBLE);
				notifyView.setViewVisibility(R.id.content_txt, View.GONE);
				notifyView.setTextViewText(R.id.title, getString(R.string.downloading));
				mBuilder.setAutoCancel(false);
				mBuilder.setContent(notifyView);
				mBuilder.setSmallIcon(R.drawable.icon);
				mNotiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				if (Build.VERSION.SDK_INT < 14) {
					PendingIntent intent = PendingIntent.getActivity(DownloadVoice.this, 0,getLaunchIntent(DownloadVoice.this), 0);
					Notification noti0 = new Notification(R.drawable.icon, "com.yp.lockscreen",System.currentTimeMillis());
					noti0.flags = Notification.FLAG_NO_CLEAR;
					noti0.contentView = notifyView;
					noti0.contentIntent = intent;
					mNotiManager.notify(downID, noti0);
				} else {
					mNotification = mBuilder.build();
					mNotification.flags = Notification.FLAG_NO_CLEAR;
					mNotiManager.notify(downID, mNotification);
				}
				isDownLoading = true;
			}
		}
	};

	private void showErrorNotifcation() {
		notifyView = new RemoteViews(getPackageName(),
				R.layout.notify_custom_download);
		notifyView.setProgressBar(R.id.progress, fileSize, downLoadFileSize,
				false);
		notifyView.setTextViewText(R.id.title, getString(R.string.downloading));
		notifyView.setViewVisibility(R.id.progress, View.GONE);

		notifyView.setViewVisibility(R.id.content_txt, View.VISIBLE);
		notifyView.setTextViewText(R.id.content_txt, getString(R.string.download_error_retry));
		// setStopDownPendingIntent();
		// setRetryDownPendingIntent();
		mBuilder.setAutoCancel(true);
		mBuilder.setContent(notifyView);
		if (Build.VERSION.SDK_INT < 14) {

		} else {
			mNotification = mBuilder.build();
			mNotification.flags = Notification.FLAG_NO_CLEAR;
			mNotiManager.notify(downID, mNotification);
		}
	}

	/** 获得应用入口intent */
	public static Intent getLaunchIntent(Context context) {
		PackageManager pm = context.getPackageManager();
		return pm.getLaunchIntentForPackage(context.getPackageName());
	}

}
