package com.zhuxiaoming.kr36.invest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhuxiaoming.kr36.R;

import java.util.List;


/**
 * Created by zhuxiaoming on 16/5/12.
 */
public class InvestViewAdapter extends BaseAdapter {
    List<String> datas;
    Context context;

    public InvestViewAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invest_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fundraisingProgressTv.setText("已募资" + datas.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView fundraisingProgressTv;

        public ViewHolder(View itemView) {
            fundraisingProgressTv = (TextView) itemView.findViewById(R.id.invest_item_fundraising_progress_tv);
        }
    }
}
