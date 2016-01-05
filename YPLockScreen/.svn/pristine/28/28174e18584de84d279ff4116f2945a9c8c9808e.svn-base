package com.yp.lockscreen.work;

import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.yp.enstudy.utils.StringUtil;
import com.yp.lockscreen.activity.WallpagerLibActivity;
import com.yp.lockscreen.bean.SearchWallpaperVO;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.HttpUtils;
import com.yp.lockscreen.utils.HttpUtils.HttpStatusException;

public class DownloadImgThread extends Thread{

	private Handler mHandler;
	
	private Context mContext;
	
	private Gson gson;
	public DownloadImgThread(Handler mHandler, Context mContext) {
		super();
		gson = new Gson();
		this.mHandler = mHandler;
		this.mContext = mContext;
	}
	@Override
	public void run() {
		super.run();
		String retStr = null;
		try {
			retStr = HttpUtils.commonGet(mContext, Global.NET_ADDRESS);
		} catch (HttpStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (StringUtil.isEmpty(retStr)) {
			mHandler.sendEmptyMessage(WallpagerLibActivity.DOWNLOAD_DATA_FAIL);
		}else {
			Message msg = mHandler.obtainMessage(); 
			msg.what = WallpagerLibActivity.DOWNLOAD_DATA_SUCCESS;
			msg.obj = gson.fromJson(retStr, SearchWallpaperVO.class);
			mHandler.sendMessage(msg);
		}
	}
}
