package com.zhuxiaoming.kr36.news.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/11.
 * 新闻界面的ListView的适配器
 */
public class NewsAdapter extends BaseAdapter {
    private NewsBean datas;
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(NewsBean datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.getData().getPageSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String type = datas.getData().getData().get(position).getColumnName();
        Long date = datas.getData().getData().get(position).getPublishTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        viewHolder.nameTv.setText(datas.getData().getData().get(position).getUser().getName());
        viewHolder.contentTv.setText(datas.getData().getData().get(position).getTitle());
        viewHolder.typeTv.setText(type);
        viewHolder.dateTv.setText(sdf.format(date));
        Picasso.with(context).load(datas.getData().getData().get(position).getFeatureImg()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(viewHolder.imageView);
        switch (type) {
            case "全部":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsAll));
                break;
            case "早期项目":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsEarlyItem));
                break;
            case "B轮后":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsAfterBWheel));
                break;
            case "大公司":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsBigCompany));
                break;
            case "资本":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsCapital));
                break;
            case "深度":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsDepth));
                break;
            case "研究":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsResearch));
                break;
            case "氪TV":
                viewHolder.typeTv.setTextColor(context.getResources().getColor(R.color.colorNewsKrTV));
                break;
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
