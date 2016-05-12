package com.zhuxiaoming.kr36.invest;

import android.widget.ListView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/12.
 */
public class InvestViewFragment extends BaseFragment {
    private List<String> datas;
    private InvestViewAdapter investViewAdapter;
    private ListView investLv;

    @Override
    protected int initLayout() {
        return R.layout.fragment_invest_tab;
    }

    @Override
    protected void initView() {
        investLv = bindView(R.id.fragment_invest_tab_lv);
    }

    @Override
    protected void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add(i + "%");
        }
        investViewAdapter = new InvestViewAdapter(getContext());
        investViewAdapter.setDatas(datas);
        investLv.setAdapter(investViewAdapter);
    }
}
