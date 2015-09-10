package zx.soft.utils.string;

import static org.junit.Assert.assertEquals;

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
		assertEquals("\"123\" AND \"456\" AND \"789\"", helper.getString());
		helper.clear();
		helper = new StringConcatHelper(ConcatMethod.OR);
		helper.add("\"456\"");
		helper.add("\"789\"");
		assertEquals("\"456\" OR \"789\"", helper.getString());
	}

}
