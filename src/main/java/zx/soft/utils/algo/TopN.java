package zx.soft.utils.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Multiset;

/**
 * TopN 算法工具类
 *
 * @author donglei
 *
 */
public class TopN {

	/**
	 * 求Map中值最大的N个键值对
	 *
	 * @param maps value可以比较的Map
	 * @param N    前N的键值对
	 * @return
	 */
	public static <K, T extends Comparable<? super T>> List<KeyValue<K, T>> topNOnValue(Map<K, T> maps, int N) {
		List<KeyValue<K, T>> topNList = new ArrayList<>();
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
	 *
	 * @param counts
	 * @param N
	 * @return
	 */
	public static <K> List<KeyValue<K, Integer>> topNOnValue(final Multiset<K> counts, int N) {
		List<KeyValue<K, Integer>> topNList = new ArrayList<>();
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

	/**
	 * 求List中前N个元素
	 *
	 * @param lists
	 * @param N
	 * @param unique
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> topN(List<T> lists, int N, boolean unique) {
		List<T> topNList = new ArrayList<>();
		for (T t : lists) {
			int i = topNList.size() - 1;
			for (; i >= 0; i--) {
				if (topNList.get(i).compareTo(t) >= 0) {
					break;
				}
			}
			if (unique) {
				if (i == -1 || topNList.get(i).compareTo(t) > 0) {
					topNList.add(i + 1, t);
					if (topNList.size() > N) {
						topNList.remove(N);
					}
				}
			} else {
				topNList.add(i + 1, t);
				if (topNList.size() > N) {
					topNList.remove(N);
				}
			}
		}

		return topNList;
	}

	/**
	 * 求List中前N个元素，去重
	 *
	 * @param lists
	 * @param N
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> topNUnique(List<T> lists, int N) {
		return topN(lists, N, true);
	}

	/**
	 * 求List中前N个元素
	 *
	 * @param lists
	 * @param N
	 * @param unique
	 * @param comparator 比较器
	 * @return
	 */
	public static <T> List<T> topN(List<T> lists, int N, boolean unique, Comparator<T> comparator) {
		List<T> topNList = new ArrayList<>();
		for (T t : lists) {
			int i = topNList.size() - 1;
			for (; i >= 0; i--) {
				if (comparator.compare(topNList.get(i), t) >= 0) {
					break;
				}
			}
			if (unique) {
				if (i == -1 || comparator.compare(topNList.get(i), t) > 0) {
					topNList.add(i + 1, t);
					if (topNList.size() > N) {
						topNList.remove(N);
					}
				}
			} else {
				topNList.add(i + 1, t);
				if (topNList.size() > N) {
					topNList.remove(N);
				}
			}
		}

		return topNList;
	}

	public static <T> List<T> topNUnique(List<T> lists, int N, Comparator<T> comparator) {
		return topN(lists, N, true, comparator);
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
			return MoreObjects.toStringHelper(this).add("key", key).add("value", value).toString();
		}

	}

}
