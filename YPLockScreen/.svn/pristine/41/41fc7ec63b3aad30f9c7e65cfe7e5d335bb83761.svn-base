package com.yp.lockscreen.work;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yp.enstudy.utils.LogHelper;
import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.SetWallpaper;
import com.yp.lockscreen.activity.StudyPlaneActivity;
import com.yp.lockscreen.bean.WallpaperVO;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.DownLoadFileUtils;
import com.yp.lockscreen.utils.DownLoadFileUtils.DownLoadFileCallBack;

public class WallpagerAdapter extends BaseAdapter {

	private Activity mContext;

	private ArrayList<WallpaperVO> wallpagers;

	private DisplayImageOptions options;

	private ImageLoader imageLoader;

	public WallpagerAdapter(Activity mContext) {
		super();
		this.mContext = mContext;
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.cache) // 在ImageView加载过程中显示图片
				// .showImageForEmptyUri(R.drawable.cache)//image连接地址为空时
				// .showImageOnFail(R.drawable.cache)//image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisk(true)// 加载图片时会在磁盘中加载缓存
				.considerExifParams(true)
				// .displayer(new
				// RoundedBitmapDisplayer(20))//设置用户加载图片task(这里是圆角图片显示)
				.build();
	}

	public WallpagerAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return wallpagers.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		WallViewHolder holder = null;
		if (v == null) {
			holder = new WallViewHolder();
			v = LayoutInflater.from(mContext).inflate(R.layout.download_wallpager_item, null);
			holder.downImg = (ImageView) v.findViewById(R.id.download_wallpager_item_unload_img);
			holder.showImg = (ImageView) v.findViewById(R.id.download_wallpager_item_img);
			holder.downLayout = (LinearLayout)v.findViewById(R.id.botoom_layout);
			v.setTag(holder);
		} else {
			holder = (WallViewHolder) v.getTag();
		}
		imageLoader.displayImage(wallpagers.get(position).getWallpaperUrl(),holder.showImg, options);

		if (wallpagers.get(position).isDownload) {
			holder.downImg.setVisibility(View.GONE);
			holder.downLayout.setVisibility(View.GONE);
		} else {
			holder.downImg.setVisibility(View.VISIBLE);
			holder.downLayout.setVisibility(View.VISIBLE);
		}

		holder.showImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (wallpagers.get(position).isDownload) {
					//保存设置
					LockConfigMgr.setImageName(mContext, wallpagers.get(position).fileName);
					mContext.setResult(mContext.RESULT_OK);
			        mContext.finish();
				} else {
					downloadFile(wallpagers.get(position).wallpaperUrl);
				}
			}
		});
		return v;
	}

	public void setDataList(ArrayList<WallpaperVO> wallpagers) {
		this.wallpagers = wallpagers;
		resetList(this.wallpagers);
		notifyDataSetChanged();
	}

	private class WallViewHolder {
		private ImageView showImg;
		private ImageView downImg;
		private LinearLayout downLayout;
	}
	
	   /**重构*/
    private ArrayList<WallpaperVO> resetList( ArrayList<WallpaperVO> list){
        for(WallpaperVO vo:list){
            String fileName = isFileExists(vo.wallpaperUrl);
            if(!TextUtils.isEmpty(fileName)){
                vo.fileName =fileName;
                vo.isDownload = true;
            }else{
                vo.isDownload = false;
            }
        }
        return list;
    }
    
    private File mCheckFile;
    private String isFileExists(String url){
            String fileName = url.substring(url.lastIndexOf("/")+1, url.length());
            String filePath = Global.IMAGE_PATH + "/" + fileName;
            mCheckFile = new File(filePath);
            if(mCheckFile.exists())
                return fileName;
            else 
                return "";
    }
    
    private void refreshAdapter(){
        if(wallpagers!=null){
            resetList(wallpagers);
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

	private synchronized void downloadFile(String url) {
		final Dialog dialog = new AlertDialog.Builder(mContext).create();
		dialog.show();
		View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_download_img, null);
		dialog.getWindow().setContentView(v);
		final ProgressBar bar = (ProgressBar)v.findViewById(R.id.dialog_download_img_content_bar);
		final Button btn = (Button)v.findViewById(R.id.dialog_download_img_content_canncel_btn);
		bar.setMax(100);
		final DownLoadFileUtils downLoadFileUtils = new DownLoadFileUtils(new DownLoadFileCallBack() {
			@Override
			public boolean success() {
				dialog.dismiss();
				refreshAdapter();
				return false;
			}

			@Override
			public boolean error(DownLoadFileUtils downLoadFileUtils) {
				downLoadFileUtils.setDownLoadStop(true);
				dialog.dismiss();
				Toast.makeText(mContext, R.string.download_error_retry, Toast.LENGTH_SHORT).show();
				return false;
			}

			@Override
			public long downSize(float size) {
//				LogHelper.i("图片下载ing.......", size + "kb");
				int tep = (int)(size*100);
				bar.setProgress(tep);
				return 0;
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				downLoadFileUtils.setDownLoadStop(true);
				dialog.dismiss();
			}
		});
		
		new DownLoadFileThread(url, null, Global.IMAGE_PATH,downLoadFileUtils
				, mContext).start();
		
	}
}
