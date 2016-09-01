package com.zhanglemeng.www.f2503.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.minsheng.app.infomationmanagement.home.fragments.HomeFragment;
import com.minsheng.app.infomationmanagement.message.fragments.MessageFragment;
import com.minsheng.app.infomationmanagement.mine.fragments.MineFragment;
import com.minsheng.app.infomationmanagement.office.fragments.OfficeFragment;
import com.zhanglemeng.www.f2503.R;

/**
 * 主界面
 * **/
public class MainActivity extends BaseActivity{
    // 退出时间间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;
    public static MainActivity instance;

    FragmentTabHost tabHost;
    // 未读消息textview
    private TextView unreadLabel;
    // 退出时间
    private long currentBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);
        // 首页：
        View vHome = getLayoutInflater()
                .inflate(R.layout.tab_hostcontent, null);
        ImageView ivBid = (ImageView) vHome.findViewById(R.id.btn_main);
        ivBid.setImageResource(R.drawable.tab_home_bg);
        TextView tvHome = (TextView) vHome.findViewById(R.id.btn_main2);
        tvHome.setText("首页");
        TabHost.TabSpec bidTabSpec = tabHost.newTabSpec("tabHome").setIndicator(vHome);
        tabHost.addTab(bidTabSpec, HomeFragment.class, null);

        // 办公：
        View vOffice = getLayoutInflater()
                .inflate(R.layout.tab_hostcontent, null);
        ImageView ivOffice = (ImageView) vOffice.findViewById(R.id.btn_main);
        ivOffice.setImageResource(R.drawable.tab_office_bg);
        TextView tvOffice = (TextView) vOffice.findViewById(R.id.btn_main2);
        tvOffice.setText("办公");
        TabHost.TabSpec officeTabSpec = tabHost.newTabSpec("tabOffice").setIndicator(vOffice);
        tabHost.addTab(officeTabSpec,OfficeFragment.class, null);

        // 消息：
        View vMsg = getLayoutInflater()
                .inflate(R.layout.tab_hostcontent, null);
        ImageView ivMsg = (ImageView) vMsg.findViewById(R.id.btn_main);
        ivMsg.setImageResource(R.drawable.tab_message_bg);
        TextView tvMsg = (TextView) vMsg.findViewById(R.id.btn_main2);
        tvMsg.setText("消息");
        unreadLabel = (TextView) vMsg.findViewById(R.id.unread_msg_number);
        unreadLabel.setVisibility(View.GONE);
        TabHost.TabSpec msgTabSpec = tabHost.newTabSpec("tabMsg").setIndicator(vMsg);
        tabHost.addTab(msgTabSpec, MessageFragment.class, null);
        //我的
        View vUser = getLayoutInflater()
                .inflate(R.layout.tab_hostcontent, null);
        ImageView ivUser = (ImageView) vUser.findViewById(R.id.btn_main);
        ivUser.setImageResource(R.drawable.tab_user_bg);
        TextView tvUser = (TextView) vUser.findViewById(R.id.btn_main2);
        tvUser.setText("我的");
        TabHost.TabSpec userTabSpec = tabHost.newTabSpec("tabUser").setIndicator(vUser);
        tabHost.addTab(userTabSpec, MineFragment.class, null);
    }

    @Override
    public void onBackPressed() {
        // 不能存在super
        // 判断时间间隔
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "再按一次，退出", Toast.LENGTH_LONG).show();
        } else {
            // 退出
            finish();
        }
    }

    public void MainFinish()
    {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
