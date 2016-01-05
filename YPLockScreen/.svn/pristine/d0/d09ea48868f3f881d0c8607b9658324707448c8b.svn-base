package com.yp.lockscreen.work;

import android.os.Handler;
import android.os.Message;

import com.yp.enstudy.DownloadManager.DownLoadCallBack;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.DownLoadFileUtils;
import com.yp.lockscreen.utils.DownLoadFileUtils.DownLoadFileCallBack;

public class LockDownLoadThread extends Thread {

	public static final long DOWNLOAD_SUCCESS = -1;

	public static final long DOWNLOAD_ERROR = -2;

	public static final int DOWNLOAD_VOICE_FLAG = 1;

	public static final int DOWNLOAD_DB_FLAG = 2;
	
	public static final int DOWNLOAD_IMG_FLAG = 3;

	private Handler mCallbackHandler;
	private String mName;
	private int mFlag;

	public LockDownLoadThread(String name, Handler callback, int flag) {

		this.mCallbackHandler = callback;
		this.mName = name;
		this.mFlag = flag;

	}

	@Override
	public void run() {

		super.run();
		switch (mFlag) {
		case DOWNLOAD_VOICE_FLAG:
			Global.gDownLoad.downloadVoice(mName, new DownLoadVoiceCallBack());
			break;
		case DOWNLOAD_DB_FLAG:
			Global.gDownLoad.downloadDB(mName, new DownLoadVoiceCallBack());
			break;
		}
	}

	private class DownLoadVoiceCallBack implements DownLoadCallBack {

		@Override
		public boolean success() {
			Message msg = mCallbackHandler.obtainMessage();
			msg.obj = DOWNLOAD_SUCCESS;
			mCallbackHandler.sendMessage(msg);
			return false;
		}

		@Override
		public boolean error() {
			Message msg = mCallbackHandler.obtainMessage();
			msg.obj = DOWNLOAD_ERROR;
			mCallbackHandler.sendMessage(msg);
			return false;
		}

		@Override
		public long downSize(long size) {

			Message msg = mCallbackHandler.obtainMessage();
			msg.obj = size;
			mCallbackHandler.sendMessage(msg);
			return 0;
		}

	}

}
