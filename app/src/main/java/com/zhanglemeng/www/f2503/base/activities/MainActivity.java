package com.zhanglemeng.www.f2503.base.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.base.fragments.HomeFragment;
import com.zhanglemeng.www.f2503.bill.fragments.BillMainFragment;
import com.zhanglemeng.www.f2503.tenant.fragments.TenantMainFragment;

/**
 *  主界面,定义底部导航菜单，提醒、房间、租客、账单
 */
public class MainActivity extends BaseActivity {

    // 两次返回键的时间间隔要求，必须小于两秒，才能退出APP
    private static final int BACK_PRESSED_INTERVAL = 2000;

    // 按返回键时的当前毫秒数
    private long currentBackPressedTime = 0;

    public static MainActivity instance;

    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);

        //提醒
        View vNotice = getLayoutInflater().inflate(R.layout.tab_hostcontent, null);
        ImageView ivNotice = (ImageView) vNotice.findViewById(R.id.btn_main);
        ivNotice.setImageResource(R.drawable.tab_notice);
        TabHost.TabSpec noticeTabSpec = tabHost.newTabSpec("tabNotice").setIndicator(vNotice);
        tabHost.addTab(noticeTabSpec, HomeFragment.class, null);

        //房间
        View vRoom = getLayoutInflater().inflate(R.layout.tab_hostcontent, null);
        ImageView ivRoom = (ImageView) vRoom.findViewById(R.id.btn_main);
        ivRoom.setImageResource(R.drawable.tab_room);
        TabHost.TabSpec roomTabSpec = tabHost.newTabSpec("tabRoom").setIndicator(vRoom);
        tabHost.addTab(roomTabSpec, HomeFragment.class, null);

        //租客
        View vTenant = getLayoutInflater().inflate(R.layout.tab_hostcontent, null);
        ImageView ivTenant = (ImageView) vTenant.findViewById(R.id.btn_main);
        ivTenant.setImageResource(R.drawable.tab_tenant);
        TabHost.TabSpec tenantTabSpec = tabHost.newTabSpec("tabTenant").setIndicator(vTenant);
        tabHost.addTab(tenantTabSpec, TenantMainFragment.class, null);

        //账单
        View vBill = getLayoutInflater().inflate(R.layout.tab_hostcontent, null);
        ImageView ivBill = (ImageView) vBill.findViewById(R.id.btn_main);
        ivBill.setImageResource(R.drawable.tab_bill);
        TabHost.TabSpec billTabSpec = tabHost.newTabSpec("tabUser").setIndicator(vBill);
        tabHost.addTab(billTabSpec, BillMainFragment.class, null);
    }

    /**
     * 重写父类方法，用于退出APP操作
     */
    @Override
    public void onBackPressed() {

        // 不能存在super

        // 判断时间间隔，必须要连续按两次退出键才能退出
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "再按一次，退出", Toast.LENGTH_LONG).show();
        } else {
            // 退出
            finish();
        }
    }

    public void MainFinish() {
        finish();
    }

}
