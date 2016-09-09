package com.zhanglemeng.www.f2503.tenant.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

/**
 * Created by inkun on 16/9/8.
 */
public class DetailBasicFragment extends Fragment {

    private View convertView;
    private Activity activity;

    //租客信息输入框
    private EditText et_name_input, et_sex_input, et_phone_input, et_id_card;
    private String str_name, str_sex, str_phone, str_id_card;

    //合同信息输入框
    private EditText et_begin_date, et_term_input, et_rent_input, et_payment_method;
    private String str_begin_date, str_term, str_rent, str_payment_method;

    //房间信息输入框
    private EditText et_check_in_room;
    private String str_check_in_room;

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

    /**
     * 初始化页面
     * @param v
     */
    private void initView(View v) {

        //获取控件
        et_name_input = (EditText) v.findViewById(R.id.name_input);
        et_sex_input = (EditText) v.findViewById(R.id.sex_input);
        et_phone_input = (EditText) v.findViewById(R.id.phone_input);
        et_id_card = (EditText) v.findViewById(R.id.id_card);
        et_begin_date = (EditText) v.findViewById(R.id.begin_date);
        et_payment_method = (EditText) v.findViewById(R.id.payment_method);
        et_term_input = (EditText) v.findViewById(R.id.term_input);
        et_rent_input = (EditText) v.findViewById(R.id.rent_input);
        et_check_in_room = (EditText) v.findViewById(R.id.check_in_room);

        //初始化父activity
        activity = getActivity();

        //初始化数据库
        myDB = myDB.getInstance(activity);

        //获取租客ID
        tenant_id = getArguments().getInt("tenant_id", 0);

        showDetail();
    }


    /**
     * 查询租客详情并展示在页面上
     */
    public void showDetail() {

        //数据库操作
        tenant = myDB.queryTenantDetail(tenant_id);

        //展现到页面上
        et_name_input.setText(tenant.getName());
        et_sex_input.setText(tenant.getSex());
        et_phone_input.setText(tenant.getPhone());
        et_id_card.setText(tenant.getId_card());
        et_begin_date.setText(tenant.getBegin_date());
        et_payment_method.setText(tenant.getPayment_method());
        et_term_input.setText(tenant.getTerm());
        et_rent_input.setText(tenant.getRent());
        et_check_in_room.setText(tenant.getRoom());

    }

}
