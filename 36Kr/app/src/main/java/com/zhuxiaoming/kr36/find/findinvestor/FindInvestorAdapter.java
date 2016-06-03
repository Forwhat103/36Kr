package com.zhuxiaoming.kr36.find.findinvestor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuxiaoming.kr36.R;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/13.
 * 寻找投资人的适配器
 */
public class FindInvestorAdapter extends BaseAdapter {
    private FindInvestorBean datas;
    private Context context;

    public FindInvestorAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(FindInvestorBean datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_find_investor_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(datas.getData().getData().get(position).getUser().getAvatar()).placeholder(R.mipmap.equity_icon_founding_time).error(R.mipmap.common_bounced_icon_warning).into(viewHolder.headImage);// 头像
        viewHolder.nameTv.setText(datas.getData().getData().get(position).getUser().getName());// 姓名
        List<String> investStage = datas.getData().getData().get(position).getInvestPhases();// 融资阶段
        if (investStage.size() > 0) {
            String investStageData = new String();
            for (int i = 0; i < investStage.size(); i++) {
                investStageData += investStage.get(i) + " ";
            }
            viewHolder.investStageTv.setText(investStageData);
        } else {
            viewHolder.investStageTv.setText("未披露");
        }
        List<String> careField = datas.getData().getData().get(position).getFocusIndustry();// 关注领域
        if (careField.size() > 0) {
            String careFieldData = new String();
            for (int i = 0; i < careField.size(); i++) {
                careFieldData += careField.get(i) + " ";
            }
            viewHolder.careFieldTv.setText(careFieldData);
        } else {
            viewHolder.careFieldTv.setText("未披露");
        }
        return convertView;
    }

    class ViewHolder {
        ImageView headImage;
        TextView nameTv;
        TextView careFieldTv;
        TextView investStageTv;


        public ViewHolder(View itemView) {
            headImage = (ImageView) itemView.findViewById(R.id.find_investor_item_image);
            nameTv = (TextView) itemView.findViewById(R.id.find_investor_item_name_tv);
            careFieldTv = (TextView) itemView.findViewById(R.id.find_investor_item_care_field_tv);
            investStageTv = (TextView) itemView.findViewById(R.id.find_investor_item_invest_stage_tv);
        }
    }
}
