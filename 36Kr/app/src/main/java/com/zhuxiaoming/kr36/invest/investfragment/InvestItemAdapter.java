package com.zhuxiaoming.kr36.invest.investfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.MyApplication;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/24.
 */
public class InvestItemAdapter extends BaseAdapter {
    private List<InvestBean.DataBean.DataBean1.CfAdvantageBean> datas;

    public void setDatas(List<InvestBean.DataBean.DataBean1.CfAdvantageBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

//    // 所有item是否可以点击
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }

    // 下标为position 的item不可选中，不可点击
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
            convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_invest_lv_item_lv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(datas.get(position).getAdname());
        holder.contentTv.setText(datas.get(position).getAdcontent());
        return convertView;
    }

    class ViewHolder {
        TextView nameTv;
        TextView contentTv;

        public ViewHolder(View itemView) {
            nameTv = (TextView) itemView.findViewById(R.id.invest_item_lv_name);
            contentTv = (TextView) itemView.findViewById(R.id.invest_item_lv_content);
        }
    }
}
