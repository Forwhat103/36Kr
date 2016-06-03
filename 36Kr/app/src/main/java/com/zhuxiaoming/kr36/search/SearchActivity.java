package com.zhuxiaoming.kr36.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.news.all.NewsBean;
import com.zhuxiaoming.kr36.util.VolleySingle;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是搜索界面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText inputEt;
    private ImageView deleteIv;
    private LinearLayout newsLl;
    private LinearLayout userLl;
    private LinearLayout companyLl;
    private LinearLayout organizationLl;
    private ImageView noRecordIv;
    private TextView noRecordTv;
    private ImageView searchIv;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        inputEt = bindView(R.id.search_aty_input_et);
        deleteIv = bindView(R.id.search_aty_delete_iv);
        newsLl = bindView(R.id.search_aty_news_ll);
        userLl = bindView(R.id.search_aty_user_ll);
        companyLl = bindView(R.id.search_aty_company_ll);
        organizationLl = bindView(R.id.search_aty_organization_ll);
        noRecordIv = bindView(R.id.search_aty_no_record_iv);
        noRecordTv = bindView(R.id.search_aty_no_record_tv);
        searchIv = bindView(R.id.search_aty_search_iv);
    }

    @Override
    protected void initData() {
        bindView(R.id.search_aty_cancel_tv).setOnClickListener(this);
        deleteIv.setVisibility(View.VISIBLE);
        deleteIv.setOnClickListener(this);
        newsLl.setOnClickListener(this);
        userLl.setOnClickListener(this);
        companyLl.setOnClickListener(this);
        organizationLl.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        inputEt.addTextChangedListener(new MyTextWatcher(inputEt));// 设置文字改变监听
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_aty_cancel_tv:
                // 退出
                finish();
                break;
            case R.id.search_aty_delete_iv:
                // 删除
                inputEt.setText("");
                newsLl.setVisibility(View.GONE);
                userLl.setVisibility(View.GONE);
                companyLl.setVisibility(View.GONE);
                organizationLl.setVisibility(View.GONE);
                noRecordTv.setVisibility(View.VISIBLE);
                noRecordIv.setVisibility(View.VISIBLE);
                break;
            case R.id.search_aty_search_iv:
                // 搜索
                newsLl.setVisibility(View.VISIBLE);
                userLl.setVisibility(View.VISIBLE);
                companyLl.setVisibility(View.VISIBLE);
                organizationLl.setVisibility(View.VISIBLE);
                noRecordTv.setVisibility(View.GONE);
                noRecordIv.setVisibility(View.GONE);
                break;
            case R.id.search_aty_news_ll:
            case R.id.search_aty_user_ll:
            case R.id.search_aty_company_ll:
            case R.id.search_aty_organization_ll:
                // 跳转
                if (!inputEt.getText().toString().isEmpty()) {
                    Intent intent = new Intent(this, SearchResultAty.class);
                    intent.putExtra("keyword", inputEt.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请输入关键词", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /// EditText文字改变监听的TextWatcher
    public class MyTextWatcher implements TextWatcher {
        private EditText etId;
        private String beforeUrl = "https://rong.36kr.com/api/mobi/news/search?keyword=";
        private String afterUrl = "&pageSize=20";

        public MyTextWatcher(EditText etId) {
            this.etId = etId;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (inputEt == etId) {
                if (!(inputEt.getText().toString().isEmpty())) {
                    // 解析网络数据
                    VolleySingle.addRequest(beforeUrl + inputEt.getText().toString() + afterUrl, NewsBean.class,
                            new Response.Listener<NewsBean>() {
                                @Override
                                public void onResponse(NewsBean response) {
                                    if (response.getData().getTotalPages() > 0) {
                                        newsLl.setVisibility(View.VISIBLE);
                                        userLl.setVisibility(View.VISIBLE);
                                        companyLl.setVisibility(View.VISIBLE);
                                        organizationLl.setVisibility(View.VISIBLE);
                                        noRecordTv.setVisibility(View.GONE);
                                        noRecordIv.setVisibility(View.GONE);
                                    } else {
                                        newsLl.setVisibility(View.VISIBLE);
                                        userLl.setVisibility(View.GONE);
                                        companyLl.setVisibility(View.GONE);
                                        organizationLl.setVisibility(View.GONE);
                                        noRecordTv.setVisibility(View.VISIBLE);
                                        noRecordIv.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                }
            }
        }
    }

}
