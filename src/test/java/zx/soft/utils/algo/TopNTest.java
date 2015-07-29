package zx.soft.utils.algo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import zx.soft.utils.algo.TopN.KeyValue;

public class TopNTest {
	private Map<String, Integer> maps = new HashMap<String, Integer>();

	@Before
	public void beforeTest() {
		maps.put("3", 3);
		maps.put("10", 10);
		maps.put("4", 4);
		maps.put("8", 8);
		maps.put("12", 12);
		maps.put("7", 7);
		maps.put("123", 123);
		maps.put("0", 0);
		maps.put("60", 60);
		maps.put("120", 120);
	}

	@Test
	public void testTopNOnValue1() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 2);
		System.out.println(keys);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}]".equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue2() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 3);
		System.out.println(keys);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}]"
				.equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue3() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 5);
		System.out.println(keys);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}, KeyValue{key=12, value=12}, KeyValue{key=10, value=10}]"
				.equals(keys.toString()));
	}

	@After
	public void afterTest() {
		maps.clear();
	}

}
