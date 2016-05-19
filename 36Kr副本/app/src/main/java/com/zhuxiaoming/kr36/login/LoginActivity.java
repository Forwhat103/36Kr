package com.zhuxiaoming.kr36.login;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 登录注册界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> fragments;
    private TabLayout loginTab;
    private ViewPager loginVp;
    private ImageView closeIv;// 关闭按钮
    private LoginAdapter loginAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginTab = bindView(R.id.login_aty_tab);
        loginVp = bindView(R.id.login_aty_vp);
        closeIv = bindView(R.id.login_aty_close_iv);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginAdapter.setFragments(fragments);
        loginVp.setAdapter(loginAdapter);
        loginTab.setupWithViewPager(loginVp);
        loginTab.setTabTextColors(Color.rgb(116, 174, 247), Color.WHITE);
        closeIv.setOnClickListener(this);

        loginVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        if (((EditText) bindView(R.id.register_fragment_phone_et)).getText().toString().isEmpty()) {
//                            bindView(R.id.register_fragment_phone_et).setVisibility(View.GONE);
//                            bindView(R.id.register_fragment_phone_hint_tv).clearAnimation();
//                            new RegisterFragment().closePhoneAnim();
//                        }
//                        break;
//                    case 1:
//                        if (((EditText) bindView(R.id.login_fragment_user_et)).getText().toString().isEmpty()) {
//                            bindView(R.id.login_fragment_user_et).setVisibility(View.GONE);
//                            bindView(R.id.login_fragment_user_hint_tv).clearAnimation();
//                        }
//                        if (((EditText) bindView(R.id.login_fragment_code_et)).getText().toString().isEmpty()) {
//                            bindView(R.id.login_fragment_code_et).setVisibility(View.GONE);
//                            bindView(R.id.login_fragment_code_hint_tv).clearAnimation();
//                        }
//                        break;
//                }
                }

                @Override
                public void onPageScrollStateChanged ( int state){
                }
            });

        }

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.login_aty_close_iv:
                    // 关闭按钮
                    finish();
                    break;
            }
        }
    }
