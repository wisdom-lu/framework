// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BeanUtils.java

package org.framework.h4.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

// Referenced classes of package org.framework.util:
//            StringUtils, BeanUtilsBean

public final class BeanUtils
{

    public BeanUtils()
    {
    }

    public static Object getDeclaredProperty(Object object, String propertyName)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = object.getClass().getDeclaredField(propertyName);
        return getDeclaredProperty(object, field);
    }

    public static Object getDeclaredProperty(Object object, Field field)
        throws IllegalAccessException
    {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(object);
        field.setAccessible(accessible);
        return result;
    }

    public static void setDeclaredProperty(Object object, String propertyName, Object newValue)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = object.getClass().getDeclaredField(propertyName);
        setDeclaredProperty(object, field, newValue);
    }

    public static void setDeclaredProperty(Object object, Field field, Object newValue)
        throws IllegalAccessException
    {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(object, newValue);
        field.setAccessible(accessible);
    }

    public static String getReadMethodName(Class type, String fieldName)
    {
        if(type != null && type.getName().equals("boolean"))
            return (new StringBuilder("is")).append(StringUtils.capitalize(fieldName)).toString();
        else
            return (new StringBuilder("get")).append(StringUtils.capitalize(fieldName)).toString();
    }

    public static String getWriteMethodName(String fieldName)
    {
        return (new StringBuilder("set")).append(StringUtils.capitalize(fieldName)).toString();
    }

    public static Object cloneBean(Object bean)
        throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
    {
        return BeanUtilsBean.getInstance().cloneBean(bean);
    }

    public static void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }

    public static void copyProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        BeanUtilsBean.getInstance().copyProperty(bean, name, value);
    }

    public static Map describe(Object bean)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return BeanUtilsBean.getInstance().describe(bean);
    }

    public static void populate(Object bean, Map properties)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        BeanUtilsBean.getInstance().populate(bean, properties);
    }

    public static void setProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        BeanUtilsBean.getInstance().setProperty(bean, name, value);
    }
}
