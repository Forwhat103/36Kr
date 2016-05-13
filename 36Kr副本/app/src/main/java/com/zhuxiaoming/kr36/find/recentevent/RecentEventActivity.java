package com.zhuxiaoming.kr36.find.recentevent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.util.GsonRequest;

/**
 * Created by zhuxiaoming on 16/5/13.
 * 发现界面近期活动界面
 */
public class RecentEventActivity extends BaseActivity {
    private RecentEventBean datas;
    private RecentEventAdapter recentEventAdapter;
    private ListView recentEventLv;

    @Override
    protected int getLayout() {
        return R.layout.activity_recent_event;
    }

    @Override
    protected void initView() {
        recentEventLv = bindView(R.id.recent_event_lv);
    }

    @Override
    protected void initData() {
        recentEventAdapter = new RecentEventAdapter(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        GsonRequest<RecentEventBean> gsonRequest = new GsonRequest(Request.Method.GET, "https://rong.36kr.com/api/mobi/activity/list?page=1",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<RecentEventBean>() {
            @Override
            public void onResponse(RecentEventBean response) {
                recentEventAdapter.setDatas(response);
            }
        }, RecentEventBean.class);
        requestQueue.add(gsonRequest);
        recentEventLv.setAdapter(recentEventAdapter);

        recentEventLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecentEventActivity.this, "就不跳", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
