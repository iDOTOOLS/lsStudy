package com.yp.lockscreen.work;

import android.content.Context;

import com.yp.lockscreen.utils.DownLoadFileUtils;

public class DownLoadFileThread extends Thread{
	
	private String url;
	
	private String name;
	
	private String path;
	
	private DownLoadFileUtils fileUtil;
	
	private Context context;
	
	public DownLoadFileThread(String url, String name, String path,
			DownLoadFileUtils fileUtil,Context context) {
		super();
		this.url = url;
		this.name = name;
		this.path = path;
		this.fileUtil = fileUtil;
		this.context = context;
	}

	@Override
	public void run() {
		super.run();
		fileUtil.download(url, path, name, context);
	}
}
