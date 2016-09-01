package com.zhanglemeng.www.f2503.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 * 首页界面
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private View convertView;
    private TextView tv_concat_launch,tv_concat_join;
    private View view_concat_launch,view_concat_join;
    private RelativeLayout rl_home,rl_job;
    private ImageView iv_search,iv_concat;
    private ViewPager vp;

    private List<Fragment> data=new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_main, container, false);
            initView(convertView);
        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }


    private void initView(View view) {

        tv_concat_launch.setTextColor(getResources().getColor(R.color.white));
        tv_concat_join.setTextColor(getResources().getColor(R.color.white));
        view_concat_join.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        rl_home.setOnClickListener(this);
        rl_job.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_concat.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {


            default:
                break;
        }
        if (intent!=null)
        {
            startActivity(intent);
        }
    }
}
