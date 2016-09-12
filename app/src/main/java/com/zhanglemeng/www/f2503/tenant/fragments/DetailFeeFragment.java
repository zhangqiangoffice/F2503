package com.zhanglemeng.www.f2503.tenant.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.bean.Payment;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.tenant.adapters.RoomAvailableAdapter;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;
import com.zhanglemeng.www.f2503.utils.PopupWindowUtils;
import com.zhanglemeng.www.f2503.utils.T;

/**
 * Created by inkun on 16/9/8.
 */
public class DetailFeeFragment extends Fragment implements View.OnClickListener{

    //结清两种费用的常量
    private final int BALANCE_ROOM = 0;
    private final int BALANCE_WE = 1;

    private View convertView;
    private Activity activity;
    private PopupWindow popupWindow;

    //结清费用种类
    private int balance;


    //水电费
    private TextView tv_last_pay_date, tv_last_record_date, tv_water_fee, tv_electricity_fee, tv_w_e_total_fee;

    //房租
    private TextView tv_next_pay_date, tv_balance_times, tv_rent_total;

    //费用合计
    private TextView tv_total_fee;

    //底部按钮
    private Button btn_balance_w_e, btn_balance_room;

    //数据库相关
    private MyDB myDB;
    private int tenant_id;
    private Tenant tenant;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_detail_fee, container, false);
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
        tv_balance_times = (TextView) v.findViewById(R.id.balance_times);
        tv_rent_total = (TextView) v.findViewById(R.id.rent_total);
        tv_total_fee = (TextView) v.findViewById(R.id.total_fee);

        //获取控件，底部按钮
        btn_balance_w_e = (Button) v.findViewById(R.id.balance_w_e);
        btn_balance_room = (Button) v.findViewById(R.id.balance_room);

        //初始化父activity
        activity = getActivity();

        //初始化数据库
        myDB = myDB.getInstance(activity);

        //获取租客ID
        tenant_id = getArguments().getInt("tenant_id", 0);

        //展示详情
        showFee();

        //绑定点击事件
        btn_balance_room.setOnClickListener(this);
        btn_balance_w_e.setOnClickListener(this);

    }

    /**
     * 展示详情
     */
    private void showFee() {

        //数据库操作，获取租客对象
        tenant = myDB.queryTenantFee(tenant_id);
        //数据库操作，最近抄表日期
        String last_record_date = myDB.queryLastRecordDate();

        //水电费
        tv_last_pay_date.setText(tenant.getLast_pay_date());
        tv_last_record_date.setText(last_record_date);
        tv_water_fee.setText(tenant.getWater_feeString());
        tv_electricity_fee.setText(tenant.getElectric_feeString());
        tv_w_e_total_fee.setText(tenant.getW_e_total_feeString());

        //房租
        tv_next_pay_date.setText(tenant.getNext_pay_date());
        tv_balance_times.setText(tenant.getBalance_timesString());
        tv_rent_total.setText(tenant.getRent_totalString());
        tv_total_fee.setText(tenant.getTotal_feeString());

    }

    /**
     * 弹出结清房租或结清水电确认框
     * @param v
     */
    private void showConfirmPopup(View v) {
        popupWindow = PopupWindowUtils.newPop(activity, R.layout.popup_confirm, v);
        TextView tv_confirm = (TextView) popupWindow.getContentView().findViewById(R.id.confirm);
        tv_confirm.setOnClickListener(this);
    }

    /**
     * 结清房租操作
     *
     */
    private void balanceRoom() {

    }

    /**
     * 结清水电操作
     */
    private void balanceWE() {

        //清空待缴水电费、更新上次结清日期
        int result1 = myDB.tenantBalanceWE(tenant_id);

        //生成缴费记录
        Payment payment = new Payment(tenant_id, Payment.TYPE_W_E, tenant.getW_e_total_feeString());
        int result2 = myDB.addPayment(payment);

        if (result1 > 0 && result2 > 0) {
            showFee();
            T.showShort(activity , R.string.success_to_do);
        } else {
            T.showShort(activity, R.string.fail_to_do);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击底部“结清房租”按钮，弹出确认框
            case R.id.balance_room:
                balance = BALANCE_ROOM;
                showConfirmPopup(v);
                break;

            //点击底部“结清水电”按钮，弹出确认框
            case R.id.balance_w_e:
                balance = BALANCE_WE;
                showConfirmPopup(v);
                break;

            //点击确认框的“确定”按钮
            case R.id.confirm:
                if (balance == BALANCE_ROOM) {
                    balanceRoom();
                } else if (balance == BALANCE_WE) {
                    balanceWE();
                }
                break;

            default:
                break;


        }
    }
}
