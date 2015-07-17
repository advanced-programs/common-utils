package zx.soft.utils.json;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TestJson {

	@Test
	public void testJson() {
		boolean tmp = true;
		String json = "[{\"foo\": \"bar\"},{\"foo\": \"biz\"}]";
		List<Foo> foos = JsonUtils.parseJsonArray(json, Foo.class);
		for (Foo foo : foos) {
			System.out.println(foo.getFoo());
		}
		assertTrue(tmp);
	}
}