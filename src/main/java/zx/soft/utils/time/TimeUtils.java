package zx.soft.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

	private static final DateFormat SINA_API_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	private static final DateFormat SOLR_RETURN_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	private static final DateFormat SOLR_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static final DateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final DateFormat TWITTER_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static final SimpleDateFormat DATE_HOUR = new SimpleDateFormat("yyyy-MM-dd,HH");

	public static void main(String[] args) {
		System.out.println(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014"));
		System.out.println(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014", 8));
		System.out.println(TimeUtils.transToSolrDateStr(getMidnight(System.currentTimeMillis(), -31)));
		System.out.println(TimeUtils.transToSolrDateStr(transCurrentTime(System.currentTimeMillis(), 0, 0, -31, 0)));
		System.out.println(convertMilliToStr(1 * 3600 * 1000 + 30 * 60 * 1000 + 10000));
		System.out.println(timeStrByHour(System.currentTimeMillis()));
	}

	/**
	 * 将时间戳转换成Solr标准的Date格式，注意：该转换快8小时
	 * 如：2014-04-10T10:07:14Z
	 */
	public static String transToSolrDateStr(long timestamp) {
		return SOLR_FORMAT.format(new Date(timestamp));
	}

	public static long tranSolrDateStrToMilli(String str) throws ParseException {
		return SOLR_FORMAT.parse(str).getTime();
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
		return LONG_FORMAT.format(new Date(timestamp));
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式
	 * 如：Thu Apr 10 11:40:56 CST 2014——>2014-04-10 10:07:14
	 */
	public static String transStrToCommonDateStr(String str) {
		try {
			return LONG_FORMAT.format(SOLR_RETURN_FORMAT.parse(str));
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "";
			//			throw new RuntimeException();
		}
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式，并提前N小时，Solr提前8小时
	 */
	public static String transStrToCommonDateStr(String str, int hours) {
		try {
			return LONG_FORMAT.format(SOLR_RETURN_FORMAT.parse(str).getTime() - hours * 3600 * 1000);
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
			Date date = LONG_FORMAT.parse(str);
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
			Date date = TWITTER_FORMAT.parse(str);
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
			return SINA_API_FORMAT.parse(timeStr);
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将当前的时间戳转换成小时精度，如："2014-09-05,14"
	 */
	public static String timeStrByHour(long milliSecond) {
		return DATE_HOUR.format(new Date(milliSecond));
	}
}
