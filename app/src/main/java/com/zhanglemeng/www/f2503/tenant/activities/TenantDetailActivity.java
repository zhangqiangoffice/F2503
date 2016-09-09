package com.zhanglemeng.www.f2503.tenant.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.base.activities.BaseActivity;
import com.zhanglemeng.www.f2503.tenant.adapters.BasicFragmentPagerAdapter;
import com.zhanglemeng.www.f2503.tenant.fragments.DetailBasicFragment;
import com.zhanglemeng.www.f2503.tenant.fragments.DetailFeeFragment;
import com.zhanglemeng.www.f2503.tenant.fragments.DetailPaymentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：无名大强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/9/8
 */
public class TenantDetailActivity extends BaseActivity implements View.OnClickListener{

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //滑动页标题栏
    private ViewPager vp;
    private TextView tv_vp_left, tv_vp_right, tv_vp_middle;
    private LinearLayout ll_vp_selected;

    //滑动页
    private DetailBasicFragment fragment_left;
    private DetailFeeFragment fragment_middlle;
    private DetailPaymentFragment fragment_right;
    private List<Fragment> list_fragment = new ArrayList<Fragment>();

    private int tenant_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_detail);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {

        //获取控件
        tv_top_title = (TextView) findViewById(R.id.top_title);
        tv_top_right_text = (TextView) findViewById(R.id.top_right_text);
        tv_vp_left = (TextView) findViewById(R.id.vp_left);
        tv_vp_middle = (TextView) findViewById(R.id.vp_middle);
        tv_vp_right  = (TextView) findViewById(R.id.vp_right);
        ll_vp_selected = (LinearLayout) findViewById(R.id.vp_selected);
        vp = (ViewPager) findViewById(R.id.view_pager);

        //获取租客ID
        tenant_id = getIntent().getIntExtra("tenant_id", 0);

        //设置标题栏显示
        tv_top_title.setText(R.string.tenant_detail);
        tv_top_right_text.setText(R.string.add);

        //初始化左中右fragment
        fragment_left = new DetailBasicFragment();
        fragment_middlle = new DetailFeeFragment();
        fragment_right = new DetailPaymentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("tenant_id", tenant_id);

        fragment_left.setArguments(bundle);
        fragment_middlle.setArguments(bundle);
        fragment_right.setArguments(bundle);

        list_fragment.add(fragment_left);
        list_fragment.add(fragment_middlle);
        list_fragment.add(fragment_right);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);
        tv_vp_left.setOnClickListener(this);
        tv_vp_middle.setOnClickListener(this);
        tv_vp_right.setOnClickListener(this);

        //设置ViewPage
        vp.setAdapter(new BasicFragmentPagerAdapter(getSupportFragmentManager(), list_fragment));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //页面切换后，要更换字体颜色和下划线颜色
                if (position == 0) {
                    ll_vp_selected.setGravity(Gravity.LEFT);
                    tv_vp_left.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    tv_vp_middle.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                    tv_vp_right.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                } else if (position == 1) {
                    ll_vp_selected.setGravity(Gravity.CENTER);
                    tv_vp_left.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                    tv_vp_middle.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    tv_vp_right.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                } else {
                    ll_vp_selected.setGravity(Gravity.RIGHT);
                    tv_vp_left.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                    tv_vp_middle.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
                    tv_vp_right.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
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


            //点击左侧“租客详情”，滑动页滑动
            case R.id.vp_left:
                if (vp.getCurrentItem() != 0) {
                    vp.setCurrentItem(0);
                }
                break;

            //点击中间“费用详情”，滑动页滑动
            case R.id.vp_middle:
                if (vp.getCurrentItem() != 1) {
                    vp.setCurrentItem(1);
                }
                break;

            //点击右侧“缴费记录”，滑动页滑动
            case R.id.vp_right:
                if (vp.getCurrentItem() != 2) {
                    vp.setCurrentItem(2);
                }
                break;

            default:
                break;
        }

    }


}
