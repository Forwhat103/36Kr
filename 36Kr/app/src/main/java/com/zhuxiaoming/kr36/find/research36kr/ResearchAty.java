package com.zhuxiaoming.kr36.find.research36kr;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.news.details.NewsDetailsActivity;
import com.zhuxiaoming.kr36.news.earlyitem.EarlyItemAdapter;
import com.zhuxiaoming.kr36.news.earlyitem.EarlyItemBean;
import com.zhuxiaoming.kr36.util.VolleySingle;

/**
 * Created by zhuxiaoming on 16/5/23.
 * 发现界面36氪研究院
 */
public class ResearchAty extends BaseActivity implements View.OnClickListener {
    private ListView researchLv;
    private EarlyItemAdapter adapter;
    private ImageView backIv;// 标题栏返回按钮
    private TextView titleTv;// 标题
    private EarlyItemBean datas;

    @Override
    protected int getLayout() {
        return R.layout.activity_36kr_research;
    }

    @Override
    protected void initView() {
        researchLv = bindView(R.id.kr36_research_aty_lv);
        backIv = bindView(R.id.title_bar_content_back_tv);
        titleTv = bindView(R.id.title_bar_content_title_tv);

    }

    @Override
    protected void initData() {
        adapter = new EarlyItemAdapter(this);
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=71&pagingAction=up", EarlyItemBean.class,
                new Response.Listener<EarlyItemBean>() {
                    @Override
                    public void onResponse(EarlyItemBean response) {
                        datas = response;
                        adapter.setDatas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        researchLv.setAdapter(adapter);
        backIv.setOnClickListener(this);
        titleTv.setText("36氪研究院");
        researchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResearchAty.this, NewsDetailsActivity.class);
                intent.putExtra("key", "earlyItemId");
                intent.putExtra("earlyItemId", datas.getData().getData().get(position).getFeedId());
                startActivity(intent);
            }
        });
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
