package zx.soft.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

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

	private static final ObjectMapper mapper = new ObjectMapper();

	//	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	static {
		mapper.setDateFormat(dateFormat);
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static String toJson(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static String toJsonWithoutPretty(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author donglei
	 * @param s
	 * @param t
	 * @return
	 */
	public static <T> T getObject(String s, Class<T> t) {
		ObjectReader objectReader = mapper.reader(t);
		try {
			return objectReader.readValue(s);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @author donglei
	 * @param node
	 * @param cls
	 * @return
	 */
	public static <T> T getObject(JsonNode node, Class<T> cls) {
		ObjectReader objectReader = mapper.reader(cls);
		try {
			return objectReader.readValue(node);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @author donglei
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> List<T> parseJsonArray(String json, Class<T> cls) {
		List<T> ts = new LinkedList<T>();

		JsonFactory factory = new JsonFactory();
		JsonParser jp = null;
		try {
			jp = factory.createParser(json);
			jp.nextToken();
			while (jp.nextToken() == JsonToken.START_OBJECT) {
				T t = mapper.readValue(jp, cls);
				ts.add(t);
			}
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}

		return ts;
	}

	/**
	 *  测试函数
	 */
	public static void main(String[] args) throws ParseException {

		//		System.out.println(dateFormat.format(new Date()));
		// Wed Oct 23 16:58:17 +0800 2013
		// Wed Oct 23 16:58:17 CST 2013
		Date parse = dateFormat.parse("Thu Apr 10 11:40:56 CST 2014");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse));

	}

}
