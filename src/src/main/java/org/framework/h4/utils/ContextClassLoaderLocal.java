// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContextClassLoaderLocal.java

package org.framework.h4.utils;

import java.util.Map;
import java.util.WeakHashMap;

public class ContextClassLoaderLocal
{

    public ContextClassLoaderLocal()
    {
        valueByClassLoader = new WeakHashMap();
        globalValueInitialized = false;
    }

    protected Object initialValue()
    {
        return null;
    }

    public synchronized Object get()
    {
        valueByClassLoader.isEmpty();
        try
        {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if(contextClassLoader != null)
            {
                Object value = valueByClassLoader.get(contextClassLoader);
                if(value == null && !valueByClassLoader.containsKey(contextClassLoader))
                {
                    value = initialValue();
                    valueByClassLoader.put(contextClassLoader, value);
                }
                return value;
            }
        }
        catch(SecurityException securityexception) { }
        if(!globalValueInitialized)
        {
            globalValue = initialValue();
            globalValueInitialized = true;
        }
        return globalValue;
    }

    public synchronized void set(Object value)
    {
        valueByClassLoader.isEmpty();
        try
        {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if(contextClassLoader != null)
            {
                valueByClassLoader.put(contextClassLoader, value);
                return;
            }
        }
        catch(SecurityException securityexception) { }
        globalValue = value;
        globalValueInitialized = true;
    }

    public synchronized void unset()
    {
        try
        {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            unset(contextClassLoader);
        }
        catch(SecurityException securityexception) { }
    }

    public synchronized void unset(ClassLoader classLoader)
    {
        valueByClassLoader.remove(classLoader);
    }

    private Map valueByClassLoader;
    private boolean globalValueInitialized;
    private Object globalValue;
}
