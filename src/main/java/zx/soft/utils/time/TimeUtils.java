package zx.soft.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * 时间工具类
 *
 * @author wanggang
 *
 */
public class TimeUtils {

	private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	/**
	 * 将时间戳转换成Solr标准的Date格式，注意：该转换快8小时
	 * 如：2014-04-10T10:07:14Z
	 */
	public static String transToSolrDateStr(long timestamp) {
		return DateFormatterThreadLocal.getSolrDateFormat().format(new Date(timestamp));
	}

	public static long tranSolrDateStrToMilli(String str) throws ParseException {
		return DateFormatterThreadLocal.getSolrDateFormat().parse(str).getTime();
	}

	/**
	 * 将Solr标准的Date转换成可读性较好的格式
	 * 如：2014-04-10 10:07:14
	 */
	public static String transToCommonDateStr(String str) {
		return str.replace("T", " ").replace("Z", "");
	}

	/**
	 * 将long型日期（毫秒级别）转换成可读性较好的格式
	 * 如：2014-04-10 10:07:14
	 */
	public static String transToCommonDateStr(long timestamp) {
		return DateFormatterThreadLocal.getLongDateFormat().format(new Date(timestamp));
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式
	 * 如：Thu Apr 10 11:40:56 CST 2014——>2014-04-10 10:07:14
	 */
	public static String transStrToCommonDateStr(String str) {
		try {
			return DateFormatterThreadLocal.getLongDateFormat().format(
					DateFormatterThreadLocal.getSolrReturnDateFormat().parse(str));
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "";
			//			throw new RuntimeException();
		}
	}

	public static long transSolrReturnStrToMilli(String str) {
		try {
			return DateFormatterThreadLocal.getSolrReturnDateFormat().parse(str).getTime();
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return 0L;
			//			throw new RuntimeException();
		}
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式，并提前N小时，Solr提前8小时
	 */
	public static String transStrToCommonDateStr(String str, int hours) {
		try {
			return DateFormatterThreadLocal.getLongDateFormat().format(
					DateFormatterThreadLocal.getSolrReturnDateFormat().parse(str).getTime() - hours * 3600 * 1000);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "";
			//			throw new RuntimeException();
		}
	}

	/**
	 * 将"2014-08-25 00:00:00"转换为"2014-08-25T00:00:00Z"
	 * @param str
	 * @return
	 */
	public static String transTimeStr(String str) {
		String[] strs = str.split("\\s");
		if (strs.length != 2) {
			throw new RuntimeException("TimeStr=" + str + " converted error!");
		} else {
			return strs[0] + "T" + strs[1] + "Z";
		}
	}

	/**
	 * 将"2014-08-25 00:00:00"转换为long型时间戳
	 * @param str
	 * @return
	 */
	public static long transTimeLong(String str) {
		try {
			Date date = DateFormatterThreadLocal.getLongDateFormat().parse(str);
			return date.getTime();
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将"2015-05-01T23:50:17.083Z"转换为long型时间戳
	 * @param str
	 * @return
	 */
	public static long transTwitterTimeLong(String str) {
		try {
			Date date = DateFormatterThreadLocal.getTwitterDateFormat().parse(str);
			return date.getTime();
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得距离当前时间intervalDay的零点时间
	 * @author donglei
	 * @param gapDay
	 * @return
	 */
	public static long getMidnight(long milli, int gapDay) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(milli);
		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH) + gapDay, 0, 0, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTimeInMillis();
	}

	public static long getZeroHourTime(long milli) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(milli);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTimeInMillis();
	}

	/**
	 * 获得获得距离当前时间intervalDay的时间
	 * @author donglei
	 * @param gapDay
	 * @return
	 */
	public static long transCurrentTime(long milli, int gapYear, int gapMonth, int gapDay, int gapHour) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(milli);
		date.set(Calendar.YEAR, date.get(Calendar.YEAR) + gapYear);
		date.set(Calendar.MONTH, date.get(Calendar.MONTH) + gapMonth);
		date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) + gapDay);
		date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) + gapHour);
		return date.getTimeInMillis();
	}

	/**
	 * @author donglei
	 * @param milli
	 * @return
	 */
	public static String convertMilliToStr(long milli) {
		StringBuilder sBuilder = new StringBuilder();
		int ms = (int) (milli % 1000);
		milli = milli / 1000;
		int hour = (int) (milli / 3600);
		sBuilder = sBuilder.append(hour + "H ");
		milli = milli % 3600;
		int min = (int) (milli / 60);
		sBuilder = sBuilder.append(min + "M ");
		milli = milli % 60;
		sBuilder = sBuilder.append(milli + "S ");
		sBuilder = sBuilder.append(ms + "MS ");
		return sBuilder.toString();
	}

	public static Date tranSinaApiDate(String timeStr) {
		try {
			return DateFormatterThreadLocal.getSinaApiDateFormat().parse(timeStr);
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将当前的时间戳转换成小时精度，如："2014-09-05,14"
	 */
	public static String timeStrByHour(long milliSecond) {
		return DateFormatterThreadLocal.getDateHourDateFormat().format(new Date(milliSecond));
	}

	public static DateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern, DateFormatPattern.DEFAULT_LOCALE);
	}

}
