package com.yp.lockscreen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.yp.lockscreen.R;
import com.yp.lockscreen.activity.StudyPlaneActivity;
import com.yp.lockscreen.port.LockConfigMgr;

public class WelcomeFragment extends Fragment {

	private Activity mActivity;
	int mNum; // 页号
	
	
	private Button btn;

	public static WelcomeFragment newInstance(int num) {
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle args = new Bundle();
		args.putInt("num", num);
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这里我只是简单的用num区别标签，其实具体应用中可以使用真实的fragment对象来作为叶片
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		mActivity = getActivity();
	}

	/** 为Fragment加载布局时调用 **/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_viewpager_item, null);
		View v = (View) view.findViewById(R.id.fragment_viewpager_item_view);

		btn = (Button) view
				.findViewById(R.id.fragment_viewpager_item_button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LockConfigMgr.setIsFirstUse(mActivity, false);
				if (LockConfigMgr.getIsFirstSet(mActivity)) {
					
					Intent intent = new Intent(mActivity,
							StudyPlaneActivity.class);
					intent.putExtra("is_first", true);
					startActivity(intent);
					
				}
				mActivity.finish();
				
			}
		});
		switch (mNum) {
		case 0:
			v.setBackgroundResource(R.drawable.welcome_1);
			btn.setVisibility(View.GONE);
			break;
		case 1:
			v.setBackgroundResource(R.drawable.welcome_2);
			btn.setVisibility(View.GONE);
			break;
		case 2:
			v.setBackgroundResource(R.drawable.welcome_3);
			btn.setVisibility(View.GONE);
			break;
		case 3:
			v.setBackgroundResource(R.drawable.welcome_4);
			btn.setVisibility(View.VISIBLE);
			break;
		}

		return view;
	}
}
