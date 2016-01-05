package com.yp.lockscreen.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.views.DXLockScreenSDKBaseView;
import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.bean.Word;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.enstudy.utils.StringUtil;
import com.yp.enstudy.utils.TimeUtil;
import com.yp.lockscreen.R;
import com.yp.lockscreen.StudyManager;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.ImageUtil;
import com.yp.lockscreen.utils.LogHelper;
import com.yp.lockscreen.view.HorizontalScrollGestureLy.OnHorizontalScrollListener;
import com.yp.lockscreen.view.HorizontalScrollGestureLy.OnInterceptListener;
import com.yp.lockscreen.view.PulltoRefresh.ScrollStateListener;
import com.yp.lockscreen.work.LockScreenAdapter;
import com.yp.lockscreen.work.LockScreenAdapter.WordCallBack;

public class WordLockView extends DXLockScreenSDKBaseView {

	private Context						mContext;

	/**
	 * 横向滑动控件
	 */
	private HorizontalScrollGestureLy	mGestureLy;

	private ViewPager					mViewPager;

	/**
	 * 侧边
	 */
	private RelativeLayout				sildeRy;

	private ImageView					slideimg;

	private Vibrator					mVibrator;

	private static final String			TAG						= "WordLockView";

	private final int					SPEED					= 10;

	private int							mScroll;

	private final LayoutInflater		mInflater;

	private List<View>					adapterViews;

	private int							mCurrentItem;

	private DXLockScreenMediator		mMediator;

	private boolean						isUnlock;

	private TextView					timeText;
	private TextView					dateText;
	private TextView					chargText;
	private TextView					stateText;

	private boolean						isCharging				= true;

	private SoundPool					mPool;

	private boolean						isLockX;

	private Typeface					mFace;

	/**
	 * 锁屏页面的页数(需要复习的单词数)
	 */
	private int							lockSize;

	/**
	 * 读取到的页数(上一次解锁读到的位置)
	 */
	private int							readLockSize;

	private boolean						isOpenVibrator;

	private LockScreenAdapter			mAdapter;

	// private RadioGroup rGroup;

	private TextView					reviewText;
	private LinearLayout				mPagerPointLayout;

	private int							reviewSize;

	/**
	 * 判断是否为复习状态
	 */
	private boolean						isReViewState			= false;

	private int							lastLockWordPosition	= 0;

	public WordLockView(Context context, DXLockScreenMediator mediator) {
		super(context, mediator);
		this.mContext = context;
		this.mMediator = mediator;

		DXLockScreenUtils.DBG = true;
		mInflater = LayoutInflater.from(mContext);
		View v = mInflater.inflate(R.layout.lock_screen_main, this, true);
		readLockSize = LockConfigMgr.getLockSize(mContext);
		reviewSize = readLockSize;
		initView(v);
	}

	private ArrayList<View>	mPagePointView;

	private void addPagePoints(int pointSize) {
		mPagePointView = new ArrayList<View>();
		ImageView lockImg;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 10, 10, 10);
		for (int i = 0; i <= pointSize; i++) {
			if (i == pointSize) {
				lockImg = new ImageView(mContext);
				lockImg.setLayoutParams(params);
				lockImg.setBackgroundResource(R.drawable.unlock_point_default);
				mPagerPointLayout.addView(lockImg);
				mPagePointView.add(lockImg);
			} else if (i == 0) {
				lockImg = new ImageView(mContext);
				lockImg.setLayoutParams(params);
				lockImg.setBackgroundResource(R.drawable.lock_point_checked);
				mPagerPointLayout.addView(lockImg);
				mPagePointView.add(lockImg);
			} else {
				lockImg = new ImageView(mContext);
				lockImg.setLayoutParams(params);
				lockImg.setBackgroundResource(R.drawable.lock_point_default);
				mPagerPointLayout.addView(lockImg);
				mPagePointView.add(lockImg);
			}
		}
	}

	/***
	 * 页数切换逻辑
	 * 
	 * @param p
	 *            页数
	 * @param isUnlock
	 *            是否是解锁 解锁时 p 页数为-1
	 */
	private void changePagePointLogic(int p, boolean isUnlock) {
		if (mPagePointView == null)
			return;
		try {
			for (int i = 0; i < mPagePointView.size(); i++) {
				if ((p - lockModelFirstIndex) == i) {
					mPagePointView.get(i).setBackgroundResource(R.drawable.lock_point_checked);
				} else {
					mPagePointView.get(i).setBackgroundResource(R.drawable.lock_point_default);
				}
				if (isUnlock)
					mPagePointView.get(mPagePointView.size() - 1).setBackgroundResource(R.drawable.unlock_point_checked);
				else {
					mPagePointView.get(mPagePointView.size() - 1).setBackgroundResource(R.drawable.unlock_point_default);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshPointPageForRemove() {
		mPagerPointLayout.removeAllViews();
		mPagerPointLayout.postInvalidate();
		addPagePoints(mPagePointView.size() - 1);
		changePagePointLogic(mViewPager.getCurrentItem(), false);
		if (mViewPager.getCurrentItem() < lockModelFirstIndex) {
			isReViewState = true;
			mPagerPointLayout.setVisibility(View.GONE);
			reviewText.setVisibility(View.VISIBLE);
		} else {
			isReViewState = false;
			mPagerPointLayout.setVisibility(View.VISIBLE);
			reviewText.setVisibility(View.GONE);
		}
	}

	private AudioManager	audiomanage;
	/** 是否显示过锁屏 */
	private boolean			isShowLockScreen	= false;

	void initView(View v) {
		setOnKeyListener(mOnVolumeKeyListener);
		audiomanage = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		setFocusableInTouchMode(true);
		initGuideView(v);
		LogHelper.d("Lock", "锁屏启动");
		isShowLockScreen = LockConfigMgr.getIsShowLockScreen(mContext);
		LockConfigMgr.setIsShowLockScreen(mContext);
		mPagerPointLayout = (LinearLayout) findViewById(R.id.pages_point_layout);

		mFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidSans.ttf");

		mPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		timeText = (TextView) v.findViewById(R.id.lock_screen_main_time_text);
		dateText = (TextView) v.findViewById(R.id.lock_screen_main_date_text);
		chargText = (TextView) v.findViewById(R.id.lock_screen_main_charging_text);
		chargText.setVisibility(View.GONE);

		stateText = (TextView) v.findViewById(R.id.lock_screen_main_state_text);
		stateText.setVisibility(View.GONE);

		// rGroup =
		// (RadioGroup)v.findViewById(R.id.lock_screen_main_radio_grouop);
		reviewText = (TextView) v.findViewById(R.id.lock_screen_main_review_text);

		mGestureLy = (HorizontalScrollGestureLy) v.findViewById(R.id.lock_screen_vp_ly);
		mViewPager = (ViewPager) v.findViewById(R.id.lock_screen_vp);
		sildeRy = (RelativeLayout) v.findViewById(R.id.lock_screen_slide_ry);
		slideimg = (ImageView) v.findViewById(R.id.lock_screen_lock_img);

		initList();

		mAdapter = new LockScreenAdapter(adapterViews, mLockScreenList, mContext, handler, mPool, wordCallBack);
		mViewPager.setAdapter(mAdapter);

		// mViewPager.setCurrentItem(readLockSize);
		mViewPager.setCurrentItem(lockModelFirstIndex);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mCurrentItem = arg0;
				if (mCurrentItem < lockModelFirstIndex) {
					isReViewState = true;
					mPagerPointLayout.setVisibility(View.GONE);
					reviewText.setVisibility(View.VISIBLE);
				} else {
					isReViewState = false;
					mPagerPointLayout.setVisibility(View.VISIBLE);
					changePagePointLogic(mCurrentItem, false);
					reviewText.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (!ConfigManager.isExplain(mContext))
						adapterViews.get(mCurrentItem).findViewById(R.id.lock_word_push_view_translate).setVisibility(View.INVISIBLE);
					else {
						adapterViews.get(mCurrentItem).findViewById(R.id.lock_word_push_view_translate).setVisibility(View.VISIBLE);
					}
				}
				return false;
			}
		});
		resizeLayout();
		mGestureLy.setonHorizontalScrollListener(new OnHorizontalScrollListener() {
			@Override
			public void doOnScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				onScroll(distanceX);
			}

			@Override
			public void doOnRelease() {
				onRelease();
			}
		});
		mGestureLy.setOnInterceptListenr(new OnInterceptListener() {

			@Override
			public int isIntercept() {
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mGestureLy.getLayoutParams();

				if (adapterViews.size() == mViewPager.getCurrentItem() + 1) { // viewpager最后一个页面
					if (layoutParams.leftMargin != 0) { // 如果是一次解锁的话，那么就把事件交给里面
						return 0;
					} else if (layoutParams.leftMargin == 0) {
						return 1;
					} else {
						return 2;
					}
				} else {
					return 2;
				}
			}
		});
		setWallpaper();
		updateTimeAndDate();
	}

	WordCallBack			wordCallBack	= new WordCallBack() {
												@Override
												public void postFinish(int[] pos) {
													guideStep(pos);
												}
											};

	private Button			mGuideBtn;
	private LinearLayout	mGuideLayout;
	private RelativeLayout	mGuideUpBGLayout;
	private ImageView		mGuideLeftImg;
	private ImageView		mGuideRightImg;

	private void initGuideView(View v) {
		mGuideLayout = (LinearLayout) findViewById(R.id.guid_layout);
		mGuideLayout.setOnClickListener(null);
		mGuideUpBGLayout = (RelativeLayout) findViewById(R.id.guid_bg_up);
		mGuideBtn = (Button) findViewById(R.id.guide_btn);
		mGuideLeftImg = (ImageView) findViewById(R.id.guide_left_img);
		mGuideRightImg = (ImageView) findViewById(R.id.guide_right_img);
	}

	private int	guideStep	= 1;

	private void guideStep(int[] wordPosition) {
		if (!isShowLockScreen) {
			mGuideLayout.setVisibility(View.VISIBLE);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, wordPosition[1]);
			mGuideUpBGLayout.setLayoutParams(params);
			executeGuidStep(guideStep);
			mGuideBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					executeGuidStep(++guideStep);
				}
			});
		} else {
			mGuideLayout.setVisibility(View.GONE);
		}
	}

	private void executeGuidStep(int step) {
		switch (step) {
		case 1:
			TranslateAnimation animationLeft = new TranslateAnimation(0, 50, 0, 0);
			animationLeft.setRepeatCount(-1);
			animationLeft.setDuration(900);
			animationLeft.setRepeatMode(Animation.REVERSE);
			TranslateAnimation animationRight = new TranslateAnimation(50, 0, 0, 0);
			animationRight.setRepeatCount(-1);
			animationRight.setDuration(900);
			animationRight.setRepeatMode(Animation.REVERSE);
			mGuideLeftImg.startAnimation(animationLeft);
			mGuideRightImg.startAnimation(animationRight);
			break;
		case 2:
			TranslateAnimation animationTop = new TranslateAnimation(0, 0, 0, 50);
			animationTop.setRepeatCount(-1);
			animationTop.setDuration(900);
			animationTop.setRepeatMode(Animation.REVERSE);
			TranslateAnimation animationBottom = new TranslateAnimation(0, 0, 50, 0);
			animationBottom.setRepeatCount(-1);
			animationBottom.setDuration(900);
			animationBottom.setRepeatMode(Animation.REVERSE);
			mGuideLeftImg.setBackgroundResource(R.drawable.guide_up);
			mGuideLeftImg.startAnimation(animationTop);
			mGuideRightImg.setBackgroundResource(R.drawable.guide_down);
			mGuideRightImg.startAnimation(animationBottom);
			mGuideBtn.setBackgroundResource(R.drawable.guide_ok);
			break;
		case 3:
			mGuideLayout.setVisibility(View.GONE);
			break;
		}
	}

	public ArrayList<Word>	mLockScreenList		= new ArrayList<Word>();	// 解锁界面
																			// 总的单词列表
	ArrayList<Word>			lockModelList		= new ArrayList<Word>();	// 解锁单词列表
	public int				lockModelFirstIndex	= 0;						// 第一个解锁单词所在的位置

	/**
	 * 初始化viewpager中页面，放到view中
	 */
	void initList() {
		Global.gWordData.initCurCikuName();
		if (Global.gWordData.addEveryDayFirstUseRecord()) { // 如果做为新的一天 添加过数据
															// 则返回true
			sm.initDayListData(mContext);
			sm.clearNoRememberWord(mContext); // 初始化新一天数据时清除之前 开始复习 当中未记住的缓存数据
			sm.initDayListForWord();
		}

		/** 先添加复习模式下list 再添加锁屏单词 */
		mLockScreenList.addAll(sm.getReviewModelInLockScreen(mContext));
		lockModelFirstIndex = mLockScreenList.size();

		/** 再添加解锁模式下list */
		lockModelList = sm.getLockModelInLockScreen(mContext); // 解锁单词列表
		if (lockModelList == null || lockModelList.size() == 0) { // 已全掌握时 添加下一组
			sm.initNextGroupReivewWords();
			lockModelList = sm.getLockModelInLockScreen(mContext);
		}
		mLockScreenList.addAll(lockModelList);

		int lockScreenSize = lockModelList.size(); // 解锁单词数
		adapterViews = new ArrayList<View>();
		lockSize = mLockScreenList.size(); // 总的单词数 = 解锁单词数 + 复习单词数
		for (int i = 0; i < lockSize; i++) {
			View v = mInflater.inflate(R.layout.lockscreen_vp_item, null);
			adapterViews.add(v);
		}
		addPagePoints(lockScreenSize);
	}

	StudyManager	sm	= new StudyManager();

	/** 获得解锁模式单词 */
	void initLockModelList() {
		ArrayList<Word> lockModelList = sm.getLockModelInLockScreen(mContext);
		for (Word word : lockModelList) {
			System.out.println("lock word:" + word.word);
		}
	}

	/** 获得复习模式单词 */
	void initReViewModeList() {
		// ArrayList<Word> reModeList = sm.getReviewModelInLockScreen(mContext);
		// for(Word word:reModeList){
		// System.out.println("review word:"+word.word);
		// }
	}

	protected synchronized void handlerVibrator(long duration) {
		isOpenVibrator = ConfigManager.isUnlockVibration(mContext);
		if (!isOpenVibrator)
			return;
		if (mVibrator == null) {
			mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		}
		mVibrator.vibrate(duration);
	}

	private Handler	handler	= new Handler() {
								@Override
								public void handleMessage(Message msg) {
									super.handleMessage(msg);
									switch (msg.what) {
									case ScrollStateListener.SCROLL_STATE_CANCEL:
										timeText.setVisibility(View.VISIBLE);
										dateText.setVisibility(View.VISIBLE);
										if (isCharging)
											chargText.setVisibility(View.VISIBLE);
										else
											chargText.setVisibility(View.GONE);
										stateText.setVisibility(View.GONE);

										break;
									case ScrollStateListener.SCROLL_STATE_TOP:
										timeText.setVisibility(View.GONE);
										dateText.setVisibility(View.GONE);
										if (isCharging)
											chargText.setVisibility(View.GONE);
										stateText.setVisibility(View.VISIBLE);
										stateText.setText(R.string.tag_have);
										handlerVibrator(50);
										break;
									case ScrollStateListener.SCROLL_STATE_END:
										timeText.setVisibility(View.GONE);
										stateText.setVisibility(View.VISIBLE);
										dateText.setVisibility(View.GONE);
										if (isCharging)
											chargText.setVisibility(View.GONE);

										int i = (Integer) msg.obj;
										if (i == 0) {
											stateText.setText(R.string.remove_new_word);
										} else {
											stateText.setText(R.string.add_new_word);
										}
										handlerVibrator(50);
										break;
									case ScrollStateListener.REMOVE_NOTIFY:
										int pos = (Integer) msg.obj;
										lockSize -= 1;
										if (pos >= adapterViews.size() - 1) {// 如果是最后一页，则解锁
											if (mMediator != null)
												mMediator.notifyUnlockNormal(DXLockScreenUtils.UNLOCK_POSITION_HOME);
											Global.gWordData.updateTodayUnlockCount();
											LockConfigMgr.setLockSize(mContext, lockSize);
											return;
										}
										if (isReViewState) {// 如果是复习模式那么移除之后复习size
															// -1
											reviewSize -= 1;
										}
										if (mAdapter != null) {
											adapterViews.remove(pos);
											mLockScreenList.remove(pos);
											mAdapter.notifyDataSetChanged();
										}
										try {
											if (pos >= lockModelFirstIndex && mPagePointView.size() > 2) { // 如果移除的位置是解锁单词
																											// 注:
																											// 有锁图标
																											// 所以为2
												mPagePointView.remove(0);
											}
											if (pos < lockModelFirstIndex) {
												lockModelFirstIndex--; // 解锁单词
																		// 首位后移一位
											}
											refreshPointPageForRemove();
										} catch (Exception e) {
											e.printStackTrace();
										}
										break;
									}
								}
							};

	/** ---- silding 控制 start----- */

	private void resizeLayout() {
		DisplayMetrics dm = getResources().getDisplayMetrics();

		// 固定 main layout, 防止被左、右挤压变形
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGestureLy.getLayoutParams();
		lp.width = dm.widthPixels;
		mGestureLy.setLayoutParams(lp);

		// 将右layout调整至main layout右边
		RelativeLayout.LayoutParams ryLp = (RelativeLayout.LayoutParams) sildeRy.getLayoutParams();
		ryLp.leftMargin = dm.widthPixels;
		ryLp.rightMargin = -lp.width;
		sildeRy.setLayoutParams(ryLp);
	}

	void onScroll(float distanceX) {

		mScroll += distanceX;// 向左为正
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGestureLy.getLayoutParams();
		RelativeLayout.LayoutParams lpRight = (RelativeLayout.LayoutParams) sildeRy.getLayoutParams();
		int distance = 0;
		if (mScroll > 0) {// 向左移动，打开右侧菜单的过程
			distance = lpRight.width - Math.abs(lp.leftMargin);
			if (distance < 0) {
				distance = 0;
			}
			if (mScroll >= distance) {
				mScroll = distance;
			}

		} else if (mScroll <= 0) {// 向右移动，关闭右侧菜单的过程
			distance = lp.leftMargin;
			if (distance >= 0) {
				mScroll = 0;
			}
		}
		if (distanceX != 0) {
			rollLayout(-mScroll);
		}
	}

	/** 解决锁事件 */
	void onRelease() {
		if (isUnlock) {
			if (mMediator != null)
				mMediator.notifyUnlockNormal(DXLockScreenUtils.UNLOCK_POSITION_HOME);
			Global.gWordData.updateTodayUnlockCount();
			LockConfigMgr.setLockSize(mContext, lockSize);
			LockConfigMgr.setLastLockWordPosition(mContext, lockSize);
		}
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGestureLy.getLayoutParams();
		if (lp.leftMargin < 0) { // 左移
			/*
			 * 左移大于右导航宽度一半，则自动展开,否则自动缩回去
			 */
			if (Math.abs(lp.leftMargin) >= sildeRy.getLayoutParams().width) {
				new SlideMenu().execute(sildeRy.getLayoutParams().width - Math.abs(lp.leftMargin), -SPEED);
			} else {
				new SlideMenu().execute(Math.abs(lp.leftMargin), SPEED);
			}
		} else if (lp.leftMargin > 0) {
			/*
			 * 右移大于左导航宽度一半，则自动展开,否则自动缩回去
			 */
			if (Math.abs(lp.leftMargin) >= sildeRy.getLayoutParams().width) {
				new SlideMenu().execute(sildeRy.getLayoutParams().width - Math.abs(lp.leftMargin), SPEED);
			} else {
				new SlideMenu().execute(Math.abs(lp.leftMargin), -SPEED);
			}
		}
	}

	private int	mVibratorCount	= 0;

	private void rollLayout(int margin) {

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGestureLy.getLayoutParams();
		lp.leftMargin += margin;
		lp.rightMargin -= margin;
		mGestureLy.setLayoutParams(lp);
		RelativeLayout.LayoutParams Rylp = (RelativeLayout.LayoutParams) sildeRy.getLayoutParams();
		Rylp.leftMargin += margin;
		// LogHelper.i(TAG, "rollLayout");

		if (Math.abs(lp.leftMargin) >= sildeRy.getLayoutParams().width) {
			// LogHelper.i(TAG, "rollLayout: true");
			isUnlock = true;
			if (mVibratorCount == 0) {
				handlerVibrator(15);
				mVibratorCount++;
			}
			changePagePointLogic(-1, true);
		} else {
			// LogHelper.i(TAG, "rollLayout: false");
			isUnlock = false;
			mVibratorCount = 0;
			changePagePointLogic(mViewPager.getCurrentItem(), false);
		}
		sildeRy.setLayoutParams(Rylp);
	}

	/** ---- silding 控制 end----- */

	private ImageView	mLockBg;

	@Override
	public void setWallpaper() {
		try {
			mLockBg = (ImageView) findViewById(R.id.lock_screen_lock_bg);
			mLockBg.setDrawingCacheEnabled(true);
			if (Build.VERSION.SDK_INT >= 11)
				mLockBg.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultWallpaper();
	}

	private void setDefaultWallpaper() {
		if (mLockBg != null) {
			int flag = GlobalConfigMgr.getWallFlag(mContext);
			Drawable wallDraw = null;
			switch (flag) {
			case 0:// 壁纸库
				String fileName = LockConfigMgr.getImageName(mContext);
				if (StringUtil.isEmpty(fileName)) {
					wallDraw = mContext.getResources().getDrawable(R.drawable.picture);
				} else {
					wallDraw = new BitmapDrawable(ImageUtil.getBitmapFromLocal(Global.IMAGE_PATH + "/", fileName));
				}
				break;
			case -1:// 默认
				wallDraw = mContext.getResources().getDrawable(R.drawable.picture);
				break;
			case 1:// 手机相册
				wallDraw = Drawable.createFromPath(Global.IMAGE_PATH + File.separator + "user_define_bg");
				break;
			case 2:// 默认壁纸
				WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
				// 获取当前壁纸
				wallDraw = wallpaperManager.getDrawable();
				break;
			}
			if (wallDraw != null) {
				mLockBg.setBackgroundDrawable(wallDraw);
			}
		}
	}

	@Override
	public void updateBatteryStatus(boolean arg0, int arg1) {
		isCharging = arg0;
		if (arg0) {
			chargText.setVisibility(View.VISIBLE);
			chargText.setText(mContext.getString(R.string.battery_ing) + arg1 + "%");

		} else {
			chargText.setVisibility(View.GONE);
		}
	}

	@Override
	public void updateCallSmsCount(int arg0, int arg1) {

	}

	@Override
	public void updateCarrierInfo(CharSequence arg0) {

	}

	@Override
	public void updateTimeAndDate() {
		String timeStr = TimeUtil.getHHmm(System.currentTimeMillis() / 1000);
		String weekStr = TimeUtil.getWeekTime(System.currentTimeMillis() / 1000);
		String dateStr = TimeUtil.getDateTime(System.currentTimeMillis() / 1000);
		// LogHelper.i(TAG, timeStr);
		timeText.setText(timeStr);
		dateText.setText(dateStr + " " + weekStr);

	}

	public class SlideMenu extends AsyncTask<Integer, Integer, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			if (params.length != 2) {
			}

			int times = params[0] / Math.abs(params[1]);
			if (params[0] % Math.abs(params[1]) != 0) {
				times++;
			}

			for (int i = 0; i < times; i++) {
				this.publishProgress(params[0], params[1], i + 1);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (values.length != 3) {

			}

			int distance = Math.abs(values[1]) * values[2];
			int delta = values[0] - distance;

			int leftMargin = 0;
			if (values[1] < 0) { // 左移
				leftMargin = (delta > 0 ? values[1] : -(Math.abs(values[1]) - Math.abs(delta)));
			} else {
				leftMargin = (delta > 0 ? values[1] : (Math.abs(values[1]) - Math.abs(delta)));
			}

			rollLayout(leftMargin);
		}
	}

	private OnKeyListener	mOnVolumeKeyListener	= new OnKeyListener() {
														@Override
														public boolean onKey(View v, int keyCode, KeyEvent event) {
															if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
																audiomanage.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
																return true;
															} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
																// 第一个参数：声音类型
																// 第二个参数：调整音量的方向
																// 第三个参数：可选的标志位
																audiomanage.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
																return true;
															}
															return false;
														}
													};
}
