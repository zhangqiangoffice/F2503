package com.zhanglemeng.www.f2503.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhanglemeng.www.f2503.bill.bean.Payment;
import com.zhanglemeng.www.f2503.bill.bean.Record;
import com.zhanglemeng.www.f2503.notice.bean.Notice;
import com.zhanglemeng.www.f2503.room.bean.Room;
import com.zhanglemeng.www.f2503.tenant.bean.Tenant;
import com.zhanglemeng.www.f2503.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 无名大强  on 2016/6/17.
 */
public class MyDB {

    //数据库相关
    public static final String DB_NAME = "f_2503";
    public static final int VERSION = 1;

    //返回结果，成功或失败
    public static final int OK = 1;
    public static final int ERR = 0;

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

    /**
     * 保存水电表记录
     * @param record
     */
    public int saveRecord (Record record) {
        int result = ERR;
        long result1 = 0;
        int result2 = 1;

        //获取在住租客列表
        List<Tenant> list = queryTenantOn();

        //在住租客人数
        int n = list.size();

        //如果存在租客，分摊费用
        if (n > 0) {

            //上一次的历史记录
            Record last_record = queryLastRecord();

            //平均每户分摊的钱数
            double ave_water = ((record.getWater() - last_record.getWater()) / n ) * 0.5653;
            double ave_electric = ((record.getElectric() - last_record.getElectric()) / n) *3.2;

            //更新数据库租客表
            for (int i = 0; i < n; i++) {
                result2 = updateTenantWE(list.get(i), ave_water, ave_electric);
                if(result2 <= 0 ) {
                    break;
                }
            }
        }

        //更新水电费记录表
        if (record != null) {
            ContentValues values = new ContentValues();
            values.put("water", record.getWater());
            values.put("electric", record.getElectric());
            values.put("date", record.getDate());
            result1 = db.insert("Record", null, values);
        }

        if (result1 > 0 && result2 > 0) {
            result = OK;
        }

        return result;
    }

    /**
     * 查询水电表历史记录
     * @return
     */
    public List<Record> queryRecord () {
        List<Record> recordList = new ArrayList<Record>();
        Cursor cursor = db.query("Record", new String[] { "date", "water", "electric" }, null, null, null, null,"id DESC", null);
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
        int result = ERR;

        if (tenant != null) {

            ContentValues values = new ContentValues();
            values.put("name", tenant.getName());
            values.put("phone", tenant.getPhone());
            values.put("sex", tenant.getSex());
            values.put("phone", tenant.getPhone());
            values.put("id_card", tenant.getId_card());
            values.put("begin_date", tenant.getBegin_date());
            values.put("term", tenant.getTermString());
            values.put("rent", tenant.getRent());
            values.put("payment_method", tenant.getPayment_method());
            values.put("room", tenant.getRoom());


            int id = tenant.getId();

            //存在ID就是编辑更新
            if (id > 0) {

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
                        result = OK;
                    }

                }

            //不存在ID就是新建租客
            } else {

                //新建租客时，水电上次结清日期和房租待结算日期都设定为当前日期
                values.put("last_pay_date", tenant.getBegin_date());
                values.put("next_pay_date", tenant.getBegin_date());

                long result1 = db.insert("Tenant", null, values);

                //更新房间表，修改为入住状态
                int result2 = roomIn(tenant.getRoom());

                //都成功才成功
                if (result1 > 0 && result2 == 1) {
                    result = OK ;
                }
            }
        }

        return result;
    }


    /**
     * 查询最近一次抄表日期
     * @return 最近一次抄表日期
     */
    public String queryLastRecordDate() {
        Record record = queryLastRecord();
        return record.getDate();
    }

    /**
     * 查询最近一次抄表记录
     * @return 最近一次抄表记录
     */
    public Record queryLastRecord() {
        Record record = new Record("没有记录", 0, 0);
        Cursor cursor = db.query("Record", new String[] { "date", "water", "electric" }, null, null, null, null, "id DESC", "1");
        if (cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int water = cursor.getInt(cursor.getColumnIndex("water"));
            int electric = cursor.getInt(cursor.getColumnIndex("electric"));
            record = new Record(date, water, electric);
        }
        cursor.close();
        return record;
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
        return changeRoomStatus(name, Room.STATUS_ON);
    }

    /**
     * 房间空出
     * @return
     */
    public int roomOut (String name) {
        return changeRoomStatus(name, Room.STATUS_OFF);
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

    /**
     * 查询空余房间的名称和id
     * @return
     */
    public List<Room> queryRoomOff () {
        return queryRoom(Room.STATUS_OFF);
    }

    /**
     * 查询在用的房间的名称和id
     * @return
     */
    public List<Room> queryRoomOn () {
        return queryRoom(Room.STATUS_ON);
    }

    /**
     * 查询所有房间列表
     * @return 所有房间列表
     */
    public List<Room> queryRoomAll () {
        List<Room> roomList = new ArrayList<Room>();
        Cursor cursor = db.query("Room", new String[] { "name", "id", "status" }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                Room room = new Room(id, name, status);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }


    /**
     * 查询房间的名称和id
     * @param status
     * @return
     */
    public List<Room> queryRoom (int status) {
        List<Room> roomList = new ArrayList<Room>();
        Cursor cursor = db.query("Room", new String[] { "name","id" }, "status = ?", new String[]{String.valueOf(status)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Room room = new Room(id, name);
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
        Cursor cursor = db.query("Tenant", new String[] { "name", "id", "room", "water_fee", "electric_fee", "next_pay_date" }, "status = ?", new String[]{String.valueOf(Tenant.STATUS_ON)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String room = cursor.getString(cursor.getColumnIndex("room"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                double water_fee = cursor.getInt(cursor.getColumnIndex("water_fee"));
                double electric_fee = cursor.getInt(cursor.getColumnIndex("electric_fee"));
                String next_pay_date = cursor.getString(cursor.getColumnIndex("next_pay_date"));
                Tenant tenant = new Tenant(id, name, room);
                tenant.setWater_fee(water_fee);
                tenant.setElectric_fee(electric_fee);
                tenant.setNext_pay_date(next_pay_date);
                tenantList.add(tenant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tenantList;
    }

    /**
     * 查找历史租客列表
     * @return
     */
    public List<Tenant> queryTenantOff() {
        List<Tenant> tenantList = new ArrayList<>();
        Cursor cursor = db.query("Tenant", new String[] { "name", "id", "room" }, "status = ?", new String[]{String.valueOf(Tenant.STATUS_OFF)}, null, null, null);
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
     * 根据ID查询租客的费用情况
     * @param id
     * @return
     */
    public Tenant queryTenantFee(int id) {
        Cursor cursor = db.query("Tenant", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        Tenant tenant = new Tenant();
        if (cursor.moveToFirst()) {

            String water_fee = cursor.getString(cursor.getColumnIndex("water_fee"));
            String electric_fee = cursor.getString(cursor.getColumnIndex("electric_fee"));
            String rent = cursor.getString(cursor.getColumnIndex("rent"));
            String balance_times = cursor.getString(cursor.getColumnIndex("balance_times"));
            String payment_method = cursor.getString(cursor.getColumnIndex("payment_method"));
            String last_pay_date = cursor.getString(cursor.getColumnIndex("last_pay_date"));
            String next_pay_date = cursor.getString(cursor.getColumnIndex("next_pay_date"));

            tenant = new Tenant(last_pay_date, water_fee, electric_fee, rent, balance_times, payment_method, next_pay_date);

        }
        cursor.close();
        return tenant;
    }

    /**
     * 租客搬出，根据租客ID修改租客status为off，修改房间status为off
     * @param id
     * @return
     */
    public int tenantCheckOut (int id) {

        int result = ERR;

        //修改租客状态
        ContentValues cv = new ContentValues();
        cv.put("status", Tenant.STATUS_OFF);
        int result1 = db.update("Tenant", cv, "id = ?", new String[] {String.valueOf(id)});

        //查找该租客的房间名称
        String room_name = queryTenantDetail(id).getRoom();

        //修改房间状态为搬出
        int result2 = roomOut(room_name);

        if (result1 > 0 && result2 > 0) {
            result = OK;
        }

        return result;

    }

    /**
     * 租客待缴费用的增加
     * @param tenant
     * @param water
     * @param electric
     * @return
     */
    public int updateTenantWE(Tenant tenant, double water, double electric) {

        //变量赋值
        ContentValues cv = new ContentValues();
        cv.put("water_fee", tenant.getWater_fee() + water);
        cv.put("electric_fee", tenant.getElectric_fee() + electric);

        return db.update("Tenant", cv, "id = ?", new String[] {String.valueOf(tenant.getId())});

    }

    /**
     * 清空水电费及上次缴费日期
     * @param id
     * @return
     */
    public int tenantBalanceWE(int id) {

        ContentValues cv = new ContentValues();
        cv.put("water_fee", 0);
        cv.put("electric_fee", 0);
        cv.put("last_pay_date", DateUtils.NowDate());

        return db.update("Tenant", cv, "id = ?", new String[] {String.valueOf(id)});
    }

    /**
     * 结算房租，更新已结算次数和下次结算日期
     * @param id
     * @param times
     * @param next_date
     * @return
     */
    public int tenantBalanceRoom(int id, int times, String next_date) {

        //更新结算次数和下次结算日期
        ContentValues cv = new ContentValues();
        cv.put("balance_times", times);
        cv.put("next_pay_date", next_date);

        return db.update("Tenant", cv, "id = ?", new String[] {String.valueOf(id)});


    }

    /**
     * 新增缴费记录
     * @param payment
     * @return
     */
    public int addPayment(Payment payment) {

        //初始化返回值，默认为0
        int result = ERR;

        //变量赋值，日期默认为当前日期
        ContentValues values = new ContentValues();
        values.put("tenant_id", payment.getTenant_id());
        values.put("date", DateUtils.NowDate());
        values.put("type", payment.getType());
        values.put("money", payment.getMoney());

        long result1 = db.insert("Payment", null, values);

        if (result1 > 0 ) {
            result = OK;
        }

        return result;
    }

    /**
     * 获取缴费记录列表
     * @param id
     * @return
     */
    public List<Payment> queryPaymentList(int id) {
        List<Payment> payment_list = new ArrayList<>();
        Cursor cursor = db.query("Payment", new String[]{"date", "type", "money"}, "tenant_id = ?", new String[]{String.valueOf(id)}, null, null, "id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                Payment payment = new Payment(date, type, money);
                payment_list.add(payment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return payment_list;
    }

    /**
     * 获取提醒列表， 目前是获取所有在住租客的代缴费日期
     * @return
     */
    public List<Notice> queryNoticeList() {
        List<Notice> list_notice = new ArrayList<>();
        List<Tenant> list_tenant_on = queryTenantOn();
        for (Tenant tenant : list_tenant_on ) {
            String content = tenant.getName() + "，" + tenant.getRoom() + " 待结算";
            String date = "日期：" + tenant.getNext_pay_date();
            Notice notice = new Notice(content, date);
            list_notice.add(notice);
        }

        return list_notice;
    }



}
