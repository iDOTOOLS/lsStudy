package com.yp.lockscreen.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.yp.enstudy.utils.DeviceUtil;
import com.yp.enstudy.utils.StringUtil;

import android.content.Context;

public class DownLoadFileUtils {
	private DownLoadFileCallBack mCallback;
	private boolean isDownLoadStop = false;

	public DownLoadFileUtils(DownLoadFileCallBack callback) {
		mCallback = callback;
		isDownLoadStop = false;
	}

	public void setDownLoadStop(boolean isDownLoadStop) {
		this.isDownLoadStop = isDownLoadStop;
	}

	private File mFile;
	private String tmpPath;
	private File mFileRename;
	private int downLoadFileSize;

	/**
	 * @param url
	 *            文件的 url
	 * @param path
	 *            文件保存的全路径
	 * @param name
	 *            文件名称，为null时，则用url来做名称
	 * @param context
	 *            context
	 * @return
	 */
	public boolean download(String url, String path, String name,
			Context context) {
		boolean downError = false;
		float fileSize = 0;
		try {
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.setConnectTimeout(6000);
			conn.setReadTimeout(6000);
			conn.setRequestProperty("Accept-Encoding", "");
			conn.connect();
			InputStream is = conn.getInputStream();
			if (StringUtil.isEmpty(name)) {
				path = path + "/"
						+ StringUtil.getNameByUrl(conn.getURL().toString());
			} else {
				path = path + "/" + name;
			}

			fileSize = (float)conn.getContentLength();// 根据响应获取文件大小

			if (DeviceUtil.getSdCardHaveSize() == -1) {
				// errorMsg = "sd卡不存在";
				mCallback.error(this);
				return false;
			} else if (DeviceUtil.getSdCardHaveSize() < (fileSize / 1024)) { // 存储空间小于下载文件
				mCallback.error(this);														// 不去下载
				return false;
			}

			if (fileSize <= 0)
				throw new RuntimeException("无法获知文件大小 ");
			if (is == null)
				throw new RuntimeException("stream is null");

			try {
				mFile = new File(path);
				if (!mFile.exists()) { // 如果不存在 先命名为临时文件
					tmpPath = path.substring(0, path.length() - 4) + ".tmp";

				} else {
					return true;
				}
				LogHelper.d("wifi_download", mFile.getName() + " 下载开始");
				FileOutputStream fos = new FileOutputStream(tmpPath);
				// 把数据存入路径+文件名
				byte buf[] = new byte[1024 * 50]; // 100K
				downLoadFileSize = 0;
				do {
					try {
						int numread = is.read(buf);
						if (numread == -1) {
							break;
						}
						fos.write(buf, 0, numread);
						downLoadFileSize += numread;

						if (isDownLoadStop)
							break;

						mCallback.downSize(downLoadFileSize/fileSize);
					} catch (Exception e) {
						downError = true;
						break;
					}
				} while (true && !isDownLoadStop);

				if (isDownLoadStop) {
					return true;
				}

				if (!downError) {
					LogHelper.d("download", mFile.getName() + " 下载完成");
					/** 下载完成后 重命名 */
					mFileRename = new File(path);
					mFile = new File(tmpPath);
					mFile.renameTo(mFileRename);
					mCallback.success();
					return true;
				} else {
					return false;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				LogHelper.d("download", mFile.getName() + "下载失败");
				mCallback.error(this);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 获得真实下载地址 非线程 */
	public String getDonwloadRealUrl(String url) {
		try {
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.setConnectTimeout(6000);
			conn.setReadTimeout(6000);
			conn.setRequestProperty("Accept-Encoding", "");
			conn.connect();
			InputStream is = conn.getInputStream();
			// 如果不是带有APK下载的原始地址 则请求原始地址 此原始地址 只能在conn.connect()
			// conn.getInputStream()之后 才能获得到
			return conn.getURL().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @author PC lixinbin
	 * 
	 * 下载监听器
	 *
	 */
	public interface DownLoadFileCallBack {
		public boolean success();

		public boolean error(DownLoadFileUtils downLoadFileUtils);

		public long downSize(float size);
	}

}