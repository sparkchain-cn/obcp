package cn.obcp.base.str;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtils {

	public static String NEWLINE = "\n";
	protected static Random random = new Random();

	/**
	 * 不够位数的在前面补0，保留num的长度位数字
	 *
	 * @param code
	 * @return
	 */
	// autoGenericCode("000",3)
	public static String codePreFillZero(String code, int num) {
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度为4
		// d 代表参数为正数型
		result = String.format("%0" + num + "d", Integer.parseInt(code) + 1);

		return result;
	}

	public static String capitalize(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}

	/**
	 * List中包括给定的字符串，区分大小写
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static boolean contain(List<String> arr, String s) {
		if (arr == null || arr.size() < 1 || isNullOrEmpty(s)) {
			return false;
		}
		for (String str : arr) {
			if (str.equals(s)) {
				return true;

			}
		}
		return false;
	}

	/**
	 * Arr中包括给定的字符串，区分大小写
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static boolean contain(String[] arr, String s) {
		if (arr == null || arr.length < 1 || isNullOrEmpty(s))
			return false;
		for (String str : arr) {
			if (str.equals(s)) {
				return true;

			}
		}
		return false;
	}

	/**
	 * s字符串包含 arr中的某个字符串
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static boolean containPre(String[] arr, String s) {
		if (arr == null || arr.length < 1 || isNullOrEmpty(s))
			return false;
		for (String str : arr) {
			if (s.startsWith(str)) {
				return true;

			}
		}
		return false;
	}

	/**
	 * arr中的某个字符串包含s字符串
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static boolean containStart(List<String> arr, String s) {
		if (arr == null || arr.size() < 1 || isNullOrEmpty(s))
			return false;
		for (String str : arr) {
			if (str.startsWith(s)) {
				return true;

			}
		}
		return false;
	}

	/**
	 * arr中的某个字符串包含s字符串
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static boolean containStart(String[] arr, String s) {
		if (arr == null || arr.length < 1 || isNullOrEmpty(s))
			return false;
		for (String str : arr) {
			if (s.startsWith(str)) {
				return true;

			}
		}
		return false;
	}

	/**
	 * 把字符串中的\\r\\n|\\r|\\n换成\n
	 *
	 * @param text
	 * @return
	 */
	public static String convertLineSep(String text) {
		return text.replaceAll("\\r\\n|\\r|\\n", "\n");

	}

	/**
	 * 把字符串中的\\r\\n|\\r|\\n换成demiliter
	 *
	 * @param text
	 * @param demiliter
	 * @return
	 */
	public static String convertLineSep(String text, String demiliter) {
		return text.replaceAll("\\r\\n|\\r|\\n", demiliter);

	}

	/**
	 * 替换字符串中特殊字符,主要用于html转换为纯文本
	 */
	public static String encodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}

	/**
	 * 还原字符串中特殊字符，主要用于纯文本转换为html
	 */
	public static String decodeString(String strData) {
		strData = replaceString(strData, "&lt;", "<");
		strData = replaceString(strData, "&gt;", ">");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "&quot;", "\"");
		strData = replaceString(strData, "&amp;", "&");
		return strData;
	}

	public static String defaultValue(String str) {
		return defaultValue(str, "");
	}

	public static String defaultValue(String str, String defalutv) {
		if (str == null)
			return defalutv;
		else
			return str;
	}

	/**
	 * 将异常信息转化成字符串*
	 *
	 * @param t
	 * @return
	 * @throws IOException
	 *
	 *
	 */

	public static String exception(Throwable t) {
		try {
			if (t == null)
				return null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				t.printStackTrace(new PrintStream(baos));
			} finally {
				baos.close();
			}
			return baos.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取异常的全部信息
	 *
	 * @param e
	 * @return
	 */
	public static String getFullErrorMessage(Throwable e) {
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] stacktrace = e.getStackTrace();
		buffer.append("Caused by: " + e + "\n");
		for (StackTraceElement tmp : stacktrace) {
			buffer.append("\tat " + tmp.toString() + "\n");
		}
		return buffer.toString();
	}

	/**
	 * 取得文件名的后缀
	 *
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		if (filename == null)
			return "";
		int index = filename.lastIndexOf(".");
		if (index > 0) {
			String ext = filename.substring(index + 1);
			return ext;
		} else {
			return "";
		}

	}

	public static String getExtDot(String filename) {
		if (filename == null)
			return "";
		int index = filename.lastIndexOf(".");
		if (index > -1) {
			String ext = filename.substring(index);
			return ext;
		} else {
			return "";
		}

	}

	/**
	 * s在Arr数组中第几个
	 *
	 * @param arr
	 * @param s
	 * @return
	 */
	public static int indexOfArr(String[] arr, String s) {
		if (arr == null || arr.length < 1 || isNullOrEmpty(s))
			return -1;
		for (int i = 0; i < arr.length; i++) {
			if (s.equals(arr[i])) {
				return i;
			}
		}
		return -1;
	}

	// 2. InputStream --> String
	public static String inputStream2NewLineString(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
				buffer.append(NEWLINE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	// 2. InputStream --> String
	public static String inputStream2NewLineString(InputStream is, String encoding) {
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is, encoding);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader in = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
				buffer.append(NEWLINE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static boolean isChanged(Object o1, Object o2) {
		String s1 = "";
		String s2 = "";
		if (o1 != null) {
			s1 = o1.toString();
		}
		if (o2 != null) {
			s2 = o2.toString();
		}
		return isChanged(s1, s2);
	}

	public static boolean isNotNullOrEmpty(String str) {
		return !isNullOrEmpty(str);
	}

	public static boolean isNullOrEmpty(String str) {

		if (str == null) {
			return true;
		}
		if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static boolean isNumeric(String text) {
		for (int i = 0; i < text.length(); i++) {
			char chr = text.charAt(i);
			if (!(chr >= '0' && chr <= '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 去掉left边的指定的trimChar
	 *
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String lTrim(String str, char trimChar) {
		int cnt = str.length();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c != trimChar) {
				cnt = i;
				break;
			}
		}
		return str.substring(cnt);
	}

	/**
	 * 去掉right边的指定的trimChar
	 *
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String rTrim(String str, char trimChar) {
		int cnt = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			char c = str.charAt(i);
			if (c != trimChar) {
				cnt = i + 1;
				break;
			}
		}
		return str.substring(0, cnt);
	}

	//

	/**
	 * 把o转换为字符串，并加上引号
	 *
	 * @param o
	 * @return
	 */
	public static String quote(Object o) {
		if (o == null) {
			return "\"\"";
		}
		return "\"" + o.toString() + "\"";

	}

	public static String singleQuote(Object o) {
		if (o == null) {
			return "\'\'";
		}
		return "\'" + o.toString() + "\'";

	}

	public static String unQuote(Object o) {
		if (o == null) {
			return null;
		}
		String vString = o.toString();
		if (isNullOrEmpty(vString))
			return null;
		String vString2 = vString.replaceAll("\"", "").replaceAll("'", "");
		return vString2;

	}

	/**
	 * 替换一个字符串中的某些指定字符
	 *
	 * @param strData     String 原始字符串
	 * @param regex       String 要替换的字符串
	 * @param replacement String 替代字符串
	 * @return String 替换后的字符串
	 */
	public static String replaceString(String strData, String regex, String replacement) {

		return replace(strData, regex, replacement);
	}

	public static String replace(String strData, String regex, String replacement) {
		if (strData == null || "".equals(strData) || regex == null || "".equals(regex)) {
			return null;
		}
		int index = strData.indexOf(regex);
		// String strNew = "";
		StringBuilder sBuilder = new StringBuilder();
		if (index >= 0) {
			while (index >= 0) {
				sBuilder.append(strData.substring(0, index));
				sBuilder.append(replacement);
				// strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			sBuilder.append(strData);
			return sBuilder.toString();
			// strNew += strData;
			// return strNew;
		}
		return strData;
	}

	public static String replace(String strData, String regex, String replacement, int pos) {
		if (strData == null || "".equals(strData) || regex == null || "".equals(regex)) {
			return strData;
		}
		int index = getPos(strData, regex, pos);
		if (index < 0) {
			return strData;
		}
		StringBuilder sb = new StringBuilder();
		// 前一部分
		sb.append(strData.substring(0, index));
		// 中间替换的
		sb.append(replacement);
		// 后面
		strData = strData.substring(index + regex.length());
		sb.append(strData);
		return sb.toString();
	}

	/**
	 * 取得指定regex出现的位置
	 *
	 * @param str
	 * @param regex
	 * @param count 从0开始
	 * @return 彭仁夔 于 2016年5月29日 上午8:42:18 创建
	 */
	public static int getPos(String str, String regex, int count) {
		Matcher slashMatcher = Pattern.compile(regex).matcher(str);
		int mIdx = 0;
		while (slashMatcher.find()) {
			if (mIdx == count) {// 当regex第count次出现的位置
				break;
			}
			mIdx++;
		}
		return slashMatcher.start();
	}

	public static List<Integer> getPos(String str, String regex) {
		Matcher slashMatcher = Pattern.compile(regex).matcher(str);
		List<Integer> list = new ArrayList<Integer>();
		while (slashMatcher.find()) {
			list.add(slashMatcher.start());
		}
		return list;
	}

	public static String replace(String strData, String regex, String replacement, List<Integer> poss) {
		if (strData == null || "".equals(strData) || regex == null || "".equals(regex)) {
			return strData;
		}
		int revomeLen = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < poss.size(); i++) {
			Integer p = poss.get(i);
			int index = p - revomeLen;
			int subIndex = index + regex.length();
			// 前一部分
			sb.append(strData.substring(0, index));
			// 中间替换的
			sb.append(replacement);
			// 后面
			strData = strData.substring(subIndex);
			revomeLen = revomeLen + subIndex;
		}
		sb.append(strData);

		return sb.toString();
	}

}
