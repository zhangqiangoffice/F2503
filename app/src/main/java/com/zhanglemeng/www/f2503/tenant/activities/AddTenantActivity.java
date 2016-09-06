package com.zhanglemeng.www.f2503.tenant.activities;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.base.activities.BaseActivity;

/**
 *  新增租客界面
 */

public class AddTenantActivity extends BaseActivity {

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //租客信息输入框
    private EditText et_name_input, et_sex_input, et_phone_input, et_id_card;

    //合同信息输入框
    private EditText et_begin_date, et_term_input, et_rent_input, et_payment_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {

        //获取控件
        tv_top_title = (TextView) findViewById(R.id.top_title);
        tv_top_right_text = (TextView) findViewById(R.id.top_right_text);
        et_name_input = (EditText) findViewById(R.id.name_input);
        et_sex_input = (EditText) findViewById(R.id.sex_input);
        et_phone_input = (EditText) findViewById(R.id.phone_input);
        et_id_card = (EditText) findViewById(R.id.id_card);
        et_begin_date = (EditText) findViewById(R.id.begin_date);
        et_payment_method = (EditText) findViewById(R.id.payment_method);
        et_term_input = (EditText) findViewById(R.id.term_input);
        et_rent_input = (EditText) findViewById(R.id.rent_input);


        //设置标题栏显示
        tv_top_title.setText(R.string.add_tenant);
        tv_top_right_text.setText(R.string.save);

    }


}
