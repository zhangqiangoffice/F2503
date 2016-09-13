package com.zhanglemeng.www.f2503.notice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.notice.bean.Notice;

import java.util.List;

/**
 * 作者：无名大强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/9/13
 * 提醒列表适配器
 */
public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> list;

    public NoticeListAdapter(List<Notice> list, Context context)
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
            convertView = inflater.inflate(R.layout.adapter_notice_list, parent, false);
            holder = new ViewHolder();

            //获取控件
            holder.tv_notice_content = (TextView) convertView.findViewById(R.id.notice_content);
            holder.tv_notice_date = (TextView) convertView.findViewById(R.id.notice_date);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        //获取实体
        Notice noice = (Notice) getItem(position);

        //给布局赋值
        holder.tv_notice_content.setText(noice.getContent());
        holder.tv_notice_date.setText(noice.getDate());

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
        TextView tv_notice_content, tv_notice_date;
    }


}
