package com.yp.lockscreen.work;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yp.enstudy.bean.Word;
import com.yp.lockscreen.R;
import com.yp.lockscreen.view.AutofitTextView;

public class LockPullViewAdapter extends BaseAdapter {

	private Context mContext;
	
	private List<Word> mList;
	
	private LayoutInflater mInflater;
	
	public LockPullViewAdapter(Context mContext, List<Word> mList) {
		
		super();
		this.mContext = mContext;
		this.mList = mList;
		mInflater = LayoutInflater.from(mContext);
		
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
		ViewHolder holder = null;
		if (v == null) {
			v = mInflater.inflate(R.layout.lock_word_push_view, null);
			holder = new ViewHolder();
			holder.wordText = (AutofitTextView)v.findViewById(R.id.lock_word_push_view_word);
			holder.psText = (AutofitTextView)v.findViewById(R.id.lock_word_push_view_ps);
			holder.translateText = (TextView)v.findViewById(R.id.lock_word_push_view_translate);
			holder.enText = (TextView)v.findViewById(R.id.lock_word_push_view_en);
			holder.cnText = (TextView)v.findViewById(R.id.lock_word_push_view_cn);
		}else {
			holder = (ViewHolder)v.getTag();
		}
		return v;
	}
	
	private class ViewHolder{
		
		AutofitTextView wordText;
		AutofitTextView psText;
		TextView translateText;
		TextView enText;
		TextView cnText;
		
	}

}
