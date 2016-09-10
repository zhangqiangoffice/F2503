package com.zhanglemeng.www.f2503.room.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhanglemeng.www.f2503.R;
import com.zhanglemeng.www.f2503.base.activities.BaseActivity;
import com.zhanglemeng.www.f2503.db.MyDB;
import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.utils.PopupWindowUtils;
import com.zhanglemeng.www.f2503.utils.T;

/**
 *  新增房间界面
 */

public class AddRoomActivity extends BaseActivity implements View.OnClickListener{

    private PopupWindow popupWindow;

    //头部导航栏控件
    private TextView tv_top_title, tv_top_right_text;

    //房间信息输入框
    private EditText et_room_name_input;
    private String str_room_name;

    //数据库相关
    private MyDB myDB;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {

        //获取控件
        tv_top_title = (TextView) findViewById(R.id.top_title);
        tv_top_right_text = (TextView) findViewById(R.id.top_right_text);
        et_room_name_input = (EditText) findViewById(R.id.room_name_input);

        //初始化数据库
        myDB = myDB.getInstance(this);

        //设置标题栏显示
        tv_top_title.setText(R.string.add_tenant);
        tv_top_right_text.setText(R.string.save);

        //绑定点击事件
        tv_top_right_text.setOnClickListener(this);

    }

    /**
     * 检查新增房间的各项数据是否已经准备妥当，非空
     * @return
     */
    private boolean checkData () {

        //获取用户在界面上的输入值
        str_room_name = et_room_name_input.getText().toString();

        if (TextUtils.isEmpty(str_room_name)) {
            T.showLong(this, R.string.no_room_name_input);
            return false;
        }

        return true;
    }

    /**
     * 弹出确认选择框
     */
    private void showConfirmPopup(View v) {
        popupWindow = PopupWindowUtils.newPop(this, R.layout.popup_confirm, v);
        TextView tv_confirm = (TextView) popupWindow.getContentView().findViewById(R.id.confirm);
        tv_confirm.setOnClickListener(this);
    }

    /**
     * 保存新租客到本地数据库
     */
    private void saveRoom() {
        //生成房间对象
        room = new Room(str_room_name);

        //数据库操作
        myDB.saveRoom(room);
        T.showShort(this, "保存成功！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击右上角“保存”按钮，进行数据检查并保存到数据库
            case R.id.top_right_text:
                if (checkData()) {
                    showConfirmPopup(v);
                }
                break;

            //点击确认弹出框的“确定”按钮，执行保存房间方法
            case R.id.confirm:
                saveRoom();
                PopupWindowUtils.destroy(popupWindow);
                finish();
            default:
                break;

        }
    }


}
