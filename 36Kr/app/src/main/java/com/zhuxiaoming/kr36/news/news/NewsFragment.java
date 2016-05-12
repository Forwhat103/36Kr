package com.zhuxiaoming.kr36.news.news;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是新闻界面的碎片
 */
public class NewsFragment extends BaseFragment {

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected int initView() {
        return 0;
    }

    @Override
    protected void initData() {

    }
}
