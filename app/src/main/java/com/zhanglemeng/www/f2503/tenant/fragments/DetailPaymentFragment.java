package com.zhanglemeng.www.f2503.tenant.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.bean.Payment;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.tenant.adapters.PaymentListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inkun on 16/9/8.
 * 缴费记录列表
 */
public class DetailPaymentFragment extends Fragment {

    private View convertView;
    private Activity activity;
    private MyDB myDB;
    private int tenant_id;
    private List<Payment> list_payment;

    //缴费列表
    private ListView lv_payment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.public_list_no_divider, container, false);
            initView(convertView);

        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //展示详情
        showPayment();
    }

    /**
     * 初始化视图
     * @param v
     */
    private void initView(View v) {

        //获取控件
        lv_payment = (ListView) v.findViewById(R.id.list);

        //初始化父activity
        activity = getActivity();

        //初始化数据库
        myDB = myDB.getInstance(activity);

        //初始化变量
        list_payment = new ArrayList<>();

        //获取租客ID
        tenant_id = getArguments().getInt("tenant_id", 0);

    }

    /**
     * 展示缴费详情
     */
    private void showPayment() {

        //数据库操作，获取租客的缴费列表
        list_payment = myDB.queryPaymentList(tenant_id);

        //生成缴费记录适配器并显示
        PaymentListAdapter adapter = new PaymentListAdapter(list_payment, activity);
        lv_payment.setAdapter(adapter);

    }
}
