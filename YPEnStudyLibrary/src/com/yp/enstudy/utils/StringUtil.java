package com.yp.enstudy.utils;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.text.TextUtils;

/**
 * 字符串处理工具
 * 
 * @author 李文波
 * @version 0.3 2011-10-10
 */
public final class StringUtil {

    public static final String EMPTY_STRING = "";

    // \u3000 is the double-byte space character in UTF-8
    // \u00A0 is the non-breaking space character (&nbsp;)
    // \u2007 is the figure space character (&#8199;)
    // \u202F is the narrow non-breaking space character (&#8239;)
    public static final String WHITE_SPACES = " \r\n\t\u3000\u00A0\u2007\u202F";

    public static final String LINE_BREAKS = "\r\n";

    private static final Pattern htmlTagPattern = Pattern
            .compile("</?[a-zA-Z][^>]*>");

    private static final Pattern characterReferencePattern = Pattern
            .compile("&#?[a-zA-Z0-9]{1,8};");

    private static final Pattern dbSpecPattern = Pattern
            .compile("(.*)\\{(\\d+),(\\d+)\\}(.*)");

    // This class should not be instantiated, hence the private constructor
    private StringUtil() {
    }

    /** Split "str" by run of delimiters and return. */
    public static String[] split(String str, String delims) {
        return split(str, delims, false);
    }

    /**
     * Split "str" into tokens by delimiters and optionally remove white spaces
     * from the splitted tokens.
     * 
     * @param trimTokens
     *            if true, then trim the tokens
     */
    public static String[] split(String str, String delims, boolean trimTokens) {
        StringTokenizer tokenizer = new StringTokenizer(str, delims);
        int n = tokenizer.countTokens();
        String[] list = new String[n];
        for (int i = 0; i < n; i++) {
            if (trimTokens) {
                list[i] = tokenizer.nextToken().trim();
            } else {
                list[i] = tokenizer.nextToken();
            }
        }
        return list;
    }

    public static String reMoveLeft(String time) {
        while (time.startsWith("0")) {
            time = time.substring(1);
        }

        if (TextUtils.isEmpty(time))
            return "0";
        else
            return time;
    }

    /**
     * Short hand for <code>split(str, delims, true)</code>
     */
    public static String[] splitAndTrim(String str, String delims) {
        return split(str, delims, true);
    }

    /** Parse comma-separated list of ints and return as array. */
    public static int[] splitInts(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        int n = tokenizer.countTokens();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            String token = tokenizer.nextToken();
            list[i] = Integer.parseInt(token);
        }
        return list;
    }

    /** Parse comma-separated list of longs and return as array. */
    public static long[] splitLongs(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        int n = tokenizer.countTokens();
        long[] list = new long[n];
        for (int i = 0; i < n; i++) {
            String token = tokenizer.nextToken();
            list[i] = Long.parseLong(token);
        }
        return list;
    }

    /**
     * <p>
     * Checks if a String is not empty ("") and not null.
     * </p>
     * 
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * Helper function for null and empty string testing.
     * 
     * @return true iff s == null or s.equals("");
     */
    public static boolean isEmpty(String s) {
        return makeSafe(s).length() == 0;
    }

    /**
     * Helper function for null, empty, and whitespace string testing.
     * 
     * @return true if s == null or s.equals("") or s contains only whitespace
     *         characters.
     */
    public static boolean isEmptyOrWhitespace(String s) {
        s = makeSafe(s);
        for (int i = 0, n = s.length(); i < n; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     * 
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Helper function for making empty strings into a null.
     * 
     * @return null if s is zero length. otherwise, returns s.
     */
    public static String toNullIfEmpty(String s) {
        return (StringUtil.isEmpty(s)) ? null : s;
    }

    /**
     * Helper function for turning empty or whitespace strings into a null.
     * 
     * @return null if s is zero length or if s contains only whitespace
     *         characters. otherwise, returns s.
     */
    public static String toNullIfEmptyOrWhitespace(String s) {
        return (StringUtil.isEmptyOrWhitespace(s)) ? null : s;
    }

    /**
     * Serializes a map
     * 
     * @param map
     *            A map of String keys to arrays of String values
     * @param keyValueDelim
     *            Delimiter between keys and values
     * @param entryDelim
     *            Delimiter between entries
     * 
     * @return String A string containing a serialized representation of the
     *         contents of the map.
     * 
     *         e.g. arrayMap2String({"foo":["bar","bar2"],"foo1":["bar1"]}, "=",
     *         "&") returns "foo=bar&foo=bar2&foo1=bar1"
     */
    public static String arrayMap2String(Map<String, String[]> map,
            String keyValueDelim, String entryDelim) {
        Set<Entry<String, String[]>> entrySet = map.entrySet();
        Iterator<Entry<String, String[]>> itor = entrySet.iterator();
        StringWriter sw = new StringWriter();
        while (itor.hasNext()) {
            Entry<String, String[]> entry = itor.next();
            String key = entry.getKey();
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                sw.write(entry.getKey() + keyValueDelim + values[i]);
                if (i < values.length - 1) {
                    sw.write(entryDelim);
                }
            }
            if (itor.hasNext()) {
                sw.write(entryDelim);
            }
        }
        return sw.toString();
    }

    /**
     * Compares two strings, guarding against nulls If both Strings are null we
     * return true
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true; // Either both the same String, or both null
        }
        if (s1 != null) {
            if (s2 != null) {
                return s1.equals(s2);
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return true; // Either both the same String, or both null
        }
        if (s1 != null) {
            if (s2 != null) {
                return s1.equalsIgnoreCase(s2);
            }
        }
        return false;
    }

    /**
     * Splits s with delimiters in delimiter and returns the last token
     */
    public static String lastToken(String s, String delimiter) {
        String[] parts = split(s, delimiter);
        return (parts.length == 0) ? "" : parts[parts.length - 1];
    }

    /**
     * Determines if a string contains only ascii characters
     */
    public static boolean allAscii(String s) {
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if ((s.charAt(i) & 0xff80) != 0) {
                return false;
            }
        }
        return true;
    }


    private static Stack<BigDecimal> numbers = new Stack<BigDecimal>();

    private static Stack<Character> chs = new Stack<Character>();

    private static boolean isNum(String num) {
        return num.matches("[0-9]");
    }

    /***
     * 验证是否 纯数字
     */
    public static boolean checkIsNum(String str) {
        String regEx = "[0-9]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /** 整理字符串 去掉无用的换行 */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    /**
     *  判断某个字符串是否存在于数组中
     *  @param stringArray 原数组
     *  @param source 查找的字符串
     *  @return 是否找到
     */
    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);

        // 利用list的包含方法,进行判断
        if (tempList.contains(source)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String getNameByUrl(String url){
        try {
            return url.substring(url.lastIndexOf("/") + 1, url.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
