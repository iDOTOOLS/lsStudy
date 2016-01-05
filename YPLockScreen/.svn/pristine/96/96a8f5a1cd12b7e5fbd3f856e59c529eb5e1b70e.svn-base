package com.yp.lockscreen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.GraspActivity;
import com.yp.lockscreen.activity.WordBookActivity;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.LogHelper;

public class WordFragment extends Fragment implements OnClickListener{
	private static final String TAG = "WordFragment";
	private View retView;
	
	private RelativeLayout cikuRy;
	
	private RelativeLayout graspRy;
	
	private TextView bookCount;
	
	private TextView graspCount;
	
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogHelper.i(TAG, "onCreate");
		mContext = this.getActivity();

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogHelper.i(TAG, "onCreateView");
		retView = inflater.inflate(R.layout.word_fragment, null);
		initView(retView);
		return retView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
		if (bookCount != null) {
			bookCount.setText(Global.gUnknownWords.size()+" ");
		}
		if (graspCount != null) {
			graspCount.setText(Global.gGraspWords.size()+" ");
		}
	}
	
	public void initData(){
	    try {
	        Global.gUnknownWords = Global.updataNoRememberList();
	        Global.gGraspWords = Global.updataRemember();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	void initView(View v){
		
		cikuRy = (RelativeLayout)v.findViewById(R.id.word_fragment_book_ry);
		graspRy = (RelativeLayout)v.findViewById(R.id.word_fragment_can_ry);
		bookCount = (TextView)v.findViewById(R.id.word_fragment_ci_book_word_count_text);
		graspCount = (TextView)v.findViewById(R.id.word_fragment_ci_grasp_count);
		cikuRy.setOnClickListener(this);
		graspRy.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.word_fragment_book_ry:
			Intent bookIntent = new Intent(mContext, WordBookActivity.class);
			mContext.startActivity(bookIntent);
			break;
		case R.id.word_fragment_can_ry:
			Intent graspIntent = new Intent(mContext, GraspActivity.class);
			mContext.startActivity(graspIntent);
			break;
		}
	}

}
