package zx.soft.utils.regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegexUtilsTest {

	@Test
	public void testRegexUtils() {
		assertEquals("[#合肥论坛类:, #芜湖论坛类:, #蚌埠论坛类:]",
				RegexUtils.findMatchStrs("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", true).toString());
		assertEquals("[合肥论坛类, 芜湖论坛类, 蚌埠论坛类]",
				RegexUtils.findMatchStrs("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", false).toString());
		assertEquals("[#合肥论坛类12:, #芜湖论坛类123:, #蚌埠论坛类213:]",
				RegexUtils.findMatchStrs("#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445", "#(.*?):", true).toString());
		assertEquals("[合肥论坛类12, 芜湖论坛类123, 蚌埠论坛类213]",
				RegexUtils.findMatchStrs("#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445", "#(.*?):", false).toString());
		assertEquals("%%%429,296%%%347%%%445", "#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445".replaceAll("#(.*?):", "%%%"));
		assertEquals("#合肥论坛类:%%%#芜湖论坛类:%%%#蚌埠论坛类:%%%",
				"#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445".replaceAll("(\\d+)(\\,\\d+)*", "%%%"));
	}

}
