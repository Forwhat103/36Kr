package com.zhuxiaoming.kr36.news.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.base.MyApplication;
import com.zhuxiaoming.kr36.util.FormatDisplayTime;
import com.zhuxiaoming.kr36.util.VolleySingle;


import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Created by zhuxiaoming on 16/5/17.
 * 新闻界面详情
 */
public class NewsDetailsActivity extends BaseActivity {
    private ImageView headImage;
    private TextView nameTv;
    //    private TextView infoTv;
    private TextView titleTv;
    private TextView summaryTv;
    private TextView timeTv;
    private TextView contentTv;
    private String beforeUrl = "https://rong.36kr.com/api/mobi/news/";
    private Html.ImageGetter imgGetter;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        headImage = bindView(R.id.news_details_aty_image);
        nameTv = bindView(R.id.news_details_aty_name_tv);
        titleTv = bindView(R.id.news_details_aty_title_tv);
        summaryTv = bindView(R.id.news_details_aty_summary_tv);
        timeTv = bindView(R.id.news_details_aty_time_tv);
        contentTv = bindView(R.id.news_details_aty_web);
        struct();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        final String id = intent.getStringExtra(key);
        if (id != null) {
            VolleySingle.addRequest(beforeUrl + id, DetailsBean.class,
                    new Response.Listener<DetailsBean>() {
                        @Override
                        public void onResponse(DetailsBean response) {
                            Picasso.with(NewsDetailsActivity.this).load(response.getData().getUser().getAvatar()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(headImage);
                            nameTv.setText(response.getData().getUser().getName());
                            titleTv.setText(response.getData().getTitle());
                            summaryTv.setText(response.getData().getSummary());
                            // 设置时间
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = new FormatDisplayTime().formatDisplayTime(sdf.format(response.getData().getPublishTime()), "yyyy-MM-dd HH:mm:ss");
                            timeTv.setText(time);
                            String html = response.getData().getContent();
                            contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                            contentTv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                            contentTv.setText(Html.fromHtml(html, imgGetter, null));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
        }

        //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
        // 获取网路图片
        imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Log.i("RG", "source---?>>>" + source);
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    Log.i("RG", "url---?>>>" + url);
                    drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                DisplayMetrics dm = new DisplayMetrics();
                // 获取屏幕信息
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                drawable.setBounds(0, 0, (dm.widthPixels - 180), (dm.widthPixels - 180) / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                Log.i("RG", "url---?>>>" + url);
                return drawable;
            }
        };
    }

    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }
}
