package com.zhuxiaoming.kr36.invest.investfragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.util.VolleySingle;

/**
 * Created by zhuxiaoming on 16/5/12.
 */
public class InvestFinishFragment extends BaseFragment {
    private InvestViewAdapter investViewAdapter;
    private ListView investLv;
    private String[] types = {"all", "underway", "raise", "finish"};

    @Override
    protected int initLayout() {
        return R.layout.fragment_invest_tab;
    }

    @Override
    protected void initView() {
        investLv = bindView(R.id.fragment_invest_tab_lv);
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/cf/actions/list?page=1&type=" + types[3] + "&pageSize=20", InvestBean.class,
                new Response.Listener<InvestBean>() {
                    @Override
                    public void onResponse(InvestBean response) {
                        if (response != null) {
                            investViewAdapter.setDatas(response);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        investViewAdapter = new InvestViewAdapter(context);
        investLv.setAdapter(investViewAdapter);
    }

    @Override
    protected void initData() {
        investLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "就不跳", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
