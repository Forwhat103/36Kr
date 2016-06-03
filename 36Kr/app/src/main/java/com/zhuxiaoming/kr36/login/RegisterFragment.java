package com.zhuxiaoming.kr36.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.database.DaoMaster;
import com.zhuxiaoming.kr36.database.DaoSession;
import com.zhuxiaoming.kr36.database.FavoriteBean;
import com.zhuxiaoming.kr36.database.FavoriteBeanDao;
import com.zhuxiaoming.kr36.database.GreendaoSingle;
import com.zhuxiaoming.kr36.database.LoginBean;
import com.zhuxiaoming.kr36.database.LoginBeanDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 注册界面Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    //    private TextView phoneTv;
//    private EditText phoneEt;
//    private ImageView deleteIv;
    private TextView userTv;
    private EditText userEt;
    private TextView codeTv;
    private EditText codeEt;
    private ImageView userDeleteIv;
    private ImageView codeDeleteIv;
    private CheckBox eyeIv;
    private TextView getResultCodeBtn;
    private LoginBeanDao loginBeanDao;

    @Override
    protected int initLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
//        phoneTv = bindView(R.id.register_fragment_phone_hint_tv);
//        phoneEt = bindView(R.id.register_fragment_phone_et);
//        deleteIv = bindView(R.id.register_fragment_phone_delete_iv);
        userTv = bindView(R.id.register_fragment_user_hint_tv);
        userEt = bindView(R.id.register_fragment_user_et);
        codeTv = bindView(R.id.register_fragment_code_hint_tv);
        codeEt = bindView(R.id.register_fragment_code_et);
        userDeleteIv = bindView(R.id.register_fragment_user_delete_iv);
        codeDeleteIv = bindView(R.id.register_fragment_code_delete_iv);
        eyeIv = bindView(R.id.register_fragment_eye_iv);
        getResultCodeBtn = bindView(R.id.register_fragment_get_result_code_btn);
    }

    @Override
    protected void initData() {
        userEt.addTextChangedListener(new MyTextWatcher(userEt));//设置文字改变监听
        codeEt.addTextChangedListener(new MyTextWatcher(codeEt));
        getResultCodeBtn.setOnClickListener(this);
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
            case R.id.register_fragment_user_hint_tv:
                userTv.startAnimation(upAnimationSet);
                userEt.setVisibility(View.VISIBLE);
                if (codeEtVisible = true && codeEt.getText().toString().isEmpty()) {
                    codeTv.clearAnimation();
                    codeEt.setVisibility(View.GONE);
                    codeEt.setVisibility(View.GONE);
                    codeEtVisible = false;
                }
                userEtVisible = true;
                break;
            case R.id.register_fragment_code_hint_tv:
                codeTv.startAnimation(upAnimationSet);
                codeEt.setVisibility(View.VISIBLE);
                eyeIv.setVisibility(View.VISIBLE);
                if (userEtVisible = true && userEt.getText().toString().isEmpty()) {
                    userTv.clearAnimation();
                    userEt.setVisibility(View.GONE);
                    userEtVisible = false;
                }
                codeEtVisible = true;
                break;
            case R.id.register_fragment_user_delete_iv:
                // 登录名清除按钮
                userEt.setText("");
                userDeleteIv.setVisibility(View.GONE);
                getResultCodeBtn.setTextColor(getResources().getColor(R.color.colorLoginButtonText));
                break;
            case R.id.register_fragment_code_delete_iv:
                // 密码清除按钮
                codeEt.setText("");
                codeDeleteIv.setVisibility(View.GONE);
                getResultCodeBtn.setTextColor(getResources().getColor(R.color.colorLoginButtonText));
                break;
            case R.id.register_fragment_get_result_code_btn:// 注册按钮
                // 单例的favoriteBeanDao 在所有类当中我们保证只有一个favoriteBeanDao
                loginBeanDao = GreendaoSingle.getInstance().getLoginBeanDao();
                if ((!(userEt.getText().toString().isEmpty())) && (!(codeEt.getText().toString().isEmpty()))) {
                    if (loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.User.eq(userEt.getText().toString())).list().size() > 0) {
                        Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        loginBeanDao.insert(new LoginBean(userEt.getText().toString(), codeEt.getText().toString()));
                        Toast.makeText(context, "注册成功,请登录!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "请输入想要注册的用户名和密码", Toast.LENGTH_SHORT).show();
                }
                List<LoginBean> logins = loginBeanDao.queryBuilder().list();
                for (LoginBean login : logins) {
                    Log.d("login>>>数据库全部账户数据", "login:" + login.getUser() + " - " + login.getPassword());
                }
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
            } else if (etId == userEt && etId == codeEt) {
                if ((userEt.getText().toString() != null) || (codeEt.getText().toString() != null)) {
                    getResultCodeBtn.setTextColor(getResources().getColor(R.color.colorTabBarText));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
