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

}
