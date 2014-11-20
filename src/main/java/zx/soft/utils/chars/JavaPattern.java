package zx.soft.utils.chars;

public class JavaPattern {

	public static boolean isAllNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

}
