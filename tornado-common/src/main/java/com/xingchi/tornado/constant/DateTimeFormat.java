package com.xingchi.tornado.constant;

/**
 * 日期格式化常量
 *
 * @author xingchi
 * @date 2023/4/19 20:17
 * @modified xingchi
 */
public interface DateTimeFormat {

    String TIME_FORMAT = "HH:mm:ss";
    String TIME_FORMAT_CN = "HH时mm分ss秒";
    String TIME_FORMAT_NOT_SEPARATOR = "HHmmss";

    String DATE_FORMAT = "yyyy-MM-dd";
    String DATE_FORMAT_NOT_SEPARATOR = "yyyyMMdd";
    String DATE_FORMAT_CN = "yyyy年MM月dd日";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DATE_TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    String DATE_YEAR_MONTH_FORMAT = "yyyy-MM";
    String DATE_YEAR_MONTH_FORMAT_CN = "yyyy年MM月";
    String DATE_MONTH_DAY_FORMAT = "MM-dd";
    String DATE_MONTH_DAY_FORMAT_CN = "MM月dd日";
    String DATE_TIME_MICROSECONDS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    String DATE_TIME_MICROSECONDS_FORMAT_NOT_SEPARATOR = "yyyyMMddHHmmssSSS";
    String DATE_TIME_FORMAT_NOT_SEPARATOR = "yyyyMMddHHmmss";
    String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    String TIME_MICROSECONDS_FORMAT = "HH:mm:ss:SSS";

    String YEAR = "yyyy";
    String MONTH = "MM";
    String DAY = "dd";

}
