// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericsUtils.java

package org.framework.h4.utils;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: 通过泛型参数获取泛型类型的工具类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：Feb 26, 2014 2:27:08 PM
 * @version 1.0
 */
public class GenericsUtils
{

    private GenericsUtils()
    {
    }

    public static Class getSuperClassGenricType(Class clazz)
    {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index)
        throws IndexOutOfBoundsException
    {
        java.lang.reflect.Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType))
            throw new IllegalArgumentException((new StringBuilder(String.valueOf(clazz.getSimpleName()))).append("'s superclass not ParameterizedType").toString());
        java.lang.reflect.Type params[] = ((ParameterizedType)genType).getActualTypeArguments();
        if(index >= params.length || index < 0)
            throw new IllegalArgumentException((new StringBuilder("Index: ")).append(index).append(", Size of ").append(clazz.getSimpleName()).append("'s Parameterized Type: ").append(params.length).toString());
        if(!(params[index] instanceof Class))
            throw new IllegalArgumentException((new StringBuilder(String.valueOf(clazz.getSimpleName()))).append(" not set the actual class on superclass generic parameter").toString());
        else
            return (Class)params[index];
    }

    public static Object getFirstElement(List aList)
    {
        if(aList == null || aList.isEmpty())
            return null;
        else
            return aList.get(0);
    }

    public static Object[] toArray(List aList)
    {
        if(aList == null || aList.isEmpty())
            return null;
        int size = aList.size();
        Object ts[] = (Object[])Array.newInstance(aList.get(0).getClass(), size);
        Iterator iterator = aList.iterator();
        int i = 0;
        while(iterator.hasNext()) 
            ts[i++] = iterator.next();
        return ts;
    }
}
