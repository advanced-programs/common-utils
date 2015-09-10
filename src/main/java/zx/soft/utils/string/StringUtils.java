package zx.soft.utils.string;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 字符串处理工具
 *
 * @author hwdlei
 * @version 1.0.0
 */
public class StringUtils {

	/**
	 * 判断字符是否为空，为空则返回true
	 * 为空的条件：null、""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转化为指定编码格式
	 * @param str
	 * @param charsetName
	 * @return
	 */
	public static String parseStrByCharSet(String str, String charsetName) {
		if (str != null) {
			byte[] bytes = str.getBytes();
			try {
				return new String(bytes, charsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将字符串转化为UTF-8格式
	 * @param str
	 * @return
	 */
	public static String parseStrByUTF8(String str) {
		return parseStrByCharSet(str, "UTF-8");
	}

	/**
	 * 将字符串转化为UTF-16格式
	 * @param str
	 * @return
	 */
	public static String parseStrByUTF16(String str) {
		return parseStrByCharSet(str, "UTF-16");
	}

	/**
	 * 将字符串转化为GBK格式
	 * @param str
	 * @return
	 */
	public static String parseStrByGBK(String str) {
		return parseStrByCharSet(str, "GBK");
	}

	/**
	 * 获取换行符
	 *
	 * @return
	 */
	public static String getNewLine() {
		return System.getProperty("line.separator");
	}

	/**
	 * 获取指定编码的字节数组
	 *
	 * @param str
	 * @param charset
	 * @return
	 */

	public static byte[] getBytesByCharset(String str, Charset charset) {
		if (str != null) {
			return str.getBytes(charset);
		}
		return null;
	}

	/**
	 * 获取UTF8编码的字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getUTF8Bytes(String str) {
		return getBytesByCharset(str, Charset.forName("UTF-8"));
	}

	/**
	 * 获取UTF16编码的字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getUTF16Bytes(String str) {
		return getBytesByCharset(str, Charset.forName("UTF-16"));
	}

	/**
	 * 获取GBK编码的字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getGBKBytes(String str) {
		return getBytesByCharset(str, Charset.forName("GBK"));
	}

	/**
	 * 获取ISO-8859-1编码的字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getISO8859Bytes(String str) {
		return getBytesByCharset(str, Charset.forName("ISO-8859-1"));
	}

	public static void main(String args[]) {
		String a = "";
		System.out.println(a.getBytes().length);
	}

}
