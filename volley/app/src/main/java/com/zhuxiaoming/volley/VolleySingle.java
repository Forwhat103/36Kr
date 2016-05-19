package com.zhuxiaoming.volley;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ObbInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by zhuxiaoming on 16/5/16.
 */
public class VolleySingle {
    private static Context context;
    private RequestQueue queue;// 请求队列
    private static VolleySingle ourInstance = new VolleySingle(context);

    // 获取单例的对象
    public static VolleySingle getInstance() {
        return ourInstance;
    }

    private VolleySingle(Context context) {
        // 获取Application里面的context
        this.context = context.getApplicationContext();
        queue = getQueue();// 初始化我的请求对列

    }

    // 提供一个方法,新建请求队列
    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public static final String TAG = "VolleySingleton";

    // 添加请求
    public <T> void _addRequest(Request<T> request) {
        request.setTag(TAG);// 为我的请求添加标签,方便管理
        queue.add(request);// 将请求添加到队列中
    }

    public <T> void _addRequest(Request<T> request, Object tag) {
        request.setTag(tag);
        queue.add(request);
    }

    public void _addRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        // 创建StringRequest 三个参数分别是 url接口网址;成功时的回调;失败时的回调
        StringRequest stringRequest = new StringRequest(url, listener, errorListener);
        // 将请求添加到队列中
        queue.add(stringRequest);
    }

    public <T> void _addRequest(String url, Class<T> mClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        // 创建StringRequest 三个参数分别是 url接口网址;成功时的回调;失败时的回调
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, url, errorListener, listener, mClass);
        // 将请求添加到队列中
        _addRequest(gsonRequest);
    }

    // 这个方法是将请求移除队列
    public void removeRequest(Object tag) {
        queue.cancelAll(tag);// 根据不同的tag移除出队列
    }

    public static void addRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        // 获取单例对象,调用添加请求的方法(这个是StringRequest的请求)
        getInstance()._addRequest(url, listener, errorListener);
    }

    public static <T> void addRequest(String url, Class<T> mClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        // 同上方法将GsonRequest请求加入单例的队列
        getInstance()._addRequest(url, mClass, listener, errorListener);

    }
}
