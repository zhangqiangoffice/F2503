package com.zhanglemeng.www.f2503.room.bean;

/**
 * Created by 无名大强 on 2016/9/7.
 * 房间实体类
 */
public class Room {

    private int id;        //房间ID
    private String name;   //房间名

    public Room(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
