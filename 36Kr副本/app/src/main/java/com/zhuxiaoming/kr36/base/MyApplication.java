package com.zhuxiaoming.kr36.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhuxiaoming on 16/5/17.
 */
public class MyApplication extends Application{
    private static Context myContext;

    // Application 创建的原因是我们需要一个属于自己的大"环境"(Context)
    // 第一个生命周期中我们对Context赋值
    @Override
    public void onCreate() {
        super.onCreate();
        // This代表当前的环境
        myContext = this;
    }

    // 对外提供一个方法 这个方法就是让别的类获取自己的Context对象
    public static  Context getContext(){
        return myContext;
    }
}
