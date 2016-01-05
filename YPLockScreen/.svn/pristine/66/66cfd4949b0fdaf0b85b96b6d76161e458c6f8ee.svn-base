package com.yp.lockscreen.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yp.lockscreen.R;
import com.yp.lockscreen.bean.SearchWallpaperVO;
import com.yp.lockscreen.bean.WallpaperVO;
import com.yp.lockscreen.work.DownloadImgThread;
import com.yp.lockscreen.work.WallpagerAdapter;

public class WallpagerLibActivity extends Activity {

	private LinearLayout backLy;

	private GridView pagerGv;

	public static final int DOWNLOAD_DATA_FAIL = 0;

	public static final int DOWNLOAD_DATA_SUCCESS = 1;
	
	private Activity mContext;
	
	private ArrayList<WallpaperVO> vos;
	
	private WallpagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_wallpager_activity);
		this.mContext = this;
		initView();
		new DownloadImgThread(handler, this).start();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initView() {

		backLy = (LinearLayout) findViewById(R.id.download_wallpager_activity_back_ly);
		vos = new ArrayList<WallpaperVO>();
		
		backLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pagerGv = (GridView) findViewById(R.id.download_wallpager_gv);
		mAdapter = new WallpagerAdapter(mContext);
		mAdapter.setDataList(vos);
		pagerGv.setAdapter(mAdapter);
	}

	
	private Handler handler= new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case DOWNLOAD_DATA_FAIL:
				Toast.makeText(WallpagerLibActivity.this, R.string.netword_error, Toast.LENGTH_SHORT).show();
				//TODO
				break;
			case DOWNLOAD_DATA_SUCCESS:
				SearchWallpaperVO vo = (SearchWallpaperVO)msg.obj;
				vos = vo.data;
				if (vos != null) {
					mAdapter.setDataList(vos);
				}
				break;
			}
		}
	};
}
