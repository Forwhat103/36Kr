package com.zhuxiaoming.kr36.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;

/**
 * Created by zhuxiaoming on 16/5/28.
 * 修改密码界面
 */
public class ChangeCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView userTv;
    private EditText userEt;
    private TextView codeTv;
    private EditText codeEt;
    private TextView sureCodeTv;
    private EditText sureCodeEt;
    private ImageView userDeleteIv;
    private ImageView codeDeleteIv;
    private ImageView sureCodeDeleteIv;
    private CheckBox eyeCb;
    private CheckBox sureCodeEyeCb;
    private TextView sureBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_change_code;
    }

    @Override
    protected void initView() {
        userTv = bindView(R.id.change_code_aty_user_tv);
        userEt = bindView(R.id.change_code_aty_user_et);
        codeTv = bindView(R.id.change_code_aty_code_tv);
        codeEt = bindView(R.id.change_code_aty_code_et);
        sureCodeTv = bindView(R.id.change_code_aty_sure_code_tv);
        sureCodeEt = bindView(R.id.change_code_aty_sure_code_et);
        userDeleteIv = bindView(R.id.change_code_aty_delete_user_iv);
        codeDeleteIv = bindView(R.id.change_code_aty_code_delete_iv);
        sureCodeDeleteIv = bindView(R.id.change_code_aty_sure_code_delete_iv);
        eyeCb = bindView(R.id.change_code_aty_code_eye_cb);
        sureCodeEyeCb = bindView(R.id.change_code_aty_sure_code_eye_cb);
        sureBtn = bindView(R.id.change_code_aty_sure_change_tv);
    }

    @Override
    protected void initData() {
        bindView(R.id.change_code_aty_back_tv).setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        userTv.setOnClickListener(this);
        codeTv.setOnClickListener(this);
        sureCodeTv.setOnClickListener(this);
        userDeleteIv.setOnClickListener(this);
        codeDeleteIv.setOnClickListener(this);
        sureCodeDeleteIv.setOnClickListener(this);
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
            }
        });
        sureCodeEyeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sureCodeEyeCb.isChecked()) {
                    // 设置EditText的密码为可见的
                    sureCodeEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // 设置密码为隐藏的
                    sureCodeEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        // 设置文字改变监听
        userEt.addTextChangedListener(new MyTextWatcher(userEt));
        codeEt.addTextChangedListener(new MyTextWatcher(codeEt));
        sureCodeEt.addTextChangedListener(new MyTextWatcher(codeEt));
    }


    @Override
    public void onClick(View v) {
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
        boolean sureCodeEtVisible = false;

        switch (v.getId()) {
            case R.id.change_code_aty_back_tv:
                // 返回
                finish();
                break;
            case R.id.change_code_aty_user_tv:// 用户名栏
                userTv.startAnimation(upAnimationSet);
                userEt.setVisibility(View.VISIBLE);
                if (codeEtVisible = true && codeEt.getText().toString().isEmpty()) {
                    codeTv.clearAnimation();
                    codeEt.setVisibility(View.GONE);
                    eyeCb.setVisibility(View.GONE);
                    codeEtVisible = false;
                }
                if (sureCodeEtVisible = true && sureCodeEt.getText().toString().isEmpty()) {
                    sureCodeTv.clearAnimation();
                    sureCodeEt.setVisibility(View.GONE);
                    sureCodeEyeCb.setVisibility(View.GONE);
                    sureCodeEtVisible = false;
                }
                userEtVisible = true;
                break;
            case R.id.change_code_aty_code_tv:// 密码栏
                codeTv.startAnimation(upAnimationSet);
                codeEt.setVisibility(View.VISIBLE);
                eyeCb.setVisibility(View.VISIBLE);
                if (userEtVisible = true && userEt.getText().toString().isEmpty()) {
                    userTv.clearAnimation();
                    userEt.setVisibility(View.GONE);
                    userEtVisible = false;
                }
                if (sureCodeEtVisible = true && sureCodeEt.getText().toString().isEmpty()) {
                    sureCodeTv.clearAnimation();
                    sureCodeEt.setVisibility(View.GONE);
                    sureCodeEyeCb.setVisibility(View.GONE);
                    sureCodeEtVisible = false;
                }
                codeEtVisible = true;
                break;
            case R.id.change_code_aty_sure_code_tv:// 确认密码栏
                sureCodeTv.startAnimation(upAnimationSet);
                sureCodeEt.setVisibility(View.VISIBLE);
                sureCodeEyeCb.setVisibility(View.VISIBLE);
                if (userEtVisible = true && userEt.getText().toString().isEmpty()) {
                    userTv.clearAnimation();
                    userEt.setVisibility(View.GONE);
                    userEtVisible = false;
                }
                if (codeEtVisible = true && codeEt.getText().toString().isEmpty()) {
                    codeTv.clearAnimation();
                    codeEt.setVisibility(View.GONE);
                    eyeCb.setVisibility(View.GONE);
                    codeEtVisible = false;
                }
                sureCodeEtVisible = true;
                break;
            case R.id.change_code_aty_delete_user_iv:// 登录名清除按钮
                userEt.setText("");
                userDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.change_code_aty_code_delete_iv:// 密码清除按钮
                codeEt.setText("");
                codeDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.change_code_aty_sure_code_delete_iv:// 密码清除按钮
                sureCodeEt.setText("");
//                sureCodeDeleteIv.setVisibility(View.GONE);
                break;
        }
    }

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
//            } else if (etId == sureCodeEt) {
//                if (sureCodeEt.getText().toString() != null) {
//                    sureCodeDeleteIv.setVisibility(View.VISIBLE);
//                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
