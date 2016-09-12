package com.zhanglemeng.www.f2503.tenant.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.base.activities.BaseActivity;
import com.zhanglemeng.www.f2503.base.activities.MainActivity;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.tenant.adapters.RoomAvailableAdapter;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;
import com.zhanglemeng.www.f2503.utils.DateUtils;
import com.zhanglemeng.www.f2503.utils.PopupWindowUtils;
import com.zhanglemeng.www.f2503.utils.T;
import com.zhanglemeng.www.f2503.utils.whell.TimePopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  新增租客界面
 */

public class AddTenantActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private PopupWindow popupWindow;

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

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
    private Tenant tenant;
    private List<Room> list_room;

    //日期选择控件
    private TimePopupWindow pwTime;
//    private SimpleDateFormat format;

    //是否是编辑
    private boolean is_edit;
    private int tenant_id;

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
        et_check_in_room = (EditText) findViewById(R.id.check_in_room);

        //初始化数据库
        myDB = myDB.getInstance(this);

        //判断是否是编辑
        tenant_id = getIntent().getIntExtra("tenant_id", 0);

        if (tenant_id > 0) {
            is_edit = true;
        } else {
            is_edit = false;
        }

        if (is_edit) {

            //显示编辑信息
            showEditInfo();
        }

        //设置标题栏显示
        tv_top_title.setText(R.string.add_tenant);
        tv_top_right_text.setText(R.string.save);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);
        et_check_in_room.setOnClickListener(this);
        et_begin_date.setOnClickListener(this);

        //初始化日期选择控件
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY, this);
//        format = new SimpleDateFormat("yyyy-MM-dd");
//        format.setLenient(false);

        //日期选择控件选择后
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                et_begin_date.setText(DateUtils.DateString(date));
            }
        });

    }

    /**
     *
     */
    private void showEditInfo() {

        //数据库操作
        tenant = myDB.queryTenantDetail(tenant_id);

        //填充要修改的客户的信息
        et_name_input.setText(tenant.getName());
        et_sex_input.setText(tenant.getSex());
        et_phone_input.setText(tenant.getPhone());
        et_id_card.setText(tenant.getId_card());

        et_begin_date.setText(tenant.getBegin_date());
        et_term_input.setText(tenant.getTermString());
        et_rent_input.setText(tenant.getRentString());
        et_payment_method.setText(tenant.getPayment_methodString());

        et_check_in_room.setText(tenant.getRoom());
    }

    /**
     * 检查新增租客的各项数据是否已经准备妥当，非空
     * @return
     */
    private boolean checkData () {

        //获取用户在界面上的输入值
        str_name = et_name_input.getText().toString();
        str_sex = et_sex_input.getText().toString();
        str_phone = et_phone_input.getText().toString();
        str_id_card = et_id_card.getText().toString();

        str_begin_date = et_begin_date.getText().toString();
        str_term = et_term_input.getText().toString();
        str_rent = et_rent_input.getText().toString();
        str_payment_method = et_payment_method.getText().toString();

        str_check_in_room = et_check_in_room.getText().toString();

        list_room = new ArrayList<>() ;

        if (TextUtils.isEmpty(str_name)) {
            T.showLong(this, R.string.no_name_input);
            return false;
        }

        if (TextUtils.isEmpty(str_sex)) {
            T.showLong(this, R.string.no_sex_input);
            return false;
        }

        if (TextUtils.isEmpty(str_phone)) {
            T.showLong(this, R.string.no_phone_input);
            return false;
        }

        if (TextUtils.isEmpty(str_id_card)) {
            T.showLong(this, R.string.no_id_card_input);
            return false;
        }

        if (TextUtils.isEmpty(str_begin_date)) {
            T.showLong(this, R.string.no_contract_begin_date_input);
            return false;
        }

        if (TextUtils.isEmpty(str_term)) {
            T.showLong(this, R.string.no_contract_term_input);
            return false;
        }

        if (TextUtils.isEmpty(str_rent)) {
            T.showLong(this, R.string.no_month_rent_input);
            return false;
        }

        if (TextUtils.isEmpty(str_payment_method)) {
            T.showLong(this, R.string.no_payment_method_input);
            return false;
        }

        if (TextUtils.isEmpty(str_check_in_room)) {
            T.showLong(this, R.string.no_check_in_room_input);
            return false;
        }

        return true;
    }

    /**
     * 弹出确认选择框
     */
    private void showConfirmPopup(View v) {
        popupWindow = PopupWindowUtils.newPop(this, R.layout.popup_confirm, v);
        TextView tv_confirm = (TextView) popupWindow.getContentView().findViewById(R.id.confirm);
        tv_confirm.setOnClickListener(this);
    }

    /**
     * 弹出可用房间选择框
     * @param v
     */
    private void showRoomPopup(View v) {
        popupWindow = PopupWindowUtils.newPop(this, R.layout.popup_list_view, v);
        ListView lv_room = (ListView) popupWindow.getContentView().findViewById(R.id.popup_list);
        list_room = myDB.queryRoomOff();

        RoomAvailableAdapter adapter = new RoomAvailableAdapter(list_room, this);
        lv_room.setAdapter(adapter);
        lv_room.setOnItemClickListener(this);

    }

    /**
     * 保存新租客到本地数据库
     */
    private void saveTenant() {
        //生成租客对象
        tenant = new Tenant(str_name, str_sex, str_phone, str_id_card,str_begin_date, str_term, str_rent, str_payment_method, str_check_in_room);

        //如果是编辑状态下则要添加租客ID
        if (is_edit) {
            tenant.setId(tenant_id);
        }

        //数据库操作
        if (myDB.saveTenant(tenant) > 0) {
            T.showShort(this, "保存成功！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击右上角“保存”按钮，进行数据检查并保存到数据库
            case R.id.top_right_text:
                if (checkData()) {
                    showConfirmPopup(v);
                }
                break;

            //点击确认弹出框的“确定”按钮，执行保存租客方法
            case R.id.confirm:
                saveTenant();
                PopupWindowUtils.destroy(popupWindow);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            //点击“入住房间”输入框
            case R.id.check_in_room:
                showRoomPopup(v);
                break;

            //点击“合同起期”输入框
            case R.id.begin_date:
                pwTime.showAtLocation(this, v, new Date());
                break;

            default:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //选择不同的房间
        Room room = list_room.get(position);
        et_check_in_room.setText(room.getName());
        PopupWindowUtils.destroy(popupWindow);
    }


}
