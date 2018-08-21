package com.quickdone.znwh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xing ye on 2018/3/3.
 */
public class DateSearchUtils {
    public static void main(String[] args) {
        System.out.println("当前时间：" + new Date().toLocaleString());
        System.out.println("当天0点时间：" + getTimesmorning().toLocaleString());
        System.out.println("当天24点时间：" + getTimesnight().toLocaleString());
        System.out.println("本周周一0点时间：" + getTimesWeekmorning().toLocaleString());
        System.out.println("本周周日24点时间：" + getTimesWeeknight().toLocaleString());
        System.out.println("本月初0点时间：" + getTimesMonthmorning().toLocaleString());
        System.out.println("本月未24点时间：" + getTimesMonthnight().toLocaleString());
        System.out.println("本月未24点时间111111111：" + getTimesDay(-30).toLocaleString());
        System.out.println("上周一：" + getPreviousWeekdayStart().toLocaleString());
        System.out.println("上周日：" + getPreviousWeekdayEnd().toLocaleString());
        System.out.println("上个月第一天：" + getPreviousMonthStart().toLocaleString());
        System.out.println("上个月最后一天：" + getPreviousMonthEnd().toLocaleString());
        System.out.println("某个月第一天：" + getStartMonthDate(0,0).toLocaleString());
        System.out.println("某个月最后一天：" + getEndMonthDate(0,0).toLocaleString());
        System.out.println("某个月最后一天：" + getDate("2017-01-01",0).toLocaleString());
        System.out.println(""+new java.sql.Timestamp(new Date().getTime()));
        System.out.println(""+new java.sql.Timestamp(new Date().getTime()+1));
        Date date = new Date(0);
        System.out.println(date+"122121212121");
    }

    // 获得当天0点时间
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    // 获得当天24点时间
    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }


    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    //获取上周一
    public static Date getPreviousWeekdayStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    //获取上周日
    public static Date getPreviousWeekdayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        //   cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    //获取上月第一天
    public static Date getPreviousMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    //获取上月最后一天
    public static Date getPreviousMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.getActualMaximum(5), 23, 59, 59);
        return cal.getTime();
    }

    //获取某年某月的第一天日期
    public static Date getStartMonthDate( int month,int type) {//0：今年 1去年
        Calendar calendar = Calendar.getInstance();
        if(type==1){
            calendar.add(Calendar.YEAR, -1);
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    //获取某年某月的最后一天日期
    public static Date getEndMonthDate( int month,int type) {//0：今年 1去年
        Calendar calendar = Calendar.getInstance();
        if(type==1){
            calendar.add(Calendar.YEAR, -1);
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.getActualMaximum(5), 23, 59, 59);
        return calendar.getTime();
    }

    //获取当前月份
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return month;
    }

    //对当天 进行天数的  加减
    public static Date getTimesDay(int number) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, number);
        return cal.getTime();
    }

    //给年月日加时分秒
    public static Date getDate(String date,int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(type==1){
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
        }
        return calendar.getTime();
    }

    //获取当前年
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    //获取月份有多少天
    public static int getDaysOfMonth(String year,String month) {
         month = month.length()<2?"0"+month:month;
        String date = year+"-"+month;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //时间相减 返回差多少分钟
    public static Double diff(Date time1,Date time2) {
        long diff = time1.getTime() - time2.getTime();//这样得到的差值是微秒级别
        long minutes  = diff / (1000 * 60);//分钟
        return (double)minutes;
    }
}
