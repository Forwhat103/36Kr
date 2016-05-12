package com.zhuxiaoming.kr36.invest;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是股权投资界面的碎片
 */
public class InvestFragment extends BaseFragment implements View.OnClickListener {
    private View searchIv;// 标题栏搜索按钮
    private InvestAdapter investAdapter;
    private TabLayout investTab;
    private ViewPager investVp;
    private List<Fragment> fragments;

    @Override
    protected int initLayout() {
        return R.layout.fragment_invest;
    }

    @Override
    protected void initView() {
        searchIv = bindView(R.id.invest_title_bar_search_iv);
        investTab = bindView(R.id.fragment_invest_tab);
        investVp = bindView(R.id.fragment_invest_vp);
    }

    @Override
    protected void initData() {
        searchIv.setOnClickListener(this);
        investAdapter = new InvestAdapter(getActivity().getSupportFragmentManager());
        initFragment();
        investAdapter.setFragments(fragments);
        investVp.setAdapter(investAdapter);
        investTab.setupWithViewPager(investVp);
        investTab.setSelectedTabIndicatorColor(Color.rgb(66, 133, 244));
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(new InvestViewFragment());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_title_bar_search_iv:
                // 点击搜索按钮跳转到搜索界面
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
