package zx.soft.utils.checksum;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验工具类
 *
 * MD5的算法在RFC1321中定义，在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 *     MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 *     MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 *     MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 *     MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 *     MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 *
 * @author wanggang
 *
 */
public class CheckSumUtils {

	private static Logger logger = LoggerFactory.getLogger(CheckSumUtils.class);

	// 用来将字节转换成 16 进制表示的字符
	private static final char HEX_DIGITS[] = //
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * modified by donglei
	 *
	 * MD5加码 生成32位md5码
	 */
	public static String getMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such md5 algorithm!");
			return null;
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * 速度较快的MD5，结果同上
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5Speed(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such md5 algorithm!");
			return null;
		}
		byte[] bs = md5.digest(str.getBytes(Charset.forName("UTF-8")));
		// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
		char charArr[] = new char[16 * 2];
		// 表示转换结果中对应的字符位置
		int k = 0;
		// 从第一个字节开始，对 MD5 的每一个字节
		for (int i = 0; i < 16; i++) {
			// 转换成 16 进制字符的转换, 取第 i 个字节
			byte byte0 = bs[i];
			// 取字节中高 4 位的数字转换,
			charArr[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
			// >>> 为逻辑右移，将符号位一起右移, 取字节中低 4 位的数字转换
			charArr[k++] = HEX_DIGITS[byte0 & 0xf];
		}

		return new String(charArr);
	}

	public static byte[] md5sum(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such md5 algorithm!");
			return null;
		}
		return md5.digest(str.getBytes());
	}

	/**
	 * CRC32校验
	 */
	public static long getCRC32(String str) {
		// get bytes from string
		byte bytes[] = str.getBytes();
		Checksum checksum = new CRC32();
		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);
		// get the current checksum value
		return checksum.getValue();
	}

}
