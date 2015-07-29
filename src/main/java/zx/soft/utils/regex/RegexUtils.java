package zx.soft.utils.regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zx.soft.utils.string.StringUtils;

public class RegexUtils {

	public static void main(String[] args) throws IOException {
		try {
			//			String s = null;
			//			s = createJson("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445");
			//			System.err.println(s);
			//			s = createJson("#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445");
			//			System.err.println(s);
			System.out.println(findMatchStrs("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", true));
			System.out.println(findMatchStrs("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445", "#(.*?):", false));
			System.out.println(findMatchStrs("#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445", "#(.*?):", true));
			System.out.println(findMatchStrs("#合肥论坛类12:429,296#芜湖论坛类123:347#蚌埠论坛类213:445", "#(.*?):", false));
			System.out.println("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445".replaceAll("#(.*?):", "%%%"));
			System.out.println("#合肥论坛类:429,296#芜湖论坛类:347#蚌埠论坛类:445".replaceAll("(\\d+)(\\,\\d+)*", "%%%"));
			System.out.println("http://www.iteye.com/problems/75335".replaceAll(
					"[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*", "DDDDD"));
			String content = "http://www.iteye.com/problems/75335";
			System.out.println(content.replaceAll("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*", ""));
			System.out.println(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 从字符串中找出所有符合pattern模式的子串，group控制整个子串还是子组
	 * @param str
	 * @param pattern
	 *   贪婪: 最长匹配 .* : 输出: <biao><>c<b>
	 *   不知是否非贪婪 .*? : 输出: <biao>, <>, <b>
	 *   使用组, 输出<>里的内容, 输出: 'biao', ' ', 'b' group(1)
	 *   0组代表整个表达式, 子组从1开始 group(0)
	 * @param group
	 * @return
	 */
	public static List<String> findMatchStrs(String str, String pattern, boolean group) {
		List<String> strs = new ArrayList<>();
		if (StringUtils.isEmpty(str)) {
			return strs;
		}
		Pattern pat = Pattern.compile(pattern);
		Matcher matcher = pat.matcher(str);
		while (matcher.find()) {
			if (group) {
				strs.add(matcher.group(0));
			} else {
				strs.add(matcher.group(1));
			}
		}
		return strs;
	}

	/**
	 *
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceMatchStrs(String str, String pattern, String replace) {
		if(str != null) {
			return str.replaceAll(pattern, replace);
		}
		return null;
	}

}