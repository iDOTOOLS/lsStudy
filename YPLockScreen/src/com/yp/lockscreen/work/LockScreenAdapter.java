package com.yp.lockscreen.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.bean.Word;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.port.LockConfigMgr;
import com.yp.lockscreen.utils.LogHelper;
import com.yp.lockscreen.utils.StringUtils;
import com.yp.lockscreen.view.AutofitTextView;
import com.yp.lockscreen.view.PulltoRefresh;
import com.yp.lockscreen.view.PulltoRefresh.CusTouchListener;
import com.yp.lockscreen.view.PulltoRefresh.ScrollStateListener;

public class LockScreenAdapter extends PagerAdapter {

	private static final String TAG = "LockScreenAdapter";

	// private List<Word> list;

	private List<View> views;

	private Context mContext;

	private Handler mHandler;

	private SoundPool mSoundPool;

	private boolean isShowCn;

	private int egFlag;
	
	private SoundPool psPool;
	
	public interface WordCallBack{
	    public void postFinish(int [] pos);
	}

	/**
	 * 是否有语音包
	 */
	private boolean isVoice;

	public LockScreenAdapter() {
		super();
	}

	public LockScreenAdapter(Context context) {
		super();
		this.mContext = context;
	}

	public WordCallBack callback;
	Typeface mFace;
	private ArrayList<Word> mWordList;
	public LockScreenAdapter(List<View> list,ArrayList<Word> wordList, Context context, Handler handler,
			SoundPool pool,WordCallBack callback) {
		super();
		this.mContext = context;
		this.views = list;
		this.mWordList = wordList;
		this.mHandler = handler;
		this.mSoundPool = pool;
		this.callback = callback;
		this.isVoice = Global.gWordData.checkVoice(ConfigManager.getCurCiku(context));
		isShowCn = ConfigManager.isExplain(mContext);
		egFlag = ConfigManager.getSentenceType(mContext);
		psPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		mFace=Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidSans.ttf");
	}

	@Override
	public int getCount() {
	    return mWordList.size();
//		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final View v = views.get(position);
		try {
	        int pos = position;
//	        if (position >= Global.gReViewWords.size() - 1) {
//	            pos = position + 1 - Global.gReViewWords.size();
//	            LockConfigMgr.setLockSize(mContext, pos);
//	        }
//	        final Word word = Global.gReViewWords.get(pos);
	        final Word word = mWordList.get(pos);
	        initItemView(v, word, container, position);
	        container.addView(v);
	        v.setOnClickListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return v;
	}

	public int[] wordPostionInWindows = new int[2];
	private void initItemView(View v, final Word word,ViewGroup container, final int pos) {
	    long start = System.currentTimeMillis();
		final PulltoRefresh refreshLy = (PulltoRefresh) v.findViewById(R.id.pull_refresh_scroll_view);

		final LinearLayout topLy = (LinearLayout) v.findViewById(R.id.pull_refresh_scroll_view_top_ly);

		final LinearLayout centerLy = (LinearLayout) v.findViewById(R.id.lock_vp_item_centerLy);

		LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(Global.screen_width, Global.screen_Height * 2 / 7);

		topLy.setLayoutParams(topParams);

		topLy.invalidate();

		LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(Global.screen_width, Global.screen_Height * 5 / 7);

		centerLy.setLayoutParams(centerParams);

		centerLy.invalidate();

		final AutofitTextView autoWrod = (AutofitTextView) v.findViewById(R.id.lock_word_push_view_word);
		autoWrod.setText(word.word);
		autoWrod.post(new Runnable() {
            @Override
            public void run() {
                autoWrod.getLocationOnScreen(wordPostionInWindows);
                callback.postFinish(wordPostionInWindows);
            }
        });

		final ImageView isInBook = (ImageView) v.findViewById(R.id.lock_word_push_view_is_book);
		if (word.remember == 1) {
			isInBook.setVisibility(View.VISIBLE);
		} else {
			isInBook.setVisibility(View.GONE);
		}

		final AutofitTextView autoPS = (AutofitTextView) v.findViewById(R.id.lock_word_push_view_ps);
		autoPS.setTypeface(mFace);
		autoPS.setText(word.ps);
		final ImageView psImg = (ImageView)v.findViewById(R.id.lock_word_push_view_voice_img);
		if (isVoice) {
		    psImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int soundId = psPool.load(Global.gWordData.getVoicePathByWord(word.word), 0);
                    psPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(SoundPool soundPool,int sampleId, int status) {
                            psPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
                        }
                    });
                }
            });
			psImg.setVisibility(View.VISIBLE);
		}else {
			psImg.setVisibility(View.GONE);
		}
		autoPS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isVoice) {
					return;
				}
				final int soundID = mSoundPool.load(Global.gWordData.getVoicePathByWord(word.word), 0);
				mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								mSoundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
							}
						});
			}
		});
		final TextView translateText = (TextView) v.findViewById(R.id.lock_word_push_view_translate);
		translateText.setText(word.interpretation);

		final TextView enText = (TextView) v.findViewById(R.id.lock_word_push_view_en);
//		enText.setText(word.en);
		setTxtUnderline(enText, word.en,word.word);
		final TextView cnText = (TextView) v.findViewById(R.id.lock_word_push_view_cn);
		cnText.setText(word.cn);
		if (!isShowCn)
			translateText.setVisibility(View.INVISIBLE);
		switch (egFlag) {
		case 0:
			enText.setVisibility(View.GONE);
			cnText.setVisibility(View.GONE);
			break;
		case 1:
			enText.setVisibility(View.VISIBLE);
			cnText.setVisibility(View.GONE);
			break;
		case 2:
			enText.setVisibility(View.VISIBLE);
			cnText.setVisibility(View.VISIBLE);
			break;
		}

		refreshLy.setCusTouchListener(new CusTouchListener() {

			@Override
			public void onTouchEvent(MotionEvent ev) {
				int action = ev.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					translateText.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_UP:
					if (!isShowCn) {
						translateText.setVisibility(View.INVISIBLE);
					}
					break;
				}
			}
		});
		refreshLy.setOnScrollStateListener(new ScrollStateListener() {

			@Override
			public void stateCannle(int flag) {
				switch (flag) {
				case ScrollStateListener.SCROLL_STATE_CANCEL:

					break;
				case ScrollStateListener.SCROLL_STATE_TOP:
					word.remember = 2;
					Global.gWordData.setRememberWord(word.word);
					Global.gReViewWords.remove(word);
					Global.gGraspWords.add(word);
					Message msg = mHandler.obtainMessage();
					msg.obj = pos;
					msg.what = REMOVE_NOTIFY;
					mHandler.sendMessage(msg);
					Toast.makeText(mContext, word.word + " "+mContext.getString(R.string.tag_have),Toast.LENGTH_SHORT).show();
					break;
				case ScrollStateListener.SCROLL_STATE_END:
					if (word.remember != 1) {
						Global.gWordData.setNoRememberWord(word.word);
						word.remember = 1;
						Global.gUnknownWords.add(word);
						isInBook.setVisibility(View.VISIBLE);
						Toast.makeText(mContext, word.word + " "+mContext.getString(R.string.add_new_word),Toast.LENGTH_SHORT).show();
					} else {
						Global.gWordData.removeRememberWord(word.word);
						word.remember = 0;
						Global.gUnknownWords.remove(word);
						Toast.makeText(mContext, word.word + " "+mContext.getString(R.string.remove_new_word),Toast.LENGTH_SHORT).show();
						isInBook.setVisibility(View.GONE);
					}
					break;
				}
			}

			@Override
			public void stateChanged(int flag) {

				Message msg = mHandler.obtainMessage();

				switch (flag) {
				case ScrollStateListener.SCROLL_STATE_CANCEL:

					mHandler.sendEmptyMessage(ScrollStateListener.SCROLL_STATE_CANCEL);
					break;
				case ScrollStateListener.SCROLL_STATE_TOP:

					mHandler.sendEmptyMessage(ScrollStateListener.SCROLL_STATE_TOP);
					break;
				case ScrollStateListener.SCROLL_STATE_END:

					if (word.remember != 1) {
						msg.obj = 1;
						msg.what = SCROLL_STATE_END;
						mHandler.sendMessage(msg);
					} else {
						msg.obj = 0;
						msg.what = SCROLL_STATE_END;
						mHandler.sendMessage(msg);
					}
					break;
				}
			}
		});
//		LogHelper.d("lockview", (System.currentTimeMillis()-start)+"ms");
	}
	
	SpannableString mSs;
    private void setTxtUnderline(TextView txt, String msg, String key) {
        HashMap<Integer, Integer> maps = StringUtils.checkWordInPosition(key, msg);
        mSs = new SpannableString(msg);
        if (maps != null) {
            Iterator iter = maps.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                mSs.setSpan(new UnderlineSpan(), Integer.valueOf(entry.getKey().toString()), Integer.valueOf(entry.getValue().toString()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        txt.setText(mSs);
    }
}