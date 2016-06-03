package com.zhuxiaoming.kr36.welcome;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhuxiaoming on 16/1/8.
 * 引导页的适配器
 */
public class GuideAdapter extends PagerAdapter {
    private ArrayList<View> data;

    public GuideAdapter(ArrayList<View> data) {
        super();
        this.data = data;
    }

    /*
     * 获取数据的数量
     * */
    @Override
    public int getCount() {
        return data.size();
    }

    /*
     * 判断数据与View视图是否存在关系
     * */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*
     * 增加选项卡
     * */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = data.get(position);
        container.addView(view);
        return view;
    }

    /*
     * 删除选项卡
     * */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = data.get(position);
        container.removeView(view);
    }
}