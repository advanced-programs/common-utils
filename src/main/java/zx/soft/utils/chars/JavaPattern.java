package zx.soft.utils.chars;


public class JavaPattern {

	/**
	 * 判断是否全部为数字
	 */
	public static boolean isAllNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 判断是否全部为数字或字母
	 */
	public static boolean isAllNumAndLetter(String str) {
		return str.matches("^[-+]?(([0-9A-Za-z]+)([.]([0-9A-Za-z]+))?|([.]([0-9A-Za-z]+))?)$");
	}

	/**
	 * 判断是否全部为中文
	 */
	public static boolean isAllChinese(String str) {
		return str.matches("[\u4E00-\u9FA5]+");
	}

	/**
	 * 判断输入的字符串是否符合Email格式.
	 */
	public static boolean isEmail(String str) {
		if (str == null || str.length() < 1 || str.length() > 256) {
			return false;
		}
		return str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 */
	public static boolean isChinese(String str) {
		return str.matches("[\u0391-\uFFE5]+$");
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 */
	public static boolean isDouble(String str) {
		return str.matches("^[-\\+]?\\d+\\.\\d+$");
	}

	/**
	 * 判断是否为整数
	 */
	public static boolean isInteger(String str) {
		return str.matches("^[-\\+]?[\\d]+$");
	}

}
