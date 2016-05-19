package com.zhuxiaoming.kr36.news.earlyitem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.news.details.NewsDetailsActivity;
import com.zhuxiaoming.kr36.util.GsonRequest;
import com.zhuxiaoming.kr36.util.VolleySingle;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 新闻界面的早期项目
 */
public class EarlyItemFragment extends BaseFragment {
    private EarlyItemAdapter earlyItemAdapter;
    private ListView earlyItemLv;
    private EarlyItemBean datas;

    @Override
    protected int initLayout() {
        return R.layout.fragment_early_item;
    }

    @Override
    protected void initView() {
        earlyItemLv = bindView(R.id.early_item_fragment_lv);

    }

    @Override
    protected void initData() {
        earlyItemAdapter = new EarlyItemAdapter(getContext());
        Bundle arg = getArguments();
//        String title = titleArg.getString("title");
//        Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
//        titleTv.setText(title);
        int urlId = arg.getInt("urlId");
        Log.d("EarlyItemFragment", "-------------" + urlId);
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=" + urlId + "&pagingAction=up", EarlyItemBean.class,
                new Response.Listener<EarlyItemBean>() {
                    @Override
                    public void onResponse(EarlyItemBean response) {
                        earlyItemAdapter.setDatas(response);
                        datas = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "error:" + error, Toast.LENGTH_SHORT).show();
                    }
                });
        earlyItemLv.setAdapter(earlyItemAdapter);

        earlyItemLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("key", "earlyItemId");
                intent.putExtra("earlyItemId", datas.getData().getData().get(position).getFeedId());
                startActivity(intent);
            }
        });
    }
}
