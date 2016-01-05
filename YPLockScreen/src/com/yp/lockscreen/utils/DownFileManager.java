package com.yp.lockscreen.utils;

import android.content.Context;


public class DownFileManager {

	private Context mContext;

	private String downName;

	public DownFileManager(Context context) {
		this.mContext = context;
	}
//	public boolean downloadImg(){
//		
//	}

//	public boolean downloadImg(String name, final String url,
//			final DownLoadFileCallBack callback) {
//		this.downName = name;
//		final DownLoadUtils mUtils = new DownLoadUtils(callback);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				if (mUtils.download(url + downName, context)) {
//					mZipUtils = new ZipUtils();
//					File file = new File(Constans.PATH_DOWNLOAD + downName);
//					if (file.exists()) {
//						try {
//							String dbPath = "/data/data/"
//									+ context.getPackageName() + "/databases/";
//							mZipUtils.upZipFile(file, dbPath);
//							LogHelper.d("unzip", "解压成功!");
//							if (callback != null)
//								callback.success();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						file.delete();
//					}
//				} else {
//					if (callback != null)
//						callback.error();
//				}
//			}
//		}).start();
//		return false;
//	}

}
