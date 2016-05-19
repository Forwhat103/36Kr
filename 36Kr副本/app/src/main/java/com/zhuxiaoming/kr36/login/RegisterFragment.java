package com.zhuxiaoming.kr36.login;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 注册界面Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private TextView phoneTv;
    private EditText phoneEt;
    private ImageView deleteIv;
    private TextView getResultCodeBtn;

    @Override
    protected int initLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
        phoneTv = bindView(R.id.register_fragment_phone_hint_tv);
        phoneEt = bindView(R.id.register_fragment_phone_et);
        deleteIv = bindView(R.id.register_fragment_phone_delete_iv);
        getResultCodeBtn = bindView(R.id.register_fragment_get_result_code_btn);
    }

    @Override
    protected void initData() {
        phoneTv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        getResultCodeBtn.setOnClickListener(this);

//        if (phoneEt.getText().toString().length() > 0) {
//                    deleteIv.setVisibility(View.VISIBLE);
//            getResultCodeBtn.setTextColor(Color.BLUE);
//        }
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
        switch (v.getId()) {
            case R.id.register_fragment_phone_hint_tv:
                phoneTv.startAnimation(upAnimationSet);
                phoneEt.setVisibility(View.VISIBLE);
                deleteIv.setVisibility(View.VISIBLE);
                break;
            case R.id.register_fragment_phone_delete_iv:
                phoneEt.setText("");
//                deleteIv.setVisibility(View.GONE);
                break;
            case R.id.register_fragment_get_result_code_btn:
                if (!(phoneEt.getText().toString().isEmpty())) {
//                    deleteIv.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "点死你", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void closePhoneAnim() {
        phoneTv.clearAnimation();
        phoneEt.setVisibility(View.GONE);
    }
}
