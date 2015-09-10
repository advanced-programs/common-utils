package zx.soft.utils.time;

import java.util.Locale;

/**
 * 常用的时间格式类型
 *
 * @author wanggang
 *
 */
public enum DateFormatPattern {

	SINA_API_FORMAT("EEE MMM dd HH:mm:ss Z yyyy"), //

	SOLR_RETURN_FORMAT("EEE MMM dd HH:mm:ss zzz yyyy"), //

	SOLR_FORMAT("yyyy-MM-dd'T'HH:mm:ss'Z'"), //

	LONG_FORMAT("yyyy-MM-dd HH:mm:ss"), //

	TWITTER_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	// 成员变量
	private final String pattern;

	// 构造方法
	private DateFormatPattern(String pattern) {
		this.pattern = pattern;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.pattern;
	}

	public static final Locale DEFAULT_LOCALE = Locale.US;

}
