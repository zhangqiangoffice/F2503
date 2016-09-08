package com.zhanglemeng.www.f2503.tenant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.bean.Record;
import com.zhanglemeng.www.f2503.room.bean.Room;

import java.util.List;

/**
 * Created by 无名大强 on 2016/9/5.
 * 可用房间列表选择适配器
 */
public class RoomAvailableAdapter extends BaseAdapter {

    private Context context;
    private List<Room> list;

    public RoomAvailableAdapter(List<Room> list, Context context)
    {
        this.list = list;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_room_available, parent, false);
            holder = new ViewHolder();

            //获取控件
            holder.tv_popup_item = (TextView) convertView.findViewById(R.id.popup_item);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        //获取实体
        Room room = list.get(position);

        //给布局赋值
        holder.tv_popup_item.setText(room.getName());

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
        TextView tv_popup_item;
    }


}
