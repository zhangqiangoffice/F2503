package com.zhanglemeng.www.f2503.tenant.bean;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Tenant {

    private int id;                 //租客ID
    private String name;            //姓名
    private String sex;             //性别
    private String phone;           //电话
    private String id_card;         //身份证
    private String begin_date;      //合同起期
    private String term;            //合同期限
    private String rent;            //每月租金
    private String payment_method;  //支付方式
    private String room;            //入住房间

    public Tenant(String name, String sex, String phone, String id_card, String begin_date, String term, String rent, String payment_method, String room) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.id_card = id_card;
        this.begin_date = begin_date;
        this.term = term;
        this.rent = rent;
        this.payment_method = payment_method;
        this.room = room;
    }

    public Tenant(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
