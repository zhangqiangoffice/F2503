package com.zhanglemeng.www.f2503.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

/**
 * Created by Administrator on 2016/6/17.
 */
public class MyOpenHelper extends SQLiteOpenHelper{


    //建立水电费记录表
    public static final String CREATE_RECORD = "create table Record ("
            + "id integer primary key autoincrement, "
            + "date text, "
            + "water integer, "
            + "electric integer)";

    //建立租客表
    public static final String CREATE_TENANT = "create table Tenant ("
            + "id integer primary key autoincrement, " //租客ID，自增
            + "name text, "                            //姓名
            + "phone text, "                           //电话
            + "sex text, "                             //性别
            + "id_card text, "                         //身份证
            + "begin_date text, "                      //合同起期
            + "term text, "                            //合同期限
            + "rent text, "                            //每月租金
            + "payment_method text, "                  //支付方式
            + "room text, "                            //房间名
            + "water_fee real default 0, "             //待缴水费
            + "electric_fee real default 0, "          //待缴电费
            + "last_pay_date text default 尚未结算, "                   //上次缴清水电费日期
            + "balance_times integer default 0, "      //房租已结算次数
            + "status integer default " + Tenant.STATUS_ON + ")"; //状态，默认：在住

    //建立房间表
    public static final String CREATE_ROOM = "create table Room ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "status integer default " + Room.STATUS_OFF + ")";

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD);
        db.execSQL(CREATE_TENANT);
        db.execSQL(CREATE_ROOM);
        db.execSQL("insert into Room (name) values ('1号房');");
        db.execSQL("insert into Room (name) values ('2号房');");
        db.execSQL("insert into Room (name) values ('3号房');");
        db.execSQL("insert into Room (name) values ('4号房');");
        db.execSQL("insert into Room (name) values ('5号房');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
