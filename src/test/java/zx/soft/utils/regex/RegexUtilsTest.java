package zx.soft.utils.regex;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilsTest {

	@Test
	public void testFindMatchStrs1() {
		Assert.assertTrue("[#合肥论坛类:, #芜湖论坛类:, #蚌埠论坛类:]".equals(RegexUtils.findMatchStrs(
				"#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", true).toString()));
	}

	@Test
	public void testFindMatchStrs2() {
		Assert.assertTrue("[合肥论坛类, 芜湖论坛类, 蚌埠论坛类]".equals(RegexUtils.findMatchStrs(
				"#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", false).toString()));
	}

	@Test
	public void testFindMatchStrs3() {
		Assert.assertTrue("[#合肥论坛类12:, #芜湖论坛类123:, #蚌埠论坛类213:]".equals(RegexUtils.findMatchStrs(
				"#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445",
 "#(.*?):", true).toString()));
	}

	@Test
	public void testReplace() {
		Assert.assertTrue("http://www.iteye.com/problems/75335".replaceAll(
				"[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*", "").isEmpty());
	}

}
