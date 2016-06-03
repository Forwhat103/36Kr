package com.zhuxiaoming.kr36.search;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.news.all.NewsAllAdapter;
import com.zhuxiaoming.kr36.news.all.NewsBean;
import com.zhuxiaoming.kr36.news.details.NewsDetailsActivity;
import com.zhuxiaoming.kr36.util.VolleySingle;

/**
 * Created by zhuxiaoming on 16/5/21.
 * 搜索结果界面
 */
public class SearchResultAty extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private NewsAllAdapter adapter;
    private NewsBean datas;
    private String beforeUrl = "https://rong.36kr.com/api/mobi/news/search?keyword=";
    private String afterUrl = "&pageSize=20";

    @Override
    protected int getLayout() {
        return R.layout.aty_search_reault;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.search_result_aty_lv);
    }

    @Override

    protected void initData() {
        bindView(R.id.title_bar_content_back_tv).setOnClickListener(this);
        adapter = new NewsAllAdapter(this);
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        ((TextView) bindView(R.id.title_bar_content_title_tv)).setText("搜索: " + keyword);
        // 解析网络数据
        VolleySingle.addRequest(beforeUrl + keyword + afterUrl, NewsBean.class,
                new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {
                        if (response.getData().getTotalPages() > 0) {
                            adapter.setDatas(response);
                            listView.setAdapter(adapter);
                            datas = response;
                            ((TextView) bindView(R.id.search_result_aty_count_tv)).setText("共搜到" + response.getData().getTotalCount() + "篇文章");
                        } else {
                            listView.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // listView的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(SearchResultAty.this, NewsDetailsActivity.class);
                intent1.putExtra("key", "searchId");
                if (datas != null) {
                    intent1.putExtra("searchId", datas.getData().getData().get(position).getFeedId());
                }
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_content_back_tv:
                // 返回
                finish();
                break;
        }
    }
}
