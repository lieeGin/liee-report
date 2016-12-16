package com.liee.core.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {

	private static final String WEEKNAME = "日一二三四五六";

	/**
	 * 获取某年某月的工作日天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonthWeekday(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		int rc = 20;
		int total = cal.getActualMaximum(Calendar.DATE);
		for (int i = 29; i <= total; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				rc++;
			}
		}
		return rc;
	}

	/**
	 * 获取某年某月的周末天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonthWeekend(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		int total = cal.getActualMaximum(Calendar.DATE);
		int rc = 8;
		for (int i = 29; i <= total; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				rc++;
			}
		}
		return rc;
	}

	/**
	 * 获取某年某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);

		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 获取星期名称
	 * 
	 * @param time
	 * @return
	 */
	public static String formatWeekName(String time) {
		return formatWeekName(parseTimestamp(time));
	}

	public static String formatWeekName(java.util.Date time) {
		return time == null ? "" : formatWeekName(time.getTime());
	}

	public static String formatWeekName(long time) {
		int weekday = getWeek(time);

		return WEEKNAME.substring(weekday - 1, weekday);
	}

	public static String formatWeekNameByWeek(int week) {
		if (week < 1 || week > 7) return "";
		return WEEKNAME.substring(week - 1, week);
	}

	/**
	 * 获取某年某月的第一天是星期几 SUNDAY =1
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWeekOfMonthFirstDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取某年某月的最后一天是星期几 SUNDAY =1
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWeekOfMonthLastDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		cal.add(Calendar.DATE, -1);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(String time) {
		return getWeek(parseTimestamp(time));
	}

	public static int getWeek(java.util.Date time) {
		return time == null ? -1 : getWeek(time.getTime());
	}

	public static int getWeek(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek() {
		return getWeek(System.currentTimeMillis());
	}

	public static String formatDate() {
		return formatDateTime(System.currentTimeMillis(), 0);
	}

	public static String formatDate(long time) {
		return formatDateTime(time, 0);
	}

	public static String formatDate(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 0);
	}

	public static String formatShortDate() {
		return formatDateTime(System.currentTimeMillis(), 3);
	}

	public static String formatShortDate(long time) {
		return formatDateTime(time, 3);
	}

	public static String formatShortDate(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 3);
	}

	public static String formatTime() {
		return formatDateTime(System.currentTimeMillis(), 1);
	}

	public static String formatTime(long time) {
		return formatDateTime(time, 1);
	}

	public static String formatTime(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 1);
	}

	public static String formatShortTime() {
		return formatDateTime(System.currentTimeMillis(), 4);
	}

	public static String formatShortTime(long time) {
		return formatDateTime(time, 4);
	}

	public static String formatShortTime(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 4);
	}

	public static String formatShortTimestamp() {
		return formatDateTime(System.currentTimeMillis(), 5);
	}

	public static String formatShortTimestamp(long time) {
		return formatDateTime(time, 5);
	}

	public static String formatShortTimestamp(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 5);
	}

	public static String formatTimestamp() {
		return formatDateTime(System.currentTimeMillis(), 2);
	}

	public static String formatTimestamp(long time) {
		return formatDateTime(time, 2);
	}

	public static String formatTimestamp(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 2);
	}

	public static String formatTimestamp2() {
		return formatDateTime(System.currentTimeMillis(), 6);
	}

	public static String formatTimestamp2(long time) {
		return formatDateTime(time, 6);
	}

	public static String formatTimestamp2(java.util.Date time) {
		return time == null ? "" : formatDateTime(time.getTime(), 6);
	}

	public static String formatYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatWeekBegin() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day > 1)
			cal.add(Calendar.DATE, -(day - 2));
		else
			cal.add(Calendar.DATE, -6);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatWeekBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day > 1)
			cal.add(Calendar.DATE, -(day - 2));
		else
			cal.add(Calendar.DATE, -6);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatWeekEnd() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day > 1) cal.add(Calendar.DATE, 8 - day);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatWeekEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day > 1) cal.add(Calendar.DATE, 8 - day);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatTendaysBegin() {
		Calendar cal = Calendar.getInstance();
		int i = (cal.get(Calendar.DAY_OF_MONTH) / 10) * 10 + 1;
		if (i > 30) i = 20;
		cal.set(Calendar.DAY_OF_MONTH, i);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatTendaysEnd() {
		Calendar cal = Calendar.getInstance();
		int i = (cal.get(Calendar.DAY_OF_MONTH) / 10);
		if (i < 2)
			cal.set(Calendar.DAY_OF_MONTH, (i + 1) * 10);
		else {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
		}
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatLastMonthBegin() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatLastMonthBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatLastMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -cal.get(Calendar.DAY_OF_MONTH));
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatLastMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -cal.get(Calendar.DAY_OF_MONTH));
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatMonthBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatMonthBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatYearBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static String formatYearEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.add(Calendar.YEAR, 1);
		return formatDateTime(cal.getTimeInMillis(), 0);
	}

	public static long dateDiff(long startDate, long endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.add(Calendar.DAY_OF_YEAR, 1);

		return (endDate - startDate + cal.getTimeInMillis() / 2) / cal.getTimeInMillis();
	}

	public static long dateDiff(java.util.Date startDate, java.util.Date endDate) {
		long d1 = (startDate == null) ? 0 : startDate.getTime();
		long d2 = (endDate == null) ? 0 : endDate.getTime();
		return dateDiff(d1, d2);
	}

	public static long dateDiff(String startDate, String endDate) {
		long d1 = parseTimestamp(startDate), d2 = parseTimestamp(endDate);
		return dateDiff(d1, d2);
	}

	public static long parseTimestamp(String strDate) {
		if (strDate == null || strDate.length() == 0) return 0;
		int d[] = new int[7];
		int pos = 0, limit = strDate.length();
		boolean flag = false;
		for (int i = 0; i < limit && pos < d.length; i++) {
			char c = strDate.charAt(i);
			if (c >= '0' && c <= '9') {
				flag = true;
				d[pos] = d[pos] * 10 + (c & 0xF);
			} else if (flag) {
				pos++;
				flag = false;
			}
		}
		if (d[1] < 1 || d[1] > 12 || d[2] < 1 || d[2] > DateTimeUtil.getDaysOfMonth(d[0], d[1])) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(d[0], --d[1], d[2], d[3], d[4], d[5]);
		cal.set(Calendar.MILLISECOND, d[6]);
		return cal.getTimeInMillis();
	}

	public static Date addYear(java.util.Date date, int value) {
		return addTime0(date, Calendar.YEAR, value);
	}

	public static Date addMonth(java.util.Date date, int value) {
		return addTime0(date, Calendar.MONTH, value);
	}

	public static Date addDay(java.util.Date date, int value) {
		return addTime0(date, Calendar.DATE, value);
	}

	public static Date addHour(java.util.Date date, int value) {
		return addTime0(date, Calendar.HOUR, value);
	}

	public static Date addMinute(java.util.Date date, int value) {
		return addTime0(date, Calendar.MINUTE, value);
	}

	public static Date addSecond(java.util.Date date, int value) {
		return addTime0(date, Calendar.SECOND, value);
	}

	public static long addTime(long baseTime, int field, int value) {
		return addTime0(new java.sql.Date(baseTime), field, value).getTime();
	}

	private static Date addTime0(java.util.Date date, int field, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date == null ? 0 : date.getTime());
		cal.add(field, value);

		return new Date(cal.getTime().getTime());
	}

	public static int getYear() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.YEAR);
	}

	public static int getYear(java.util.Date date) {
		return getDateFieldValue(date, Calendar.YEAR);
	}

	public static int getMonth() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.MONTH) + 1;
	}

	public static int getMonth(java.util.Date date) {
		return getDateFieldValue(date, Calendar.MONTH) + 1;
	}

	public static int getDay() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH);
	}

	public static int getDay(java.util.Date date) {
		return getDateFieldValue(date, Calendar.DAY_OF_MONTH);
	}

	public static int getHour() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.HOUR_OF_DAY);
	}

	public static int getHour(java.util.Date date) {
		return getDateFieldValue(date, Calendar.HOUR_OF_DAY);
	}

	public static int getMinute() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.MINUTE);
	}

	public static int getMinute(java.util.Date date) {
		return getDateFieldValue(date, Calendar.MINUTE);
	}

	public static int getSecond() {
		return getDateFieldValue(Calendar.getInstance().getTime(), Calendar.SECOND);
	}

	public static int getSecond(java.util.Date date) {
		return getDateFieldValue(date, Calendar.SECOND);
	}

	private static int getDateFieldValue(java.util.Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date == null ? 0 : date.getTime());

		return cal.get(field);
	}

	public static boolean isWeekend() {
		return isWeekend(System.currentTimeMillis());
	}

	public static boolean isWeekend(long time) {
		int weekDay = getWeekDay(time);

		return (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY);
	}

	public static boolean isWeekend(java.util.Date time) {
		long value = (time == null) ? 0 : time.getTime();

		return isWeekend(value);
	}

	public static int getWeekDay(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeekDay(java.util.Date time) {
		long value = (time == null) ? 0 : time.getTime();

		return getWeekDay(value);
	}

	public static long sqlTimeToLong(java.sql.Time t) {
		return t == null ? 0 : t.getTime();
	}

	public static long sqlDateToLong(java.sql.Date t) {
		return t == null ? 0 : t.getTime();
	}

	public static long timestampToLong(Timestamp t) {
		return t == null ? 0 : t.getTime();
	}

	private static String formatDateTime(long time, int type) {
		SimpleDateFormat df;
		switch (type) {
		case 0:
			df = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 1:
			df = new SimpleDateFormat("HH:mm:ss");
			break;
		case 2:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			df = new SimpleDateFormat("MM-dd");
			break;
		case 4:
			df = new SimpleDateFormat("HH:mm");
			break;
		case 5:
			df = new SimpleDateFormat("MM-dd HH:mm:ss");
			break;
		case 6:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			break;
		default:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return df.format(cal.getTime());
	}

	public static String formatDateTime(long time, String format) {
		if (time == 0 || format == null) return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return df.format(cal.getTime());
	}

	public static String formatDateTime(java.util.Date date, String format) {
		if (date == null || format == null) return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return df.format(cal.getTime());
	}

	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static java.sql.Date getCurrentDate() {
		return getDateFromToday(0);
	}

	public static java.sql.Date getLastDate() {
		return getDateFromToday(-1);
	}

	public static java.sql.Date getNextDate() {
		return getDateFromToday(1);
	}

	public static java.sql.Date getDateFromToday(int delta) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, cal1.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, cal1.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal1.get(Calendar.DAY_OF_MONTH));
		if (delta != 0) cal.add(Calendar.DATE, delta);

		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Time getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal1.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal1.get(Calendar.MILLISECOND));

		return new java.sql.Time(cal.getTimeInMillis());
	}

	public static java.sql.Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static java.sql.Time getNextFullorHalfTime() {
		return getNextFullorHalfTime(Calendar.getInstance().getTime());
	}

	public static java.sql.Time getNextFullorHalfTime(java.util.Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time == null ? 0 : time.getTime());

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		if (minute < 30) {
			minute = 30;
		} else {
			minute = 0;
			hour = (hour + 1) % 24;
		}
		cal.clear();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);

		return new java.sql.Time(cal.getTimeInMillis());
	}

	/**
	 * 获取之前半小时
	 * @return
	 */
	public static java.sql.Time getLastFullorHalfTime() {
		return getLastFullorHalfTime(Calendar.getInstance().getTime());
	}

	/**
	 * 获取之前半小时
	 * @return
	 */
	public static java.sql.Time getLastFullorHalfTime(java.util.Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time == null ? 0 : time.getTime());

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		if (minute > 30) {
			minute = 30;
		}else if(minute == 0){
			minute = 30;
			hour = (hour - 1) % 24;
		}else if( minute > 0 ){
			minute = 0;
			hour = (hour - 1) % 24;
		}
		cal.clear();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);

		return new java.sql.Time(cal.getTimeInMillis());
	}
	
	public static java.sql.Timestamp mergeDateTime(java.util.Date date, java.util.Date time) {
		if (time == null && date == null)
			return null;
		else if (time == null)
			return new Timestamp(date.getTime());
		else if (date == null) return new Timestamp(time.getTime());

		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(date.getTime());

		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(time.getTime());

		cal1.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
		cal1.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
		cal1.set(Calendar.SECOND, cal2.get(Calendar.SECOND));
		cal1.set(Calendar.MILLISECOND, cal2.get(Calendar.MILLISECOND));

		return new Timestamp(cal1.getTimeInMillis());
	}

	public static String formatTopicTime(Timestamp topicTime) {
		if (topicTime == null) {
			return "";
		}
		long currTime = System.currentTimeMillis();
		long beginTime = topicTime.getTime();
		long minute = (currTime - beginTime) / (60 * 1000);
		if (minute <= 3)
			return "刚刚";
		else if (minute < 60) return minute + "分钟前";

		long hour = minute / 60;
		if (hour < 24)
			return hour + "小时前";
		else if (hour < 48 && DateTimeUtil.formatDate(topicTime).equals(DateTimeUtil.formatYesterday())) return "昨天";
		
		if(DateTimeUtil.getCurrentYear()>DateTimeUtil.getYear(topicTime)){
			return DateTimeUtil.formatDateTime(topicTime, "yyyy-MM-dd HH:mm");
		}else{
			return DateTimeUtil.formatDateTime(topicTime, "MM-dd HH:mm");
		}
	//	return DateTimeUtil.formatDateTime(topicTime, "MM-dd HH:mm");
	}

	public static String formatOpenTime(Timestamp openTime) {
		if (openTime == null) {
			return "";
		}
		long currTime = System.currentTimeMillis();
		long beginTime = openTime.getTime();
		long hour = (beginTime - currTime) / (60 * 1000) / 60;
		if (beginTime <= currTime)
			return "报名已截止";
		else if (hour == 0) {
			return "报名即将截止";
		} else if (hour <= 24)
			return hour + "小时后截止";
		else
			return hour / 24 + "天后截止";
	}
	
	
	/**
	 * 字符串转日期
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static  java.util.Date getDateFromString(String dateStr,int type){
		SimpleDateFormat df;
		switch (type) {
		case 0:
			df = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 1:
			df = new SimpleDateFormat("HH:mm:ss");
			break;
		case 2:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			df = new SimpleDateFormat("MM-dd");
			break;
		case 4:
			df = new SimpleDateFormat("HH:mm");
			break;
		case 5:
			df = new SimpleDateFormat("MM-dd HH:mm:ss");
			break;
		case 6:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			break;
		default:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
