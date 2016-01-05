package com.yp.lockscreen.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.bean.Word;
import com.yp.enstudy.utils.NumberUtil;
import com.yp.lockscreen.R;
import com.yp.lockscreen.StudyManager;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.DeviceUtil;
import com.yp.lockscreen.utils.SerializableUtils;
import com.yp.lockscreen.utils.ShareUtils;
import com.yp.lockscreen.view.AutofitTextView;
import com.yp.lockscreen.work.VoiceDownLoadManger;

public class ReviewActivity extends Activity implements OnClickListener {

    private static final String TAG = "ReviewActivity";

    private AutofitTextView wordEnAutofit;

    private AutofitTextView wordSoundAutofit;

    private TextView cnAutofit;

    private TextView egEnText1;

    private TextView egCnText1;
    /**
     * 进程text
     */
    private TextView progressText;

    private Button rememberBtn;

    private Button forgetBtn;

    private Button pronunceBtn;

    private Button addBtn;

    private ProgressBar reviewProgress;

    private LinearLayout wordLy;

    private LinearLayout backLy;

    private LinearLayout succedLy;

    private LinearLayout hideLy;

    private LinearLayout showLy;

    /** 下面是隐藏界面的View */

    private EditText shareText;

    private ImageView animImg;

    private Button shareBtn;

    private Button restartBtn;

    private int tempReviewCount;

    // private List<Word> mList;

    private Context mActivity;

    private SoundPool psPool;

    private ShareUtils shareUtils;

    private SpannableStringBuilder mWord;
    private StudyManager mSm;

    void initView() {
        Typeface mFace = Typeface.createFromAsset(getAssets(), "fonts/DroidSans.ttf");
        shareUtils = new ShareUtils(mActivity);
        mWord = new SpannableStringBuilder();
        psPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        wordEnAutofit = (AutofitTextView) findViewById(R.id.word_details_word_en);
        wordSoundAutofit = (AutofitTextView) findViewById(R.id.word_details_word_soundmark);
        wordSoundAutofit.setTypeface(mFace);
        cnAutofit = (TextView) findViewById(R.id.word_details_word_translate_text);
        egEnText1 = (TextView) findViewById(R.id.word_details_word_eg_en_1);
        egCnText1 = (TextView) findViewById(R.id.word_details_word_eg_cn_1);
        progressText = (TextView) findViewById(R.id.word_details_review_progress);
        rememberBtn = (Button) findViewById(R.id.word_details_remember_btn);
        rememberBtn.setOnClickListener(this);
        forgetBtn = (Button) findViewById(R.id.word_details_forget_btn);
        forgetBtn.setOnClickListener(this);
        pronunceBtn = (Button) findViewById(R.id.word_details_pronunce_btn);
        pronunceBtn.setOnClickListener(this);
        addBtn = (Button) findViewById(R.id.word_details_add_btn);
        addBtn.setOnClickListener(this);
        backLy = (LinearLayout) findViewById(R.id.word_details_back_Ly);
        backLy.setOnClickListener(this);
        hideLy = (LinearLayout) findViewById(R.id.word_details_hid_details_ly);
        hideLy.setOnClickListener(this);
        showLy = (LinearLayout) findViewById(R.id.word_details_show_details_ly);
        reviewProgress = (ProgressBar) findViewById(R.id.word_details_review_bar);
        setProgress();
        wordLy = (LinearLayout) findViewById(R.id.word_details_word_ly);
        succedLy = (LinearLayout) findViewById(R.id.word_details_finished_ly);
        shareText = (EditText) findViewById(R.id.word_details_finish_share_text);
        shareText.setTextColor(Color.rgb(11, 139, 230));
        animImg = (ImageView) findViewById(R.id.word_details_finish_anim_img);
        shareBtn = (Button) findViewById(R.id.word_details_finish_share_btn);
        shareBtn.setOnClickListener(this);
        restartBtn = (Button) findViewById(R.id.word_details_finish_re_study_btn);
        restartBtn.setOnClickListener(this);
        setViewsInfo(0);
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
        if(mSm!=null)
            mSm.cacheNoRememberWord(mNoRememberWords, mActivity);
    }

    /**
     * 对view进行赋值
     * 
     * @param position
     *            需要显示的数据
     */
    void setViewsInfo(int position) {
        if(mNoRememberWords==null || mNoRememberWords.size()==0) initNoRememberWords();
        Word word = mNoRememberWords.get(position);
        wordEnAutofit.setText(word.word);
        wordSoundAutofit.setText(word.ps);
        cnAutofit.setText(word.interpretation);
//        egEnText1.setText(word.en);
        setTxtUnderline(egEnText1,word.en,word.word);
        egCnText1.setText(word.cn);
        hideLy.setVisibility(View.VISIBLE);
        showLy.setVisibility(View.GONE);
        if (word.remember == 0) {
            addBtn.setBackgroundResource(R.drawable.add_norma);
        } else if (word.remember == 1) {
            addBtn.setBackgroundResource(R.drawable.add_pressed);
        }
        int count = tempReviewCount - mNoRememberWords.size();
        progressText.setText(count + "/" + tempReviewCount);
        setAddBtn(word.remember);
    }
    
    public void initNoRememberWords(){
        mNoRememberWords.addAll(Global.gReViewWords);
        tempReviewCount = mNoRememberWords.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_details);
        mActivity = this;
        mSm = new StudyManager();
        initList();
        initView();
    }
    

    /**开始复习页面 未记住的单词*/
    public ArrayList<Word> mNoRememberWords;
    private void initList(){
        checkReviewList();
        mNoRememberWords = (ArrayList<Word>) SerializableUtils.readSerializableFile("norememberwords", this);
        if(mNoRememberWords==null || mNoRememberWords.size()<=0){
            if (Global.gReViewWords == null || Global.gReViewWords.size()==0) {     //如果无值  则添加下一组
                mSm.initNextGroupReivewWords();
            }
            mNoRememberWords = new ArrayList<Word>();
            mNoRememberWords.addAll(Global.gReViewWords);
        }
        resetTempReviewWord();
        tempReviewCount = mNoRememberWords.size();
    }
    
    /**验证复习池中 单词是否还有单词 如果没有则初始化下一组
     * true  还有   
     * false 没有
     * */
    public void checkReviewList(){
        ArrayList<Word> lockModelList = mSm.getLockModelInLockScreen(this);    
        if(lockModelList==null || lockModelList.size()==0){           //已全掌握时 添加下一组
            mSm.initNextGroupReivewWords();
            mSm.clearNoRememberWord(this);
        }
    }
    
    /** 过滤已掌握 */
    public void resetTempReviewWord() {
        if (Global.gGraspWords != null || mNoRememberWords != null) {
            ArrayList<Word> tmp = new ArrayList<Word>();
            for (Word reviewVO : mNoRememberWords) {
                for (Word graspWord : Global.gGraspWords) {
                    if (reviewVO.word.equals(graspWord.word)) {
                        tmp.add(reviewVO);
                    }
                }
            }
            mNoRememberWords.removeAll(tmp);
        }
    }

    Word mTmpWord = new Word();
    @Override
    public void onClick(View v) {
        int id = v.getId();
        mTmpWord = new Word();
        switch (id) {
        case R.id.word_details_forget_btn:
            mTmpWord = mNoRememberWords.get(0);         //移到list队列后面
            mNoRememberWords.remove(0);
            mNoRememberWords.add(mTmpWord);
            setViewsInfo(0);
            break;
        case R.id.word_details_remember_btn:
            reviewProgress.incrementProgressBy(1);
            if (mNoRememberWords.size() == 1) {
                wordLy.setVisibility(View.GONE);
                succedLy.setVisibility(View.VISIBLE);

                Global.gWordData.updateReviewState(true);
                Global.gWordData.updateTodayReViewWordCount(mSm.getTodayStudyNum(Global.gContext));

                mWord = new SpannableStringBuilder();
                shareText.setText(getShareString(mSm.getTodayStudyNum(Global.gContext)));

                mNoRememberWords.clear();
                return;
            }
            mNoRememberWords.remove(0);
//            if (mPosition >= Global.gTempReviewWord.size() - 1) {
//                mPosition +=1;
//            }
            setViewsInfo(0);
            break;

        case R.id.word_details_pronunce_btn:
            if (Global.gWordData.checkVoice(ConfigManager.getCurCiku(ReviewActivity.this))) {
                final int soundId = psPool.load(Global.gWordData.getVoicePathByWord(mNoRememberWords.get(0).word), 0);
                psPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        psPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
                    }
                });
            } else {
                new VoiceDownLoadManger().startDownLoad(ReviewActivity.this);
            }
            break;

        case R.id.word_details_add_btn:
            mTmpWord = mNoRememberWords.get(0);
            if (mTmpWord.remember == 1) {
                mTmpWord.remember = 0;
                Global.gWordData.removeNoRememberWord(mTmpWord.word);
                Global.gReViewWords.get(0).remember = 0;
                Global.gUnknownWords.remove(mTmpWord);
                setAddBtn(0);
                Toast.makeText(ReviewActivity.this, R.string.remove_new_word, Toast.LENGTH_SHORT).show();
            } else {
                mTmpWord.remember = 1;
                Global.gWordData.setNoRememberWord(mTmpWord.word);
                Global.gReViewWords.get(0).remember = 1;
                Global.gUnknownWords.add(mTmpWord);
                setAddBtn(1);
                Toast.makeText(ReviewActivity.this, R.string.add_new_word, Toast.LENGTH_SHORT).show();
            }

            break;

        case R.id.word_details_hid_details_ly:
            showLy.setVisibility(View.VISIBLE);
            hideLy.setVisibility(View.GONE);
            break;
        case R.id.word_details_finish_share_btn:

            shareUtils.CreateShare(getString(R.string.review_title_text), shareText.getText().toString(), ReviewActivity.this);
            break;
        case R.id.word_details_finish_re_study_btn:
            SerializableUtils.delSerializableFile("norememberwords", ReviewActivity.this);
            if (Global.gReViewWords != null) {
                initList();
            }
            wordLy.setVisibility(View.VISIBLE);
            succedLy.setVisibility(View.GONE);
            setProgress();
            setViewsInfo(0);
            break;
        case R.id.word_details_back_Ly:

            finishActivity();
            break;
        }
    }

    private SpannableStringBuilder getShareString(int num) {
        String review = " " + num + " ";
        int start = 0;
        int end = 0;

        if (DeviceUtil.getLocalLanguage().contains("CN")) {
        	mWord.append(getString(R.string.today_study_content_1));
            end = 6;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(review);
            start = end;
            end = start + review.length();
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(11, 139, 230)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(getString(R.string.today_study_content_2));
            start = end;
            end = start + 13;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(" ");
            start = end;
            end = start + 1;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(11, 139, 230)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

		}else if (DeviceUtil.getLocalLanguage().contains("en")) {
			mWord.append(getString(R.string.today_study_content_1));
            end = 22;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(review);
            start = end;
            end = start + review.length();
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(11, 139, 230)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(getString(R.string.today_study_content_2));
            start = end;
            end = start + 33;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mWord.append(" ");
            start = end;
            end = start + 1;
            mWord.setSpan(new ForegroundColorSpan(Color.rgb(11, 139, 230)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
        
        return mWord;
    }

    void setProgress() {
        reviewProgress.setMax(mNoRememberWords.size());
        if (mNoRememberWords.size() > 0) {
            reviewProgress.setProgress(0);
        }
    }

    SpannableStringBuilder getEditTextString(CharSequence s, SpannableStringBuilder word) {

        for (int i = 0; i < s.length(); i++) {
            int start = word.length();
            int end = start + 1;
            String tempStr = s.charAt(i) + "";
            word.append(tempStr);
            if (NumberUtil.isNumber(tempStr)) {
                word.setSpan(new ForegroundColorSpan(Color.rgb(13, 139, 224)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return word;
    }

    void setAddBtn(int i) {
        if (addBtn == null) {
            return;
        }
        if (i == 1) {
            addBtn.setBackgroundResource(R.drawable.add_pressed);
        } else {
            addBtn.setBackgroundResource(R.drawable.add_norma);
        }
    }

    void finishActivity() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        psPool.release();
        psPool = null;
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
}
