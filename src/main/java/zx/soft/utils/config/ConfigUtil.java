package zx.soft.utils.config;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件读取类
 *
 * @author wanggang
 *
 */
public class ConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	public static Properties UTILS_PROPS;

	static {
		try {
			UTILS_PROPS = getProps("utils.properties");
		} catch (Exception e) {
			UTILS_PROPS = getProps("utils-sample.properties");
		}
	}

	public static Properties getProps(String confFileName) {
		Properties result = new Properties();
		logger.info("Load resource: " + confFileName);
		try (InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(confFileName);) {
			result.load(in);
			return result;
		} catch (final Exception e) {
			logger.error("Config file '{}' does not exist!", confFileName);
			throw new RuntimeException();
		}
	}

}
