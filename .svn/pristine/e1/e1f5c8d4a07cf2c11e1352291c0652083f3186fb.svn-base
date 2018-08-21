package com.quickdone.znwh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @description 字符串日期转日期对象
	 * @author slzhou3
	 * @create 2016年11月30日下午11:26:23
	 * @version 1.0
	 * @param date
	 *            日期格式字符串
	 * @return
	 */
	public static Date stringToDate(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 字符串日期转日期对象
	 * @author slzhou3
	 * @create 2016年11月30日下午11:27:39
	 * @version 1.0
	 * @param date
	 *            日期格式字符串
	 * @param pattern
	 *            日期格式模式
	 * @return
	 */
	public static Date stringToDate(String date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dateToStr(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static String getCurrentTimeStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
		return simpleDateFormat.format(new Date());
	}

	/**
	 *根据开始时间和结束时间，算出二个时间相差多长时间
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int 	minuteBetween(Date startDate,Date endDate) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startDate=sdf.parse(sdf.format(startDate));
		endDate=sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		//开始时间
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		//结束时间
		long time2 = cal.getTimeInMillis();
		long minuteBetween=(time2-time1)/(1000*60);

		return Integer.parseInt(String.valueOf(minuteBetween));
	}

	public static String dealWithTime(String timeStr) throws Exception{
		int length=timeStr.length();
		if(length >11){
			try{
				SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
				Date dateOne = sdfOne.parse(timeStr);
				timeStr=sdf.format(dateOne);
				return timeStr;
			}catch (Exception e){
 				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
					Date dateOne = sdf.parse(timeStr);
					timeStr=sdf.format(dateOne);
					return  timeStr;
				}catch (Exception e1){
					throw e1;
				}
			}
		}else{
			try{
				SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy/mm/dd");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				Date dateOne = sdfOne.parse(timeStr);
				timeStr=sdf.format(dateOne);
				return timeStr;
			}catch (Exception e){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
					Date dateOne = sdf.parse(timeStr);
					timeStr=sdf.format(dateOne);
					return  timeStr;
				}catch (Exception e1){
					throw e1;
				}
			}
		}

	}
}
