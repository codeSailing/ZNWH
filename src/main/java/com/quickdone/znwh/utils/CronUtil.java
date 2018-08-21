package com.quickdone.znwh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhum
 * @Date: 2018/7/19 15:54
 * @Description: 格式化日期生成 定时任务cron参数
 */
public class CronUtil {
    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null ) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "00 06 10 15 01 ? 2014"
     * @param dateStr  : 时间字符串
     * @return
     */
    public static String getCron(String  dateStr) throws ParseException {
        String dateFormat="ss mm HH dd MM ? yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =simpleDateFormat.parse(dateStr);
        return formatDateByPattern(date, dateFormat);
    }


    public static void main(String[] args) throws ParseException {
        String cron=CronUtil.getCron("2018-07-20 11:03:00");
        System.out.println(cron);
    }
}
