package com.zhanglemeng.www.f2503.room.bean;

/**
 * Created by 无名大强 on 2016/9/7.
 * 房间实体类
 */
public class Room {

    public static final int STATUS_ON = 1;     //房间在用
    public static final int STATUS_OFF = 0;    //房间空闲

    private int id;        //房间ID
    private String name;   //房间名
    private int status;    //房间状态

    public Room(String name) {
        this.name = name;
    }

    public Room(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Room(int id, String name, int status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    /**
     * 获得房间状态的汉字表示
     * @return
     */
    public String getStatusString () {
        String str = "";
        switch (status) {
            case STATUS_ON:
                str = "在用";
                break;
            case STATUS_OFF:
                str = "空闲";
                break;
            default:
                str = "其他";
                break;
        }
        return str;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
