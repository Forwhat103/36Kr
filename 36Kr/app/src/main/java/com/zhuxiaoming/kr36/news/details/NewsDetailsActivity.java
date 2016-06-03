package com.zhuxiaoming.kr36.news.details;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.base.MyApplication;
import com.zhuxiaoming.kr36.database.FavoriteBean;
import com.zhuxiaoming.kr36.database.FavoriteBeanDao;
import com.zhuxiaoming.kr36.database.GreendaoSingle;
import com.zhuxiaoming.kr36.util.FormatDisplayTime;
import com.zhuxiaoming.kr36.util.MyScrollView;
import com.zhuxiaoming.kr36.util.VolleySingle;


import org.w3c.dom.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhuxiaoming on 16/5/17.
 * 新闻界面详情
 */
public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView headImage;
    private ImageView arrowIv;
    private TextView nameTv;
    private TextView infoTv;
    private TextView titleTv;
    private TextView summaryTv;
    private TextView timeTv;
    private TextView contentTv;
    private String beforeUrl = "https://rong.36kr.com/api/mobi/news/";
    private Html.ImageGetter imgGetter;
    private PopupWindow popupWindow;
    private String id;
    private PopupAdapter popupAdapter;
    private DetailsAuthorBean bean;
    private MyScrollView scrollView;
    private LinearLayout toolbarLl;
    private FavoriteBeanDao favoriteBeanDao;// 数据库内收藏表的操作对象
    private ImageView favoriteIv;
    boolean isHave = false;
    private DetailsBean datas;


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
        arrowIv = bindView(R.id.news_details_aty_arrow_iv);
        infoTv = bindView(R.id.news_details_aty_info_tv);
        scrollView = bindView(R.id.news_details_aty_scroll);
        toolbarLl = bindView(R.id.news_details_toolbar_ll);
        favoriteIv = bindView(R.id.news_details_toolbar_favorite_iv);
        struct();
    }


    @Override
    protected void initData() {
        bindView(R.id.news_details_toolbar_back_iv).setOnClickListener(this);
        bindView(R.id.news_details_toolbar_share_iv).setOnClickListener(this);
        favoriteIv.setOnClickListener(this);
        arrowIv.setOnClickListener(this);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        id = intent.getStringExtra(key);
        // 遍历数据库
        List<FavoriteBean> favorites = GreendaoSingle.getInstance().getFavoriteBeanDao().queryBuilder().list();
        for (FavoriteBean favorite : favorites) {
            if (id.equals(favorite.getFeedId())) {
                favoriteIv.setImageDrawable(getResources().getDrawable(R.mipmap.news_toolbar_icon_favorite_blue));
            }
        }
        // 解析数据
        if (id != null) {
            VolleySingle.addRequest(beforeUrl + id, DetailsBean.class,
                    new Response.Listener<DetailsBean>() {
                        @Override
                        public void onResponse(DetailsBean response) {
                            datas = response;
                            Picasso.with(NewsDetailsActivity.this).load(response.getData().getUser().getAvatar()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(headImage);
                            nameTv.setText(response.getData().getUser().getName());
                            titleTv.setText(response.getData().getTitle());
                            summaryTv.setText("『  " + response.getData().getSummary() + "  』");
                            // 设置时间
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = new FormatDisplayTime().formatDisplayTime(sdf.format(response.getData().getPublishTime()), "yyyy-MM-dd HH:mm:ss");
                            timeTv.setText(time);
                            String html = response.getData().getContent();
                            contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                            contentTv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                            contentTv.setText(Html.fromHtml(html, imgGetter, null));
                            infoTv.setText(response.getData().getType());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
        }
        initPopup();
        //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
        // 获取网路图片
        imgGetter = new Html.ImageGetter()

        {
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                DisplayMetrics dm = new DisplayMetrics();
                // 获取屏幕信息
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                drawable.setBounds(0, 0, dm.widthPixels - 180,
                        (dm.widthPixels - 180) / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        // ScrollView的滑动监听
        scrollView.setScrollListener(new MyScrollView.ScrollListener() {
            @Override
            public void scrollOritention(int oritention) {
                switch (oritention) {
                    case 1:// 向下滑动
                        toolbarLl.setVisibility(View.GONE);
                        break;
                    case 16:// 向上滑动
                        toolbarLl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
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

    @Override
    public void onClick(View v) {
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.info_icon_1);
        UMImage image = new UMImage(NewsDetailsActivity.this, "http://www.umeng.com/images/pic/social/integrated_3.png");
        //UMImage image = new UMImage(NewsDetailsActivity.this,bitmap);
        //UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        UMusic music = new UMusic("http://y.qq.com/#type=song&mid=002I7CmS01UAIH&tpl=yqq_song_detail");
        music.setTitle("This is music title");
        music.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

        music.setDescription("my description");

        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        video.setThumb("http://www.adiumxtras.com/images/thumbs/dango_menu_bar_icon_set_11_19047_6240_thumb.png");
        String url = "http://www.umeng.com";
        switch (v.getId()) {
            case R.id.news_details_aty_arrow_iv:
                // 作者栏下拉箭头
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    arrowIv.setImageDrawable(getResources().getDrawable(R.mipmap.common_filter_arrow_down));

                } else {
                    popupWindow.showAsDropDown(bindView(R.id.news_details_aty_title));
                    arrowIv.setImageDrawable(getResources().getDrawable(R.mipmap.common_filter_arrow_up));
                }
                break;
            case R.id.news_details_toolbar_back_iv:
                // 工具栏返回按钮
                finish();
                // 单例的favoriteBeanDao 在所有类当中我们保证只有一个favoriteBeanDao
                favoriteBeanDao = GreendaoSingle.getInstance().getFavoriteBeanDao();
                List<FavoriteBean> favoriteBeens = favoriteBeanDao.queryBuilder().list();
                for (FavoriteBean favoriteBean : favoriteBeens) {
                    Log.d("*&&&&&&&&&&&&&&&&", "id:" + favoriteBean.getFeedId());
                }
                break;
            case R.id.news_details_toolbar_favorite_iv:// 收藏
                // 单例的favoriteBeanDao 在所有类当中我们保证只有一个favoriteBeanDao
                favoriteBeanDao = GreendaoSingle.getInstance().getFavoriteBeanDao();
                // 工具栏收藏按钮
                List<FavoriteBean> favorites = favoriteBeanDao.queryBuilder().list();
                for (FavoriteBean favorite : favorites) {
                    if (id.equals(favorite.getFeedId())) {
                        isHave = true;
                    }
                }
                if (isHave == false) {
                    // 收藏这篇文章
                    favoriteBeanDao.insert(new FavoriteBean(id, datas.getData().getFeatureImg(), datas.getData().getUser().getName(), datas.getData().getTitle(), datas.getData().getColumnName(), datas.getData().getPublishTime()));
                    favoriteIv.setImageDrawable(getResources().getDrawable(R.mipmap.news_toolbar_icon_favorite_blue));
                } else {
                    // 删除这条收藏
                    favoriteBeanDao.queryBuilder().where(FavoriteBeanDao.Properties.FeedId.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
                    favoriteIv.setImageDrawable(getResources().getDrawable(R.mipmap.news_toolbar_icon_favorite));
                    isHave = false;
                }
                break;
            case R.id.news_details_toolbar_share_iv:// 分享
                /**shareboard  need the platform all you want and callbacklistener,then open it**/
                new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTargetUrl("http://www.36kr.com/p/" + id + ".html")
                        .withText("---来自夫诸的36氪")
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .open();
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(NewsDetailsActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewsDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsDetailsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetailsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            new ShareAction(NewsDetailsActivity.this).setPlatform(share_media).setCallback(umShareListener)
                    .withText("多平台分享")
                    .share();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }

    public void initPopup() {
        // 设置popup的属性
        Display display = getWindowManager().getDefaultDisplay();
        popupWindow = new PopupWindow(display.getWidth(), display.getHeight());
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window_news_details, null);
        final TextView countTV = (TextView) view.findViewById(R.id.news_details_popup_count_tv);
        final TextView clickTv = (TextView) view.findViewById(R.id.news_details_popup_click_tv);
        final ListView authorLv = (ListView) view.findViewById(R.id.news_details_popup_lv);
        // 下面两个参数设置PopupWindow的点击空白消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(view);
        // 下载popup作者界面的数据
        VolleySingle.addRequest("http://rong.36kr.com/api/mobi/news/" + id + "/author-region", DetailsAuthorBean.class,
                new Response.Listener<DetailsAuthorBean>() {
                    @Override
                    public void onResponse(DetailsAuthorBean response) {
                        bean = response;
                        countTV.setText(response.getData().getTotalCount() + "篇");
                        clickTv.setText(response.getData().getTotalView() % 10000 + "万");
//                        infoTv.setText(response.getData().getBrief());
                        popupAdapter = new PopupAdapter();
                        popupAdapter.setDatas(response);
                        authorLv.setAdapter(popupAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        // 作者近期三篇文章的点击监听
        authorLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idId) {
                String postId = String.valueOf(bean.getData().getLatestArticle().get(position).getPostId());
                if (postId.equals(id)) {
                    Toast.makeText(NewsDetailsActivity.this, "你正在査看本篇文章,看看其他的吧", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MyApplication.getContext(), NewsDetailsActivity.class);
                    intent.putExtra("key", "postId");
                    intent.putExtra("postId", postId);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            }
        });
        // 点击空白让popup消失
        view.findViewById(R.id.news_details_popup_space).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return false;
            }
        });
        // popup消失的监听事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                arrowIv.setImageDrawable(getResources().getDrawable(R.mipmap.common_filter_arrow_down));
            }
        });
    }


    // popup的适配器
    class PopupAdapter extends BaseAdapter {
        private DetailsAuthorBean datas;

        public void setDatas(DetailsAuthorBean datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.getData().getLatestArticle().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_news_details_lv, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(datas.getData().getLatestArticle().get(position).getTitle());
            Picasso.with(MyApplication.getContext()).load(datas.getData().getLatestArticle().get(position).getFeatureImg()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(holder.image);
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView tv;

            public ViewHolder(View itemView) {
                image = (ImageView) itemView.findViewById(R.id.news_details_item_iv);
                tv = (TextView) itemView.findViewById(R.id.news_details_item_tv);
            }
        }
    }
}
