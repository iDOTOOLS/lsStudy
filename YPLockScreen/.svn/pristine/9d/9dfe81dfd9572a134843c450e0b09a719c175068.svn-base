package com.yp.lockscreen.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.WordData;
import com.yp.enstudy.bean.Word;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.view.AutofitTextView;
import com.yp.lockscreen.work.VoiceDownLoadManger;
import com.yp.lockscreen.work.WordBookListAdapter;

public class WordBookActivity extends Activity implements OnClickListener{

	/**
	 * 返回btn
	 */
	private Button backBtn;
	
	private Button modleBtn;
	
	private Button lastBtn;
	
	private Button nextBtn;
	
	private Button pronunceBtn;
	
	private Button addbtn;
	
	private AutofitTextView autofitEnText;
	
	private AutofitTextView autoPronunceText;
	
	private TextView transText;
	
	private TextView egEnText1;
	
	private TextView egCnText1;
	
	private TextView progressText;
	
	private ProgressBar progressBar;
	
	private ListView mListView;
	
	private LinearLayout listLy;
	
	private LinearLayout wordModelLy;
	private LinearLayout backLy;
	
	private LinearLayout showLy;
	
	private LinearLayout hideLy;
	
	private Typeface mFace;
	
	
	private ArrayList<Word> mList; 
	
	private boolean isList = false;
	
	private int mPosition = 1;
	
	private SoundPool mPool;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_book_activity);
		mPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		mList = new ArrayList<Word>();
		mList.addAll(Global.gUnknownWords);
		initView();
		
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
	void initView(){
		
		mFace =Typeface.createFromAsset(getAssets(), "fonts/DroidSans.ttf");
		backBtn = (Button)findViewById(R.id.word_book_back_btn);
		backBtn.setOnClickListener(this);
		modleBtn = (Button)findViewById(R.id.word_book_model_choose_btn);
		modleBtn.setOnClickListener(this);
		lastBtn = (Button)findViewById(R.id.word_book_forget_btn);
		lastBtn.setBackgroundResource(R.drawable.btn_bg_gray_pressed);
		lastBtn.setOnClickListener(this);
		
		nextBtn = (Button)findViewById(R.id.word_book_remember_btn);
		if (mList.size()>1) {
			nextBtn.setBackgroundResource(R.drawable.btn_bg_yellow_selector);
		}else {
			nextBtn.setBackgroundResource(R.drawable.btn_bg_gray_pressed);
		}
		nextBtn.setOnClickListener(this);
		pronunceBtn = (Button)findViewById(R.id.word_book_pronunce_btn);
		pronunceBtn.setOnClickListener(this);
		addbtn = (Button)findViewById(R.id.word_book_add_btn);
		addbtn.setOnClickListener(this);
		autofitEnText = (AutofitTextView)findViewById(R.id.word_book_word_en);
		autoPronunceText= (AutofitTextView)findViewById(R.id.word_book_word_soundmark);
		autoPronunceText.setTypeface(mFace);
		transText = (TextView)findViewById(R.id.word_book_word_translate_text);
		egEnText1 = (TextView)findViewById(R.id.word_book_word_eg_en_1);
		egCnText1 = (TextView)findViewById(R.id.word_book_word_eg_cn_1);
		progressText = (TextView)findViewById(R.id.word_book_review_progress);
		listLy = (LinearLayout)findViewById(R.id.word_book_list_ly);
		wordModelLy = (LinearLayout)findViewById(R.id.word_book_word_ly);
		backLy = (LinearLayout)findViewById(R.id.word_book_activity_back_Ly);
		backLy.setOnClickListener(this);
		showLy = (LinearLayout)findViewById(R.id.word_book_show_details_ly);
		hideLy = (LinearLayout)findViewById(R.id.word_book_hide_details_ly);
		hideLy.setOnClickListener(this);
		mListView = (ListView)findViewById(R.id.word_book_list_view);
		mListView.setAdapter(new WordBookListAdapter(this, mList,WordBookListAdapter.WORD_NEW_TYPE));
		progressBar = (ProgressBar)findViewById(R.id.word_book_review_bar);
		if (Global.gUnknownWords.size() > 0) {
			progressBar.setMax(Global.gUnknownWords.size());
			progressBar.setProgress(1);
			setWordModelInfo(mList.get(mPosition-1));
		}else {
			wordModelLy.setVisibility(View.GONE);
		}
		
	}
	
	void setWordModelInfo(Word word){
		showLy.setVisibility(View.GONE);
		hideLy.setVisibility(View.VISIBLE);
		autofitEnText.setText(word.word);
		autoPronunceText.setText(word.ps);
		transText.setText(word.interpretation);
//		egEnText1.setText(word.en);
		setTxtUnderline(egEnText1,word.en,word.word);
		egCnText1.setText(word.cn);
		if (word.remember == 0) {
			addbtn.setBackgroundResource(R.drawable.add_norma);
		}else if (word.remember == 1) {
			addbtn.setBackgroundResource(R.drawable.add_pressed);
		}
		progressText.setText(mPosition+"/"+mList.size());
	}
	
	
	@Override
	public void onClick(View v) {

		int id = v.getId();
		
		switch (id) {
		case R.id.word_book_back_btn:
			finishActivity();
			break;
		case R.id.word_book_model_choose_btn:
		    if(mList==null || mList.size()==0) return;
			if (isList) {
				wordModelLy.setVisibility(View.VISIBLE);
				listLy.setVisibility(View.GONE);
				isList = false;
			}else {
				wordModelLy.setVisibility(View.GONE);
				listLy.setVisibility(View.VISIBLE);
				isList = true;
			}
			break;
		case R.id.word_book_forget_btn:
			
			if (mPosition <= 1) {
				return;
			}
			mPosition -= 1;
			progressBar.incrementProgressBy(-1);
			setWordModelInfo(mList.get(mPosition-1));
			if (mPosition <= 1) {
				lastBtn.setBackgroundResource(R.drawable.btn_bg_gray_pressed);
			} else {
				lastBtn.setBackgroundResource(R.drawable.btn_bg_yellow_selector);
			}
			nextBtn.setBackgroundResource(R.drawable.btn_bg_yellow_selector);
			
			break;
		case R.id.word_book_remember_btn:
			if (mPosition >= mList.size()) {
				return;
			}
			mPosition += 1;
			progressBar.incrementProgressBy(1);
			setWordModelInfo(mList.get(mPosition-1));
			if (mPosition >= mList.size()) {
				nextBtn.setBackgroundResource(R.drawable.btn_bg_gray_pressed);
			}else {
				
				nextBtn.setBackgroundResource(R.drawable.btn_bg_yellow_selector);
			}
			lastBtn.setBackgroundResource(R.drawable.btn_bg_yellow_selector);
			break;
		case R.id.word_book_pronunce_btn:
			if (Global.gWordData.checkVoice(ConfigManager.getCurCiku(WordBookActivity.this))){
				final int soundId = mPool.load(Global.gWordData.getVoicePathByWord(mList.get(mPosition-1).word), 0);
				mPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
					@Override
					public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
						mPool.play(soundId, 1.0f,1.0f, 1, 0, 1.0f);
					}
				});
			}else {
				new VoiceDownLoadManger().startDownLoad(WordBookActivity.this );
			}
			
			break;
		case R.id.word_book_add_btn:
		    try {
		        Word word  = mList.get(mPosition-1);
	            if (word.remember == 0) {
	                word.remember = 1;
	                Global.gWordData.setNoRememberWord(word.word);
	                Global.gUnknownWords.add(word);
	                addbtn.setBackgroundResource(R.drawable.add_pressed);
	                Toast.makeText(this, R.string.add_new_word, Toast.LENGTH_SHORT).show();
	            } else if (word.remember == 1) {
	                word.remember = 0;
	                Global.gWordData.removeNoRememberWord(word.word);
	                Global.gUnknownWords.remove(word);
	                addbtn.setBackgroundResource(R.drawable.add_norma);
	                Toast.makeText(this, R.string.remove_new_word, Toast.LENGTH_SHORT).show();
	            }
            } catch (Exception e) {
                e.printStackTrace();
            }
		    break;
		case R.id.word_book_hide_details_ly:
			showLy.setVisibility(View.VISIBLE);
			hideLy.setVisibility(View.GONE);
			break;
		case R.id.word_book_activity_back_Ly:
			
			finishActivity();
			break;
		}
	}
	
    SpannableString mSs;
    private void setTxtUnderline(TextView txt, String msg, String key) {
        HashMap<Integer, Integer> maps = com.yp.lockscreen.utils.StringUtils.checkWordInPosition(key, msg);
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
	
	void finishActivity(){
		this.finish();
	} 
}
