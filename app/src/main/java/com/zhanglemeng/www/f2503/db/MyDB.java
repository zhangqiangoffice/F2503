package com.zhanglemeng.www.f2503.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhanglemeng.www.f2503.bill.bean.Record;
import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class MyDB {
    public static final String DB_NAME = "f_2503";
    public static final int VERSION = 1;
    public static final int ROOM_STATUS_IN = 1;        //房间在住
    public static final int ROOM_STATUS_OUT = 0;       //房间空闲


    private static MyDB myDB;
    private SQLiteDatabase db;

    private MyDB(Context context) {
        MyOpenHelper dbHelper = new MyOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static MyDB getInstance(Context context) {
        if (myDB == null) {
            myDB = new MyDB(context);
        }
        return myDB;
    }

    //保存水电表记录
    public void saveRecord (Record record) {
        if (record != null) {
            ContentValues values = new ContentValues();
            values.put("water", record.getWater());
            values.put("electric", record.getElectric());
            values.put("date", record.getDate());
            db.insert("Record", null, values);
        }
    }

    //查询水电表记录
    public List<Record> queryRecord () {
        List<Record> recordList = new ArrayList<Record>();
        Cursor cursor = db.query("Record", null, null, null, null, null,"id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int water = cursor.getInt(cursor.getColumnIndex("water"));
                int electric = cursor.getInt(cursor.getColumnIndex("electric"));
                Record record = new Record(date, water, electric);
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return recordList;
    }


    /**
     * 更新租客表，保存或更新租客信息、合同信息，调整房间状态，操作成功就返回 1，失败返回 0
     * @param tenant
     */
    public int saveTenant (Tenant tenant) {
        int result = 0;

        if (tenant != null) {

            ContentValues values = new ContentValues();
            values.put("name", tenant.getName());
            values.put("phone", tenant.getPhone());
            values.put("sex", tenant.getSex());
            values.put("phone", tenant.getPhone());
            values.put("id_card", tenant.getId_card());
            values.put("begin_date", tenant.getBegin_date());
            values.put("term", tenant.getTerm());
            values.put("rent", tenant.getRent());
            values.put("payment_method", tenant.getPayment_method());
            values.put("room", tenant.getRoom());

            int id = tenant.getId();

            if (id > 0) {

                //存在ID就是编辑更新

                //先检查是否需要修改房间表
                String old_room_name = "";
                Cursor cursor =  db.query("Tenant", new String[] { "room"}, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    old_room_name = cursor.getString(cursor.getColumnIndex("room"));
                }

                if (old_room_name.equals(tenant.getRoom())) {

                    //如果相同，则不需要修改房间表，仅需要更新租客表
                    result =  db.update("Tenant", values, "id = ?", new String[]{String.valueOf(id)});

                } else {

                    //如果不相同，则先修改房间表，再更新租客表
                    int result3 = roomOut(old_room_name);
                    int result4 = roomIn(tenant.getRoom());
                    int result5 = db.update("Tenant", values, "id = ?", new String[]{String.valueOf(id)});

                    if ( result3 > 0 && result4 > 0 && result5 > 0) {
                        result = 1;
                    }

                }


            } else {

                //不存在ID就是新建租客
                long result1 = db.insert("Tenant", null, values);

                //更新房间表，修改为入住状态
                int result2 = roomIn(tenant.getRoom());

                //都成功才成功
                if (result1 > 0 && result2 == 1) {
                    result = 1 ;
                }

            }

            //更新租客表



        }

        return result;
    }


    /**
     * 新增一个房间
     * @param room
     */
    public void saveRoom (Room room) {
        if (room != null) {

            //房间表出入一条记录
            ContentValues values = new ContentValues();
            values.put("name", room.getName());

            db.insert("Room", null, values);

        }
    }

    /**
     * 房间入住
     * @return
     */
    public int roomIn (String name) {
        return changeRoomStatus(name, ROOM_STATUS_IN);
    }

    /**
     * 房间空出
     * @return
     */
    public int roomOut (String name) {
        return changeRoomStatus(name, ROOM_STATUS_OUT);
    }

    /**
     * 改变房间状态
     * @return
     */
    public int changeRoomStatus(String name, int status) {

        //更新房间表
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return db.update("Room", cv, "name = ?", new String[]{name});
    }

    //查询空余房间的名称和id
    public List<Room> queryRoomOff () {
        return queryRoom("0");
    }

    //查询在用的房间的名称和id
    public List<Room> queryRoomOn () {
        return queryRoom("1");
    }

    //查询房间的名称和id
    public List<Room> queryRoom (String stutus) {
        List<Room> roomList = new ArrayList<Room>();
        Cursor cursor = db.query("Room", new String[] { "name","id" }, "status = ?", new String[]{stutus}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Room room = new Room(name, id);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }

    /**
     * 查找在住租客列表
     * @return
     */
    public List<Tenant> queryTenantOn() {
        List<Tenant> tenantList = new ArrayList<>();
        Cursor cursor = db.query("Tenant", new String[] { "name", "id", "room" }, "status = ?", new String[]{"1"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String room = cursor.getString(cursor.getColumnIndex("room"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Tenant tenant = new Tenant(id, name, room);
                tenantList.add(tenant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tenantList;
    }

    /**
     * 根据ID查租客的详情
     * @param id
     * @return
     */
    public Tenant queryTenantDetail(int id) {
        Cursor cursor = db.query("Tenant", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        Tenant tenant = new Tenant();
        if (cursor.moveToFirst()) {

                String name = cursor.getString(cursor.getColumnIndex("name"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String id_card = cursor.getString(cursor.getColumnIndex("id_card"));
                String begin_date = cursor.getString(cursor.getColumnIndex("begin_date"));
                String term = cursor.getString(cursor.getColumnIndex("term"));
                String rent = cursor.getString(cursor.getColumnIndex("rent"));
                String payment_method = cursor.getString(cursor.getColumnIndex("payment_method"));
                String room = cursor.getString(cursor.getColumnIndex("room"));

                tenant = new Tenant(name, sex, phone, id_card, begin_date, term, rent, payment_method, room);

        }
        cursor.close();
        return tenant;
    }

    /**
     * 租客搬出，根据ID修改status为0
     * @param id
     * @return
     */
    public int tenantCheckOut (int id) {
        ContentValues cv = new ContentValues();
        cv.put("status", 0);
        return db.update("Tenant", cv, "id = ?", new String[] {String.valueOf(id)});
    }

}
