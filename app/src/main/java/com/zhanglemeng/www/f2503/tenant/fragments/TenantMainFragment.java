package com.zhanglemeng.www.f2503.tenant.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.tenant.adapters.BasicFragmentPagerAdapter;
import com.zhanglemeng.www.f2503.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强 on 2016/9/4.
 * 账单首页
 */
public class TenantMainFragment extends Fragment implements View.OnClickListener {

    private View convertView;
    private Activity activity;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_tenant_main, container, false);
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
        tv_vp_left = (TextView) v.findViewById(R.id.vp_left);
        tv_vp_right  = (TextView) v.findViewById(R.id.vp_right);
        ll_vp_selected = (LinearLayout) v.findViewById(R.id.vp_selected);
        vp = (ViewPager) v.findViewById(R.id.view_pager);

        //初始化要素
        activity = getActivity();

        //设置标题栏显示
        tv_top_title.setText(R.string.tenant);
        tv_top_right_text.setText(R.string.add);

        //初始化左右fragment
        leftFragment = new TenantLeftFragment();
        rightFragment = new TenantRightFragment();
        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);
        tv_vp_left.setOnClickListener(this);
        tv_vp_right.setOnClickListener(this);

        //设置ViewPage
        vp.setAdapter(new BasicFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ll_vp_selected.setGravity(Gravity.LEFT);
                    tv_vp_left.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                    tv_vp_right.setTextColor(ContextCompat.getColor(activity, R.color.black_text));
                } else {
                    ll_vp_selected.setGravity(Gravity.RIGHT);
                    tv_vp_left.setTextColor(ContextCompat.getColor(activity, R.color.black_text));
                    tv_vp_right.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击右上角的 “新增” 按钮，跳转到新增租客界面
            case R.id.top_right_text:
                T.showShort(activity, "新增租客");
                break;

            //点击左侧“在住租客”，滑动页滑动
            case R.id.vp_left:
                if (vp.getCurrentItem() != 0) {
                    vp.setCurrentItem(0);
                }
                break;

            //点击左侧“历史租客”，滑动页滑动
            case R.id.vp_right:
                if (vp.getCurrentItem() != 1) {
                    vp.setCurrentItem(1);
                }
                break;

            default:
                break;
        }

    }

}
