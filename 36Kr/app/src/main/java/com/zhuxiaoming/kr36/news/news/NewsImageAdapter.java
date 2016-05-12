package com.zhuxiaoming.kr36.news.news;

import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/10.
 * 新闻界面轮播图适配器
 */
public class NewsImageAdapter extends PagerAdapter {
    List<ImageView> imageViews = null;

    public NewsImageAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = imageViews.get(position);
        ViewParent parent = view.getParent();
        if (parent != null) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);// 移除当前页面
        }
        ((ViewPager) container).addView(view);// 添加当前页面
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
