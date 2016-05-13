package com.zhuxiaoming.kr36.find.findinvestor;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.util.GsonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/13.
 * 发现界面寻找投资人Item
 */
public class FindInvestorActivity extends BaseActivity implements View.OnClickListener {
    private ListView findInvestorLv;
    private FindInvestorAdapter findInvestorAdapter;
    private ImageView backIv;
    private TextView titleTv;

    @Override
    protected int getLayout() {
        return R.layout.activity_find_investor;
    }

    @Override
    protected void initView() {
        findInvestorLv = bindView(R.id.find_investor_aty_lv);
        backIv = bindView(R.id.title_bar_content_back_tv);
        titleTv = bindView(R.id.title_bar_content_title_tv);

    }

    @Override
    protected void initData() {
        findInvestorAdapter = new FindInvestorAdapter(this);
        // 获取解析网络数据
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        GsonRequest<FindInvestorBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "https://rong.36kr.com/api/mobi/investor?page=1&pageSize=20",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }, new Response.Listener<FindInvestorBean>() {
            @Override
            public void onResponse(FindInvestorBean response) {
                findInvestorAdapter.setDatas(response);
            }
        }, FindInvestorBean.class);
        requestQueue.add(gsonRequest);
        findInvestorLv.setAdapter(findInvestorAdapter);
        backIv.setOnClickListener(this);
        titleTv.setText("寻找投资人");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_content_back_tv:
                // 返回按钮
                finish();
                break;
        }
    }
}
