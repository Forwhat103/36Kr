package com.zhuxiaoming.kr36.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/9.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;// 创建Fragment集合的对象
    private String[] titles = {"新闻", "发现", "股权投资", "我的"};

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // 替换Fragment的方法
    public void replaceFragment(Fragment newFragment) {
        notifyDataSetChanged();
    }
}
