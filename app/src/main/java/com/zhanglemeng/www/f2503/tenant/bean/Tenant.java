package com.zhanglemeng.www.f2503.tenant.bean;

/**
 * Created by 无名大强 on 2016/6/17.
 */
public class Tenant {

    public static final int STATUS_ON = 1;       //租客在住
    public static final int STATUS_OFF = 0;      //租客搬出

    private int id;                 //租客ID
    private String name;            //姓名
    private String sex;             //性别
    private String phone;           //电话
    private String id_card;         //身份证
    private String begin_date;      //合同起期
    private int term;               //合同期限
    private double rent;            //每月租金
    private int payment_method;     //支付方式
    private String room;            //入住房间
    private int status;             //租客状态
    private String last_pay_date;   //上次缴清水电费日期
    private String next_pay_date;   //待缴房租日期
    private int balance_times;      //房租已结算次数
    private double water_fee;       //待缴水费
    private double electric_fee;    //待缴电费


    /**
     * 以全部的字符串参数形式新建租客，用于新增租客页面
     * @param name
     * @param sex
     * @param phone
     * @param id_card
     * @param begin_date
     * @param term
     * @param rent
     * @param payment_method
     * @param room
     */
    public Tenant(String name, String sex, String phone, String id_card, String begin_date, String term, String rent, String payment_method, String room) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.id_card = id_card;
        this.begin_date = begin_date;
        this.term = Integer.valueOf(term);
        this.rent = Double.parseDouble(rent);
        this.payment_method = Integer.valueOf(payment_method);
        this.room = room;
    }

    public Tenant(String last_pay_date, String water_fee, String electric_fee, String rent, String balance_times, String payment_method, String next_pay_date) {
        this.last_pay_date = last_pay_date;
        this.water_fee = Double.parseDouble(water_fee);
        this.electric_fee = Double.parseDouble(electric_fee);
        this.rent = Double.parseDouble(rent);
        this.balance_times = Integer.valueOf(balance_times);
        this.payment_method = Integer.valueOf(payment_method);
        this.next_pay_date = next_pay_date;
    }

    public Tenant(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }

    public Tenant() {

    }

    /**
     * 返回字符串类型的待缴水费
     * @return
     */
    public String getWater_feeString() {
        return String.valueOf(water_fee);
    }

    /**
     * 返回字符串类型的待缴电费
     * @return
     */
    public String getElectric_feeString() {
        return String.valueOf(electric_fee);
    }

    /**
     * 返回字符串类型的租金
     * @return
     */
    public String getRentString() {
        return String.valueOf(rent);
    }

    /**
     * 返回字符串已结算次数
     * @return
     */
    public String getBalance_timesString() {
        return String.valueOf(balance_times);
    }

    /**
     * 返回字符串类型的待缴水电费合计
     * @return
     */
    public String getW_e_total_feeString() {
        double total = water_fee + electric_fee;
        return String.valueOf(total);
    }

    /**
     * 返回字符串类型的水电费合计
     * @return
     */
    public String getRent_totalString() {
        double total = rent * payment_method;
        return String.valueOf(total);
    }

    /**
     * 返回字符串类型的合同期限
     * @return
     */
    public String getTermString() {
        return String.valueOf(term);
    }

    public String getPayment_methodString() {
        return String.valueOf(payment_method);
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLast_pay_date() {
        return last_pay_date;
    }

    public void setLast_pay_date(String last_pay_date) {
        this.last_pay_date = last_pay_date;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }

    public int getBalance_times() {
        return balance_times;
    }

    public void setBalance_times(int balance_times) {
        this.balance_times = balance_times;
    }

    public double getWater_fee() {
        return water_fee;
    }

    public void setWater_fee(double water_fee) {
        this.water_fee = water_fee;
    }

    public double getElectric_fee() {
        return electric_fee;
    }

    public void setElectric_fee(double electric_fee) {
        this.electric_fee = electric_fee;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getNext_pay_date() {
        return next_pay_date;
    }

    public void setNext_pay_date(String next_pay_date) {
        this.next_pay_date = next_pay_date;
    }
}
