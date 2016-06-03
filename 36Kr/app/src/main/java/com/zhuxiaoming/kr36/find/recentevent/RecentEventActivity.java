package com.zhuxiaoming.kr36.find.recentevent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.news.activity.ActivityAty;
import com.zhuxiaoming.kr36.util.GsonRequest;
import com.zhuxiaoming.kr36.util.VolleySingle;

import static android.graphics.Color.WHITE;

/**
 * Created by zhuxiaoming on 16/5/13.
 * 发现界面近期活动界面
 */
public class RecentEventActivity extends BaseActivity implements View.OnClickListener {
    private RecentEventAdapter recentEventAdapter;
    private ListView recentEventLv;
    private ImageView backIv;// 标题栏返回按钮
    private TextView titleTv;// 标题
    private RecentEventBean datas;
    private TextView typeTv;
    private TextView timeTv;
    private PopupWindow timePopup;
    private PopupWindow typePopup;
    String beforeUrl = "https://rong.36kr.com/api/mobi/activity/list?page=1&categoryId=";
    String afterUrl = "&pageSize=20";
    String url = "https://rong.36kr.com/api/mobi/activity/list?page=1";

    @Override
    protected int getLayout() {
        return R.layout.activity_recent_event;
    }

    @Override
    protected void initView() {
        recentEventLv = bindView(R.id.recent_event_lv);
        backIv = bindView(R.id.title_bar_content_back_tv);
        titleTv = bindView(R.id.title_bar_content_title_tv);
        typeTv = bindView(R.id.recent_event_type_tv);
        timeTv = bindView(R.id.recent_event_time_tv);
    }

    @Override
    protected void initData() {
        recentEventAdapter = new RecentEventAdapter(this);
        setEventType(url);
        recentEventLv.setAdapter(recentEventAdapter);

        recentEventLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecentEventActivity.this, ActivityAty.class);
                intent.putExtra("url", datas.getData().getData().get(position).getActivityLink());
                startActivity(intent);
            }
        });

        backIv.setOnClickListener(this);
        titleTv.setText("近期活动");
        typeTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        showTypePopup();
        showTimePopup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_content_back_tv:
                // 返回按钮
                finish();
                break;
            case R.id.recent_event_type_tv:
                // 活动类型
                if (typePopup.isShowing()) {
                    typePopup.dismiss();
                } else {
                    typePopup.showAsDropDown(bindView(R.id.recent_event_type_rl));
                    typeTv.setTextColor(getResources().getColor(R.color.colorTabBarText));
                    bindView(R.id.recent_event_type_arrow).setBackground(getResources().getDrawable(R.mipmap.common_filter_arrow_up));
                }
                if (timePopup.isShowing()) {
                    timePopup.dismiss();
                }
                break;
            case R.id.recent_event_time_tv:
                // 活动时间
                if (timePopup.isShowing()) {
                    timePopup.dismiss();
                } else {
                    timePopup.showAsDropDown(bindView(R.id.recent_event_time_rl));
                    timeTv.setTextColor(getResources().getColor(R.color.colorTabBarText));
                    bindView(R.id.recent_event_time_arrow).setBackground(getResources().getDrawable(R.mipmap.common_filter_arrow_up));
                }
                if (typePopup.isShowing()) {
                    typePopup.dismiss();
                }
                break;
        }
    }

    // 点击活动类型的popup
    private void showTypePopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_recent_event_type, null);
        // 设置popup的属性
        Display display = getWindowManager().getDefaultDisplay();
        typePopup = new PopupWindow(display.getWidth(), display.getHeight());
        typePopup.setContentView(view);
        final TextView allTv = (TextView) view.findViewById(R.id.recent_event_type_all);
        TextView demoTv = (TextView) view.findViewById(R.id.recent_event_type_demo_day);
        TextView zoneTv = (TextView) view.findViewById(R.id.recent_event_type_kr_zone);
        TextView investTv = (TextView) view.findViewById(R.id.recent_event_type_invest);
        TextView serviceTv = (TextView) view.findViewById(R.id.recent_event_type_service);
        TextView financingTv = (TextView) view.findViewById(R.id.recent_event_type_financing);
        // 全部
        allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("全部");
                typePopup.dismiss();
                setEventType(url);
            }
        });
        demoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("Demo Day");
                typePopup.dismiss();
                setEventType(beforeUrl + 1 + afterUrl);
            }
        });
        zoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("氪空间");
                typePopup.dismiss();
                setEventType(beforeUrl + 4 + afterUrl);
            }
        });
        investTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("股权投资");
                typePopup.dismiss();
                setEventType(beforeUrl + 5 + afterUrl);
            }
        });
        serviceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("企业服务");
                typePopup.dismiss();
                setEventType(beforeUrl + 6 + afterUrl);
            }
        });
        financingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText("极速融资");
                typePopup.dismiss();
                setEventType(beforeUrl + 7 + afterUrl);
            }
        });
        // 点击popup外面 ,关闭popup
        view.findViewById(R.id.recent_event_type_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePopup.dismiss();
            }
        });
        // popup关闭的时候的监听
        typePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                typeTv.setTextColor(getResources().getColor(R.color.colorToolBarText));
                bindView(R.id.recent_event_type_arrow).setBackground(getResources().getDrawable(R.mipmap.common_filter_arrow_down));
            }
        });
    }

    // 活动时间的popup
    private void showTimePopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_recent_event_time, null);
        // 设置popup的属性
        Display display = getWindowManager().getDefaultDisplay();
        timePopup = new PopupWindow(display.getWidth(), display.getHeight());
        timePopup.setContentView(view);
        TextView allTv = (TextView) view.findViewById(R.id.recent_event_time_all);
        TextView weekendTv = (TextView) view.findViewById(R.id.recent_event_time_this_weekend);
        TextView thisWeekTv = (TextView) view.findViewById(R.id.recent_event_time_this_week);
        TextView nextWeekTv = (TextView) view.findViewById(R.id.recent_event_time_next_week);

        // 全部
        allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTv.setText("全部");
                timePopup.dismiss();
            }
        });
        weekendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTv.setText("本周末");
                timePopup.dismiss();
            }
        });
        thisWeekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTv.setText("本周");
                timePopup.dismiss();
            }
        });
        nextWeekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTv.setText("下周");
                timePopup.dismiss();
            }
        });
        // 点击popup外面 ,关闭popup
        view.findViewById(R.id.recent_event_time_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePopup.dismiss();
            }
        });
        // popup关闭的时候的监听
        timePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                timeTv.setTextColor(getResources().getColor(R.color.colorToolBarText));
                bindView(R.id.recent_event_time_arrow).setBackground(getResources().getDrawable(R.mipmap.common_filter_arrow_down));
            }
        });
    }

    public void setEventType(String type) {
        VolleySingle.addRequest(type, RecentEventBean.class,
                new Response.Listener<RecentEventBean>() {
                    @Override
                    public void onResponse(RecentEventBean response) {
                        recentEventAdapter.setDatas(response);
                        datas = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
    }
}
