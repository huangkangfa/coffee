package com.hkf.coffee.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author wanglei 2015年8月1日 上午7:03:28
 */
public class TimeTypeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static String DEFAULT_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd E HH:mm:ss SS
     */
    public static String DEFAULT_DATE_FORMAT_WITHWEEK = "yyyy-MM-dd E HH:mm:ss SS";
    /**
     * MM月dd日 HH:mm
     */
    public static String HOUR_MINUTE_FORMAT = "MM月dd日  HH:mm";
    /**
     * HH:mm
     */
    public static String HOUR = "HH:mm";
    /**
     * yyyy-MM-dd
     */
    public static String DEFAULT_DAY_FORMAT = "yyyy-MM-dd";
    /**
     * yyyyMMddHHmmss
     */
    public static String DEFAULT_DATE_NO_SEPRATOR_FORMAT = "yyyyMMddHHmmss";
    /**
     * yyyyMMdd
     */
    public static final String DEFAULT_DAY_NO_SEPRATOR_FORMAT = "yyyyMMdd";
    /**
     * yyyyMMdd
     */
    public static final String DEFAULT_DAY_NO_SEPRATOR = "yyyy.MM.dd";
    /**
     * dd/MM/yyyy
     */
    public static final String DEFAULT_SLASH_FORMAT = "dd/MM/yyyy";
    /**
     * dd/MM/yyyy
     */
    public static final String DEFAULT_FORMAT = "yyyy/MM/dd/";

    /**
     * 指定日期格式，转化时间字符串为Date对象
     *
     * @param pattern
     * @param dateString
     * @return
     */
    public static Date parseDate(String pattern, String dateString) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateString);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 指定日期格式，转化时间字符串为Date对象
     *
     * @return
     */
    public static Date parseDate(String dateString) {
        return parseDate(DEFAULT_DATE_FORMAT, dateString);
    }

    /**
     * 指定日期格式，转化Date对象为时间字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String parseString(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 指定日期格式，转化Date对象为时间字符串
     *
     * @param date
     * @return
     */
    public static String parseString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }


    /**
     * 时间格式转换
     *
     * @return
     */
    public static String parseString(String dateStr) {
        Date date = parseDate(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        return sdf.format(date);
    }

    /**
     * Date转化为时间戳
     *
     * @return
     */
    public static Long dateToLong(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /** 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(HOUR_MINUTE_FORMAT);
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToStringss(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符串
     * 自定义格式
     * @param time
     * @param pattern
     * @return
     */
    public static String getDateToString(long time, String pattern) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(d);
    }


    /**
     * 时间戳转换成字符串
     * yyyy-MM-dd HH:mm 格式
     * @param time
     * @return
     */
    public static String getDateToStringOrder(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_HOUR_MINUTE_FORMAT);
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符串
     * yyyy.MM.dd 格式
     * @param time
     * @return
     */
    public static String getDateToStringday(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DAY_NO_SEPRATOR);
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符串
     * HH:mm 格式
     * @param time
     * @return
     */
    public static String getDateToStringHour(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(HOUR);
        return sf.format(d);
    }

    /**
     * 将周几转换成对应的数字
     * @param name
     * @return
     */
    public static int getWeekNumFromString(String name){
        switch (name){
            case "周一":
                return 2;
            case "周二":
                return 3;
            case "周三":
                return 4;
            case "周四":
                return 5;
            case "周五":
                return 6;
            case "周六":
                return 7;
            case "周日":
                return 1;
        }
        return -1;
    }
    /**
     * 将数字转换成对应的周几
     * @param weekNum
     * @return
     */
    public static String getStringFromWeekNum(int weekNum){
        switch (weekNum){
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
        }
        return "未知";
    }

}
