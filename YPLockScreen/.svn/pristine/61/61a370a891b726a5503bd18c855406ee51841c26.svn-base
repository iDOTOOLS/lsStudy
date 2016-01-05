package com.yp.lockscreen.work;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.yp.lockscreen.fragment.RecordFragment;
import com.yp.lockscreen.fragment.ReviewFragment;
import com.yp.lockscreen.fragment.SettingFragment;
import com.yp.lockscreen.fragment.WordFragment;

public class VPFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments; 
    private FragmentManager fm;
    public VPFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        initFragments();
    }
    
    private void initFragments(){
        fragments = new ArrayList<Fragment>();
        fragments.add(new ReviewFragment());
        fragments.add(new RecordFragment());
        fragments.add(new WordFragment());
        fragments.add(new SettingFragment());
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }
    
    @Override  
    public int getItemPosition(Object object) {  
        return POSITION_NONE;  
    }  

    public void notifyFragmentsChanged() {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        initFragments();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
