package zx.soft.utils.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类（String 类型）
 *
 * @author donglei
 * @version 1.0.0
 */
public class DateUtils {

	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";

	public static final String DATE_PATTERN_TIME = "HH:mm:ss";

	public static final String DATE_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**  时期格式  yyyy-MM-dd*/
	public static DateFormat dateformater = new SimpleDateFormat(DATE_PATTERN_DEFAULT);;

	/**  时间格式 HH:mm:ss*/
	public static DateFormat timeformater = new SimpleDateFormat(DATE_PATTERN_TIME);

	/** 日期时间格式  yyyy-MM-dd HH:mm:ss*/
	public static DateFormat dateTimeformater = new SimpleDateFormat(DATE_PATTERN_DATETIME);;

	/** 一天毫秒数 */
	public static final long DAY_IN_MILLISECOND = 1000 * 3600 * 24;

	/** 一小时毫秒数 */
	public static final long HOUR_IN_MILLISECOND = 1000 * 3600;

	/**
	 * 根据传入的格式，获取日期格式化实例，如果传入格式为空，则为默认格式 yyyy-MM-dd
	 * @param pattern 日期格式
	 * @return 格式化实例
	 */
	public static DateFormat getDateFormat(String pattern) {
		if (null == pattern || "".equals(pattern.trim())) {
			return new SimpleDateFormat(DateUtils.DATE_PATTERN_DEFAULT);
		} else {
			return new SimpleDateFormat(pattern);
		}
	}

	/**
	 * 根据 yyyy-MM-dd 字符串解析成相应的日期
	 * @param strDate yyyy-MM-dd 格式的日期
	 * @return 转换后的日期
	 */
	public static Date parseDate(String strDate) {
		Date date = null;
		try {
			date = dateformater.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据传入的日期格式 将字符串解析成 日期类型
	 * @param strDate 日期字符串
	 * @param pattern 日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd
	 * @return 日期类型
	 */
	public static Date parseDate(String strDate, String pattern) {
		Date date = null;
		try {
			DateFormat format = getDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return date;
	}

	/**
	 * 根据 HH:mm:ss 字符串解析成相应的时间格式
	 * @param strTime HH:mm:ss 格式的时间格式
	 * @return 转换后的时间
	 */
	public static Date parseTime(String strTime) {
		Date date = null;
		try {
			date = timeformater.parse(strTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据yyyy-MM-dd HH:mm字符串解析成相应的日期时间
	 * @param strDateTime 以"yyyy-MM-dd HH:mm:ss"为格式的时间字符串
	 * @return 转换后的日期
	 */
	public static Date parseDateTime(String strDateTime) {
		Date date = null;
		try {
			date = dateTimeformater.parse(strDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取系统当前时间
	 * @return 系统当前时间
	 */
	public static Date getCurrentDate() {
		Calendar gc = GregorianCalendar.getInstance();
		return gc.getTime();
	}

	/**
	 * 转换日期为 yyyy-MM-dd 格式的字符串
	 * @param date 需要转换的日期
	 * @return 转换后的日期字符串
	 */
	public static String formatDate(Date date) {
		return dateformater.format(date);
	}

	/**
	 * 转换指定日期成时间格式 HH:mm:ss 格式的字符串
	 * @param date 指定的时间
	 * @return 转换后的字符串
	 */
	public static String formatTime(Date date) {
		return timeformater.format(date);
	}

	/**
	 * 转换指定日期成 yyyy-MM-dd HH:mm:ss 格式的字符串
	 * @param date 需要转换的日期
	 * @return 转换后的字符串
	 */
	public static String formatDateTime(Date date) {
		return dateTimeformater.format(date);
	}

	/**
	 * 根据指定的转化模式，转换日期成字符串
	 * @param date 需要转换的日期
	 * @param pattern 日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd
	 * @return 转换后的字符串
	 */
	public static String formatDate(Date date, String pattern) {
		DateFormat formater = getDateFormat(pattern);
		return formater.format(date);
	}

	/**
	 * 将毫秒时间转换指定日期成 yyyy-MM-dd HH:mm:ss 格式的字符串
	 * @param date 需要转换的日期
	 * @return 转换后的字符串
	 */
	public static String convertMilliToString(long milli) {
		return formatDate(new Date(milli), DATE_PATTERN_DATETIME);
	}

	/**
	 * 根据传入的日期格式 获取当前系统时间
	 *
	 * @param pattern
	 *            日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd
	 * @return 对应格式的当前系统时间
	 */
	public static String getCurrentDate(String pattern) {
		Calendar cal = GregorianCalendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = getDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 得到指定日期在当前星期中的天数，例如2004-5-20日，返回5，
	 * 每周以周日为开始按1计算，所以星期四为5
	 * @param date 指定的日期
	 * @return 返回天数
	 */
	public static int getDateWeekDay(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 得到指定日期在当前周内是第几天 (周一开始)
	 * @param date 指定日期
	 * @return 周内天书
	 */
	public static int getWeek(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return ((cal.get(Calendar.DAY_OF_WEEK) - 1) + 7) % 7;
	}

	/**
	 * 计算两个时间之间的时间差
	 * @param from 开始
	 * @param to 结束
	 * @return 时间差
	 */
	public static long calculateTimeInMillis(Date from, Date to) {
		Calendar fromCal = getCalendar(from);
		Calendar toCal = getCalendar(to);
		if (fromCal.after(toCal)) {
			fromCal.setTime(to);
			toCal.setTime(from);
		}
		return toCal.getTimeInMillis() - fromCal.getTimeInMillis();
	}

	/**
	 * 获取Calendar实例
	 * @param date 日期类型
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		return gc;
	}

}