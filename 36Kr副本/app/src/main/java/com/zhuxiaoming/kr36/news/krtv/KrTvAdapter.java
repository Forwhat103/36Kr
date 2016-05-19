package com.zhuxiaoming.kr36.news.krtv;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;

import java.text.SimpleDateFormat;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 新闻界面的氪Tv适配器
 */
public class KrTvAdapter extends BaseAdapter {
    private KrTvBean krTvBean;
    private Context context;

    public KrTvAdapter(Context context) {
        this.context = context;
    }

    public void setKrTvBean(KrTvBean krTvBean) {
        this.krTvBean = krTvBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return krTvBean == null ? 0 : krTvBean.getData().getPageSize();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_kr_tv_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.titleTv.setText(krTvBean.getData().getData().get(position).getTv().getTitle());
        Picasso.with(context).load(krTvBean.getData().getData().get(position).getTv().getFeatureImg()).into(viewHolder.image);

        viewHolder.timeTv.setText(krTvBean.getData().getData().get(position).getTv().getDuration());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaController mediaController = new MediaController(context);
                finalViewHolder.layout.setVisibility(View.GONE);
                finalViewHolder.image.setVisibility(View.GONE);
                finalViewHolder.video.setVisibility(View.VISIBLE);
                finalViewHolder.video.setMediaController(mediaController);
                finalViewHolder.video.setVideoURI(Uri.parse(krTvBean.getData().getData().get(position).getTv().getVideoSource()));
                mediaController.setMediaPlayer(finalViewHolder.video);
                finalViewHolder.video.requestFocus();
                finalViewHolder.video.start();
            }
        });
        return convertView;
    }

    class ViewHolder {
        VideoView video;
        ImageView image;
        TextView titleTv;
        TextView timeTv;
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            video = (VideoView) itemView.findViewById(R.id.kr_tv_item_video);
            image = (ImageView) itemView.findViewById(R.id.kr_tv_item_image);
            titleTv = (TextView) itemView.findViewById(R.id.kr_tv_item_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.kr_tv_item_time_tv);
            layout = (RelativeLayout) itemView.findViewById(R.id.kr_tv_item_rl);
        }
    }
}
