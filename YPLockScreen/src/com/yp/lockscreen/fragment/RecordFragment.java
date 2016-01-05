package com.yp.lockscreen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yp.enstudy.WordData;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.LogHelper;
import com.yp.lockscreen.work.RecordAdapter;

public class RecordFragment extends Fragment{

	private View retView;
	private static final String TAG = "RecordFragment";
	
	private Context mContext;
	
	private ListView mlistView;
	
	
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
		retView = inflater.inflate(R.layout.record_fragment, null);
		initView();
		return retView;
	}
	
	void initView(){
		if (retView == null) {
			return;
		}
		mlistView = (ListView)retView.findViewById(R.id.record_fragment_lsit);
		mlistView.setAdapter(new RecordAdapter(mContext,Global.gWordData.getRecordAllList()));
	}

}
