package com.zhuxiaoming.kr36.base;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

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

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    // 对外提供一个方法 这个方法就是让别的类获取自己的Context对象
    public static  Context getContext(){
        return myContext;
    }

    //各个平台的配置
    {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wxfd3df5e64e4b2109", "9607b0ce1e4e371c3eb4ce0cb7982e30");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("2343389171","a7d19716cd1bd7e89c7d8908529a27c7");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105363925", "FdUs8vQYxl82vPJm");
    }
}
