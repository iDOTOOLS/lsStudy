package com.yp.lockscreen.activity;

import java.io.File;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.enstudy.utils.NumberUtil;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.ImageUtil;

public class SetWallpaper extends Activity implements OnClickListener {

	private LinearLayout backLy;

	private RelativeLayout wallPagerLy;

	private RelativeLayout phoneLy;

	private RelativeLayout homeLy;

	private ImageView preImg;

	private Button saveBtn;

	private Bitmap wallBm;

	private boolean isChangeWall = false;

	private static final int PHOTO_REQUEST_CODE = 0;
	private static final int PHOTORESOULT = 1;
	
	public static final int REQUEST_WALL_LIB_CODE = 2;

	private int tempFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wallpager_activity);
		tempFlag = GlobalConfigMgr.getWallFlag(this);
		tempFlag = tempFlag<0?0:tempFlag;
		initViews();
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

	void initViews() {
		backLy = (LinearLayout) findViewById(R.id.wall_activity_back_ly);
		wallPagerLy = (RelativeLayout) findViewById(R.id.wallpager_activity_choose_lib_ry);
		phoneLy = (RelativeLayout) findViewById(R.id.wallpager_activity_choose_photo_ry);
		homeLy = (RelativeLayout) findViewById(R.id.wallpager_acticity_choose_home_ry);
		preImg = (ImageView) findViewById(R.id.wallpager_activity_pre_img);
		saveBtn = (Button) findViewById(R.id.wallpager_activity_sure_btn);

		saveBtn.setOnClickListener(this);
		backLy.setOnClickListener(this);
		wallPagerLy.setOnClickListener(this);
		homeLy.setOnClickListener(this);
		phoneLy.setOnClickListener(this);
		switch (GlobalConfigMgr.getWallFlag(this)) {
		case 0:
			// 壁纸库的照片
			preImg.setImageBitmap(ImageUtil.getBitmapFromLocal(Global.IMAGE_PATH + "/", LockConfigMgr.getImageName(this)));
			break;
		case 1:
			preImg.setImageBitmap(ImageUtil.getBitmapFromLocal(Global.IMAGE_PATH + File.separator, "user_define_bg"));
			break;
		case 2:
			WallpaperManager wallpaperManager = WallpaperManager.getInstance(SetWallpaper.this);
			// 获取当前壁纸
			Drawable wallpaperDrawable = wallpaperManager.getDrawable();
			// 将Drawable,转成Bitmap
			wallBm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
//			Bitmap tempWallBm = optBitmap(wallBm);
			preImg.setImageBitmap(wallBm);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.wall_activity_back_ly:
			if (isChangeWall) {
				// TODO 弹出对话框，请用户确认是否保存设置
			}
			finishActivity();
			break;
		case R.id.wallpager_activity_choose_lib_ry:// 在壁纸库中选择
			tempFlag = 0;
			isChangeWall = true;
			Intent libIntent = new Intent(this, WallpagerLibActivity.class);
			startActivityForResult(libIntent, REQUEST_WALL_LIB_CODE);
			
			break;
		case R.id.wallpager_activity_choose_photo_ry:// 在手机相册中选择
			album();
			break;
		case R.id.wallpager_acticity_choose_home_ry:// 取得壁纸
			WallpaperManager wallpaperManager = WallpaperManager
					.getInstance(SetWallpaper.this);
			// 获取当前壁纸
			Drawable wallpaperDrawable = wallpaperManager.getDrawable();
			// 将Drawable,转成Bitmap
			wallBm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
//			Bitmap tempWallBm = optBitmap(wallBm);
			preImg.setImageBitmap(wallBm);
			isChangeWall = true;
			tempFlag = 2;
			break;
		case R.id.wallpager_activity_sure_btn:
			isChangeWall = false;
			if (tempFlag == 1 && wallBm != null) {
				ImageUtil.saveCompressBitmapToLocalDir(wallBm,Global.IMAGE_PATH + File.separator + "user_define_bg");
			}
			if(wallBm!=null)
			    GlobalConfigMgr.setWallFlag(SetWallpaper.this, tempFlag);
			finishActivity();
			break;
		}
	}

	void finishActivity() {
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case PHOTORESOULT:
					Bundle extras = data.getExtras();
					if (extras != null) {
						wallBm = extras.getParcelable("data");
//						Bitmap tempWallBm = optBitmap(wallBm);
						if (preImg != null) {
							preImg.setImageBitmap(wallBm);
							isChangeWall = true;
							tempFlag = 1;
						}
						// ImageUtil.saveCompressBitmapToLocalDir(photo,
						// Global.IMAGE_PATH+File.separator+"user_define_bg");
					}
					break;
				case PHOTO_REQUEST_CODE:
					startPhotoZoom(data.getData()); // 读取相册缩放图片
					break;
				case REQUEST_WALL_LIB_CODE:
					tempFlag = 0;
					if(preImg!=null){
					    String fileName = LockConfigMgr.getImageName(getApplicationContext());
					    Bitmap bmp = BitmapFactory.decodeFile(Global.IMAGE_PATH + File.separator + fileName);
					    preImg.setImageBitmap(bmp);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", Global.screen_width);
		intent.putExtra("aspectY", Global.screen_Height);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("outputX", Global.screen_width/5);
		intent.putExtra("outputY", Global.screen_Height/5);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	private void album() {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
			startActivityForResult(intent, PHOTO_REQUEST_CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Bitmap optBitmap(Bitmap b) {
		int tempH = b.getHeight();
		int tempW = b.getWidth();
		int width = ImageUtil.dip2px(this, 120f);
		tempH = tempH >= tempW ? tempW : tempH;
		width = tempH >= width ? width : tempH;
		return Bitmap.createBitmap(b, 0, 0, width, width);
	}
}