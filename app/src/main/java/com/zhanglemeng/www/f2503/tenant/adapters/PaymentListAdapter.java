package com.zhanglemeng.www.f2503.tenant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.bean.Payment;

import java.util.List;

/**
 * 作者：无名大强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/9/13
 * 缴费列表适配器
 */
public class PaymentListAdapter extends BaseAdapter {

    private Context context;
    private List<Payment> list;

    public PaymentListAdapter(List<Payment> list, Context context)
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
            convertView = inflater.inflate(R.layout.adapter_payment_list, parent, false);
            holder = new ViewHolder();

            //获取控件
            holder.tv_payment_date = (TextView) convertView.findViewById(R.id.payment_date);
            holder.tv_payment_type = (TextView) convertView.findViewById(R.id.payment_type);
            holder.tv_payment_money = (TextView) convertView.findViewById(R.id.payment_money);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        //获取实体
        Payment payment = (Payment) getItem(position);

        //给布局赋值
        holder.tv_payment_date.setText(payment.getDate());
        holder.tv_payment_type.setText(payment.getTypeString());
        holder.tv_payment_money.setText(payment.getMoneyString());

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
        TextView tv_payment_date, tv_payment_type, tv_payment_money;
    }
}
