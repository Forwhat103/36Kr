package com.zhuxiaoming.kr36.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuxiaoming on 16/5/9.
 */
public abstract class BaseFragment extends Fragment {
    private Context context;// 创建Context的对象

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // context从依附的Activity上获取Context对象
        context = this.context;
    }

    // 初始化视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(initLayout(), container, false);
    }

    // 加载组件
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    // 加载数据
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract int initLayout();

    protected abstract int initView();

    protected abstract void initData();

    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }
}
