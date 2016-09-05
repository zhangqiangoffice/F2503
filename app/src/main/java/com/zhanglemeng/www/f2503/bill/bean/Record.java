package com.zhanglemeng.www.f2503.bill.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Record {
    private int id;

    //水表示数
    private int water;

    //电表示数
    private int electric;

    //抄表日期
    private String date;

    public Record(String date, int water, int electric) {
        this.date = date;
        this.water = water;
        this.electric = electric;
    }

    public Record() {

        //默认生成当前日期的记录
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str_date = formatter.format(curDate);

        this.date = str_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getElectric() {
        return electric;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
