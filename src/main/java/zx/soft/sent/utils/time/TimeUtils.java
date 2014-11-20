package zx.soft.sent.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * 
 * @author wanggang
 *
 */
public class TimeUtils {

	//	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	private static final SimpleDateFormat SOLR_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		System.out.println(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014"));
		System.out.println(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014", 8));
	}

	/**
	 * 将时间戳转换成Solr标准的Date格式，注意：该转换快8小时
	 * 如：2014-04-10T10:07:14Z
	 */
	public static String transToSolrDateStr(long timestamp) {
		return SOLR_FORMAT.format(new Date(timestamp));
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
			return LONG_FORMAT.format(dateFormat.parse(str));
		} catch (ParseException e) {
			return "";
			//			throw new RuntimeException();
		}
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式，并提前N小时
	 */
	public static String transStrToCommonDateStr(String str, int hours) {
		try {
			return LONG_FORMAT.format(dateFormat.parse(str).getTime() - 8 * 3600 * 1000);
		} catch (ParseException e) {
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
			Date date = LONG_FORMAT.parse("2014-08-25 12:31:45");
			return date.getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
