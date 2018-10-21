package matech.utils.string;


import matech.framework.autoCode.AutoCodeUtil;
import matech.framework.pub.listener.UserSession;
import matech.utils.log.Log;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil2 {
	private static Log log=new Log(StringUtil2.class);
	private static final String UMBER_PATTERN = "^(-)?\\d+(\\.\\d+)?$";
	private static NumberFormat curformatter = new DecimalFormat("#,###,##0.00");
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final String[] fraction = { "角", "分" };
	private static final String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆",
			"柒", "捌", "玖" };
	private static final String[][] unit = { { "元", "万", "亿" },
			{ "", "拾", "佰", "仟" } };

	public static final String getToday() {
		return new java.sql.Date(System.currentTimeMillis()).toString();
	}

	public static String showNull(String str, String v) {
		return "<div nowrap>" + showNull(str) + "</div>";
	}

	public static String dealData(String n, String s) {
		if (n == null) {
			return null;
		}

		if (s == null) {
			s = "";
		}
		if (n.indexOf("showNull") != -1) {
			s = showNull(s);
		}

		if (n.indexOf("showMoney1") != -1)
			s = showMoney1(s);
		else if (n.indexOf("showMoney") != -1)
			s = showMoney(s);
		else if (n.indexOf("maxLen") != -1)
			s = maxLen(s, n);
		else if (n.indexOf("hiddenLen") != -1)
			s = hiddenLen(s, n);
		else if (n.indexOf("showDateByLong") != -1)
			s = showDateByLong(s, n);
		else if (n.indexOf("showDate") != -1)
			s = showDate(s, n);
		else if (n.indexOf("showHidden") != -1)
			s = showHidden(s);
		else if (n.indexOf("showRight") != -1)
			s = showRight(s);
		else if (n.indexOf("showCenter") != -1)
			s = showCenter(s);
		else if (n.indexOf("showLeft") != -1)
			s = showLeft(s);
		else if (n.indexOf("color") != -1)
			s = color(s, n);
		else if (n.indexOf("showPercent") != -1)
			s = showPercent(s);
		else {
			s = showLeft(s);
		}

		return s;
	}

	public static String dealData(String n, String s, String value) {
		if (n == null) {
			return null;
		}

		if (s == null) {
			s = "";
		}
		if (n.indexOf("showNull") != -1) {
			s = showNull(s);
		}

		if (n.indexOf("showMoney1") != -1)
			s = showMoney1(s);
		else if (n.indexOf("showMoney") != -1)
			s = showMoney(s);
		else if (n.indexOf("maxLen") != -1)
			s = maxLen(s, n);
		else if (n.indexOf("hiddenLen") != -1)
			s = hiddenLen(s, n);
		else if (n.indexOf("showDateByLong") != -1)
			s = showDateByLong(s, n);
		else if (n.indexOf("showDate") != -1)
			s = showDate(s, n);
		else if (n.indexOf("showHidden") != -1)
			s = showHidden(s);
		else if (n.indexOf("showRight") != -1)
			s = showRight(s);
		else if (n.indexOf("showCenter") != -1)
			s = showCenter(s);
		else if (n.indexOf("showLeft") != -1)
			s = showLeft(s);
		else if (n.indexOf("color") != -1)
			s = color(s, n);
		else if (n.indexOf("showPercent") != -1)
			s = showPercent(s);
		else {
			s = showLeft(s);
		}

		return s;
	}

	public static String showHidden(String s) {
		return "<div style=\"display:none\">" + s + "</div>";
	}

	public static String showWidth(String s, String v) {
		return "<div id=\"widthTD" + v + "\" >" + s + "</div>";
	}

	public static String showHidden(String s, String v) {
		return "<div id=\"hiddenTD" + v + "\" style=\"display:none\">" + s
				+ "</div>";
	}

	public static String showMoney2(String s) {
		return ((s == null) ? "" : s.replaceAll(",", ""));
	}

	public static String showMoney3(String s) {
		s = showNull(s);
		if (!(s.equals(""))) {
			s = curformatter.format(Double.valueOf(s).doubleValue());
		}
		return s;
	}

	public static String showMoney(String s) {
		s = showNull(s);
		if (!(s.equals(""))) {
			double d = Double.valueOf(s).doubleValue();
			s = curformatter.format(d);

			if (s.trim().indexOf("-") == 0) {
				return "<div style=\"text-align:right;color:#FF0000;\" title=\""
						+ s + "\" >" + s + "</div>";
			}

			return "<div style=\"text-align:right;color:#0000FF;\" title=\""
					+ s + "\" >" + s + "</div>";
		}

		return "<div style=\"text-align:right; color:#0000FF;\" > - </div>";
	}

	public static String showMoney1(String s) {
		s = showNull(s).trim();
		String s1 = "";

		if (!(s.equals(""))) {
			double d = Double.valueOf(s).doubleValue();
			if (d >= 0.0D)
				s1 = curformatter.format(d);
			else {
				s1 = "(" + curformatter.format(-d) + ")";
			}

			if (Math.abs(d) < 0.005D) {
				s1 = " ";
			}

			if (s.trim().indexOf("-") == 0) {
				return "<div style=\"text-align:right;color:#FF0000\" title=\""
						+ s1 + "\" >" + s1 + "</div>";
			}

			return "<div style=\"text-align:right;color:#0000FF\" title=\""
					+ s1 + "\" >" + s1 + "</div>";
		}

		return s1;
	}

	public static String showMoney(String s, int x) {
		s = showNull(s);
		if (!(s.equals(""))) {
			String temp = "";
			for (int i = 0; i < x; ++i) {
				temp = temp + "0";
			}
			NumberFormat formatter = new DecimalFormat("#,###,##0." + temp);
			double d = Double.valueOf(s).doubleValue();
			if (Math.abs(d) < 0.005D)
				s = " ";
			else {
				s = formatter.format(d);
			}

			if (s.trim().indexOf("-") == 0) {
				return "<div style=\"text-align:right;color:#FF0000\" title=\""
						+ s + "\" >" + s + "</div>";
			}

			return "<div style=\"text-align:right;color:#0000FF\" title=\"" + s
					+ "\" >" + s + "</div>";
		}

		return "";
	}

	public static String showMoney(String s, String style) {
		s = showNull(s);
		if (!(s.equals(""))) {
			s = curformatter.format(Double.valueOf(s).doubleValue());

			if (s.trim().indexOf("-") == 0) {
				return "<div style=\"text-align:right;color:#FF0000\" " + style
						+ " title=\"" + s + "\" >" + s + "</div>";
			}

			return "<div style=\"text-align:right;color:#0000FF\" " + style
					+ " title=\"" + s + "\" >" + s + "</div>";
		}

		return "<div style=\"text-align:right;color:#0000FF\" " + style
				+ "></div>";
	}

	public static String showPercent(String value) {
		NumberFormat formatter = new DecimalFormat("0.00");

		value = showNull(value);

		if ("".equals(value)) {
			return "<div style=\"text-align:right; color:#0000FF;\"> - </div>";
		}

		double number = Double.valueOf(value).doubleValue();

		value = formatter.format(number);

		if (number < 0.0D) {
			return "<div style=\"text-align:right;color:#FF0000;\" title=\""
					+ value + "%\" >" + value + "%</div>";
		}

		return "<div style=\"text-align:right;color:#0000FF;\" title=\""
				+ value + "%\">" + value + "%</div>";
	}

	public static String showPercent(double number) {
		NumberFormat formatter = new DecimalFormat("0.00");

		if (number < 0.0D) {
			return "<div style=\"text-align:right;color:#FF0000\" nowrap>"
					+ formatter.format(number) + "%</div>";
		}

		return "<div style=\"text-align:right;color:#0000FF\" nowrap>"
				+ formatter.format(number) + "%</div>";
	}

	public static String showRight(String s) {
		return "<div style=\"text-align:right;\" title=\"" + s + "\" >" + s
				+ "</div>";
	}

	public static String showLeft(String s) {
		return "<div style=\"text-align:left;\" title=\"" + s + "\" >" + s
				+ "</div>";
	}

	public static String showCenter(String s) {
		return "<div style=\"text-align:center;\" title=\"" + s + "\" >" + s
				+ "</div>";
	}

	public static String maxLen(String s, String format) {
		int i = format.indexOf("maxLen:") + 7;
		int j = format.indexOf(" ", i);
		int len = 0;
		try {
			if (j == -1) {
				j = format.length();
			}
			len = Integer.parseInt(format.substring(i, j));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "<div style=\"width:" + len + "\" > " + s + "</div>";
	}

	public static String showDate(String s, String format) {
		if ((s == null) || ("".equals(s))) {
			return s;
		}

		int i = format.indexOf("showDate:") + 9;
		int j = format.length();
		String formatString = "";

		formatString = format.substring(i, j);
		try {
			java.util.Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(s);

			return new SimpleDateFormat(formatString).format(d);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return s;
	}

	public static String showDateByLong(String s, String format) {
		if ((s == null) || ("".equals(s))) {
			return s;
		}
		try {
			long datalong = Long.parseLong(s);
			java.util.Date date = new java.util.Date(datalong);
			return dateTimeFormat.format(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return s;
	}

	public static String hiddenLen(String s, String format) {
		int i = format.indexOf("hiddenLen:") + 10;
		int j = format.indexOf(" ", i);
		int len = 0;
		try {
			if (j == -1) {
				j = format.length();
			}
			len = Integer.parseInt(format.substring(i, j));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (s.length() > len) {
			s = "<div title=\"" + s + "\">" + s.substring(0, len) + "..."
					+ "</div>";
		}

		return s;
	}

	public static String color(String s, String format) {
		int i = format.indexOf("color:") + 6;
		int j = format.indexOf(" ", i);
		if (j == -1) {
			j = format.length();
		}
		String color = format.substring(i, j);

		return s.replaceFirst("style=\\\"", "style=\"color:" + color + ";");
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		java.util.Date currentdate = new java.util.Date();
		return "" + dateformat.format(currentdate);
	}

	public static String getCurrentDate() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date currentdate = new java.util.Date();
		return "" + dateformat.format(currentdate);
	}

	public String getDateFormat() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");

		java.util.Date currentdate = new java.util.Date();
		return "" + dateformat.format(currentdate);
	}

	public static String getCurrentTime() {
		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");

		java.util.Date currenttime = new java.util.Date();
		return "" + dateformat.format(currenttime);
	}

	public static String replaceStr(String s, String s1, String s2) {
		if (s == null) {
			return null;
		}
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char[] ac = s.toCharArray();
			char[] ac1 = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;

			for (int k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}
			stringbuffer.append(ac, i, ac.length - i);
			return stringbuffer.toString();
		}
		return s;
	}

	public static String getExtension(String filename) {
		return getExtension(filename, "");
	}

	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf(46);

			if ((i > 0) && (i < filename.length() - 1)) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	public static String getMime(String filename) {
		String extname = getExtension(filename).toLowerCase();
		String Mime = "application/doc";
		if (filename != null)
			if (!(filename.equals(""))) {
				if (("html".equals(extname)) || ("htm".equals(extname)))
					Mime = "text/html";
				if (("doc".equals(extname)) || ("dot".equals(extname)))
					Mime = "application/doc";
				if (("jpeg".equals(extname)) || ("jpg".equals(extname)))
					Mime = "image/jpeg";
				if ("xsl".equals(extname)) {
					Mime = "application/vnd.ms-excel";
				}
			}
		return Mime;
	}

	public static String getFileName(String fullpathname) {
		String filename = fullpathname;
		if ((fullpathname != null) && (fullpathname.length() > 0)) {
			int i = fullpathname.lastIndexOf(47);

			if ((i > 0) && (i < fullpathname.length() - 1)) {
				filename = fullpathname.substring(i + 1);
			}

			i = fullpathname.lastIndexOf(92);
			if ((i > 0) && (i < fullpathname.length() - 1)) {
				filename = fullpathname.substring(i + 1);
			}
		}
		return filename;
	}

	public static String formatNumber(double num) {
		return curformatter.format(num);
	}

	public static String showNull(String str) {
		if ((str == null) || ("null".equalsIgnoreCase(str))) {
			return "";
		}
		return str;
	}

	public static String showNull(Object obj) {
		String str = String.valueOf(obj);
		if ((str == null) || ("null".equalsIgnoreCase(str))) {
			return "";
		}
		return str;
	}

	public static String kill1(String strIn) {
		if (strIn.length() > 0) {
			if (strIn.substring(0, 1).equals("-")) {
				return strIn.substring(1);
			}
			return strIn;
		}

		return strIn;
	}

	public static String nCharToString(char c, int n) {
		String ret = "";
		for (int i = 0; i < n; ++i) {
			ret = ret + c;
		}
		return ret;
	}

	public static String nCharToString(String c, int n) {
		String ret = "";
		for (int i = 0; i < n; ++i) {
			ret = ret + c;
		}
		return ret;
	}

	public static String killEndToken(String c, String token) {
		String result = c;
		if ((c == null) || (c.equals("")))
			return result;
		int opt = token.length();
		if (result.substring(result.length() - opt).equals(token)) {
			result = result.substring(0, result.length() - opt);
		}
		return result;
	}

	public static String getStringFromArray(String[] strArray, String strToken) {
		return killEndToken(getStringFromArray1(strArray, strToken), strToken);
	}

	public static String getStringFromArray1(String[] strArray, String strToken) {
		String strResult = "";
		if (strArray == null)
			return "";
		for (int i = 0; i < strArray.length; ++i) {
			strResult = strResult + strArray[i] + strToken;
		}
		return strResult;
	}

	public static final String[] getVaribles(String str) {
		if ((str == null) || (str.equals("")))
			return null;
		int i1 = 0;
		int i2 = 0;
		int i = 0;
		String[] _retStr = new String[str.length() / 3];

		while ((i1 = str.indexOf("${", i2)) != -1) {
			i2 = str.indexOf("}", i1);
			_retStr[(i++)] = str.substring(i1 + 2, i2);
		}
		String[] retStr = new String[i];
		for (int j = 0; j < i; ++j) {
			retStr[j] = _retStr[j];
		}
		return retStr;
	}

	public static final String[] getVaribles(String startStr, String endStr,
			String str) {
		if ((str == null) || (str.equals("")))
			return null;
		int i1 = 0;
		int i2 = 0;
		int i = 0;
		String[] _retStr = new String[str.length() / 3];

		Set set = new HashSet();
		while ((i1 = str.indexOf(startStr, i2)) != -1) {
			i2 = str.indexOf(endStr, i1);
			_retStr[(i++)] = str.substring(i1 + startStr.length(), i2);
		}

		for (int j = 0; j < i; ++j) {
			set.add(_retStr[j]);
		}

		String[] retStr = new String[set.size()];

		Iterator it = set.iterator();
		i = 0;
		while (it.hasNext()) {
			retStr[(i++)] = ((String) it.next());
		}

		return retStr;
	}

	public static String getProperty(String s, int index) {
		if (s == null)
			return "";
		if (index <= 0) {
			return "";
		}
		--index;
		int length = s.length();
		String ret = "";
		if (index <= length - 1) {
			ret = s.substring(index, index + 1);
		}
		return ret;
	}

	public static String SetProperty(String s, int index, char c) {
		--index;

		if (index < 0) {
			return s;
		}
		if (s == null) {
			s = "";
		}

		String result = "";
		int length = s.length();
		String front;
		if (index < length - 1) {
			front = s.substring(0, index);
			String end = s.substring(index + 1);
			result = front + c + end;
		}

		if (index == length - 1) {
			front = s.substring(0, index);
			result = front + c;
		}

		if (index >= length) {
			result = s + nCharToString(' ', index - length) + c;
		}
		return result;
	}

	public String GBToUTF(String strIn) {
		String strOut = null;
		if ((strIn == null) || (strIn.trim().equals("")))
			return strIn;
		try {
			byte[] b = strIn.getBytes("ISO8859_1");
			strOut = new String(b, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return strOut;
	}

	public static String code2code(String strIn, String sourceCode,
			String targetCode) {
		String strOut = null;
		if ((strIn == null) || (strIn.trim().equals("")))
			return strIn;
		try {
			byte[] b = strIn.getBytes(sourceCode);
			strOut = new String(b, targetCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return strOut;
	}

	public static String decode(String s, String enc) throws Exception {
		boolean needToChange = false;
		StringBuffer sb = new StringBuffer();
		int numChars = s.length();
		int i = 0;

		if (enc.length() == 0) {
			throw new Exception("URLDecoder: empty string enc parameter");
		}

		while (i < numChars) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				++i;
				needToChange = true;
				break;
			case '%':
				try {
					byte[] bytes = new byte[(numChars - i) / 3];
					int pos = 0;

					while ((i + 2 < numChars) && (c == '%')) {
						bytes[(pos++)] = (byte) Integer.parseInt(
								s.substring(i + 1, i + 3), 16);

						i += 3;
						if (i < numChars)
							;
						c = s.charAt(i);
					}

					if ((i < numChars) && (c == '%')) {
						throw new IllegalArgumentException(
								"URLDecoder: Incomplete trailing escape (%) pattern");
					}

					sb.append(new String(bytes, 0, pos, enc));
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
					throw new IllegalArgumentException(
							"URLDecoder: Illegal hex characters in escape (%) pattern - "
									+ e.getMessage());
				}

				needToChange = true;
				break;
			default:
				sb.append(c);
				++i;
			}

		}

		return ((needToChange) ? sb.toString() : s);
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 255)) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; ++j) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String toUtfQQ8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 255)) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; ++j) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("." + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String getOrderId(String taskCode) {
		if (("".equals(taskCode)) || (taskCode == null))
			return "";
		String orderId = "";
		String strTemp = "";

		int iStart = -1;
		int iEnd = -1;

		if ((taskCode == null) || (taskCode.equals(""))) {
			return "";
		}
		for (int i = 0; i < taskCode.length(); ++i) {
			String strCTemp = taskCode.substring(i, i + 1);
			try {
				Integer.parseInt(strCTemp);

				if (iStart == -1) {
					iStart = i;
					iEnd = i;
				} else {
					++iEnd;
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if (iStart == -1) {
					orderId = orderId + strCTemp;
				} else {
					strTemp = taskCode.substring(iStart, iEnd + 1);

					orderId = orderId + nCharToString('0', 4 - iEnd + iStart)
							+ strTemp + taskCode.substring(i, i + 1);

					iStart = iEnd = -1;
				}
			}

		}

		if (iStart >= 0) {
			strTemp = taskCode.substring(iStart, iEnd + 1);

			orderId = orderId + nCharToString('0', 4 - iEnd + iStart) + strTemp;
		}

		return orderId;
	}

	public static String getNewTaskCode(String taskCode) {
		if (("".equals(taskCode)) || (taskCode == null))
			return "";
		String strTemp = "";
		String orderid = "";
		int intTemp = 0;
		int intEnd = 0;
		intEnd = getNumericIndex(taskCode, 0);
		if (intEnd >= 0) {
			strTemp = taskCode.substring(intEnd);
			intTemp = Integer.parseInt(strTemp);
			++intTemp;
			strTemp = String.valueOf(intTemp);

			orderid = taskCode.substring(0, intEnd)
					+ nCharToString('0',
							taskCode.length() - intEnd - strTemp.length())
					+ strTemp;
		} else {
			orderid = taskCode + "1";
		}
		return orderid;
	}

	private static int getNumericIndex(String srch, int flag) {
		if (("".equals(srch)) || (srch == null)) {
			return -1;
		}
		int idx = -1;
		char temp = ' ';
		int i;
		if (flag == 0) {
			for (i = srch.length() - 1; i >= 0; --i) {
				temp = srch.charAt(i);
				if (!(Character.isDigit(temp)))
					break;
				idx = i;
			}

		} else {
			for (i = 0; i < srch.length(); ++i) {
				temp = srch.charAt(i);
				if (!(Character.isDigit(temp)))
					break;
				idx = i;
			}

		}

		return idx;
	}

	public static boolean isNumeric(String str) {
		int begin = 0;
		boolean once = true;
		if ((str == null) || (str.trim().equals(""))) {
			return false;
		}
		str = str.trim();
		if ((str.startsWith("+")) || (str.startsWith("-"))) {
			if (str.length() == 1) {
				return false;
			}
			begin = 1;
		}
		for (int i = begin; i < str.length(); ++i) {
			if (!(Character.isDigit(str.charAt(i)))) {
				if ((str.charAt(i) == '.') && (once)) {
					once = false;
				} else
					return false;

			}

		}

		return ((str.length() != begin + 1) || (once));
	}

	public static boolean isInteger(String str) {
		int begin = 0;
		if ((str == null) || (str.trim().equals(""))) {
			return false;
		}
		str = str.trim();
		if ((str.startsWith("+")) || (str.startsWith("-"))) {
			if (str.length() == 1) {
				return false;
			}
			begin = 1;
		}
		for (int i = begin; i < str.length(); ++i) {
			if (!(Character.isDigit(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumericEx(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage(), ex);
		}
		return false;
	}

	public static boolean isIntegerEx(String str) {
		str = str.trim();
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage(), ex);
			if (str.startsWith("+"))
				return isIntegerEx(str.substring(1));
		}
		return false;
	}

	public static String getStr(String Str) {
		int iS = Str.indexOf(">");
		int iE = Str.indexOf(";");
		String str = "";
		if ((iS >= 0) && (iE >= 0) && (iS < 6) && (iE < 6)) {
			if (iS < iE)
				str = Str.substring(iS + 1);
			else
				str = Str.substring(iE + 1);
		} else if ((iS >= 0) && (iS < 6))
			str = Str.substring(iS + 1);
		else if ((iE >= 0) && (iE < 6))
			str = Str.substring(iE + 1);
		else {
			str = Str;
		}
		iS = str.lastIndexOf("<");
		iE = str.lastIndexOf("&");
		if ((iS > str.length() - 6) && (iE > str.length() - 6)) {
			if (iS > iE)
				str = str.substring(0, iS);
			else
				str = str.substring(0, iE);
		} else if (iS > str.length() - 6)
			str = str.substring(0, iS);
		else if (iE > str.length() - 6) {
			str = str.substring(0, iE);
		}
		return str;
	}

	public static String getStr(String Str, String swhere) {
		int sS = Str.indexOf(swhere);
		int sE = sS + swhere.length();

		int iS = Str.indexOf(">");
		int iE = Str.indexOf(";");
		String str = "";
		if ((iS >= 0) && (iE >= 0) && (iS < sS) && (iE < sS)) {
			if (iS > iE)
				str = Str.substring(iS + 1);
			else
				str = Str.substring(iE + 1);
		} else if ((iS >= 0) && (iS < sS))
			str = Str.substring(iS + 1);
		else if ((iE >= 0) && (iE < sS))
			str = Str.substring(iE + 1);
		else {
			str = Str;
		}
		iS = str.lastIndexOf("<");
		iE = str.lastIndexOf("&");
		if ((iS > sE) && (iE > sE)) {
			if (iS > iE)
				str = str.substring(0, iS);
			else
				str = str.substring(0, iE);
		} else if (iS > sE)
			str = str.substring(0, iS);
		else if (iE > sE) {
			str = str.substring(0, iE);
		}

		return str;
	}

	public static boolean isStringContainChinese(String strIn) {
		if ((strIn == null) || ("".equals(strIn))) {
			return false;
		}

		try {
			return (strIn.length() != new String(strIn.getBytes(), "8859_1")
					.length());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static int getStrDisplayTime(String str, String token) {
		int result = 0;
		int i = -1;
		while ((i = str.indexOf(token, i + 1)) != -1) {
			++result;
		}
		return result;
	}

	public static String formatDate(String date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

			java.util.Date date2 = simpleDateFormat.parse(date);
			date = simpleDateFormat.format(date2);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return date;
	}

	public static String getStringByString(String str) {
		String strs = "";
		try {
			if (str != null) {
				str = str.replaceAll("'", "").replaceAll("\"", "");

				for (int i = 0; i < str.split(" ").length; ++i) {
					if ("".equals(str.split(" ")[i].trim()))
						continue;
					strs = strs + str.split(" ")[i] + ",";
				}

				if (strs.endsWith(",")) {
					strs = strs.substring(0, strs.length() - 1);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return strs;
	}

	public static String addBlank(String str) {
		try {
			str = str.replaceAll("<=", " #1# ").replaceAll(">=", " #2# ")
					.replaceAll("!=", " #3# ");

			str = str.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ")
					.replaceAll("=", " = ").replaceAll("<>", " <> ")
					.replaceAll("#1#", "<=").replaceAll("#2#", ">=")
					.replaceAll("#3#", "!=");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return str;
	}

	public static String setXMLData(String XML, String name, String data) {
		try {
			if ((XML == null) || (name == null)) {
				XML = "";
			}
			String XML2 = XML;

			int len1 = name.length();
			int i = XML.indexOf("<" + name + ">");
			if (i >= 0) {
				int j = XML.indexOf("</" + name + ">");
				if (j >= 0) {
					return XML.substring(0, i + len1 + 2) + data
							+ XML2.substring(j);
				}

				return XML;
			}
			return XML;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return XML;
	}

	public static String getXMLData(String XML, String name) {
		try {
			if ((XML == null) || (name == null)) {
				return "";
			}

			int len1 = name.length();
			int i = XML.indexOf("<" + name + ">");
			if (i >= 0) {
				int j = XML.indexOf("</" + name + ">");
				if (j >= 0) {
					return XML.substring(i + len1 + 2, j).trim();
				}
				return "";
			}
			return "";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "";
	}

	public static String getXMLData(String xml) {
		StringBuffer jsonString = new StringBuffer();
		try {
			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><REQUEST_XML_ROOT>"
					+ xml + "</REQUEST_XML_ROOT>";

			Document srcdoc = DocumentHelper.parseText(xmlString);
			Element root = srcdoc.getRootElement();

			List list = root.elements();

			for (int i = 0; i < list.size(); ++i) {
				Element element = (Element) list.get(i);
				jsonString.append(element.getName()).append("=")
						.append(element.getText());

				if (i != list.size() - 1)
					jsonString.append(",");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return jsonString.toString();
	}

	public static String getXMLData(String XML, String name,
			String defaultvalule) {
		try {
			if ((XML == null) || (name == null)) {
				return "";
			}

			int len1 = name.length();
			int i = XML.indexOf("<" + name + ">");
			if (i >= 0) {
				int j = XML.indexOf("</" + name + ">");
				if (j >= 0) {
					String tempStr = XML.substring(i + len1 + 2, j).trim();
					if (tempStr.equals("")) {
						return defaultvalule;
					}
					return tempStr;
				}

				return defaultvalule;
			}
			return defaultvalule;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return defaultvalule;
	}

	public static String tranExtTableTitle(String strMtTableTitle) {
		if ((strMtTableTitle == null) || ("".equals(strMtTableTitle))) {
			return "";
		}

		char[] c = strMtTableTitle.toCharArray();

		char cc = '\0';
		char oldcc = '\0';
		int iLevel = 0;
		StringBuffer result = new StringBuffer("");
		int iTokenStart = 0;
		int iColCount = 0;
		String strToken = "";
		for (int i = 0; i < c.length; ++i) {
			oldcc = cc;
			cc = c[i];

			switch (cc) {
			case ',':
				if ((iLevel == 0) && (oldcc != '}')) {
					result.append(",{}");
				}

				++iColCount;

				iTokenStart = i;
				break;
			case '{':
				++iLevel;

				iColCount = 1;

				strToken = strMtTableTitle.substring(iTokenStart + 1, i);

				break;
			case '}':
				--iLevel;
				iTokenStart = i;

				result.append(",{header: '" + strToken + "', colspan: "
						+ iColCount + ", align: 'center'}");
			}

		}

		if (cc != '}') {
			result.append(",{}");
		}
		return result.toString().substring(1);
	}

	public static synchronized String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static synchronized String getNumUnid() {
		return String.valueOf(new java.util.Date().getTime());
	}

	public static synchronized String getCurDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date());
	}

	public static synchronized String getCurDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	}

	public static synchronized String getCurTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
	}

	public static synchronized String getCurDay() {
		return new SimpleDateFormat("dd").format(new java.util.Date());
	}

	public static synchronized String getCurMonth() {
		return new SimpleDateFormat("MM").format(new java.util.Date());
	}

	public static synchronized String getCurYear() {
		return new SimpleDateFormat("yyyy").format(new java.util.Date());
	}

	public static String getOrSetDataYear(String year) {
		String dataYear = showNull(year);
		return (("".equals(dataYear)) ? getCurYear() : dataYear);
	}

	public static int[] getOrSetStartAndEndYear(String startYear,
			String endYear, int n) {
		int[] startAndEndYear = new int[2];

		if ((!("".equals(showNull(startYear))))
				&& (!("".equals(showNull(endYear))))) {
			startAndEndYear[0] = Integer.parseInt(startYear);
			startAndEndYear[1] = Integer.parseInt(endYear);
		} else {
			int curYear = Integer.parseInt(getCurYear());

			startAndEndYear[1] = curYear;
			startAndEndYear[0] = (curYear - n);
		}
		return startAndEndYear;
	}

	public static String fixPreZero(String value) {
		String result = showNull(value).trim();
		if (result.length() == 1) {
			result = "0" + result;
		}
		return result;
	}

	public static String fixPreZero(int value) {
		String result = String.valueOf(value).trim();
		if (result.length() == 1) {
			result = "0" + result;
		}
		return result;
	}

	public static String getNextValue(String value) {
		if (StringUtils.isBlank(value))
			return null;
		if (value.length() > 2) {
			String preValue = value.substring(0, value.length() - 2);
			String lastValue = value.substring(value.length() - 2,
					value.length());

			int temp = Integer.valueOf(lastValue).intValue();
			int nextValue = temp + 1;
			return preValue + fixPreZero(nextValue);
		}
		return fixPreZero(value);
	}

	public static String createTime(int hour, int minute) {
		return fixPreZero(hour) + ":" + fixPreZero(minute);
	}

	public static String createTime(String hour, String minute) {
		return fixPreZero(hour) + ":" + fixPreZero(minute);
	}

	public static String buildToString(Object obj) {
		return ToStringBuilder.reflectionToString(obj,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public static String tranStrWithSign(String oldStr) {
		if (("".equals(oldStr)) || (oldStr == null)) {
			return "' '";
		}
		String[] newStrs = oldStr.split(",");
		String returnStr = "";
		for (String newStr : newStrs) {
			returnStr = returnStr + "'" + newStr + "',";
		}
		returnStr = returnStr + "' '";
		return returnStr;
	}

	public static synchronized String getCurDateTime2() {
		return new SimpleDateFormat("yyMMddHHmmssSS")
				.format(new java.util.Date());
	}

	public static String transAutoCodeValue(String srcStr) {
		String result = srcStr;
		String[] varibles = getVaribles("autoCode{", "}", srcStr);
		String value = "";

		if (varibles != null) {
			for (int i = 0; i < varibles.length; ++i) {
				String varible = varibles[i];
				System.out.println(varible);
				try {
					value = new AutoCodeUtil().getAutoCode(varible);

					result = result.replaceAll("autoCode\\{" + varible + "\\}",
							value);
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
				}
			}

		}

		return result;
	}

	public static String transSessionValue(HttpSession session, String srcStr) {
		String result = srcStr;
		String[] varibles = getVaribles(srcStr);

		if (varibles != null) {
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			Class clazz = UserSession.class;
			String value = "";
			for (int i = 0; i < varibles.length; ++i) {
				String varible = varibles[i];
				String str;
				if (varible.indexOf("userSession.") > -1) {
					try {
						str = varible.replaceAll("userSession.", "");
						PropertyDescriptor pd = new PropertyDescriptor(str,
								clazz);

						Method getMethod = pd.getReadMethod();
						value = (String) getMethod.invoke(userSession,
								new Object[0]);
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}

					result = result.replaceAll("\\$\\{" + varible + "\\}",
							showNull(value));
				} else if (varible.indexOf("session.") > -1) {
					str = varible.replaceAll("session.", "");
					value = (String) session.getAttribute(str);
				}
			}

		}

		return result;
	}

	public static String transRequestValue(HttpServletRequest request,
			String srcStr) {
		String result = srcStr;
		String[] varibles = getVaribles(srcStr);

		if (varibles != null) {
			String value = "";
			for (int i = 0; i < varibles.length; ++i) {
				String varible = varibles[i];

				if (varible.indexOf("request.") <= -1)
					continue;
				try {
					String str = varible.replaceAll("request.", "");

					value = request.getParameter(str);
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
				}

				result = result.replaceAll("\\$\\{" + varible + "\\}",
						showNull(value));
			}

		}

		return result;
	}

	public static String transSystemValue(String srcStr) {
		String result = srcStr;
		String[] varibles = getVaribles("sys{", "}", srcStr);
		String value = "";
		if (varibles != null) {
			for (int i = 0; i < varibles.length; ++i) {
				String varible = varibles[i];

				if (varible.indexOf("curDate") > -1) {
					try {
						String format = "";

						format = varible.replaceAll("curDate_", "");
						if (("".equals(format.trim()))
								|| ("curDate".equals(format))) {
							format = "yyyy-MM-dd HH:mm:ss";
						}
						value = new SimpleDateFormat(format)
								.format(new java.util.Date());
					} catch (Exception e) {

						log.error(e.getMessage(), e);
						value = getCurDateTime();
					}

				}

				result = result.replaceAll("sys\\{" + varible + "\\}", value);
			}

		}

		return result;
	}

	public static String formatNumber(String dsc) {
		return formatNumber(Double.parseDouble(dsc), 2);
	}

	public static String formatNumber(double dsc, int length) {
		String src = String.valueOf(dsc);
		String tmp = null;
		if (length > 0) {
			double multiple = 1.0D;
			for (int i = 0; i < length; ++i) {
				multiple *= 10.0D;
			}
			tmp = String.valueOf(Math.round(dsc * multiple) / multiple);
			int index = src.indexOf(".");
			int decLength = src.length() - index - 1;
			if (length > decLength) {
				for (int i = 0; i < length - decLength; ++i)
					tmp = tmp + "0";
			}
		} else if (length < 0) {
			String zero = "";
			for (int i = 0; i < -length; ++i) {
				zero = zero + "0";
				dsc = Math.round(dsc * 0.1D);
			}
			tmp = String.valueOf(dsc).replaceFirst("\\.\\d*", zero);
		} else {
			tmp = String.valueOf(Math.round(dsc));
		}
		return tmp;
	}

	public static String replaceNewLine(String in) {
		if (in == null) {
			return null;
		}

		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3D));
		for (int index = 0; index < len; ++index) {
			char ch = input[index];
			if (ch == '\n')
				out.append("<br>");
			else if (ch == ' ')
				out.append("&nbsp;");
			else {
				out.append(ch);
			}
		}

		return out.toString();
	}

	public static String convertIso8859ToGb2312(String in) {
		String out = null;
		try {
			byte[] ins = in.getBytes("iso-8859-1");
			out = new String(ins, "gb2312");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return out;
	}

	public static String convertGb2312ToIso8859(String in) {
		String out = null;
		try {
			byte[] ins = in.getBytes("gb2312");
			out = new String(ins, "iso-8859-1");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return out;
	}

	public static String getShortString(String str, int length) {
		String result;
		if (str.length() > length)
			result = str.substring(0, length) + "...";
		else {
			result = str;
		}
		return result;
	}

	public static String convertNullToString(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static boolean checkEmailIsValid(String email) {
		boolean isok = false;

		if ((email.equals("")) || (email == "") || (email == null)) {
			isok = false;
		}
		for (int i = 1; i < email.length(); ++i) {
			char s = email.charAt(i);
			if (s == '@') {
				isok = true;
				break;
			}
		}

		return isok;
	}

	public static String replace(String str, String pattern, String replace) {
		if (replace == null) {
			replace = "";
		}
		int s = 0;
		int e = 0;

		StringBuffer result = new StringBuffer(str.length() * 2);
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static boolean isNumber(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		String sStr = "";
		int m = 0;
		m = str.indexOf(".");

		for (int j = 0; j < str.length(); ++j) {
			if (m != j) {
				sStr = sStr + str.charAt(j);
			}
		}
		byte[] btyeStr = sStr.getBytes();
		for (int i = 0; i < btyeStr.length; ++i) {
			if ((btyeStr[i] < 48) || (btyeStr[i] > 57)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAllNumber(String str) {
		return str.matches("\\d*");
	}

	public static int strToInt(String str) {
		int i = 0;
		if ((str != null) && (str.length() != 0)) {
			try {
				i = Integer.parseInt(str.trim());
			} catch (NumberFormatException nfe) {
				log.error(nfe.getMessage(), nfe);
			}
		}
		return i;
	}

	public static double strToDouble(String str) {
		double i = 0.0D;
		if ((str != null) && (str.length() != 0)) {
			try {
				i = Double.parseDouble(str.trim());
			} catch (NumberFormatException nfe) {
				log.error(nfe.getMessage(), nfe);
			}
		}
		return i;
	}

	public static final String separateMoney(String moneyValue) {
		if ((moneyValue == null) || (moneyValue.trim().equals(""))) {
			return "";
		}
		String prefix = (moneyValue.indexOf("-") >= 0) ? "-" : "";
		String tempMoney = (moneyValue.indexOf(".") > 0) ? moneyValue
				.substring(0, moneyValue.indexOf(".")) : moneyValue;

		if (!(prefix.equals(""))) {
			tempMoney = tempMoney.substring(1);
		}

		String retValue = "";

		int i = 0;
		i = tempMoney.length();

		String tmp = tempMoney.substring((i - 3 >= 0) ? i - 3 : 0, i);

		while (!(tmp.equals(""))) {
			if (tmp.length() == 3)
				retValue = "," + tmp + retValue;
			else {
				retValue = tmp + retValue;
			}
			i = (i - 3 >= 0) ? i - 3 : 0;

			tmp = tempMoney.substring((i - 3 >= 0) ? i - 3 : 0, i);
		}

		if (retValue.charAt(0) == ',') {
			retValue = retValue.substring(1);
		}
		retValue = (moneyValue.indexOf(".") > 0) ? retValue + "."
				+ moneyValue.substring(moneyValue.indexOf(".") + 1) : retValue;

		if (!(prefix.equals(""))) {
			retValue = prefix + retValue;
		}
		return retValue;
	}

	public static final String comboIdStr(String[] fid) {
		String IdStr = "";
		for (int i = 0; i < fid.length; ++i) {
			IdStr = IdStr + fid[i];

			if (i != fid.length - 1)
				IdStr = IdStr + ",";
		}
		return IdStr;
	}

	public static final String getTreeLevelValue(String oldValue,
			String separateChar, String replaceStr) {
		String[] spStr = oldValue.split(separateChar);
		String tmp = "";

		for (int i = 0; i < spStr.length - 1; ++i) {
			tmp = tmp + replaceStr;
		}

		return tmp;
	}

	public static String getStrFromStr(String s, String s1, String s2) {
		String s3 = "";
		if ((s == null) || (s1 == null) || (s2 == null) || (s.length() < 1)
				|| (s1.length() < 1) || (s1.length() > s.length())
				|| (s1.equals(s2)) || (s.indexOf(s1) == -1)) {
			s3 = s;
		} else {
			int i = s.indexOf(s1);
			int j = s.length();
			String s4 = s.substring(0, i);
			String s5 = s.substring(i + s1.length(), j);
			s3 = s4 + s2;
			if (s5.indexOf(s1) != -1)
				s3 = s3 + getStrFromStr(s5, s1, s2);
			else {
				s3 = s3 + s5;
			}
		}
		return s3;
	}

	public static String filter(String s) {
		StringBuffer stringbuffer = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else if (c == '"')
				stringbuffer.append("&quot;");
			else if (c == '&')
				stringbuffer.append("&amp;");
			else if (c == '\n')
				stringbuffer.append("<br>");
			else {
				stringbuffer.append(c);
			}
		}

		return stringbuffer.toString();
	}

	public static String UnFilter(String s) {
		String s1 = "";
		try {
			s1 = getStrFromStr(s, "&nbsp;", " ");
			s1 = getStrFromStr(s1, "<br>", "\n");
			s1 = getStrFromStr(s1, "&amp;", "&");
			s1 = getStrFromStr(s1, "&quot;", "\"");
			s1 = getStrFromStr(s1, "&gt;", ">");
			s1 = getStrFromStr(s1, "&lt;", "<");
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
			s1 = null;
		}
		return s1;
	}

	public static String checkNull(String obj, String rtobj) {
		String s;
		if (obj == null)
			s = rtobj;
		else {
			s = obj.trim();
		}
		return s;
	}

	public static String getColor() {
		String colors = "#";
		for (int j = 0; j < 3; ++j) {
			int r = (int) (Math.random() * 16.0D + 1.0D);
			switch (r) {
			case 16:
				colors = colors + "00";
				break;
			case 15:
				colors = colors + "FF";
				break;
			case 14:
				colors = colors + "EE";
				break;
			case 13:
				colors = colors + "DD";
				break;
			case 12:
				colors = colors + "CC";
				break;
			case 11:
				colors = colors + "BB";
				break;
			case 10:
				colors = colors + "AA";
			}

			if ((r <= 9) && (r >= 0))
				colors = colors + String.valueOf(r) + String.valueOf(r);
		}
		return colors;
	}

	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; ++i) {
			++len;
			if (!(isLetter(c[i]))) {
				++len;
			}
		}
		return len;
	}

	public static boolean isLetter(char c) {
		int k = 128;
		return (c / k == 0);
	}

	public static String substring(String origin, int len, String c) {
		if ((origin == null) || (origin.equals("")) || (len < 1))
			return "";
		byte[] strByte = new byte[len];
		if (len > length(origin))
			return origin + c;
		try {
			System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
			int count = 0;
			for (int i = 0; i < len; ++i) {
				int value = strByte[i];
				if (value < 0) {
					++count;
				}
			}
			if (count % 2 != 0) {
				len = (len == 1) ? ++len : --len;
			}
			return new String(strByte, 0, len, "GBK") + c;
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static String doCreateRandomCode(int randLength) {
		String radStr = "8702461359";
		StringBuffer reVal = new StringBuffer();
		Random rand = new Random();

		for (int i = 0; i < randLength; ++i) {
			int randNum = rand.nextInt(radStr.length());
			reVal.append(radStr.substring(randNum, randNum + 1));
		}
		return reVal.toString();
	}

	public static int getRandomZeroOne() {
		return ((int) (Math.random() * 10.0D) % 2);
	}

	public static String hiddenMidString(String str, int leftStrInt,
			int midStrInt, String midStr, int rightStrInt) {
		StringBuffer reStr = new StringBuffer();
		if ((str == null) || (str.length() == 0))
			return "";
		if (str.length() == 2) {
			reStr.append(midStr + str.substring(1, 2));
		} else {
			int len = str.length();
			if (len > leftStrInt) {
				reStr.append(str.substring(0, leftStrInt));
			}
			for (int i = 0; i < midStrInt; ++i) {
				reStr.append(midStr);
			}
			if (len > rightStrInt) {
				reStr.append(str.subSequence(str.length() - rightStrInt,
						str.length()));
			}
		}

		return reStr.toString();
	}

	public static String stringToMonth(String str) {
		return str.substring(0, str.length() - 2) + "-"
				+ str.substring(str.length() - 2, str.length());
	}

	public static String stringToDay(String str) {
		return str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
				+ str.substring(6, 8);
	}

	public static String replaceNullObject(Object str) {
		if (str == null) {
			return "";
		}

		return str.toString();
	}

	public static String replaceNullObject(Object str, String toStr) {
		if ((str == null) || (str.equals(""))) {
			return toStr;
		}

		return str.toString();
	}

	public static String appendSpace(String src, int length) {
		return append(' ', src, length);
	}

	public static String append(char aChar, String src, int length) {
		int srcByteLength = 0;
		if (src == null)
			src = "";
		try {
			srcByteLength = src.getBytes("gb2312").length;
			if (srcByteLength >= length) {
				byte[] dest = new byte[length];
				System.arraycopy(src.getBytes("gb2312"), 0, dest, 0, length);
				return new String(dest);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		StringBuffer sb = new StringBuffer(src);
		for (int i = 0; i < length - srcByteLength; ++i) {
			sb.append(aChar);
		}
		return sb.toString();
	}

	public static byte[] intToByteArray(int res) throws Exception {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xFF);
		targets[1] = (byte) (res >> 8 & 0xFF);
		targets[2] = (byte) (res >> 16 & 0xFF);
		targets[3] = (byte) (res >>> 24);
		return targets;
	}

	public static int ByteArrayToInt(byte[] res) throws Exception {
		int targets = res[0] & 0xFF | res[1] << 8 & 0xFF00 | res[2] << 24 >>> 8
				| res[3] << 24;

		return targets;
	}

	public static String replaceXMLSpecialChar(String xml) {
		xml = xml.replace("&", "&#x26;");
		xml = xml.replace("'", "&#x27;");
		xml = xml.replace("<", "&#x3c;");
		xml = xml.replace(">", "&#x3e;");
		xml = xml.replace("\"", "&#x22;");
		return xml;
	}

	public static String filterSQLStr(Object sql) {
		sql = String.valueOf(sql).replace("'", "''");
		return String.valueOf(sql);
	}

	public static String RMBToUpper(double n) {
		String head = (n < 0.0D) ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; ++i) {
			s = s
					+ new StringBuilder()
							.append(digit[(int) (Math.floor(n * 10.0D
									* Math.pow(10.0D, i)) % 10.0D)])
							.append(fraction[i]).toString()
							.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; (i < unit[0].length) && (integerPart > 0); ++i) {
			String p = "";
			for (int j = 0; (j < unit[1].length) && (n > 0.0D); ++j) {
				p = digit[(integerPart % 10)] + unit[1][j] + p;
				integerPart /= 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	public static String RMBToUpper(String amount) {
		return RMBToUpper(Double.valueOf(amount).doubleValue());
	}
}
