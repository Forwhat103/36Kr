package com.zhuxiaoming.kr36.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.setting.SettingActivity;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是我的界面的碎片
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout searchFrameLl;// Toolbar搜索框
    private ImageView settingIv;// 标题栏的设置按钮

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_setting_iv:
                // 点击设置按钮跳转到设置界面
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
