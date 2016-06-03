package com.zhuxiaoming.kr36.invest.investfragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.login.LoginActivity;

import java.text.DecimalFormat;


/**
 * Created by zhuxiaoming on 16/5/12.
 */
public class InvestViewAdapter extends BaseAdapter implements View.OnClickListener {
    InvestBean datas;
    Context context;

    public InvestViewAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(InvestBean datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.getData().getData().size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invest_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(datas.getData().getData().get(position).getCompany_logo()).error(R.mipmap.common_bounced_icon_warning).placeholder(R.mipmap.equity_icon_founding_time).into(viewHolder.logoIv);
        Picasso.with(context).load(datas.getData().getData().get(position).getFile_list_img()).error(R.mipmap.common_bounced_icon_warning).placeholder(R.mipmap.equity_icon_founding_time).into(viewHolder.bigImageIv);
        viewHolder.nameTv.setText(datas.getData().getData().get(position).getCompany_name());
        viewHolder.infoTv.setText(datas.getData().getData().get(position).getCompany_brief());
        viewHolder.leadNameTv.setText(datas.getData().getData().get(position).getLead_name());
        String state = datas.getData().getData().get(position).getFundStatus().getCrowd_funding_status();
        switch (state) {
            case "RONG_ZI_SUCCESS":
                viewHolder.stateTv.setText("融资完成");
                viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.colorInvestMuZiDone));
                viewHolder.buyTv.setVisibility(View.GONE);
                break;
            case "MU_ZI_DONE":
                viewHolder.stateTv.setText("募资完成");
                viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.colorInvestMuZiDone));
                viewHolder.buyTv.setVisibility(View.GONE);
                break;
            case "MU_ZI":
                viewHolder.stateTv.setText("募资中");
                viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.colorInvestMuZi));
                viewHolder.buyTv.setVisibility(View.VISIBLE);
                break;
            case "MAO_DING":
                viewHolder.stateTv.setText("锚定中");
                viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.colorInvestMaoDing));
                viewHolder.buyTv.setVisibility(View.VISIBLE);
                break;
        }
        final DecimalFormat df = new DecimalFormat("0%");// 将小数格式转化成百分数
        viewHolder.progressTv.setText("已募资" + df.format(datas.getData().getData().get(position).getRate()));
        viewHolder.buyTv.setOnClickListener(this);
        viewHolder.careIv.setOnClickListener(this);
        viewHolder.pb.setProgress((int) (datas.getData().getData().get(position).getRate() * 100));
        if ((datas.getData().getData().get(position).getRate()) <= 1.0) {
            viewHolder.pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.shape_invest_loading_bar));
        } else {
            viewHolder.pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.shape_invest_loading_bar_done));

        }
        InvestItemAdapter adapter = new InvestItemAdapter();
        adapter.setDatas(datas.getData().getData().get(position).getCf_advantage());
        viewHolder.lv.setAdapter(adapter);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_item_subscribe_tv:
            case R.id.invest_item_care_iv:
                // 跳转到登录界面
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    class ViewHolder {
        ImageView logoIv;
        TextView nameTv;
        TextView infoTv;
        ImageView bigImageIv;
        TextView leadNameTv;
        TextView stateTv;
        TextView progressTv;
        TextView buyTv;
        ImageView careIv;
        TextView seeIv;
        ProgressBar pb;
        ListView lv;

        public ViewHolder(View itemView) {
            logoIv = (ImageView) itemView.findViewById(R.id.invest_item_logo_iv);
            nameTv = (TextView) itemView.findViewById(R.id.invest_item_name_tv);
            infoTv = (TextView) itemView.findViewById(R.id.invest_item_info_tv);
            bigImageIv = (ImageView) itemView.findViewById(R.id.invest_item_big_iv);
            leadNameTv = (TextView) itemView.findViewById(R.id.invest_item_leader_content_tv);
            stateTv = (TextView) itemView.findViewById(R.id.invest_item_fundraising_tv);
            progressTv = (TextView) itemView.findViewById(R.id.invest_item_fundraising_progress_tv);
            buyTv = (TextView) itemView.findViewById(R.id.invest_item_subscribe_tv);
            careIv = (ImageView) itemView.findViewById(R.id.invest_item_care_iv);
            seeIv = (TextView) itemView.findViewById(R.id.invest_item_see_tv);
            pb = (ProgressBar) itemView.findViewById(R.id.invest_item_progress_bar);
            lv = (ListView) itemView.findViewById(R.id.invest_item_lv);
        }
    }
}
