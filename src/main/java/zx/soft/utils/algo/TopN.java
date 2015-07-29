package zx.soft.utils.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.MoreObjects;

public class TopN {
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
						if(i < topNList.size()){
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
		System.out.println(topNOnValue(maps, 2));
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
