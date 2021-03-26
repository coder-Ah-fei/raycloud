package hqsc.ray.core.common.util;

import com.github.binarywang.java.emoji.EmojiConverter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author pangu
 */
public class StringUtil extends StringUtils {
	
	private static final EmojiConverter emojiConverter = EmojiConverter.getInstance();
	
	/**
	 * emoji 转成   :no_good:  这种格式的
	 *
	 * @param str
	 * @return
	 */
	public static String toAlias(String str) {
		return emojiConverter.toAlias(str);
	}
	
	/**
	 * emoji 转成   &#128581;  这种格式的
	 *
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		return emojiConverter.toHtml(str);
	}
	
	/**
	 * 还原emoji
	 *
	 * @param str
	 * @return
	 */
	public static String toUnicode(String str) {
		return emojiConverter.toUnicode(str);
	}
	
	
	public static boolean isBlank(String string) {
		return StringUtils.isEmpty(string) || string.equals("null");
	}
	
	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}
	
	/**
	 * 替换指定字符串的指定区间内字符为"*"
	 *
	 * @param str          字符串
	 * @param startInclude 开始位置（包含）
	 * @param endExclude   结束位置（不包含）
	 * @return 替换后的字符串
	 * @since 1.3.8
	 */
	public static String hide(CharSequence str, int startInclude, int endExclude) {
		return replace(str, startInclude, endExclude, '*');
	}
	
	/**
	 * 替换指定字符串的指定区间内字符为固定字符
	 *
	 * @param str          字符串
	 * @param startInclude 开始位置（包含）
	 * @param endExclude   结束位置（不包含）
	 * @param replacedChar 被替换的字符
	 * @return 替换后的字符串
	 * @since 1.3.8
	 */
	public static String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
		if (isEmpty(str)) {
			return str(str);
		}
		final int strLength = str.length();
		if (startInclude > strLength) {
			return str(str);
		}
		if (endExclude > strLength) {
			endExclude = strLength;
		}
		if (startInclude > endExclude) {
			// 如果起始位置大于结束位置，不替换
			return str(str);
		}
		
		final char[] chars = new char[strLength];
		for (int i = 0; i < strLength; i++) {
			if (i >= startInclude && i < endExclude) {
				chars[i] = replacedChar;
			} else {
				chars[i] = str.charAt(i);
			}
		}
		return new String(chars);
	}
	
	/**
	 * {@link CharSequence} 转为字符串，null安全
	 *
	 * @param cs {@link CharSequence}
	 * @return 字符串
	 */
	public static String str(CharSequence cs) {
		return null == cs ? null : cs.toString();
	}
	
	/**
	 * Convert a {@code Collection} into a delimited {@code String} (e.g., CSV).
	 * <p>Useful for {@code toString()} implementations.
	 *
	 * @param coll the {@code Collection} to convert
	 * @return the delimited {@code String}
	 */
	public static String join(Collection<?> coll) {
		return StringUtil.collectionToCommaDelimitedString(coll);
	}
	
	/**
	 * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
	 * <p>Useful for {@code toString()} implementations.
	 *
	 * @param coll  the {@code Collection} to convert
	 * @param delim the delimiter to use (typically a ",")
	 * @return the delimited {@code String}
	 */
	public static String join(Collection<?> coll, String delim) {
		return StringUtil.collectionToDelimitedString(coll, delim);
	}
	
	/**
	 * Convert a {@code String} array into a comma delimited {@code String}
	 * (i.e., CSV).
	 * <p>Useful for {@code toString()} implementations.
	 *
	 * @param arr the array to display
	 * @return the delimited {@code String}
	 */
	public static String join(Object[] arr) {
		return StringUtil.arrayToCommaDelimitedString(arr);
	}
	
	/**
	 * Convert a {@code String} array into a delimited {@code String} (e.g. CSV).
	 * <p>Useful for {@code toString()} implementations.
	 *
	 * @param arr   the array to display
	 * @param delim the delimiter to use (typically a ",")
	 * @return the delimited {@code String}
	 */
	public static String join(Object[] arr, String delim) {
		return StringUtil.arrayToDelimitedString(arr, delim);
	}
	
	
	/**
	 * 从内容中提取http开头的地址
	 *
	 * @param htmlStr
	 * @return
	 */
	public static List<String> getImgStr(String htmlStr) {
		Matcher m = Pattern.compile("src=\"http.*?\"").matcher(htmlStr);
		List<String> list = new ArrayList<>();
		
		while (m.find()) {
			String match = m.group();
			//Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
			Matcher k = Pattern.compile("\"http.*?\"", Pattern.CASE_INSENSITIVE).matcher(match);
			if (k.find()) {
				String group = k.group();
				list.add(group.substring(1, group.length() - 1));
			}
		}
		return list;
	}
	
	/**
	 * html中提取文字
	 *
	 * @param str    html
	 * @param length 提取的长度
	 * @return
	 */
	public static String trimHtml(String str, int length) {
		str = str.replaceAll("/(\\n)/g", "");
		str = str.replaceAll("/(\\t)/g", "");
		str = str.replaceAll("/(\\r)/g", "");
		str = str.replaceAll("<\\/?[^>]*>", "");
		str = str.replaceAll("/\\s*/g", "");
		str = str.replaceAll("/<[^>]*>/g", "");
		str = str.replaceAll("/&nbsp;/g", " ");
		if (str.length() > length) {
			str = str.substring(0, length);
		}
		return str;
	}
	
}
