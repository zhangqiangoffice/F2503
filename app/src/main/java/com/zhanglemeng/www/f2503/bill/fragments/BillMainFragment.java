package com.zhanglemeng.www.f2503.bill.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.bill.adapters.RecordHistoryAdapter;
import com.zhanglemeng.www.f2503.bill.bean.Record;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.utils.DateUtils;
import com.zhanglemeng.www.f2503.utils.PopupWindowUtils;
import com.zhanglemeng.www.f2503.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强 on 2016/9/4.
 * 账单首页
 */
public class BillMainFragment extends Fragment implements View.OnClickListener {

    private View convertView;
    private Activity activity;
    private PopupWindow popupWindow;

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //水电表示数输入框及输入的值
    private EditText et_water_input, et_electricity_input;
    private String str_water_input, str_electricity_input;

    //历史记录列表
    private ListView lv_record_history;

    //数据库相关
    private MyDB myDB;
    private Record record;
    private List<Record> recordList;
    private RecordHistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_bill_main, container, false);
            initView(convertView);

        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    /**
     * 初始化视图
     */
    private void initView(View v) {

        //获取控件
        tv_top_title = (TextView) v.findViewById(R.id.top_title);
        tv_top_right_text = (TextView) v.findViewById(R.id.top_right_text);
        et_water_input = (EditText) v.findViewById(R.id.water_input);
        et_electricity_input = (EditText) v.findViewById(R.id.electricity_input);
        lv_record_history = (ListView) v.findViewById(R.id.record_history);

        //初始化要素
        activity = getActivity();
        myDB = myDB.getInstance(activity);
        recordList = new ArrayList<>();

        //设置标题栏显示
        tv_top_title.setText(R.string.bill);
        tv_top_right_text.setText(R.string.save);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);

        //显示历史记录
        showHistory();
    }

    /**
     * 弹出保存的不同选择PopupWindow
     * @param v
     */
    private void showSavePopup(View v) {
        popupWindow = PopupWindowUtils.newPop(activity, R.layout.popup_bill_main_fragment_save, v);

        TextView tv_save = (TextView) popupWindow.getContentView().findViewById(R.id.save);
        TextView tv_new_tenant = (TextView) popupWindow.getContentView().findViewById(R.id.new_tenant);
        TextView tv_old_tenant = (TextView) popupWindow.getContentView().findViewById(R.id.old_tenant);

        tv_save.setOnClickListener(this);
        tv_new_tenant.setOnClickListener(this);
        tv_old_tenant.setOnClickListener(this);
    }


    /**
     * 检查数据格式，非空
     * @return
     */
    private boolean checkData() {

        //获取输入框的值
        str_water_input = et_water_input.getText().toString();
        str_electricity_input = et_electricity_input.getText().toString();

        //验证水表示数非空
        if (TextUtils.isEmpty(str_water_input)) {
            T.showShort(activity, R.string.no_water_input);
            return false;
        }

        //验证电表示数非空
        if (TextUtils.isEmpty(str_electricity_input)) {
            T.showShort(activity, R.string.no_electricity_input);
            return false;
        }

        //检验通过返回真
        return true;
    }

    /**
     * 将通过验证的水电表示数保存到数据库中
     */
    private void saveRecord() {

        //生成Record对象
        record = new Record(DateUtils.NowDate(), str_water_input, str_electricity_input);

        //数据库操作
        int result = myDB.saveRecord(record);

        if (result > 0) {
            T.showShort(activity, R.string.success_to_save);
        } else {
            T.showLong(activity, R.string.fail_to_do);
        }

    }


    /**
     * 显示水电的历史记录
     */
    private void showHistory() {

        //清空输入框
        et_water_input.setText("");
        et_electricity_input.setText("");

        //从数据库获取水电表记录列表
        recordList = myDB.queryRecord();

        //生成历史记录适配器并显示
        adapter = new RecordHistoryAdapter(recordList, getActivity());
        lv_record_history.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击右上角的 “保存” 按钮，弹出不同选择
            case R.id.top_right_text:
                if (checkData()) {
                    showSavePopup(v);
                }
                break;

            //点击“保存并刷新”，数据存数据库，并刷新显示
            case R.id.save:
                saveRecord();
                showHistory();
                PopupWindowUtils.destroy(popupWindow);
                break;

            //点击“新租客入住”，跳转到新客入住界面
            case R.id.new_tenant:
                T.showShort(activity, "new_tenant");
                PopupWindowUtils.destroy(popupWindow);
                break;

            //点击“老租客结算”，跳转到老租客结算界面
            case R.id.old_tenant:
                T.showShort(activity, "old_tenant");
                PopupWindowUtils.destroy(popupWindow);
                break;

            default:
                break;
        }

    }

}
