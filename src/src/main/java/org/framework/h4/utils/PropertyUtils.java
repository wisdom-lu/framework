// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyUtils.java

package org.framework.h4.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

// Referenced classes of package org.framework.util:
//            PropertyUtilsBean

public class PropertyUtils
{

    public PropertyUtils()
    {
    }

    public static void clearDescriptors()
    {
        PropertyUtilsBean.getInstance().clearDescriptors();
    }

    public static void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().copyProperties(dest, orig);
    }

    public static Map describe(Object bean)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().describe(bean);
    }

    public static Object getIndexedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getIndexedProperty(bean, name);
    }

    public static Object getIndexedProperty(Object bean, String name, int index)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getIndexedProperty(bean, name, index);
    }

    public static Object getNestedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getNestedProperty(bean, name);
    }

    public static Object getProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getProperty(bean, name);
    }

    public static PropertyDescriptor getPropertyDescriptor(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getPropertyDescriptor(bean, name);
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass)
    {
        return PropertyUtilsBean.getInstance().getPropertyDescriptors(beanClass);
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Object bean)
    {
        return PropertyUtilsBean.getInstance().getPropertyDescriptors(bean);
    }

    public static Class getPropertyEditorClass(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getPropertyEditorClass(bean, name);
    }

    public static Class getPropertyType(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getPropertyType(bean, name);
    }

    public static Method getReadMethod(PropertyDescriptor descriptor)
    {
        return PropertyUtilsBean.getInstance().getReadMethod(descriptor);
    }

    public static Object getSimpleProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return PropertyUtilsBean.getInstance().getSimpleProperty(bean, name);
    }

    public static Method getWriteMethod(PropertyDescriptor descriptor)
    {
        return PropertyUtilsBean.getInstance().getWriteMethod(descriptor);
    }

    public static boolean isReadable(Object bean, String name)
    {
        return PropertyUtilsBean.getInstance().isReadable(bean, name);
    }

    public static boolean isWriteable(Object bean, String name)
    {
        return PropertyUtilsBean.getInstance().isWriteable(bean, name);
    }

    public static void setIndexedProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().setIndexedProperty(bean, name, value);
    }

    public static void setIndexedProperty(Object bean, String name, int index, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().setIndexedProperty(bean, name, index, value);
    }

    public static void setNestedProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().setNestedProperty(bean, name, value);
    }

    public static void setProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().setProperty(bean, name, value);
    }

    public static void setSimpleProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PropertyUtilsBean.getInstance().setSimpleProperty(bean, name, value);
    }

    public static final char INDEXED_DELIM = 91;
    public static final char INDEXED_DELIM2 = 93;
    public static final char NESTED_DELIM = 46;
}
