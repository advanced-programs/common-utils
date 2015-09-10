package zx.soft.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.time.DateFormatPattern;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * JSON工具类
 *
 * @author wanggang
 *
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	// Java默认Date转换成String的格式，该格式与EEE MMM dd HH:mm:ss Z yyyy通用
	private static final String DEFAULT_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

	private static DateFormat dateFormat;

	static {
		String pattern = ConfigUtil.UTILS_PROPS.getProperty("date.format.pattern");
		if (pattern == null) {
			dateFormat = new SimpleDateFormat(DEFAULT_PATTERN, DateFormatPattern.DEFAULT_LOCALE);
		} else {
			dateFormat = new SimpleDateFormat(pattern, DateFormatPattern.DEFAULT_LOCALE);
		}
		OBJECT_MAPPER.setDateFormat(dateFormat);
	}

	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	public static DateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern, DateFormatPattern.DEFAULT_LOCALE);
	}

	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}

	public static String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static String toJsonWithoutPretty(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author donglei
	 * @param jsonStr Json字符串
	 * @param t 需要转换成的类
	 * @return 转换后的类
	 */
	public static <T> T getObject(String jsonStr, Class<T> t) {
		ObjectReader objectReader = OBJECT_MAPPER.reader(t);
		try {
			return objectReader.readValue(jsonStr);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author donglei
	 * @param node
	 * @param cls
	 * @return
	 */
	public static <T> T getObject(JsonNode node, Class<T> t) {
		ObjectReader objectReader = OBJECT_MAPPER.reader(t);
		try {
			return objectReader.readValue(node);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author donglei
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> List<T> parseJsonArray(String json, Class<T> t) {
		List<T> ts = new LinkedList<>();
		JsonFactory factory = new JsonFactory();
		JsonParser jp = null;
		try {
			jp = factory.createParser(json);
			jp.nextToken();
			while (jp.nextToken() == JsonToken.START_OBJECT) {
				T obj = OBJECT_MAPPER.readValue(jp, t);
				ts.add(obj);
			}
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}

		return ts;
	}

}
