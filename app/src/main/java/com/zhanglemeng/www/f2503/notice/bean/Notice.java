package com.zhanglemeng.www.f2503.notice.bean;

/**
 * 作者：无名大强
 * 邮箱：zhangqiangoffice@163.com
 * 日期：2016/9/13
 */
public class Notice {

    private String content;    //提醒的内容
    private String date;       //提醒的时间

    public Notice (String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
