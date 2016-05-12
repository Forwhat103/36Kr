package com.zhuxiaoming.kr36.invest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/12.
 * 股权投资界面TabLayout的适配器
 */
public class InvestAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles = {"全部", "募资中", "募资完成", "融资成功"};

    public InvestAdapter(FragmentManager fm) {
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
}
