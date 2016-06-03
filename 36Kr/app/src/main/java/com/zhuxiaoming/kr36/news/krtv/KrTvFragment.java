package com.zhuxiaoming.kr36.news.krtv;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseFragment;
import com.zhuxiaoming.kr36.util.GsonRequest;
import com.zhuxiaoming.kr36.util.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/9.
 * 新闻界面的氪Tv
 */
public class KrTvFragment extends BaseFragment {
    private ListView krTvLv;
    private KrTvAdapter krTvAdapter;
    private int playPosition = -1;
    private int currentIndex = -1;
    private VideoView mVideoView;
    private boolean isPaused = false;
    private boolean isPlaying = false;
    private MediaController mediaController;

    @Override
    protected int initLayout() {
        return R.layout.fragment_kr_tv;
    }

    @Override
    protected void initView() {
        krTvLv = bindView(R.id.kr_tv_fragment_lv);
    }

    @Override
    protected void initData() {
        krTvAdapter = new KrTvAdapter();
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=tv&pagingAction=up", KrTvBean.class,
                new Response.Listener<KrTvBean>() {
                    @Override
                    public void onResponse(KrTvBean response) {
                        krTvAdapter.setKrTvBean(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        krTvLv.setAdapter(krTvAdapter);
        krTvLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((currentIndex < firstVisibleItem || currentIndex > krTvLv.getLastVisiblePosition()) && isPlaying) {
                    playPosition = mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    isPaused = true;
                    isPlaying = false;
                }
            }
        });
    }

    class KrTvAdapter extends BaseAdapter {
        private KrTvBean krTvBean;

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
            return krTvBean.getData().getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            final int mPosition = position;
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
            viewHolder.layout.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.GONE);
            mediaController = new MediaController(context);

            if (currentIndex == position) {
                viewHolder.layout.setVisibility(View.INVISIBLE);
                viewHolder.image.setVisibility(View.INVISIBLE);

                if (isPlaying || playPosition == -1) {
                    if (mVideoView != null) {
                        mVideoView.setVisibility(View.GONE);
                        mVideoView.stopPlayback();
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }
                }
                mVideoView=(VideoView) convertView.findViewById(R.id.kr_tv_item_video);
                mVideoView.setVisibility(View.VISIBLE);
                mediaController.setAnchorView(mVideoView);
                mediaController.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(mediaController);
                mVideoView.requestFocus();
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                if (playPosition > 0 && isPaused) {
                    mVideoView.start();
                    isPaused = false;
                    isPlaying = true;
                    viewHolder.progressBar.setVisibility(View.GONE);
                } else {
                    mVideoView.setVideoURI(Uri.parse(krTvBean.getData().getData().get(position).getTv().getVideoSource()));
                    isPaused = false;
                    isPlaying = true;
                }
                mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mVideoView != null) {
                            mVideoView.seekTo(0);
                            mVideoView.stopPlayback();
                            currentIndex = -1;
                            isPaused = false;
                            isPlaying = false;
                            viewHolder.progressBar.setVisibility(View.GONE);
                            krTvAdapter.notifyDataSetChanged();
                        }
                    }
                });
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        mVideoView.start();
                    }
                });

            } else {
                viewHolder.layout.setVisibility(View.VISIBLE);
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = mPosition;
                    playPosition = -1;
                    krTvAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView image;
        TextView titleTv;
        TextView timeTv;
        RelativeLayout layout;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.kr_tv_item_image);
            titleTv = (TextView) itemView.findViewById(R.id.kr_tv_item_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.kr_tv_item_time_tv);
            layout = (RelativeLayout) itemView.findViewById(R.id.kr_tv_item_rl);
            progressBar = (ProgressBar) itemView.findViewById(R.id.kr_tv_item_progressbar);
        }
   }
}

