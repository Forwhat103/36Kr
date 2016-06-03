package com.zhuxiaoming.kr36.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.tauth.AuthActivity;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.database.GreendaoSingle;
import com.zhuxiaoming.kr36.database.LoginBean;
import com.zhuxiaoming.kr36.database.LoginBeanDao;
import com.zhuxiaoming.kr36.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 登录界面Fragment
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private TextView userTv;
    private EditText userEt;
    private TextView codeTv;
    private EditText codeEt;
    private ImageView userDeleteIv;
    private ImageView codeDeleteIv;
    private CheckBox eyeCb;
    private TextView loginBtn;
    private LoginBeanDao loginBeanDao;
    private UMShareAPI mShareAPI = null;


    @Override
    protected int initLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        userTv = bindView(R.id.login_fragment_user_hint_tv);
        userEt = bindView(R.id.login_fragment_user_et);
        codeTv = bindView(R.id.login_fragment_code_hint_tv);
        codeEt = bindView(R.id.login_fragment_code_et);
        userDeleteIv = bindView(R.id.login_fragment_user_delete_iv);
        codeDeleteIv = bindView(R.id.login_fragment_code_delete_iv);
        eyeCb = bindView(R.id.login_fragment_eye_cb);
        loginBtn = bindView(R.id.login_fragment_login_tv);

    }

    @Override
    protected void initData() {
        mShareAPI = UMShareAPI.get(context);
        bindView(R.id.login_fragment_miss_code_tv).setOnClickListener(this);
        bindView(R.id.login_fragment_wx_login).setOnClickListener(this);
        bindView(R.id.login_fragment_qq_login).setOnClickListener(this);
        bindView(R.id.login_fragment_weibo_login).setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        userTv.setOnClickListener(this);
        codeTv.setOnClickListener(this);
        userDeleteIv.setOnClickListener(this);
        codeDeleteIv.setOnClickListener(this);
        eyeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (eyeCb.isChecked()) {
                    // 设置EditText的密码为可见的
                    codeEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // 设置密码为隐藏的
                    codeEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
//                CharSequence charSequence = codeEt.getText();
//                if (charSequence instanceof Spannable) {
//                    Spannable spanText = (Spannable) charSequence;
//                    Selection.setSelection(spanText, charSequence.length());
//                }
            }
        });
        userEt.addTextChangedListener(new MyTextWatcher(userEt));// 设置文字改变监听
        codeEt.addTextChangedListener(new MyTextWatcher(codeEt));
    }


    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform = null;
        /**
         * 向上平移缩小动画
         */
        AnimationSet upAnimationSet = new AnimationSet(true);
        // 平移
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        translateAnimation.setDuration(200);
        // 缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.8f, 1, 0.8f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(200);
        upAnimationSet.addAnimation(translateAnimation);
        upAnimationSet.addAnimation(scaleAnimation);
        upAnimationSet.setFillAfter(true);

        boolean userEtVisible = false;
        boolean codeEtVisible = false;

        switch (v.getId()) {
            case R.id.login_fragment_user_hint_tv:// 用户名栏
                userTv.startAnimation(upAnimationSet);
                userEt.setVisibility(View.VISIBLE);
                if (codeEtVisible = true && codeEt.getText().toString().isEmpty()) {
                    codeTv.clearAnimation();
                    codeEt.setVisibility(View.GONE);
                    eyeCb.setVisibility(View.GONE);
                    codeEtVisible = false;
                }
                userEtVisible = true;
                break;
            case R.id.login_fragment_code_hint_tv:// 密码栏
                codeTv.startAnimation(upAnimationSet);
                codeEt.setVisibility(View.VISIBLE);
                eyeCb.setVisibility(View.VISIBLE);
                if (userEtVisible = true && userEt.getText().toString().isEmpty()) {
                    userTv.clearAnimation();
                    userEt.setVisibility(View.GONE);
                    userEtVisible = false;
                }
                codeEtVisible = true;
                break;
            case R.id.login_fragment_user_delete_iv:// 登录名清除按钮
                userEt.setText("");
                userDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.login_fragment_code_delete_iv:// 密码清除按钮
                codeEt.setText("");
                codeDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.login_fragment_login_tv:// 登录按钮
                // 单例的favoriteBeanDao 在所有类当中我们保证只有一个favoriteBeanDao
                loginBeanDao = GreendaoSingle.getInstance().getLoginBeanDao();
                if ((!(userEt.getText().toString().isEmpty())) && (!(codeEt.getText().toString().isEmpty()))) {
                    if (loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.User.eq(userEt.getText().toString())).list().size() > 0) {
                        Log.d("login>>>code", loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.User.eq(userEt.getText().toString())).build().list().get(0).getPassword());
                        Log.d("login>>>input", codeEt.getText().toString());
                        if (loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.User.eq(userEt.getText().toString())).build().list().get(0).getPassword().equals(codeEt.getText().toString())) {
                            Toast.makeText(context, "已登录", Toast.LENGTH_SHORT).show();
                            // 跳转到我的界面
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("userName", userEt.getText().toString());
                            // 并发送一条EventBus,将用户信息传递过去
                            EventBus.getDefault().post(new LoginEventBean(userEt.getText().toString(), null, null, null));
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "该用户不存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_fragment_miss_code_tv:// 忘记密码
                Intent intent = new Intent(context, ChangeCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.login_fragment_wx_login:// 微信登录
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.login_fragment_qq_login:// qq登录
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.login_fragment_weibo_login:// 新浪微博登录
                platform = SHARE_MEDIA.SINA;
                break;
        }
        /**begin invoke umeng api**/
        mShareAPI.getPlatformInfo(getActivity(), platform, umAuthListener);
    }
    // 属性动画
//    AnimatorSet set1 = new AnimatorSet();
//    set1.playTogether(ObjectAnimator.ofFloat(codeTv,"translationX",0,0),
//            ObjectAnimator.ofFloat(codeTv,"translationY",0,-1),
//            ObjectAnimator.ofFloat(codeTv,"scaleX",1,0.5f),
//            ObjectAnimator.ofFloat(codeTv,"scaleX",1,0.5f));
//    set1.setDuration(200).start();

    // EditText的文字改变监听的TextWatcher
    public class MyTextWatcher implements TextWatcher {
        private EditText etId;

        public MyTextWatcher(EditText etId) {
            this.etId = etId;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (etId == userEt) {
                if (userEt.getText().toString() != null) {
                    userDeleteIv.setVisibility(View.VISIBLE);
                }
            } else if (etId == codeEt) {
                if (codeEt.getText().toString() != null) {
                    codeDeleteIv.setVisibility(View.VISIBLE);
                }
            } else if ((userEt.getText().toString() != null) || (codeEt.getText().toString() != null)) {
                loginBtn.setTextColor(getResources().getColor(R.color.colorTabBarText));
            }
//            }
//            if (etId == userEt && etId == codeEt) {
//                if ((userEt.getText().toString() != null) || (codeEt.getText().toString() != null)) {
//                    if (userEt.getText().toString() != null) {
//                        userDeleteIv.setVisibility(View.VISIBLE);
//                    } else if (codeEt.getText().toString() != null) {
//                        codeDeleteIv.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
            Log.d("gggg", data + "");

            Gson gson = new Gson();
            String s = gson.toJson(data);
            Map<String, String> retMap = gson.fromJson(s,
                    new TypeToken<Map<String, String>>() {
                    }.getType());
            Log.d("8888888", retMap.get("result"));
            getActivity().finish();
        }


        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getActivity(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("gxxx", "data:" + data);
        Log.d("auth", "on activity re 3");
    }

}
