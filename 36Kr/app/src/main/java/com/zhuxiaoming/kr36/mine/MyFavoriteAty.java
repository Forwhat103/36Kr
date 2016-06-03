package com.zhuxiaoming.kr36.mine;

import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.base.MyApplication;
import com.zhuxiaoming.kr36.database.FavoriteBean;
import com.zhuxiaoming.kr36.database.GreendaoSingle;
import com.zhuxiaoming.kr36.news.all.NewsAllAdapter;
import com.zhuxiaoming.kr36.news.all.NewsBean;
import com.zhuxiaoming.kr36.news.details.DetailsBean;
import com.zhuxiaoming.kr36.news.details.NewsDetailsActivity;
import com.zhuxiaoming.kr36.util.FormatDisplayTime;
import com.zhuxiaoming.kr36.util.VolleySingle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/26.
 * 我收藏的文章
 */
public class MyFavoriteAty extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private MyFavoriteAdapter adapter;
//    List<FavoriteBean> datas = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.aty_search_reault;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.search_result_aty_lv);
    }

    @Override

    protected void initData() {
        ((TextView) bindView(R.id.title_bar_content_title_tv)).setText("我的收藏");
        adapter = new MyFavoriteAdapter();
        bindView(R.id.title_bar_content_back_tv).setOnClickListener(this);
        // 遍历数据库
        final List<FavoriteBean> favorites = GreendaoSingle.getInstance().getFavoriteBeanDao().queryBuilder().list();
        adapter.setDetailsBeanList(favorites);
        listView.setAdapter(adapter);

        ((TextView) bindView(R.id.search_result_aty_count_tv)).setText("共收藏" + favorites.size() + "篇文章");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(MyFavoriteAty.this, NewsDetailsActivity.class);
                intent1.putExtra("key", "feedId");
                if (favorites != null) {
                    intent1.putExtra("feedId", favorites.get(position).getFeedId());
                }
                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 遍历数据库
        final List<FavoriteBean> favorites = GreendaoSingle.getInstance().getFavoriteBeanDao().queryBuilder().list();
        adapter.setDetailsBeanList(favorites);
        listView.setAdapter(adapter);
        ((TextView) bindView(R.id.search_result_aty_count_tv)).setText("共收藏" + favorites.size() + "篇文章");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_content_back_tv:
                // 返回
                finish();
                break;
        }
    }

    class MyFavoriteAdapter extends BaseAdapter {
        private List<FavoriteBean> favoriteBeen;

        public void setDetailsBeanList(List<FavoriteBean> favoriteBeen) {
            this.favoriteBeen = favoriteBeen;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return favoriteBeen == null ? 0 : favoriteBeen.size();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_news_lv, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(MyApplication.getContext()).load(favoriteBeen.get(position).getImgUrl()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(viewHolder.imageView);
            viewHolder.nameTv.setText(favoriteBeen.get(position).getAuthor());
            viewHolder.contentTv.setText(favoriteBeen.get(position).getTitle());
            // 设置时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = new FormatDisplayTime().formatDisplayTime(sdf.format(favoriteBeen.get(position).getTime()), "yyyy-MM-dd HH:mm:ss");
            viewHolder.dateTv.setText(time);
            // 设置新闻类型
            String type = favoriteBeen.get(position).getType();
            viewHolder.typeTv.setText(type);
            if (type != null) {
                switch (type) {
                    case "全部":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsAll));
                        break;
                    case "早期项目":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsEarlyItem));
                        break;
                    case "B轮后":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsAfterBWheel));
                        break;
                    case "大公司":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsBigCompany));
                        break;
                    case "资本":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsCapital));
                        break;
                    case "深度":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsDepth));
                        break;
                    case "研究":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsResearch));
                        break;
                    case "氪TV":
                        viewHolder.typeTv.setTextColor(getResources().getColor(R.color.colorNewsKrTV));
                        break;
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView nameTv;
            TextView contentTv;
            TextView dateTv;
            TextView typeTv;
            ImageView imageView;

            public ViewHolder(View itemView) {
                nameTv = (TextView) itemView.findViewById(R.id.news_item_name_tv);
                contentTv = (TextView) itemView.findViewById(R.id.news_item_content_iv);
                dateTv = (TextView) itemView.findViewById(R.id.news_item_date_iv);
                typeTv = (TextView) itemView.findViewById(R.id.news_item_type_iv);
                imageView = (ImageView) itemView.findViewById(R.id.news_item_iv);
            }
        }
    }
}
