package com.zhuxiaoming.kr36.welcome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.main.MainActivity;
import com.zhuxiaoming.kr36.jpush.ExampleUtil;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhuxiaoming on 16/5/30.
 * 引导页的界面
 */

public class GuideAty extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ArrayList<View> views;
    private GuideAdapter adapter;
    private LinearLayout dotLl;
    public static boolean isForeground = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.guide_aty_vp);
        dotLl = bindView(R.id.guide_aty_auto_scroll_dots);
    }

    @Override
    public void initData() {
        registerMessageReceiver();  // used for receive msg

        //在第一次启动的时候,存入一个数据,第二次在启动的时候,如果读到了就跳过引导页
        SharedPreferences sharedPreferences = getSharedPreferences("edit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getString("count", null) != null) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
        editor.putString("count", "1");
        editor.commit();
        //获取图片
        View view = getLayoutInflater().inflate(R.layout.guide_one, null);
        View view1 = getLayoutInflater().inflate(R.layout.guide_two, null);
        View view2 = getLayoutInflater().inflate(R.layout.guide_three, null);
        view2.findViewById(R.id.guide_aty_three_turn_tv).setOnClickListener(this);
        views = new ArrayList<>();
        views.add(view);//将图片加入到data中
        views.add(view1);
        views.add(view2);
        adapter = new GuideAdapter(views);
        //将ViewPager 与适配器绑定
        viewPager.setAdapter(adapter);
        addDots();// 添加小点
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < views.size(); i++) {
                    ImageView dotIv = (ImageView) dotLl.getChildAt(i);
                    dotIv.setImageResource(R.mipmap.lunpo);
                }
                ImageView iv = (ImageView) dotLl.getChildAt(position % views.size());
                iv.setImageResource(R.mipmap.lunpo_dangqian);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加轮播图小点的方法
     */
    private void addDots() {
        for (int i = 0; i < views.size(); i++) {
            ImageView dotIv = new ImageView(this);
            dotIv.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            dotIv.setLayoutParams(params);
            // 设置小点样式
            if (i == 0) {
                dotIv.setImageResource(R.mipmap.lunpo_dangqian);
            } else {
                dotIv.setImageResource(R.mipmap.lunpo);
            }
            dotLl.addView(dotIv);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        JPushInterface.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                //  setCostomMsg(showMsg.toString());
            }
        }
    }
}
