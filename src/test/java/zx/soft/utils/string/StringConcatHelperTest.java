package zx.soft.utils.string;

import org.junit.Test;

/**
 *
 * @author donglei
 *
 */
public class StringConcatHelperTest {

	@Test
	public void testStringConcatHelper() {
		StringConcatHelper helper = new StringConcatHelper(ConcatMethod.AND);
		helper.add("\"123\"");
		helper.add("\"456\"");
		helper.add("\"789\"");
		String s = helper.getString();
		System.out.println(s);
		helper.clear();
		helper.add("\"456\"");
		helper.add("\"789\"");
		System.out.println(helper.getString());
		System.out.println(s);
	}

}
