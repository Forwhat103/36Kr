package com.zhuxiaoming.kr36.news.all;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.news.NewsBean;
import com.zhuxiaoming.kr36.search.SearchActivity;
import com.zhuxiaoming.kr36.util.GsonRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by zhuxiaoming on 16/5/9.
 * 这是新闻界面的碎片
 */
public class NewsAllFragment extends BaseFragment {
    private NewsAllAdapter newsAllAdapter;
    private ListView listView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    // 记录上一次点的位置
    private int oldPosition = 0;
    // 存放图片的id
    private int[] imageIds = {
            R.mipmap.cycle_view_one,
            R.mipmap.cycle_view_two,
            R.mipmap.cycle_view_three,
            R.mipmap.cycle_view_four,
    };
    private NewsCycleViewAdapter adapter;// 轮播图适配器
    private ScheduledExecutorService scheduledExecutorService;// 线程池对象
    private View cycleView;// 轮播图


    @Override
    protected int initLayout() {
        return R.layout.fragment_news_all;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.news_fragment_all_lv);
        cycleView = LayoutInflater.from(getContext()).inflate(R.layout.news_listview_header, null);
        mViewPaper = (ViewPager) cycleView.findViewById(R.id.vp);

    }

    @Override
    protected void initData() {
        initImage();// 加载轮播图图片
        adapter = new NewsCycleViewAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // 创建请求队列对象
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // 三个参数:接口网址;请求成功时候回调的方法;请求失败的时候回调的方法
        GsonRequest<NewsBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up"
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }, new Response.Listener<NewsBean>() {
            @Override
            public void onResponse(NewsBean response) {
                newsAllAdapter.setDatas(response);
            }
        }, NewsBean.class);
        // 将请求添加到队列中
        requestQueue.add(gsonRequest);
        newsAllAdapter = new NewsAllAdapter(getContext());
        listView.setAdapter(newsAllAdapter);
        listView.addHeaderView(cycleView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "就不跳", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initImage() {
        // 轮播图显示的图片
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }

        //显示轮播图的小点
        dots = new ArrayList<>();
        dots.add(cycleView.findViewById(R.id.dot_0));
        dots.add(cycleView.findViewById(R.id.dot_1));
        dots.add(cycleView.findViewById(R.id.dot_2));
        dots.add(cycleView.findViewById(R.id.dot_3));
    }

    /**
     * 自定义轮播图Adapter
     * 继承自PagerAdapter，PagerAdapter中实现了图片切换的动画效果
     */
    private class NewsCycleViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }


    }

    /**
     * 利用线程池定时执行动画轮播
     * 这个方法是界面创建完成，启动时的回调方法，创建线程池启动定时调度任务，
     * 调用自定义的线程任务，实现定时实现图片轮播效果。
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 2, 2, TimeUnit.SECONDS);
    }


    /**
     * 这个类实现了Runnable接口，在run方法中实现了图片轮播的显示效果，
     * 并通过handler通知UI线程，UI线程更新界面显示，这里我们不需要传递任何数据，所以通过handler发送空消息即可。
     */
    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }
    };

    /**
     * 停止线程池的执行，释放线程池资源。
     */
    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }


}
