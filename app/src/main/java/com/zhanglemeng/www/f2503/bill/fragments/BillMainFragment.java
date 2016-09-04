package com.zhanglemeng.www.f2503.bill.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;

/**
 * Created by 无名大强 on 2016/9/4.
 * 账单首页
 */
public class BillMainFragment extends Fragment {

    private View convertView;
    //
    private TextView tv_top_title, tv_top_right_text;

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
        tv_top_title = (TextView) v.findViewById(R.id.top_title);
        tv_top_right_text = (TextView) v.findViewById(R.id.top_right_text);

        tv_top_title.setText(R.string.bill);
        tv_top_right_text.setText(R.string.save);
    }




}
