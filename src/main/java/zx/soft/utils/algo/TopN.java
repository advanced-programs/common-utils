package zx.soft.utils.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * TopN 算法工具类
 * @author donglei
 *
 */
public class TopN {
	/**
	 * 求Map中值最大的N个键值对
	 * @param maps value可以比较的Map
	 * @param N    前N的键值对
	 * @return
	 */
	public static <K, T extends Comparable<? super T>> List<KeyValue<K, T>> topNOnValue(Map<K, T> maps, int N) {
		List<KeyValue<K, T>> topNList = new ArrayList<TopN.KeyValue<K, T>>();
		for (Entry<K, T> entry : maps.entrySet()) {
			KeyValue<K, T> keyValue = new KeyValue<>();
			keyValue.setKey(entry.getKey());
			keyValue.setValue(entry.getValue());
			int i = topNList.size();
			for (; i > 0; i--) {
				if (topNList.get(i - 1).getValue().compareTo(keyValue.getValue()) < 0) {
					if (i < N) {
						if (i < topNList.size()) {
							topNList.set(i, topNList.get(i - 1));
						} else {
							topNList.add(i, topNList.get(i - 1));
						}
					}
				} else {
					break;
				}
			}
			if (i < topNList.size()) {
				topNList.set(i, keyValue);
			} else {
				if (topNList.size() < N) {
					topNList.add(i, keyValue);
				}
			}
		}
		return topNList;
	}

	/**
	 * 取Multiset中计数最多的N的元素，并返回键值对
	 * @param counts
	 * @param N
	 * @return
	 */
	public static <K> List<KeyValue<K, Integer>> topNOnValue(final Multiset<K> counts, int N) {
		List<KeyValue<K, Integer>> topNList = new ArrayList<KeyValue<K, Integer>>();
		for (K entry : counts.elementSet()) {
			KeyValue<K, Integer> keyValue = new KeyValue<>();
			keyValue.setKey(entry);
			keyValue.setValue(counts.count(entry));
			int i = topNList.size();
			for (; i > 0; i--) {
				if (topNList.get(i - 1).getValue().compareTo(keyValue.getValue()) < 0) {
					if (i < N) {
						if (i < topNList.size()) {
							topNList.set(i, topNList.get(i - 1));
						} else {
							topNList.add(i, topNList.get(i - 1));
						}
					}
				} else {
					break;
				}
			}
			if (i < topNList.size()) {
				topNList.set(i, keyValue);
			} else {
				if (topNList.size() < N) {
					topNList.add(i, keyValue);
				}
			}
		}
		return topNList;
	}

	public static void main(String[] args) {
		int N = 100;
		Map<String, Integer> maps = new HashMap<String, Integer>();
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
		System.out.println(topNOnValue(maps, N));
		Multiset<String> counts = HashMultiset.create();
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
		System.out.println(topNOnValue(counts, N));
	}

	public static class KeyValue<K, T extends Comparable<? super T>> {
		private K key;
		private T value;

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(KeyValue.class).add("key", key).add("value", value).toString();
		}

	}

}
