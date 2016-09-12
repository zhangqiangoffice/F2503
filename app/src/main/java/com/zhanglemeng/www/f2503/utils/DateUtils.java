package com.zhanglemeng.www.f2503.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 无名大强 on 16/9/12.
 * E-mail:zhangqiangoffice@163.com
 *
 */
public class DateUtils {

    //约定日期字符串格式
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");

    /**
     * 获取当前日期
     * @return
     */
    public static String NowDate() {

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return DateString(curDate);
    }

    /**
     * 日期格式转换
     * @param date 日期
     * @return 日期字符串
     */
    public static String DateString(Date date) {
        return formatter.format(date);
    }
}
