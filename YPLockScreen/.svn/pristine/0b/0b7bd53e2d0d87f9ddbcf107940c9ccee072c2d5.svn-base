package com.yp.lockscreen.work;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yp.enstudy.bean.Word;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;

public class WordBookListAdapter extends BaseAdapter{

	private Context mContext;
	
	private List<Word> mList;
	
	private LayoutInflater mInflater;
	
	private Typeface mFace;
	
	public static int WORD_GRASP_TYPE = 1;
	public static int WORD_NEW_TYPE =2 ;
	public int wordType=0;
	
//	private SoundPool mPool;
	public WordBookListAdapter(Context context, List<Word> list,int wordType) {
		super();
		this.mContext = context;
		this.mList = list;
		this.wordType = wordType;
		mInflater = LayoutInflater.from(mContext);
		mFace =Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidSans.ttf");
	}

	public WordBookListAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return mList.size();
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
	public View getView(int position, View v, ViewGroup parent) {
		WordViewHolder holder = null;
		
		if (v == null) {
			v = mInflater.inflate(R.layout.word_book_list_modle_item, null);
			holder = new WordViewHolder();
			holder.itemLy = (LinearLayout)v.findViewById(R.id.list_modle_item_ly);
			holder.CnText = (TextView)v.findViewById(R.id.list_modle_item_cn);
			holder.EnText = (TextView)v.findViewById(R.id.list_modle_item_en);
			holder.soundmarkText = (TextView)v.findViewById(R.id.list_modle_item_phonogram);
			holder.soundmarkText.setTypeface(mFace);
		}else {
			holder = (WordViewHolder)v.getTag();
		}
		
		final Word word = mList.get(position);
		
		holder.CnText.setText(word.interpretation+"");
		holder.EnText.setText(word.word+"");
		holder.soundmarkText.setText(word.ps+"");
		holder.itemLy.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				final Dialog removeDlog = new AlertDialog.Builder(mContext).create();
				removeDlog.show();
				View dlogV = mInflater.inflate(R.layout.dialog_grasp_item_ly, null);
				LinearLayout ly = (LinearLayout)dlogV.findViewById(R.id.dialog_grasp_item);
				TextView text = (TextView)dlogV.findViewById(R.id.dialog_grast_item_delet_text);
				if(wordType == WORD_GRASP_TYPE)
				    text.setText(R.string.remove_know);
				else if(wordType==WORD_NEW_TYPE)
				    text.setText(R.string.remove_new_word);
				ly.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						removeDlog.dismiss();
						Global.gWordData.removeRememberWord(word.word);
						mList.remove(word);
						Global.gGraspWords.remove(word);
						WordBookListAdapter.this.notifyDataSetChanged();
					}
				});
				removeDlog.getWindow().setContentView(dlogV);
				return false;
			}
		});
		
		v.setTag(holder);
		
		return v;
	}
	
	private class WordViewHolder{
		
		TextView EnText;
		TextView CnText;
		TextView soundmarkText;
		LinearLayout itemLy;
	}

}
