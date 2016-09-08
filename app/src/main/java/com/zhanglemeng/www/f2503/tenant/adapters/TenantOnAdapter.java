package com.zhanglemeng.www.f2503.tenant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

import java.util.List;

/**
 * Created by 无名大强 on 2016/9/5.
 * 在住租客列表选择适配器
 */
public class TenantOnAdapter extends BaseAdapter {

    private Context context;
    private List<Tenant>list;

    public TenantOnAdapter(List<Tenant> list, Context context)
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
            convertView = inflater.inflate(R.layout.adapter_tenant_on, parent, false);
            holder = new ViewHolder();

            //获取控件
            holder.tv_name = (TextView) convertView.findViewById(R.id.name);
            holder.tv_room = (TextView) convertView.findViewById(R.id.room);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        //获取实体
        Tenant tenant = (Tenant) getItem(position);

        //给布局赋值
        holder.tv_name.setText(tenant.getName());
        holder.tv_room.setText(tenant.getRoom());

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
        TextView tv_name, tv_room;
    }


}
