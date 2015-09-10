package zx.soft.utils.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 替代转换工具
 *
 * @author wanggang
 *
 */
public class ReplaceConvert {

	private static DateFormat sinaDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

	public static void configureJacksonConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(sinaDateFormat);
	}

	/**
	 * 移除给定类的第一个注册的converter后，使用Restlet引擎注册一个converter类
	 * 参考:http://restlet.tigris.org/ds/viewMessage.do?dsForumId=4447&dsMessageId=2716118
	 */
	static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

		ConverterHelper oldConverter = null;
		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		for (ConverterHelper converter : converters) {
			if (converter.getClass().equals(converterClass)) {
				converters.remove(converter);
				oldConverter = converter;
				break;
			}
		}

		converters.add(newConverter);
		if (oldConverter == null) {
			System.err.println("Added Converter to Restlet Engine: " + newConverter.getClass().getName());
		} else {
			System.err.println("Replaced Converter " + oldConverter.getClass().getName() + " with "
					+ newConverter.getClass().getName() + " in Restlet Engine");
		}
	}

}