package com.zhanglemeng.www.f2503.bill.bean;

/**
 * Created by 无名大强 on 16/9/12.
 * E-mail:zhangqiangoffice@163.com
 * 缴费记录实体类
 */
public class Payment {

    //两种类型
    public static final int TYPE_W_E = 0;
    public static final int TYPE_ROOM = 1;

    public static final String STRING_W_E = "水电";
    public static final String STRING_ROOM = "房租";

    private int id;          //缴费记录ID
    private int tenant_id;   //租客ID
    private String date;     //缴费日期
    private int type;        //缴费类型，0=水电，1=房租
    private double money;    //缴费金额

    public Payment(int tenant_id, int type, String money) {
        this.tenant_id = tenant_id;
        this.type = type;
        this.money = Double.valueOf(money);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(int tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
