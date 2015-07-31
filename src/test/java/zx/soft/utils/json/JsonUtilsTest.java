package zx.soft.utils.json;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JsonUtilsTest {

	@Test
	public void testParseJsonArray() {
		String json = "[{\"foo\":\"bar\"},{\"foo\":\"biz\"}]";
		List<Foo> foos = JsonUtils.parseJsonArray(json, Foo.class);
		assertEquals(json, JsonUtils.toJsonWithoutPretty(foos));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetObject() {
		String json = "{\"期货\":12,\"中国梦\":14,\"项目\":13,\"员工\":11,\"价值\":25,\"源地\":74,\"发展\":20,\"瑞达\":10,\"精神\":20,\"企业\":23,\"社会\":12,\"市场\":10,\"男子\":10,\"中国\":27,\"实现\":11,\"工作\":11,\"地产\":10,\"创新\":22,\"实干\":15,\"北京\":13}";
		Map<String, Integer> maps = JsonUtils.getObject(json, Map.class);
		System.out.println(maps);
	}

}
