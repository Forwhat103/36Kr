package com.zhuxiaoming.kr36.welcome;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.main.MainActivity;

/**
 * Created by zhuxiaoming on 16/4/12.
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView welcomeIv;
    private TextView turnTv;
    private CountDownTimer time;
    private String url = "http://images.139cm.com/subscribe/journalimages/2016/03/24/91_20160324070025936/F2A7A4450383440696F81E9FA80535B5.jpg";

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        welcomeIv = (ImageView) findViewById(R.id.welcome_aty_iv);
        turnTv = (TextView) findViewById(R.id.welcome_aty_turn_tv);
    }

    @Override
    protected void initData() {
        Picasso.with(this).load(url).into(welcomeIv);
        // 倒计时  参数(总时间,频率)
        time = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                turnTv.setText(" " + millisUntilFinished / 1000 + "s 跳过>> ");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }.start();

        // 设置跳转Button的点击事件
        turnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.cancel();// 在跳转的时候 取消timer
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        time.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        time.start();
    }
}
