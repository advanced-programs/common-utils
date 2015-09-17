package zx.soft.utils.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateFormat实现线程内共享
 *
 * @author wanggang
 *
 */
public class DateFormatterThreadLocal {

	private static final ThreadLocal<DateFormat> SINA_API_FORMATS = new ThreadLocal<>();

	private static final ThreadLocal<DateFormat> SOLR_RETURN_FORMATS = new ThreadLocal<>();

	private static final ThreadLocal<DateFormat> SOLR_FORMATS = new ThreadLocal<>();

	private static final ThreadLocal<DateFormat> LONG_FORMATS = new ThreadLocal<>();

	private static final ThreadLocal<DateFormat> TWITTER_FORMATS = new ThreadLocal<>();

	private static final ThreadLocal<DateFormat> DATE_HOURS = new ThreadLocal<>();

	public static DateFormat getSinaApiDateFormat() {
		DateFormat format = SINA_API_FORMATS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.SINA_API_FORMAT.toString(),
					DateFormatPattern.DEFAULT_LOCALE);
			SINA_API_FORMATS.set(format);
		}
		return format;
	}

	public static DateFormat getSolrReturnDateFormat() {
		DateFormat format = SOLR_RETURN_FORMATS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.SOLR_RETURN_FORMAT.toString(),
					DateFormatPattern.DEFAULT_LOCALE);
			SOLR_RETURN_FORMATS.set(format);
		}
		return format;
	}

	public static DateFormat getSolrDateFormat() {
		DateFormat format = SOLR_FORMATS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.SOLR_FORMAT.toString());
			SOLR_FORMATS.set(format);
		}
		return format;
	}

	public static DateFormat getLongDateFormat() {
		DateFormat format = LONG_FORMATS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.LONG_FORMAT.toString());
			LONG_FORMATS.set(format);
		}
		return format;
	}

	public static DateFormat getTwitterDateFormat() {
		DateFormat format = TWITTER_FORMATS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.TWITTER_FORMAT.toString());
			TWITTER_FORMATS.set(format);
		}
		return format;
	}

	public static DateFormat getDateHourDateFormat() {
		DateFormat format = DATE_HOURS.get();
		if (format == null) {
			format = new SimpleDateFormat(DateFormatPattern.DATE_HOUR.toString());
			DATE_HOURS.set(format);
		}
		return format;
	}

}
