package zx.soft.utils.json;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class JsonUtilsTest {

	@Test
	public void testParseJsonArray() {
		String json = "[{\"foo\":\"bar\"},{\"foo\":\"biz\"}]";
		List<Foo> foos = JsonUtils.parseJsonArray(json, Foo.class);
		assertEquals(json, JsonUtils.toJsonWithoutPretty(foos));
	}

	@Test
	public void testDateFormat() throws ParseException {
		DateFormat dateFormat = JsonUtils.getDateFormat();
		Date date = dateFormat.parse("2013-10-23 16:58:17");
		assertEquals("2013-10-23 16:58:17", dateFormat.format(date));
	}

	@Test
	public void testObjectMapper() throws ParseException {
		String json = "[{\"date\":\"2013-10-23 16:58:17\"},{\"date\":\"2013-10-23 16:58:17\"}]";
		List<FooDate> foos = JsonUtils.parseJsonArray(json, FooDate.class);
		assertEquals(json, JsonUtils.toJsonWithoutPretty(foos));
	}

	@Test
	public void testGetDateFormat() throws ParseException {
		DateFormat dateFormat = JsonUtils.getDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2013-10-23");
		assertEquals("2013-10-23", dateFormat.format(date));
	}

}
