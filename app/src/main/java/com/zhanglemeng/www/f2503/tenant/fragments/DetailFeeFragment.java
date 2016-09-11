package com.zhanglemeng.www.f2503.tenant.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

/**
 * Created by inkun on 16/9/8.
 */
public class DetailFeeFragment extends Fragment {

    private View convertView;
    private Activity activity;

    //水电费
    private TextView tv_last_pay_date, tv_last_record_date, tv_water_fee, tv_electricity_fee, tv_w_e_total_fee;

    //房租
    private TextView tv_next_pay_date, tv_balance_times, tv_rent_total;

    //费用合计
    private TextView tv_total_fee;

    //数据库相关
    private MyDB myDB;
    private int tenant_id;
    private Tenant tenant;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_detail_basic, container, false);
            initView(convertView);

        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    private void initView(View v) {

        //获取控件，水电费
        tv_last_pay_date = (TextView) v.findViewById(R.id.last_pay_date);
        tv_last_record_date = (TextView) v.findViewById(R.id.last_record_date);
        tv_water_fee = (TextView) v.findViewById(R.id.water_fee);
        tv_electricity_fee = (TextView) v.findViewById(R.id.electricity_fee);
        tv_w_e_total_fee = (TextView) v.findViewById(R.id.w_e_total_fee);

        //获取控件，房租
        tv_next_pay_date = (TextView) v.findViewById(R.id.next_pay_date);
        tv_rent_total = (TextView) v.findViewById(R.id.rent_total);
        tv_total_fee = (TextView) v.findViewById(R.id.total_fee);


        //初始化父activity
        activity = getActivity();

        //初始化数据库
        myDB = myDB.getInstance(activity);

        //获取租客ID
        tenant_id = getArguments().getInt("tenant_id", 0);

        //展示详情
        showFee();

    }

    private void showFee() {

        //数据库操作
        tenant = myDB.queryTenantFee(tenant_id);

//        tv_last_pay_date.setText(tenant.getLast_pay_date());
        tv_water_fee.setText(tenant.getWater_fee());
        tv_electricity_fee.setText(tenant.getElectric_fee());


        tv_next_pay_date.setText("");
        tv_rent_total.setText(tenant.getRent());




    }

}
