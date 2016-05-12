package com.zhuxiaoming.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // 三个参数:接口网址;请求成功时候回调的方法;请求失败的时候回调的方法
        StringRequest request = new StringRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("mainActivity", response);
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(response, Bean.class);
                        Log.d("MainActivity", "bean.getData().getPage():" + bean.getData().getPageSize());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // 将请求添加到队列中
        requestQueue.add(request);
    }
}
