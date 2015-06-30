package zx.soft.utils.checksum;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5的算法在RFC1321 中定义
 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 *
 * 传入参数：一个字节数组
 * 传出参数：字节数组的 MD5 结果字符串
 * @author chenlb 2009-6-19 上午11:46:38
 */
public class MD5 {
	private MessageDigest md5;
	private char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public MD5() {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("no such md5 algorithm!", e);
		}
	}

	public String hexString(byte[] source) {
		byte[] bs = md5.digest(source);
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = bs[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		return new String(str);
	}

	public String hexString(String source, Charset charset) {
		String md5Str = "";
		md5Str = hexString(source.getBytes(charset));
		return md5Str;
	}

	public static void main(String[] args) {
		MD5 md5 = new MD5();
		//c2e5848ee99554aae35c090e581cd63c
		System.out.println(md5.hexString("abcdefghijklmnopqrstuvwxyz", Charset.forName("UTF-8")));
	}
}