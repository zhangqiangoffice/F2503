package com.zhanglemeng.www.f2503.notice.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.notice.adapters.NoticeListAdapter;
import com.zhanglemeng.www.f2503.notice.bean.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强 on 2016/9/13.
 * 提醒首页
 */
public class NoticeMainFragment extends Fragment {

    private View convertView;
    private Activity activity;

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;


    //历史记录列表
    private ListView lv_notice;

    //数据库相关
    private MyDB myDB;
    private List<Notice> list_notice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_notice_main, container, false);
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

        lv_notice = (ListView) v.findViewById(R.id.list);

        //初始化要素
        activity = getActivity();
        myDB = myDB.getInstance(activity);
        list_notice = new ArrayList<>();

        //设置标题栏显示
        tv_top_title.setText(R.string.notice);
//        tv_top_right_text.setText(R.string.save);
        tv_top_right_text.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();

        //显示提醒列表
        showNoticeList();
    }

    /**
     * 获取并展示提醒列表
     */
    private void showNoticeList() {

        //数据库操作，获取通知的列表
        list_notice = myDB.queryNoticeList();

        //适配器
        NoticeListAdapter adapter = new NoticeListAdapter(list_notice, activity);
        lv_notice.setAdapter(adapter);
    }




}
