package com.tianjin.beichentiyu.adapter;

import android.util.Pair;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.heitong.frame.base.fragment.BaseFragment;

import java.util.List;

/**
 * Created by 黑瞳 on 2019/9/23 22:09
 * E-Mail Address：673236072@qq.com
 */
public class TabFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Pair<String,BaseFragment>> mList;
    public TabFragmentPageAdapter(FragmentManager fm,List<Pair<String,BaseFragment>> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position).second;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).first;
    }
}
