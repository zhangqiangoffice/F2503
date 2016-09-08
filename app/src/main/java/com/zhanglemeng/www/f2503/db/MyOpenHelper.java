package com.zhanglemeng.www.f2503.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + "id integer primary key autoincrement, "
            + "name text, "
            + "phone text, "
            + "sex text, "
            + "id_card text, "
            + "begin_date text, "
            + "term text, "
            + "rent text, "
            + "payment_method text, "
            + "room text, "
            + "status integer default 1)";

    //建立房间表
    public static final String CREATE_ROOM = "create table Room ("
            + "id integer primary key autoincrement, "
            + "name text, "
//            + "tenantid integer, "
//            + "tenant text, "
            + "water_fee real default 0, "
            + "electric_fee real default 0, "
//            + "clear_date text, "
            + "status integer default 0)";

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
