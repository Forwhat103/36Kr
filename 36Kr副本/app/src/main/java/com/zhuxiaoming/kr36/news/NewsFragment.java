package com.zhuxiaoming.kr36.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.news.all.NewsAllFragment;
import com.zhuxiaoming.kr36.search.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by zhuxiaoming on 16/5/13.
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private ImageView menuIv;// 标题栏菜单键
    private ImageView searchIv;// 标题栏搜索按钮
    private TextView titleTv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        menuIv = bindView(R.id.tool_bar_menu_iv);
        searchIv = bindView(R.id.tool_bar_search_iv);
        titleTv = bindView(R.id.tool_bar_tv);
    }

    @Override
    protected void initData() {
        // 设置标题栏搜索按钮监听事件
        searchIv.setOnClickListener(this);
        // 设置标题栏菜单键的点击事件
        menuIv.setOnClickListener(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, new NewsAllFragment()).commit();
        EventBus.getDefault().register(this);// 注册EventBus
    }

    @Subscribe
    public void onEventMainThread(String event) {
        titleTv.setText(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_menu_iv:
                // 点击标题栏菜单键,发送打开抽屉的广播
                Intent intent = new Intent("com.zhuxiaoming.kr36.DRAWER_BC");
                getActivity().sendBroadcast(intent);
                break;
            case R.id.tool_bar_search_iv:
                // 跳转到搜索界面
                Intent intent1 = new Intent(getContext(), SearchActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);// 取消注册
    }
}
