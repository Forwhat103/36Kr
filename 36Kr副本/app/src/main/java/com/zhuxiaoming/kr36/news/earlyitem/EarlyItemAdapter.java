package com.zhuxiaoming.kr36.news.earlyitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.util.FormatDisplayTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 早期项目的适配器
 */
public class EarlyItemAdapter extends BaseAdapter {
    private EarlyItemBean datas;
    private Context context;

    public EarlyItemAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(EarlyItemBean datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_early_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(datas.getData().getData().get(position).getFeatureImg()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(viewHolder.imageView);
        viewHolder.nameTv.setText(datas.getData().getData().get(position).getUser().getName());
        viewHolder.titleTv.setText(datas.getData().getData().get(position).getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = new FormatDisplayTime().formatDisplayTime(sdf.format(datas.getData().getData().get(position).getPublishTime()), "yyyy-MM-dd HH:mm:ss");
        viewHolder.dateTv.setText(date);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView titleTv;
        TextView nameTv;
        TextView dateTv;

        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.early_item_lv_image);
            titleTv = (TextView) itemView.findViewById(R.id.early_item_title_tv);
            nameTv = (TextView) itemView.findViewById(R.id.early_item_name_tv);
            dateTv = (TextView) itemView.findViewById(R.id.early_item_date_tv);
        }
    }
}
