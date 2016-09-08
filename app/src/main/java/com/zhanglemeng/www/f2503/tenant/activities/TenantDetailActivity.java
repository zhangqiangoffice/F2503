package com.zhanglemeng.www.f2503.tenant.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.base.activities.BaseActivity;
import com.zhanglemeng.www.f2503.tenant.fragments.TenantLeftFragment;
import com.zhanglemeng.www.f2503.tenant.fragments.TenantRightFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：无名大强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/9/8
 */
public class TenantDetailActivity extends BaseActivity {

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //滑动页标题栏
    private ViewPager vp;
    private TextView tv_vp_left, tv_vp_right;
    private LinearLayout ll_vp_selected;

    //滑动页
    private TenantLeftFragment leftFragment;
    private TenantRightFragment rightFragment;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();


}
