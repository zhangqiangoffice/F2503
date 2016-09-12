package com.zhanglemeng.www.f2503.bill.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 无名大强 on 2016/9/7.
 * 水电表记录实体类
 */
public class Record {

    private int id;        //记录ID
    private int water;     //水表示数
    private int electric;  //电表示数
    private String date;   //抄表日期

    public Record(String date, int water, int electric) {
        this.date = date;
        this.water = water;
        this.electric = electric;
    }

    public Record(String date, String water, String electric) {
        this.date = date;
        this.water = Integer.valueOf(water);
        this.electric = Integer.valueOf(electric);
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
