package matech.utils.string;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import matech.utils.date.DateUtils;
import matech.utils.log.Log;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *
 * 字符串处理函数
 *
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author MATECH
 * @version 1.0
 */
public class StringUtil {
    private static Log log=new Log(StringUtil.class);
    //数字格式化对象
    private static NumberFormat curformatter = new DecimalFormat("#,###,##0.00");

    // 该类不能外部实例化
    private StringUtil() {

    }
    /**
     * 全球唯一ID
     * @return
     */
    public static synchronized String getUUID() {
        return UUID.randomUUID().toString();
    }
    /**
     * 处理null值为空
     *
     * @String 字符串
     * @return String
     */
    public static String showNull(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        } else {
            return str.trim();
        }
    }
    /**
     *
     * 获取Clob字段的值
     *
     */
    public static String getClobValue(Clob clob)throws SQLException {
		/*
		String value =  "";
		char[] content=new char[1000];
	    //byte用空值进行初始化
		for(int index=0;index<1000;index++){
			content[index]=0x20;
		}
		if(clob != null) {
			try {
				Reader is = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(is);
				while(br.read(content)>0){
					value=value+new String(content);
				    //byte用空值进行初始化
					for(int index=0;index<1000;index++){
						content[index]=0x20;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;*/
        if (clob!=null){
            String result=clob.getSubString(1L,(int) clob.length());
            return result;
        }else{
            return "";
        }
    }
    /**
     *
     * 获取blob字段的值
     *
     */
    public static String getBlobValue(byte[] blob,String charCode) {
        String value =  "";

        if(blob != null) {
            try {
                value=new String(blob,charCode);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }

        return value;
    }
    /**
     *
     * 获取blob字段的值
     *
     */
    public static String getBlobValue(byte[] blob) {
        String value =  "";

        if(blob != null) {
            try {
                value=new String(blob);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }

        return value;
    }
    /**
     * 处理null值为空
     *
     * @String 字符串
     * @return String
     */
    public static String showNull(Object str) {
        if (str == null || "null".equalsIgnoreCase(str.toString())) {
            return "";
        } else {
            return str.toString();
        }
    }
    /**
     * 如果对象为null，替换为toStr
     *
     * @param str
     * @param toStr
     * @return
     */
    public static String replaceNullObject(Object str, String toStr) {
        if (str == null || str.equals("")) {
            return toStr;
        }

        return str.toString();
    }
    /**
     * 去除前面的token字符
     * @return String
     */
    public static String killStartToken(String c, String token) {
        String result = c;
        if (c == null || c.equals(""))
            return result;
        int opt = token.length();
        if (result.substring(0,opt).equals(token)) {
            result = result.substring(opt);
        }
        return result;
    }
    /**
     * 去除后面的token字符
     * @return String
     */
    public static String killEndToken(String c, String token) {
        String result = c;
        if (c == null || c.equals(""))
            return result;
        int opt = token.length();
        if (result.substring(result.length() - opt).equals(token)) {
            result = result.substring(0, result.length() - opt);
        }
        return result;
    }
    /**
     *
     * 获得token在str出现的字数
     */
    public static int getStrDisplayTime(String str, String token) {
        int result = 0;
        int i = -1;
        while ((i = str.indexOf(token, i + 1)) != -1) {
            result++;
        }
        return result;
    }
    /**
     *
     * 当值小于10时，补充前导0 如：09
     * @return String
     */
    public static String fixPreZero(Object value){
        String result = showNull(String.valueOf(value)).trim();
        if(result.length() == 1){
            result="0" + result;
        }
        return result;
    }
    /**
     *
     * 格式化显示数字
     *
     * @String 数字字符串
     * @return String
     */
    public static String formatNumber(NumberFormat format,Object num) {
        if (num != null ) {
            return format.format(num);
        } else {
            return "";
        }
    }
    /**
     *
     * 格式化显示数字
     *
     * @String 数字字符串
     * @return String
     */
    public static String formatNumber(String num) {
        if (num != null ) {
            return formatNumber(curformatter,num);
        } else {
            return "";
        }
    }
    /**
     *
     * 格式化显示数字
     *
     * @String 数字字符串
     * @return String
     */
    public static String formatNumber(double num) {
        return formatNumber(curformatter,num);
    }
    /**
     * 产生随机字符串
     *
     * @return
     */
    public static String createRandomString() {
        String random = null;
        // 产生随机字符串
        random = RandomStringUtils.randomAlphabetic(10);
        // 随机字符串再加上当前的日期时间 long
        random += DateUtils.getNowTimestamp();
        return random;
    }
    /**
     * 取得特定长度的字符串
     *
     * @param str
     *            待处理的字符串
     * @param length
     *            截取字符串的长度
     * @return 处理后的字符串
     */
    public static String getShortString(String str, int length) {
        String result;

        if (str.length() > length) {
            result = str.substring(0, length) + "...";
        } else {
            result = str;
        }
        return result;
    }
    /**
     * 替换字符串某些字符操作
     *
     * @param str
     *            原始的字符串 例如：bluesunny
     * @param pattern
     *            配备的字符 例如：blue
     * @param replace
     *            替换为的字符 例如：green
     * @return 返回处理结果 例如：greensunny
     */
    public static String replace(String str, String pattern, String replace) {

        if (replace == null) {
            replace = "";
        }
        int s = 0, e = 0;

        StringBuffer result = new StringBuffer((int) str.length() * 2);
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    /**
     *  字符串替换 s 搜索字符串 s1 要查找字符串 s2 要替换字符串
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    public final static String replaceStr(String s, String s1, String s2) {
        if (s == null) {
            return null;
        }
        int i = 0;
        if ((i = s.indexOf(s1, i)) >= 0) {
            char ac[] = s.toCharArray();
            char ac1[] = s2.toCharArray();
            int j = s1.length();
            StringBuffer stringbuffer = new StringBuffer(ac.length);
            stringbuffer.append(ac, 0, i).append(ac1);
            i += j;
            int k;
            for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
                stringbuffer.append(ac, k, i - k).append(ac1);
                i += j;
            }
            stringbuffer.append(ac, k, ac.length - k);
            return stringbuffer.toString();
        } else {
            return s;
        }
    }
    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     *
     *
     * @param origin
     *            原始字符串
     * @param len
     *            截取长度(一个汉字长度按2算的)
     * @param c
     *            后缀
     * @return 返回的字符串
     */
    public static String substring(String origin, int len, String c) {
        if (origin == null || origin.equals("") || len < 1){
            return "";
        }
        byte[] strByte = new byte[len];
        if (len > length(origin)){
            return origin + c;
        }
        try {
            System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
            int count = 0;
            for (int i = 0; i < len; i++) {
                int value = (int) strByte[i];
                if (value < 0) {
                    count++;
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
    /**
     * 在src上追加空格字符直到src的长度为length.如果src的不小于length，刚不作操作直接返回 注意src长度按字节算，字节编码为
     * gb2312
     *
     * @param src
     * @param length
     * @return
     */
    public static String appendSpace(String src, int length) {
        return append(' ', src, length);
    }

    /**
     * 在src上追加字符aChar直到src的长度为length。如果src的不小于length，刚不作操作直接返回 注意src长度按字节算，字节编码为
     * gb2312
     *
     * @param aChar
     * @param src
     * @param length
     */
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
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer(src);
        for (int i = 0; i < length - srcByteLength; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }
    /**
     * 将n个字符c组装为一个字符串String
     * @param c
     * @param n
     * @return
     */
    public static String nCharToString(char c, int n) {
        String ret = "";
        for (int i = 0; i < n; i++) {
            ret += c;
        }
        return ret;
    }

    /**
     * n个字符串组装成新的字符串
     * @param c
     * @param n
     * @return
     */
    public static String nCharToString(String c, int n) {
        String ret = "";
        for (int i = 0; i < n; i++) {
            ret += c;
        }
        return ret;
    }
    /**
     * 把字符串数组拼装成一个长字符串，之间用STRTOKEN分隔， 请注意，
     * getStringFromArray：会把最后多出来的STRTOKEN截掉，比如1,2,3
     * getStringFromArray：会把最后多出来的STRTOKEN截掉，比如1,2,3,
     *
     * @param strArray
     *            String[]
     * @param strToken
     *            String
     * @return String
     */
    public static String getStringFromArray(String[] strArray, String strToken) {
        return killEndToken(getStringFromArray1(strArray, strToken), strToken);
    }

    /**
     * 把字符串数组拼装成一个长字符串，之间用STRTOKEN分隔， 请注意，
     * getStringFromArray：会把最后多出来的STRTOKEN截掉，比如1,2,3
     * getStringFromArray：会把最后多出来的STRTOKEN截掉，比如1,2,3,
     *
     * @param strArray
     *            String[]
     * @param strToken
     *            String
     * @return String
     */
    public static String getStringFromArray1(String[] strArray, String strToken) {
        String strResult = "";
        if (strArray == null)
            return "";
        for (int i = 0; i < strArray.length; i++) {
            strResult += strArray[i] + strToken;
        }
        return strResult;
    }
    /**
     * 数据库的PROPERTY字段是按照位进行控制，比如第一位控制是否显示，
     * 第二位控制是否允许为空等；为了方便大家操纵数据库表PROPERTY字段，设立本函数 实现读取指定位置的位值
     * 注意:目前是按一位控制，位与位之间没有间隔符
     *
     * @param s
     *            Property字段的初始值
     * @param index
     *            设置第几位
     * @return char 读取的Property相应位置的值;注意返回-1表示INDEX出错； 例如：
     *
     */
    public static String getProperty(String s, int index) {
        if(s == null){
            return "";}
        if (index <= 0){
            return "";
        }
        // 一般习惯是从1到5，而不是JAVA的从0到4，这里作一个替换；
        index--;
        int length = s.length();
        String ret = "";
        if (index <= length - 1) {
            ret = s.substring(index, index + 1);
        }
        return ret;
    }

    /**
     * 数据库的PROPERTY字段是按照位进行控制，比如第一位控制是否显示，
     * 第二位控制是否允许为空等；为了方便大家操纵数据库表PROPERTY字段，设立本函数 实现设置指定位置的位值
     * 注意:目前是按一位控制，位与位之间没有间隔符
     *
     * @param s
     *            Property字段的初始值
     * @param index
     *            设置第几位
     * @param c
     *            需要设置的值
     * @return String 设置完毕的Property字段; 例如：
     *
     */
    public static String SetProperty(String s, int index, char c) {
        index--;
        // 如果index值非法，则返回初始Property;
        if(index < 0){
            return s;
        }
        if (s == null) {
            s = "";
        }

        String result = "";
        int length = s.length();

        // 如果index值<当前Property长度，则修改制定串
        if (index < length - 1) {
            String front = s.substring(0, index);
            String end = s.substring(index + 1);
            result = front + c + end;
        }

        // 如果 index值=当前Property长度，则修改末尾值
        if (index == length - 1) {
            String front = s.substring(0, index);
            result = front + c;
        }

        // 如果index超出当前Property长度，则中间补充空格
        if (index >= length) {
            result = s + nCharToString(' ', index - length) + c;
        }
        return result;
    }

    /**
     * 字符串格式转换函数，将字符串由ISO8859_1转换为UTF-8
     *
     * @param strIn
     * @return
     */
    public String GBToUTF(String strIn) {
        String strOut = null;
        if (strIn == null || (strIn.trim()).equals(""))
            return strIn;
        try {
            byte[] b = strIn.getBytes("ISO8859_1");
            strOut = new String(b, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
        return strOut;
    }

    /**
     *
     * @param strIn
     *            源字符串
     * @param sourceCode
     *            源字符串的编码.比如"ISO8859_1"
     * @param targetCode
     *            目标字符串的编码，比如"UTF-8"
     * @return 目标字符串
     */
    public static String code2code(String strIn, String sourceCode,
                                   String targetCode) {
        String strOut = null;
        if(strIn == null || (strIn.trim()).equals("")){
            return strIn;}
        try {
            byte[] b = strIn.getBytes(sourceCode);
            strOut = new String(b, targetCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
        return strOut;
    }

    /**
     * 将UTF-8的编码汉字转换回来 反函数是
     *
     * @param s
     * @param enc
     * @return
     * @throws java.lang.Exception
     */
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
                    i++;
                    needToChange = true;
                    break;
                case '%':

                    /*
                     * Starting with this instance of %, process all consecutive
                     * substrings of the form %xy. Each substring %xy will yield a
                     * byte. Convert all consecutive bytes obtained this way to
                     * whatever character(s) they represent in the provided
                     * encoding.
                     */

                    try {

                        // (numChars-i)/3 is an upper bound for the number
                        // of remaining bytes
                        byte[] bytes = new byte[(numChars - i) / 3];
                        int pos = 0;

                        while (((i + 2) < numChars) && (c == '%')) {
                            bytes[pos++] = (byte) Integer.parseInt(s.substring(
                                    i + 1, i + 3), 16);
                            i += 3;
                            if(i < numChars){
                                c = s.charAt(i);  
                            }
                               
                        }

                        // A trailing, incomplete byte encoding such as
                        // "%x" will cause an exception to be thrown

                        if ((i < numChars) && (c == '%')){
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
                    i++;
                    break;
            }
        }

        return (needToChange ? sb.toString() : s);
    }

    /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名. 纵横软件制作中心雨亦奇2003.08.01
     *
     * @param s
     *            原文件名
     * @return 重新编码后的文件名
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    ex.printStackTrace();
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将文件名中的汉字转为类似UTF8编码的串,以便上传和下载时能正确访问而不被程序错误转换. 类似将%转变成了.号，以方便文件存放。
     *
     * @param s
     *            原文件名
     * @return 重新编码后的文件名
     */
    public static String toUtfQQ8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    ex.printStackTrace();
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("." + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    /**
     * 把字符串的字符集从ISO转换为gb2312
     *
     * @param in
     *            输入的ISO字符串
     * @return GB2312字符串
     */
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

    /**
     * 把字符串的字符集从GB2312转换为ISO
     *
     * @param in
     *            输入的GB2312字符串
     * @return ISO字符串
     */
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
    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c
     *            需要判断的字符
     * @return 返回true,Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }
    /**
     * 通过正则表达式判断字符是否是纯数字
     *
     * @param str
     *            待判断字符
     * @return true 纯数字，false 非纯数字
     */
    public static boolean isAllNumber(String str) {
        return str.matches("\\d*");
    }
    /**
     * 判断一个字符串是否数字字符串的函数 support Numeric format:<br>
     * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        int begin = 0;
        boolean once = true;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                if (str.charAt(i) == '.' && once) {
                    // '.' can only once
                    once = false;
                } else {
                    return false;
                }
            }
        }
        if (str.length() == (begin + 1) && !once) {
            // "." "+." "-."
            return false;
        }
        return true;
    }

    /**
     * 判断是不是整数字符串的函数 support Integer format:<br>
     * "33" "003300" "+33" " -0000 "
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isInteger(String str) {
        int begin = 0;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否数字字符串的函数，使用异常来完成 use Exception support Numeric format:<br>
     * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isNumericEx(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 判断是否整数的字符串，使用异常完成 use Exception support less than 11 digits(<11)<br>
     * support Integer format:<br>
     * "33" "003300" "+33" " -0000 " "+ 000"
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isIntegerEx(String str) {
        str = str.trim();
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            log.error(ex.getMessage(), ex);
            if (str.startsWith("+")) {
                return isIntegerEx(str.substring(1));
            }
            return false;
        }
    }
    /**
     * 判断字符串是否包含中文
     *
     * @param strIn
     * @return
     */
    public static boolean isStringContainChinese(String strIn) {
        if (strIn == null || "".equals(strIn)) {
            return false;
        }
        try {
            if (strIn.length() == (new String(strIn.getBytes(), "8859_1")).length()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    /**
     *
     * 获得下一个值,最后两位数字加1
     *
     * @return String
     */
    public static String getNextValue(String value){
        if(StringUtils.isBlank(value)) return null;
        if(value.length()>2){
            String preValue = value.substring(0, value.length()-2);
            String lastValue = value.substring(value.length()-2, value.length());
            int temp = Integer.valueOf(lastValue);
            int nextValue = temp+1;
            return preValue + fixPreZero(nextValue);
        }else{
            return fixPreZero(value);
        }
    }
    /**
     *
     * 显示对象中的所有属性及属性值
     *
     * @param  obj
     * @return String
     */
    public static String buildToString(Object obj){
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     *	将字符串(1,2,3)转换成('1','2','3') 
     *
     * @param oldStr 待转换字符串
     * @return String 转换后字符串
     */
    public static String tranStrWithSign(String oldStr){
        if("".equals(oldStr)||oldStr==null){
            return "' '";
        }
        String[] newStrs=oldStr.split(",");
        String returnStr="";
        for(String newStr:newStrs){
            returnStr=returnStr+"'"+newStr+"',";
        }
        returnStr=returnStr+"' '";//必须为空格
        return returnStr;
    }

    /**
     * 处理查询参数
     *
     */
    public static String processParameter(String oraStr){
        String newStr=oraStr;
        if(newStr==null||"".equals(newStr)){
            return newStr;
        }

        //CURRENTYEAR:当前年份
        //CURRENTMONTH:当前月份
        //CURRENTDAY:当前天数
        //DYNAMICYEAR:动态年份：更加月份变化，如果是1月则为当前年份减1，否则为当前年份
        Calendar currenRq=Calendar.getInstance();
        int currentYear=currenRq.get(Calendar.YEAR);
        int currentMonth=currenRq.get(Calendar.MONTH)+1;
        int currentDay=currenRq.get(Calendar.DAY_OF_MONTH);
        int dynamicYear=currentYear;
        int dynamicMonth=currentMonth;

        //BEFOREYEAR:去年
        //BEFOREMONTH:上月
        int beforeYear=currentYear;
        int beforeMonth=currentMonth;
        if(currentMonth==1){
            beforeYear=currentYear-1;
            beforeMonth=12;
        }else{
            beforeMonth=currentMonth-1;
        }

        if(currentDay==1 && currenRq.get(Calendar.HOUR_OF_DAY)<9){
            dynamicMonth=beforeMonth;
        }
        if(dynamicMonth==12){
            dynamicYear=currentYear-1;
        }

        newStr = newStr.replaceAll("\\{CURYEAR}", String.valueOf(currentYear));//当前年份
        newStr = newStr.replaceAll("\\{DATAYEAR}", String.valueOf(currentYear));//当前年份
        newStr = newStr.replaceAll("\\{CURMONTH}","00".substring(String.valueOf(currentMonth).length())+String.valueOf(currentMonth));//当前月份
        newStr = newStr.replaceAll("\\{DATAMONTH}","00".substring(String.valueOf(currentMonth).length())+String.valueOf(currentMonth));//当前月份
        newStr = newStr.replaceAll("\\{CURDAY}", String.valueOf(currentDay));//当前日期
        newStr = newStr.replaceAll("\\{DYNYEAR}", String.valueOf(dynamicYear));//动态年份，结合上月使用，如果好上月是去年则为去年否则为今年
        newStr = newStr.replaceAll("\\{PREYEAR}", String.valueOf(beforeYear));//上一年
        newStr = newStr.replaceAll("\\{PREMONTH}", "00".substring(String.valueOf(beforeMonth).length())+String.valueOf(beforeMonth));//上一月
        newStr = newStr.replaceAll("\\{DYNMONTH}", "00".substring(String.valueOf(dynamicMonth).length())+String.valueOf(dynamicMonth));//动态月份：每月1号9点前采集则月份为上月

        return newStr;
    }
    /**
     * 使用千分分隔符将金额分隔开(方海峰加)
     *
     * @param moneyValue
     * @return
     */
    public static final String separateMoney(String moneyValue) {
        if (moneyValue == null || moneyValue.trim().equals(""))
            return "";
        else {
            String prefix = moneyValue.indexOf("-") >= 0 ? "-" : "";
            String tempMoney = moneyValue.indexOf(".") > 0 ? moneyValue
                    .substring(0, moneyValue.indexOf(".")) : moneyValue;

            if (!prefix.equals(""))
                tempMoney = tempMoney.substring(1);

            String retValue = "";

            int i = 0;
            i = tempMoney.length();

            String tmp = tempMoney.substring((i - 3 >= 0 ? i - 3 : 0), i);

            while (!tmp.equals("")) {
                if(tmp.length() == 3){
                    retValue = "," + tmp + retValue;   
                }
                else{
                    retValue = tmp + retValue;  
                }
                 

                i = i - 3 >= 0 ? i - 3 : 0;

                tmp = tempMoney.substring((i - 3 >= 0 ? i - 3 : 0), i);
            }
            if (retValue.charAt(0) == ',') {
                retValue = retValue.substring(1);
            }
            retValue = moneyValue.indexOf(".") > 0 ? retValue + "."
                    + moneyValue.substring(moneyValue.indexOf(".") + 1)
                    : retValue;

            if (!prefix.equals("")){
                retValue = prefix + retValue;
            }
                

            return retValue;
        }

    }
    /**
     * 取随机颜色值
     *
     * @return
     */
    public static String getColor() {
        String colors = "#";
        for (int j = 0; j < 3; j++) {
            int r = (int) (Math.random() * 16 + 1);
            switch (r) {
                case 16:
                    colors += "00";
                    break;
                case 15:
                    colors += "FF";
                    break;
                case 14:
                    colors += "EE";
                    break;
                case 13:
                    colors += "DD";
                    break;
                case 12:
                    colors += "CC";
                    break;
                case 11:
                    colors += "BB";
                    break;
                case 10:
                    colors += "AA";
                    break;
            }
            if (r <= 9 && r >= 0)
                colors += String.valueOf(r) + String.valueOf(r);
        }
        return colors;
    }
    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s
     *            需要得到长度的字符串
     * @return i得到的字符串长度
     */
    public static int length(String s) {
        if(s == null){
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 整数转换为字节数组
     * @param res
     * @return
     * @throws Exception
     */
    public static byte[] intToByteArray(int res) throws Exception {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    /**
     * 字节数组转换为整数
     * @param res
     * @return
     * @throws Exception
     */
    public static int ByteArrayToInt(byte res[]) throws Exception {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    /**
     * 替换字符串的一些符号,如<、>、"等等
     * @param s
     * @return
     */
    public static String filter(String s) {
        StringBuffer stringbuffer = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '<') {
                stringbuffer.append("&lt;");
            } else if (c == '>') {
                stringbuffer.append("&gt;");
            } else if (c == '"') {
                stringbuffer.append("&quot;");
            } else if (c == '&') {
                stringbuffer.append("&amp;");
            } else if (c == '\n') {
                stringbuffer.append("<br>");
            } else {
                stringbuffer.append(c);
            }
        }

        return stringbuffer.toString();
    }

    public static final String[] getVaribles(String str){
        if ((str == null) || (str.equals("")))
            return null;
        int i1 = 0; int i2 = 0; int i = 0;
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

    public static final String[] getVaribles(String startStr, String endStr, String str){
        if ((str == null) || (str.equals("")))
            return null;
        int i1 = 0; int i2 = 0; int i = 0;
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
            retStr[(i++)] = ((String)it.next());
        }

        return retStr;
    }

    public static int strToInt(String str, int toInt){
        if(!"".equals(str)){
            toInt = Integer.parseInt(str);
        }
        return toInt;
    }

    public static String replaceRefer(String str, String refer, String seperator) {

        if (refer == null || "".equals(refer)) {
            return str;
        }

        String[] referArr = refer.split("\\" + seperator);
        for (int i = referArr.length - 1; i >= 0; i--) {
            System.out.println("$" + (i + 2) + ":" + referArr[i]);
            str = str.replaceAll("\\$" + (i + 2), referArr[i]);
        }
        return str;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    public static String URIDecode(String date) {
        try {
            return URLDecoder.decode(date,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    public static String left(String str,int len){
        String result="";
        int oLen=str.length();

        if(oLen<=len){
            result=str;
        }else{
            result=str.substring(0,len);
        }

        return result;
    }
}
