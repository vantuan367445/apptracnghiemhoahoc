package com.example.tuanvatvo.demo2.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.LamBaiActivity;
import com.example.tuanvatvo.demo2.model.MyFragment;

import java.util.List;

public class AdapterMyFragment extends FragmentPagerAdapter {

    Activity context;
    List<MyFragment>fragmentList;

    public AdapterMyFragment(FragmentManager fm, Activity context, List<MyFragment> fragmentList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    public AdapterMyFragment(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        for (int j = 0; j < LamBaiActivity.socauhoi; j ++){
            if( i == j ){
                return MyFragment.newInstance(i);
            }
        }
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String câu  = context.getResources().getString(R.string.cau)+" " + (position+1);
        return new StringBuilder(context.getResources().getString(R.string.cau)+" ").append(position + 1).toString();
    }
}
