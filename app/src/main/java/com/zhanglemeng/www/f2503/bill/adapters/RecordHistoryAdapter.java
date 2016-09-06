package com.zhanglemeng.www.f2503.bill.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.bean.Record;

import java.util.List;

/**
 * Created by 无名大强 on 2016/9/5.
 * 水电表历史记录适配器
 */
public class RecordHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<Record>list;

    public RecordHistoryAdapter(List<Record> list, Context context)
    {
        this.list = list;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_record_history, parent, false);
            holder = new ViewHolder();

            //获取控件
            holder.tv_record_date = (TextView) convertView.findViewById(R.id.record_date);
            holder.tv_record_water = (TextView) convertView.findViewById(R.id.record_water);
            holder.tv_record_electric = (TextView) convertView.findViewById(R.id.record_electric);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        //获取实体
        Record record = list.get(position);

        //给布局赋值
        holder.tv_record_date.setText(record.getDate());
        holder.tv_record_water.setText(String.valueOf(record.getWater()));
        holder.tv_record_electric.setText(String.valueOf(record.getElectric()));

        return convertView;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tv_record_date, tv_record_water, tv_record_electric;
    }


}
