package zx.soft.utils.algo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import zx.soft.utils.algo.TopN.KeyValue;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class TopNTest {

	private Map<String, Integer> maps = new HashMap<>();
	private Multiset<String> counts = HashMultiset.create();

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

		for (int i = 0; i < 3; i++) {
			counts.add("3");
		}
		for (int i = 0; i < 10; i++) {
			counts.add("10");
		}
		for (int i = 0; i < 4; i++) {
			counts.add("4");
		}
		for (int i = 0; i < 8; i++) {
			counts.add("8");
		}
		for (int i = 0; i < 12; i++) {
			counts.add("12");
		}
		for (int i = 0; i < 7; i++) {
			counts.add("7");
		}
		for (int i = 0; i < 123; i++) {
			counts.add("123");
		}
		for (int i = 0; i < 60; i++) {
			counts.add("60");
		}
		for (int i = 0; i < 120; i++) {
			counts.add("120");
		}
	}

	@Test
	public void testTopNOnValue1() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 2);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}]".equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue2() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 3);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}]"
				.equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue3() {
		List<KeyValue<String, Integer>> keys = TopN.<String, Integer> topNOnValue(maps, 5);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}, KeyValue{key=12, value=12}, KeyValue{key=10, value=10}]"
				.equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue4() {
		List<KeyValue<String, Integer>> keys = TopN.topNOnValue(counts, 2);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}]".equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue5() {
		List<KeyValue<String, Integer>> keys = TopN.topNOnValue(counts, 3);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}]"
				.equals(keys.toString()));
	}

	@Test
	public void testTopNOnValue6() {
		List<KeyValue<String, Integer>> keys = TopN.topNOnValue(counts, 5);
		Assert.assertTrue("[KeyValue{key=123, value=123}, KeyValue{key=120, value=120}, KeyValue{key=60, value=60}, KeyValue{key=12, value=12}, KeyValue{key=10, value=10}]"
				.equals(keys.toString()));
	}

	@After
	public void afterTest() {
		maps.clear();
		counts.clear();
	}

}
