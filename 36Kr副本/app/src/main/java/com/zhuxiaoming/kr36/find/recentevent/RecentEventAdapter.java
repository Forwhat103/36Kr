package com.zhuxiaoming.kr36.find.recentevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/13.
 * 近期活动Item适配器
 */
public class RecentEventAdapter extends BaseAdapter {
    private RecentEventBean datas;
    private Context context;

    public RecentEventAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(RecentEventBean datas) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recent_event_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(datas.getData().getData().get(position).getActivityImg()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(viewHolder.imageView);
        viewHolder.titleTv.setText(datas.getData().getData().get(position).getActivityDesc());
        viewHolder.nameTv.setText(datas.getData().getData().get(position).getActivityName());
        viewHolder.placeTv.setText(datas.getData().getData().get(position).getActivityCity());
        viewHolder.dateTv.setText(datas.getData().getData().get(position).getActivityTime());
        viewHolder.stateTv.setText(datas.getData().getData().get(position).getActivityStatus());
        if (datas.getData().getPageSize() > 0) {
            switch (datas.getData().getData().get(position).getActivityStatus()) {
                case "报名中":
                    viewHolder.stateTv.setBackground(context.getResources().getDrawable(R.mipmap.icon_activity_apply));
                    break;
                case "活动中":
                    viewHolder.stateTv.setBackground(context.getResources().getDrawable(R.mipmap.icon_activity_activiting));
                    break;
                case "已结束":
                    viewHolder.stateTv.setBackground(context.getResources().getDrawable(R.mipmap.icon_activity_end));
                    break;
            }
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;// 近期活动Item图片
        TextView titleTv;// 近期活动Item活动简介
        TextView nameTv;// 近期活动Item活动名
        TextView stateTv;// 近期活动Item活动状态
        TextView placeTv;// 近期活动Item活动地点
        TextView dateTv;// 近期活动Item活动日期

        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.recent_event_item_image);
            titleTv = (TextView) itemView.findViewById(R.id.recent_event_item_title_tv);
            nameTv = (TextView) itemView.findViewById(R.id.recent_event_item_name_tv);
            stateTv = (TextView) itemView.findViewById(R.id.recent_event_item_state_tv);
            placeTv = (TextView) itemView.findViewById(R.id.recent_event_item_place_tv);
            dateTv = (TextView) itemView.findViewById(R.id.recent_event_item_date_tv);
        }
    }
}
