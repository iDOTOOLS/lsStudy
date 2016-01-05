package com.yp.lockscreen.work;

import com.yp.lockscreen.fragment.WelcomeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WelcomeAdapter extends FragmentPagerAdapter {

	public WelcomeAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			return WelcomeFragment.newInstance(0);
		case 1:

			return WelcomeFragment.newInstance(1);
		case 2:

			return WelcomeFragment.newInstance(2);
		case 3:

			return WelcomeFragment.newInstance(3);

		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
