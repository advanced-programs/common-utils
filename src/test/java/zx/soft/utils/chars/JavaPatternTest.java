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

	@Test
	public void test() {
		assertTrue(JavaPattern.isAllNumAndLetter(str1));
		assertTrue(JavaPattern.isAllNumAndLetter(str2));
		assertTrue(JavaPattern.isAllNumAndLetter(str3));
		assertTrue(JavaPattern.isAllNumAndLetter(str4));
		assertFalse(JavaPattern.isAllNumAndLetter(str5));
		assertFalse(JavaPattern.isAllNumAndLetter(str6));
	}

}
