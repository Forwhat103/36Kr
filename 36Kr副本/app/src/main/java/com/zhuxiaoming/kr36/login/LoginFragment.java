package com.zhuxiaoming.kr36.login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;

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
    private CheckBox eyeIv;

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
        eyeIv = bindView(R.id.login_fragment_eye_iv);
    }

    @Override
    protected void initData() {
        userTv.setOnClickListener(this);
        codeTv.setOnClickListener(this);
        userDeleteIv.setOnClickListener(this);
        codeDeleteIv.setOnClickListener(this);
        eyeIv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (eyeIv.isChecked()) {
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

        switch (v.getId()) {
            case R.id.login_fragment_user_hint_tv:
                userTv.startAnimation(upAnimationSet);
                userEt.setVisibility(View.VISIBLE);
                userDeleteIv.setVisibility(View.VISIBLE);
                if (codeEtVisible = true && codeEt.getText().toString().isEmpty()) {
                    codeTv.clearAnimation();
                    codeEt.setVisibility(View.GONE);
                    codeEtVisible = false;
                }
                userEtVisible = true;
                break;
            case R.id.login_fragment_code_hint_tv:
                codeTv.startAnimation(upAnimationSet);
                codeEt.setVisibility(View.VISIBLE);
                codeDeleteIv.setVisibility(View.VISIBLE);
                eyeIv.setVisibility(View.VISIBLE);
                if (userEtVisible = true && userEt.getText().toString().isEmpty()) {
                    userTv.clearAnimation();
                    userEt.setVisibility(View.GONE);
                    userEtVisible = false;
                }
                codeEtVisible = true;
                break;
            case R.id.login_fragment_user_delete_iv:
                // 登录名清除按钮
                userEt.setText("");
                break;
            case R.id.login_fragment_code_delete_iv:
                // 密码清除按钮
                codeEt.setText("");
                break;
        }
    }

    // 关闭密码的动画方法
    public void closeCodeAnim() {
        codeTv.clearAnimation();
        codeEt.setVisibility(View.GONE);
    }

    // 关闭用户名的动画方法
    public void closeUserAnim() {
        userTv.clearAnimation();
        userEt.setVisibility(View.GONE);
    }

    // 属性动画
//    AnimatorSet set1 = new AnimatorSet();
//    set1.playTogether(ObjectAnimator.ofFloat(codeTv,"translationX",0,0),
//            ObjectAnimator.ofFloat(codeTv,"translationY",0,-1),
//            ObjectAnimator.ofFloat(codeTv,"scaleX",1,0.5f),
//            ObjectAnimator.ofFloat(codeTv,"scaleX",1,0.5f));
//    set1.setDuration(200).start();
}
