package zx.soft.utils.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * URL编解码器工具类
 *
 * @author wgybzb
 *
 */
public class URLCodecUtils {

	private static Logger logger = LoggerFactory.getLogger(URLCodecUtils.class);

	public static String encoder(String url, String codec) {
		try {
			String result = URLEncoder.encode(url, codec);
			return result;
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return url;
		}
	}

	public static String decoder(String url, String codec) {
		try {
			String result = URLDecoder.decode(url, codec);
			return result;
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return url;
		}
	}

}
