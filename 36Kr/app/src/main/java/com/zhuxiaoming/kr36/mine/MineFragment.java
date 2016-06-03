package com.zhuxiaoming.kr36.mine;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.login.LoginActivity;
import com.zhuxiaoming.kr36.login.LoginEventBean;
import com.zhuxiaoming.kr36.setting.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是我的界面的碎片
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout searchFrameLl;// Toolbar搜索框
    private ImageView settingIv;// 标题栏的设置按钮
    private TextView toLoginTv; // 请登录

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        searchFrameLl = bindView(R.id.find_tool_bar_search_ll);
        settingIv = bindView(R.id.tool_bar_setting_iv);
        toLoginTv = bindView(R.id.mine_fragment_login_tv);

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
        EventBus.getDefault().register(this);// 注册EventBus
    }

    // EventBus接受登录界面传过来的用户信息,设置到
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoginInfo(LoginEventBean data) {
        Log.d("uuuuuuuuuuuuuu", data.getUserName());
        toLoginTv.setText(data.getUserName());// 将用户名设置界面
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_setting_iv:
                // 点击设置按钮跳转到设置界面
                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_fragment_favorite_ll:// 我收藏的文章
                Intent intent2 = new Intent(context, MyFavoriteAty.class);
                startActivity(intent2);
                break;
            case R.id.mine_fragment_login_ll:// 登录
            case R.id.mine_fragment_message_ll:// 我的消息
            case R.id.mine_fragment_order_ll:// 我的订单
            case R.id.mine_fragment_info_rl:// 账号信息
            case R.id.mine_fragment_authentication_ll:// 跟投人认证
            case R.id.mine_fragment_company_ll:// 我投资的公司
            case R.id.mine_fragment_coupon_ll:// 我的投资券
                if (toLoginTv.getText().equals("请登录")) {
                    // 跳转到登录界面
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(context, "你已登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mine_fragment_understand_ll:// 了解股权投资
                Toast.makeText(context, "了解股权投资", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mine_fragment_hot_line_ll:// 服务热线
                showPopFormBottom();// 弹出popup
                break;

        }
    }

    public void showPopFormBottom() {
        HotLinePopup takePhotoPopWin = new HotLinePopup(context, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(getActivity().findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mine_popup_call_tv:
                    // 拨打热线电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-955-3636"));
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);// 取消注册EventBus
    }
}
