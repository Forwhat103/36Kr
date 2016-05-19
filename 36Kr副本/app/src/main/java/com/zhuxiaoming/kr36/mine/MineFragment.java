package com.zhuxiaoming.kr36.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.login.LoginActivity;
import com.zhuxiaoming.kr36.setting.SettingActivity;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是我的界面的碎片
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout searchFrameLl;// Toolbar搜索框
    private ImageView settingIv;// 标题栏的设置按钮
//    private LinearLayout loginLl;// 登录
//    private LinearLayout messageLl;// 我的消息
//    private LinearLayout orderLl;// 我的订单
//    private LinearLayout infoLl;// 账号信息
//    private LinearLayout authenticationLl;// 跟投人认证
//    private LinearLayout favoriteLl;// 我收藏的文章
//    private LinearLayout companyLl;// 我投资的公司
//    private LinearLayout couponLl;// 我的投资券
//    private LinearLayout understandLl;// 了解股权投资
//    private LinearLayout hotLineLl;// 客服热线

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        searchFrameLl = bindView(R.id.find_tool_bar_search_ll);
        settingIv = bindView(R.id.tool_bar_setting_iv);
    }

    @Override
    protected void initData() {
        searchFrameLl.setVisibility(View.GONE);
        settingIv.setOnClickListener(this);
        bindView(R.id.mine_fragment_login_ll).setOnClickListener(this);// 登录
        bindView(R.id.mine_fragment_message_ll).setOnClickListener(this);// 我的消息
        bindView(R.id.mine_fragment_order_ll).setOnClickListener(this);// 我的订单
        bindView(R.id.mine_fragment_info_rl).setOnClickListener(this);// 账号信息
        bindView(R.id.mine_fragment_authentication_ll).setOnClickListener(this);// 跟投人认证
        bindView(R.id.mine_fragment_favorite_ll).setOnClickListener(this);// 我收藏的文章
        bindView(R.id.mine_fragment_company_ll).setOnClickListener(this);// 我投资的公司
        bindView(R.id.mine_fragment_coupon_ll).setOnClickListener(this);// 我的投资券
        bindView(R.id.mine_fragment_understand_ll).setOnClickListener(this);// 了解股权投资
        bindView(R.id.mine_fragment_hot_line_ll).setOnClickListener(this);// 客服热线

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tool_bar_setting_iv:
                // 点击设置按钮跳转到设置界面
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_fragment_login_ll:// 登录
            case R.id.mine_fragment_message_ll:// 我的消息
            case R.id.mine_fragment_order_ll:// 我的订单
            case R.id.mine_fragment_info_rl:// 账号信息
            case R.id.mine_fragment_authentication_ll:// 跟投人认证
            case R.id.mine_fragment_favorite_ll:// 我收藏的文章
            case R.id.mine_fragment_company_ll:// 我投资的公司
            case R.id.mine_fragment_coupon_ll:// 我的投资券
                // 跳转到登录界面
                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_fragment_understand_ll:// 了解股权投资
                break;
            case R.id.mine_fragment_hot_line_ll:// 服务热线
                break;

        }
    }
}
