package com.zhuxiaoming.kr36.invest;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

        investLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "就不跳", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
