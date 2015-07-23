package zx.soft.utils.json;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class JsonUtilsTest {

	@Test
	public void testParseJsonArray() {
		String json = "[{\"foo\":\"bar\"},{\"foo\":\"biz\"}]";
		List<Foo> foos = JsonUtils.parseJsonArray(json, Foo.class);
		assertEquals(json, JsonUtils.toJsonWithoutPretty(foos));
	}

}
