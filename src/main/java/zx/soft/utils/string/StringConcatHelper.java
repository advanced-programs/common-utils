package zx.soft.utils.string;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;

/**
 * 字符串连接工具类
 *
 * @author donglei
 *
 */
public class StringConcatHelper {

	// 字符串缓存
	private Map<Object, StringBuilder> mBuilder = new HashMap<>();
	private static final String DEFAULT = "default";
	private ConcatMethod method;

	public StringConcatHelper(ConcatMethod method) {
		this.method = method;
		mBuilder.put(DEFAULT, new StringBuilder());
	}

	public void setMethod(ConcatMethod method) {
		this.method = method;
		clear();
	}

	public void add(String item) {
		add(DEFAULT, item);
	}

	public void add(Object key, String item) {
		StringBuilder sBuilder = null;
		if (mBuilder.containsKey(key)) {
			sBuilder = mBuilder.get(key);
		} else {
			sBuilder = new StringBuilder();
			mBuilder.put(key, sBuilder);
		}
		method.concat(sBuilder, item);
	}

	public String getString() {
		return getString(DEFAULT);
	}

	public String getString(Object key) {
		if (mBuilder.containsKey(key)) {
			return mBuilder.get(key).toString();
		} else {
			return "";
		}
	}

	public Map<Object, String> getALLString() {
		Map<Object, String> maps = new HashMap<>();
		for (Map.Entry<Object, StringBuilder> entry : mBuilder.entrySet()) {
			if (DEFAULT.equals(entry.getKey())) {
				continue;
			}
			maps.put(entry.getKey(), entry.getValue().toString());
		}
		return maps;
	}

	public void clear() {
		clearKey(DEFAULT);
	}

	public void clearKey(Object key) {
		if (mBuilder.containsKey(key)) {
			mBuilder.get(key).setLength(0);
			mBuilder.get(key).trimToSize();
		}
	}

	public void clearAll() {
		mBuilder.clear();
		mBuilder.put(DEFAULT, new StringBuilder());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("string", mBuilder.toString()).add("method", method.name())
				.toString();
	}

}
