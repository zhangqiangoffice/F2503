package com.zhanglemeng.www.f2503.room.fragments;

import android.app.Activity;
import android.content.Intent;
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
import com.zhanglemeng.www.f2503.room.activities.AddRoomActivity;
import com.zhanglemeng.www.f2503.room.adapters.RoomListAdapter;
import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.tenant.adapters.TenantOnAdapter;
import com.zhanglemeng.www.f2503.utils.PopupWindowUtils;
import com.zhanglemeng.www.f2503.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强 on 2016/9/4.
 * 房间首页
 */
public class RoomMainFragment extends Fragment implements View.OnClickListener {

    private View convertView;
    private Activity activity;

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //房间列表
    private ListView lv_room;

    //数据库相关
    private MyDB myDB;
    private Record record;
    private List<Room> list_room;
    private RoomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_room_main, container, false);
            initView(convertView);

        }
        ViewGroup viewParent = (ViewGroup) convertView.getParent();
        if (viewParent != null) {
            viewParent.removeView(convertView);
        }
        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //展示所有房间
        showAllRoom();
    }

    /**
     * 初始化视图
     */
    private void initView(View v) {

        //获取控件
        tv_top_title = (TextView) v.findViewById(R.id.top_title);
        tv_top_right_text = (TextView) v.findViewById(R.id.top_right_text);
        lv_room = (ListView) v.findViewById(R.id.room_list);

        //初始化要素
        activity = getActivity();
        myDB = myDB.getInstance(activity);
        list_room = new ArrayList<>();

        //设置标题栏显示
        tv_top_title.setText(R.string.room);
        tv_top_right_text.setText(R.string.add);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);
    }

    /**
     * 展示所有的房间
     */
    private void showAllRoom() {

        //数据库中获取所有房间
        list_room = myDB.queryRoomAll();

        //适配器
        RoomListAdapter adapter = new RoomListAdapter(list_room, activity);
        lv_room.setAdapter(adapter);
//        lv_room.setOnItemClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击右上角的 “新增” 按钮，跳转到新增房间页面
            case R.id.top_right_text:
                Intent intent = new Intent(activity, AddRoomActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

}
