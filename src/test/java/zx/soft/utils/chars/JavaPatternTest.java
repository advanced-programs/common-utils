package zx.soft.utils.chars;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JavaPatternTest {

	private static final String str1 = "1222234444";

	private static final String str2 = "fffffgeee";

	private static final String str3 = "GGGJJJKKDDHD";

	private static final String str4 = "122Gssg888";

	private static final String str5 = "测试";

	private static final String str6 = "122Gssg888测试";

	private static final String str7 = "126ede@qq.com";

	private static final String str8 = "123.325";

	@Test
	public void testIsAllNum() {
		assertTrue(JavaPattern.isAllNum(str1));
		assertFalse(JavaPattern.isAllNum(str2));
		assertFalse(JavaPattern.isAllNum(str3));
		assertFalse(JavaPattern.isAllNum(str4));
		assertFalse(JavaPattern.isAllNum(str5));
		assertFalse(JavaPattern.isAllNum(str6));
	}

	@Test
	public void testIsAllNumAndLetter() {
		assertTrue(JavaPattern.isAllNumAndLetter(str1));
		assertTrue(JavaPattern.isAllNumAndLetter(str2));
		assertTrue(JavaPattern.isAllNumAndLetter(str3));
		assertTrue(JavaPattern.isAllNumAndLetter(str4));
		assertFalse(JavaPattern.isAllNumAndLetter(str5));
		assertFalse(JavaPattern.isAllNumAndLetter(str6));
	}

	@Test
	public void testIsAllChinese() {
		assertFalse(JavaPattern.isAllChinese(str1));
		assertFalse(JavaPattern.isAllChinese(str2));
		assertFalse(JavaPattern.isAllChinese(str3));
		assertFalse(JavaPattern.isAllChinese(str4));
		assertTrue(JavaPattern.isAllChinese(str5));
		assertFalse(JavaPattern.isAllChinese(str6));
	}

	@Test
	public void testIsEmail() {
		assertTrue(JavaPattern.isEmail(str7));
	}

	@Test
	public void testIsChinese() {
		assertFalse(JavaPattern.isChinese(str1));
		assertFalse(JavaPattern.isChinese(str2));
		assertFalse(JavaPattern.isChinese(str3));
		assertFalse(JavaPattern.isChinese(str4));
		assertTrue(JavaPattern.isChinese(str5));
		assertFalse(JavaPattern.isChinese(str6));
	}

	@Test
	public void testIsDouble() {
		assertTrue(JavaPattern.isDouble(str8));
		assertFalse(JavaPattern.isDouble(str1));
	}

	@Test
	public void testIsInteger() {
		assertTrue(JavaPattern.isInteger(str1));
		assertFalse(JavaPattern.isInteger(str8));
	}

}
