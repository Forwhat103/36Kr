package com.zhuxiaoming.kr36.news.all;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.base.MyApplication;
import com.zhuxiaoming.kr36.news.NewsBean;
import com.zhuxiaoming.kr36.news.activity.ActivityAty;
import com.zhuxiaoming.kr36.news.details.NewsDetailsActivity;
import com.zhuxiaoming.kr36.util.SwipeRefreshLoadingLayout;
import com.zhuxiaoming.kr36.util.VolleySingle;

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
    private NewsCycleViewAdapter adapter;// 轮播图适配器
    private ScheduledExecutorService scheduledExecutorService;// 线程池对象
    private View cycleView;// 轮播图
    private NewsBean datas;
    private PullToRefreshListView ptrf;

    @Override
    protected int initLayout() {
        return R.layout.fragment_news_all;
    }

    @Override
    protected void initView() {
        ptrf = bindView(R.id.news_fragment_all_lv);
        cycleView = LayoutInflater.from(getContext()).inflate(R.layout.news_listview_header, null);
        mViewPaper = (ViewPager) cycleView.findViewById(R.id.vp);

    }

    @Override
    protected void initData() {
        listView = ptrf.getRefreshableView();// 将刷新加载与ListView绑定,为了添加头布局
        initImage();// 加载轮播图图片
        adapter = new NewsCycleViewAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean isAutoPlay = false;

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dots.size(); i++) {
                    if (i == position) {
                        (dots.get(position)).setBackgroundResource(R.drawable.dot_focused);
                    } else {
                        (dots.get(i)).setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case 1:// 手势滑动，空闲中
                        isAutoPlay = false;
                        break;
                    case 2:// 界面切换中
                        isAutoPlay = true;
                        break;
                    case 0:// 滑动结束，即切换完毕或者加载完毕
                        // 当前为最后一张，此时从右向左滑，则切换到第一张
                        if (mViewPaper.getCurrentItem() == mViewPaper.getAdapter().getCount() - 1 && !isAutoPlay) {
                            mViewPaper.setCurrentItem(0);
                        }
                        // 当前为第一张，此时从左向右滑，则切换到最后一张
                        else if (mViewPaper.getCurrentItem() == 0 && !isAutoPlay) {
                            mViewPaper.setCurrentItem(mViewPaper.getAdapter().getCount() - 1);
                        }
                        break;
                }
            }
        });

        // 下载解析数据
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up", NewsBean.class,
                new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {
                        newsAllAdapter.setDatas(response);
                        datas = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        ptrf.setMode(PullToRefreshBase.Mode.BOTH);
        ptrf.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up", NewsBean.class,
                        new Response.Listener<NewsBean>() {
                            @Override
                            public void onResponse(NewsBean response) {
                                newsAllAdapter.setDatas(response);
                                ptrf.onRefreshComplete();// 取消动画
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                Toast.makeText(MyApplication.getContext(), "刷新完毕", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                Toast.makeText(MyApplication.getContext(), "加载完毕", Toast.LENGTH_SHORT).show();
            }
        });
        newsAllAdapter = new NewsAllAdapter(getContext());
        ptrf.setAdapter(newsAllAdapter);
        listView.addHeaderView(cycleView);
        // 点击Item的跳转
        ptrf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("key", "allId");
                intent.putExtra("allId", datas.getData().getData().get(position).getFeedId());
                startActivity(intent);
            }
        });

    }

    private void initImage() {
        //显示轮播图的小点
        dots = new ArrayList<>();
        dots.add(cycleView.findViewById(R.id.dot_0));
        dots.add(cycleView.findViewById(R.id.dot_1));
        dots.add(cycleView.findViewById(R.id.dot_2));
        dots.add(cycleView.findViewById(R.id.dot_3));
        dots.add(cycleView.findViewById(R.id.dot_4));
        dots.add(cycleView.findViewById(R.id.dot_5));
        dots.add(cycleView.findViewById(R.id.dot_6));
        dots.add(cycleView.findViewById(R.id.dot_7));

        VolleySingle.addRequest("http://chuang.36kr.com/api/actapply", ViewBean.class,
                new Response.Listener<ViewBean>() {
                    @Override
                    public void onResponse(final ViewBean response) {
                        // 轮播图显示的图片
                        images = new ArrayList<>();
                        for (int i = 0; i < dots.size(); i++) {
                            ImageView imageView = new ImageView(MyApplication.getContext());
                            Picasso.with(getContext()).load(response.getData().getData().get(i).getListImageUrl()).into(imageView);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            images.add(imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), ActivityAty.class);
                                    intent.putExtra("url", response.getData().getData().get(finalI).getLink());
                                    startActivity(intent);
                                }
                            });
                        }
                        adapter.setViewBeans(images);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 自定义轮播图Adapter
     * 继承自PagerAdapter，PagerAdapter中实现了图片切换的动画效果
     */
    private class NewsCycleViewAdapter extends PagerAdapter {
        private List<ImageView> imageViews;

        public void setViewBeans(List<ImageView> imageViews) {
            this.imageViews = imageViews;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return imageViews == null ? 0 : imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(imageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(imageViews.get(position));
            return imageViews.get(position);
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
            currentItem = (currentItem + 1) % images.size();
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
