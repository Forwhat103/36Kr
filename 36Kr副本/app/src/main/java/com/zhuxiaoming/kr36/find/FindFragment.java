package com.zhuxiaoming.kr36.find;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.find.findinvestor.FindInvestorActivity;
import com.zhuxiaoming.kr36.find.recentevent.RecentEventActivity;
import com.zhuxiaoming.kr36.search.SearchActivity;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是发现界面的碎片
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout searchFrameLl;// Toolbar搜索框
    private LinearLayout findInvestorLl;// 寻找投资人Item
    private LinearLayout recentEventLl;// 近期活动Item

    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        searchFrameLl = bindView(R.id.find_tool_bar_search_ll);
        findInvestorLl = bindView(R.id.find_fragment_find_investor_ll);
        recentEventLl = bindView(R.id.find_fragment_recent_event_ll);
    }

    @Override
    protected void initData() {
        searchFrameLl.setOnClickListener(this);
        findInvestorLl.setOnClickListener(this);
        recentEventLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_tool_bar_search_ll:
                // 跳转到搜索界面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.find_fragment_find_investor_ll:
                // 跳转到寻找投资人界面
                Intent intent1 = new Intent(getActivity(), FindInvestorActivity.class);
                startActivity(intent1);
                break;
            case R.id.find_fragment_recent_event_ll:
                // 跳转到寻找投资人界面
                Intent intent2 = new Intent(getActivity(), RecentEventActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
