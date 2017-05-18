// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringUtils.java

package org.framework.h4.utils;

import java.beans.Introspector;

/**
 * <p>Title: 字符串处理类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：Feb 26, 2014 2:35:40 PM
 * @version 1.0
 */
public final class StringUtils
{

    public StringUtils()
    {
    }

    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return true;
        for(int i = 0; i < strLen; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }

    public static String trim(String str)
    {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str)
    {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str)
    {
        return str == null ? "" : str.trim();
    }

    public static String strip(String str)
    {
        return strip(str, null);
    }

    public static String stripToNull(String str)
    {
        if(str == null)
        {
            return null;
        } else
        {
            str = strip(str, null);
            return str.length() == 0 ? null : str;
        }
    }

    public static String stripToEmpty(String str)
    {
        return str == null ? "" : strip(str, null);
    }

    public static String strip(String str, String stripChars)
    {
        if(isEmpty(str))
        {
            return str;
        } else
        {
            str = stripStart(str, stripChars);
            return stripEnd(str, stripChars);
        }
    }

    public static String stripStart(String str, String stripChars)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        int start = 0;
        if(stripChars == null)
        {
            for(; start != strLen && Character.isWhitespace(str.charAt(start)); start++);
        } else
        {
            if(stripChars.length() == 0)
                return str;
            for(; start != strLen && stripChars.indexOf(str.charAt(start)) != -1; start++);
        }
        return str.substring(start);
    }

    public static String stripEnd(String str, String stripChars)
    {
        int end;
        if(str == null || (end = str.length()) == 0)
            return str;
        if(stripChars == null)
        {
            for(; end != 0 && Character.isWhitespace(str.charAt(end - 1)); end--);
        } else
        {
            if(stripChars.length() == 0)
                return str;
            for(; end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1; end--);
        }
        return str.substring(0, end);
    }

    public static String[] stripAll(String strs[])
    {
        return stripAll(strs, null);
    }

    public static String[] stripAll(String strs[], String stripChars)
    {
        int strsLen;
        if(strs == null || (strsLen = strs.length) == 0)
            return strs;
        String newArr[] = new String[strsLen];
        for(int i = 0; i < strsLen; i++)
            newArr[i] = strip(strs[i], stripChars);

        return newArr;
    }

    public static boolean equals(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static int indexOf(String str, char searchChar)
    {
        if(isEmpty(str))
            return -1;
        else
            return str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos)
    {
        if(isEmpty(str))
            return -1;
        else
            return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.indexOf(searchStr);
    }

    public static int ordinalIndexOf(String str, String searchStr, int ordinal)
    {
        if(str == null || searchStr == null || ordinal <= 0)
            return -1;
        if(searchStr.length() == 0)
            return 0;
        int found = 0;
        int index = -1;
        do
        {
            index = str.indexOf(searchStr, index + 1);
            if(index < 0)
                return index;
        } while(++found < ordinal);
        return index;
    }

    public static int indexOf(String str, String searchStr, int startPos)
    {
        if(str == null || searchStr == null)
            return -1;
        if(searchStr.length() == 0 && startPos >= str.length())
            return str.length();
        else
            return str.indexOf(searchStr, startPos);
    }

    public static int lastIndexOf(String str, char searchChar)
    {
        if(isEmpty(str))
            return -1;
        else
            return str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos)
    {
        if(isEmpty(str))
            return -1;
        else
            return str.lastIndexOf(searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.lastIndexOf(searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.lastIndexOf(searchStr, startPos);
    }

    public static boolean contains(String str, char searchChar)
    {
        if(isEmpty(str))
            return false;
        return str.indexOf(searchChar) >= 0;
    }

    public static boolean contains(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return false;
        return str.indexOf(searchStr) >= 0;
    }

    public static boolean containsIgnoreCase(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return false;
        else
            return contains(str.toUpperCase(), searchStr.toUpperCase());
    }

    public static String substring(String str, int start)
    {
        if(str == null)
            return null;
        if(start < 0)
            start = str.length() + start;
        if(start < 0)
            start = 0;
        if(start > str.length())
            return "";
        else
            return str.substring(start);
    }

    public static String substring(String str, int start, int end)
    {
        if(str == null)
            return null;
        if(end < 0)
            end = str.length() + end;
        if(start < 0)
            start = str.length() + start;
        if(end > str.length())
            end = str.length();
        if(start > end)
            return "";
        if(start < 0)
            start = 0;
        if(end < 0)
            end = 0;
        return str.substring(start, end);
    }

    public static String left(String str, int len)
    {
        if(str == null)
            return null;
        if(len < 0)
            return "";
        if(str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    public static String right(String str, int len)
    {
        if(str == null)
            return null;
        if(len < 0)
            return "";
        if(str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len);
    }

    public static String mid(String str, int pos, int len)
    {
        if(str == null)
            return null;
        if(len < 0 || pos > str.length())
            return "";
        if(pos < 0)
            pos = 0;
        if(str.length() <= pos + len)
            return str.substring(pos);
        else
            return str.substring(pos, pos + len);
    }

    public static String deleteWhitespace(String str)
    {
        if(isEmpty(str))
            return str;
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for(int i = 0; i < sz; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                chs[count++] = str.charAt(i);

        if(count == sz)
            return str;
        else
            return new String(chs, 0, count);
    }

    public static String removeStart(String str, String remove)
    {
        if(isEmpty(str) || isEmpty(remove))
            return str;
        if(str.startsWith(remove))
            return str.substring(remove.length());
        else
            return str;
    }

    public static String removeEnd(String str, String remove)
    {
        if(isEmpty(str) || isEmpty(remove))
            return str;
        if(str.endsWith(remove))
            return str.substring(0, str.length() - remove.length());
        else
            return str;
    }

    public static String remove(String str, char remove)
    {
        if(isEmpty(str) || str.indexOf(remove) == -1)
            return str;
        char chars[] = str.toCharArray();
        int pos = 0;
        for(int i = 0; i < chars.length; i++)
            if(chars[i] != remove)
                chars[pos++] = chars[i];

        return new String(chars, 0, pos);
    }

    public static String upperCase(String str)
    {
        if(str == null)
            return null;
        else
            return str.toUpperCase();
    }

    public static String lowerCase(String str)
    {
        if(str == null)
            return null;
        else
            return str.toLowerCase();
    }

    public static String capitalize(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        else
            return (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String uncapitalize(String str)
    {
        return Introspector.decapitalize(str);
    }

    public static String swapCase(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        StringBuffer buffer = new StringBuffer(strLen);
        char ch = '\0';
        for(int i = 0; i < strLen; i++)
        {
            ch = str.charAt(i);
            if(Character.isUpperCase(ch))
                ch = Character.toLowerCase(ch);
            else
            if(Character.isTitleCase(ch))
                ch = Character.toLowerCase(ch);
            else
            if(Character.isLowerCase(ch))
                ch = Character.toUpperCase(ch);
            buffer.append(ch);
        }

        return buffer.toString();
    }

    public static int countMatches(String str, String sub)
    {
        if(isEmpty(str) || isEmpty(sub))
            return 0;
        int count = 0;
        for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length())
            count++;

        return count;
    }

    public static boolean isNumeric(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNumericSpace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != ' ')
                return false;

        return true;
    }

    public static boolean isWhitespace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    public static String defaultString(String str)
    {
        return str == null ? "" : str;
    }

    public static String defaultString(String str, String defaultStr)
    {
        return str == null ? defaultStr : str;
    }

    public static String defaultIfEmpty(String str, String defaultStr)
    {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String reverse(String str)
    {
        if(str == null)
            return null;
        else
            return (new StringBuffer(str)).reverse().toString();
    }

    public static String abbreviate(String str, int maxWidth)
    {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth)
    {
        if(str == null)
            return null;
        if(maxWidth < 4)
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        if(str.length() <= maxWidth)
            return str;
        if(offset > str.length())
            offset = str.length();
        if(str.length() - offset < maxWidth - 3)
            offset = str.length() - (maxWidth - 3);
        if(offset <= 4)
            return (new StringBuilder(String.valueOf(str.substring(0, maxWidth - 3)))).append("...").toString();
        if(maxWidth < 7)
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        if(offset + (maxWidth - 3) < str.length())
            return (new StringBuilder("...")).append(abbreviate(str.substring(offset), maxWidth - 3)).toString();
        else
            return (new StringBuilder("...")).append(str.substring(str.length() - (maxWidth - 3))).toString();
    }

    public static String difference(String str1, String str2)
    {
        if(str1 == null)
            return str2;
        if(str2 == null)
            return str1;
        int at = indexOfDifference(str1, str2);
        if(at == -1)
            return "";
        else
            return str2.substring(at);
    }

    public static int indexOfDifference(String str1, String str2)
    {
        if(str1 == str2)
            return -1;
        if(str1 == null || str2 == null)
            return 0;
        int i;
        for(i = 0; i < str1.length() && i < str2.length(); i++)
            if(str1.charAt(i) != str2.charAt(i))
                break;

        if(i < str2.length() || i < str1.length())
            return i;
        else
            return -1;
    }

    public static String format(int value, int length)
    {
        String str = String.valueOf(value);
        int n = str.length();
        StringBuffer sbf = new StringBuffer(length);
        if(n > length)
            sbf.append(str.toCharArray(), n - length, length);
        else
        if(n < length)
        {
            for(int i = 0; i < length - n; i++)
                sbf.append("0");

            sbf.append(value);
        } else
        {
            sbf.append(value);
        }
        return sbf.toString();
    }

    public static String increase(String str, int increment, int beginIndex, int endIndex)
    {
        StringBuffer sbf = new StringBuffer(str);
        int value = Integer.parseInt(sbf.substring(beginIndex, endIndex));
        value += increment;
        sbf.replace(beginIndex, endIndex, format(value, endIndex - beginIndex));
        return sbf.toString();
    }

    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
}
