// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BeanUtilsBean.java

package org.framework.h4.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Referenced classes of package org.framework.util:
//            ContextClassLoaderLocal, PropertyUtilsBean

public class BeanUtilsBean
{

    public static synchronized BeanUtilsBean getInstance()
    {
        return (BeanUtilsBean)beansByClassLoader.get();
    }

    public static synchronized void setInstance(BeanUtilsBean newInstance)
    {
        beansByClassLoader.set(newInstance);
    }

    public BeanUtilsBean()
    {
        this(new PropertyUtilsBean());
    }

    public BeanUtilsBean(PropertyUtilsBean propertyUtilsBean)
    {
        this.propertyUtilsBean = propertyUtilsBean;
    }

    public Object cloneBean(Object bean)
        throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
    {
        Object newBean = bean.getClass().newInstance();
        getPropertyUtils().copyProperties(newBean, bean);
        return newBean;
    }

    public void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        getPropertyUtils().copyProperties(dest, orig);
    }

    public void copyProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        getPropertyUtils().setProperty(bean, name, value);
    }

    public Map describe(Object bean)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return getPropertyUtils().describe(bean);
    }

    public void populate(Object bean, Map properties)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null || properties == null)
            return;
        for(Iterator names = properties.keySet().iterator(); names.hasNext();)
        {
            String name = (String)names.next();
            if(name != null)
            {
                Object value = properties.get(name);
                setProperty(bean, name, value);
            }
        }

    }

    public void setProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        getPropertyUtils().setProperty(bean, name, value);
    }

    public PropertyUtilsBean getPropertyUtils()
    {
        return propertyUtilsBean;
    }

    private static final ContextClassLoaderLocal beansByClassLoader = new ContextClassLoaderLocal() {

        protected Object initialValue()
        {
            return new BeanUtilsBean();
        }

    }
;
    private PropertyUtilsBean propertyUtilsBean;

}
