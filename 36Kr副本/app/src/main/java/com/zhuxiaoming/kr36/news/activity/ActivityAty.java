package com.zhuxiaoming.kr36.news.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;

/**
 * Created by zhuxiaoming on 16/5/18.
 * 新闻的活动界面
 */
public class ActivityAty extends BaseActivity {
    private WebView web;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_aty;
    }

    @Override
    protected void initView() {
        web = bindView(R.id.news_aty_activity_web);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        web.loadUrl(intent.getStringExtra("url"));
        web.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            finish();
        }
        return true;
    }
}
