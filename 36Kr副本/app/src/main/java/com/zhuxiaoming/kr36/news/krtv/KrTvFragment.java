package com.zhuxiaoming.kr36.news.krtv;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.util.GsonRequest;
import com.zhuxiaoming.kr36.util.VolleySingle;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 新闻界面的氪Tv
 */
public class KrTvFragment extends BaseFragment {
    private ListView krTvLv;
    private KrTvAdapter krTvAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_kr_tv;
    }

    @Override
    protected void initView() {
        krTvLv = bindView(R.id.kr_tv_fragment_lv);
    }

    @Override
    protected void initData() {
        krTvAdapter = new KrTvAdapter(getContext());
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=tv&pagingAction=up", KrTvBean.class,
                new Response.Listener<KrTvBean>() {
                    @Override
                    public void onResponse(KrTvBean response) {
                        krTvAdapter.setKrTvBean(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        krTvLv.setAdapter(krTvAdapter);
    }

}
