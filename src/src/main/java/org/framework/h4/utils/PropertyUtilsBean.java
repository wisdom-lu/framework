// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyUtilsBean.java

package org.framework.h4.utils;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package org.framework.util:
//            BeanUtilsBean

public final class PropertyUtilsBean
{

    protected static PropertyUtilsBean getInstance()
    {
        return BeanUtilsBean.getInstance().getPropertyUtils();
    }

    public PropertyUtilsBean()
    {
        descriptorsCache = null;
        descriptorsCache = new HashMap();
    }

    public void clearDescriptors()
    {
        descriptorsCache.clear();
        Introspector.flushCaches();
    }

    public void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(dest == null)
            throw new IllegalArgumentException("No destination bean specified");
        if(orig == null)
            throw new IllegalArgumentException("No origin bean specified");
        if(orig instanceof Map)
        {
            for(Iterator names = ((Map)orig).keySet().iterator(); names.hasNext();)
            {
                String name = (String)names.next();
                if(isWriteable(dest, name))
                {
                    Object value = ((Map)orig).get(name);
                    setSimpleProperty(dest, name, value);
                }
            }

        } else
        {
            PropertyDescriptor origDescriptors[] = getPropertyDescriptors(orig);
            for(int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if(isReadable(orig, name) && isWriteable(dest, name))
                {
                    Object value = getSimpleProperty(orig, name);
                    setSimpleProperty(dest, name, value);
                }
            }

        }
    }

    public Map describe(Object bean)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        Map description = new HashMap();
        PropertyDescriptor descriptors[] = getPropertyDescriptors(bean);
        for(int i = 0; i < descriptors.length; i++)
        {
            String name = descriptors[i].getName();
            if(descriptors[i].getReadMethod() != null)
                description.put(name, getProperty(bean, name));
        }

        return description;
    }

    public Object getIndexedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        int delim = name.indexOf('[');
        int delim2 = name.indexOf(']');
        if(delim < 0 || delim2 <= delim)
            throw new IllegalArgumentException((new StringBuilder("Invalid indexed property '")).append(name).append("'").toString());
        int index = -1;
        try
        {
            String subscript = name.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException((new StringBuilder("Invalid indexed property '")).append(name).append("'").toString());
        }
        name = name.substring(0, delim);
        return getIndexedProperty(bean, name, index);
    }

    public Object getIndexedProperty(Object bean, String name, int index)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor == null)
            throw new NoSuchMethodException((new StringBuilder("Unknown property '")).append(name).append("'").toString());
        Method readMethod;
        if(descriptor instanceof IndexedPropertyDescriptor)
        {
            readMethod = ((IndexedPropertyDescriptor)descriptor).getIndexedReadMethod();
            if(readMethod != null)
            {
                Object subscript[] = new Object[1];
                subscript[0] = new Integer(index);
                try
                {
                    return invokeMethod(readMethod, bean, subscript);
                }
                catch(InvocationTargetException e)
                {
                    if(e.getTargetException() instanceof ArrayIndexOutOfBoundsException)
                        throw (ArrayIndexOutOfBoundsException)e.getTargetException();
                    else
                        throw e;
                }
            }
        }
        readMethod = getReadMethod(descriptor);
        if(readMethod == null)
            throw new NoSuchMethodException((new StringBuilder("Property '")).append(name).append("' has no getter method").toString());
        Object value = invokeMethod(readMethod, bean, new Object[0]);
        if(!value.getClass().isArray())
        {
            if(!(value instanceof List))
                throw new IllegalArgumentException((new StringBuilder("Property '")).append(name).append("' is not indexed").toString());
            else
                return ((List)value).get(index);
        } else
        {
            return Array.get(value, index);
        }
    }

    public Object getNestedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        int indexOfINDEXED_DELIM = -1;
        int indexOfNESTED_DELIM = -1;
        do
        {
            indexOfNESTED_DELIM = name.indexOf('.');
            if(indexOfNESTED_DELIM < 0)
                break;
            String next = name.substring(0, indexOfNESTED_DELIM);
            indexOfINDEXED_DELIM = next.indexOf('[');
            if(bean instanceof Map)
                bean = ((Map)bean).get(next);
            else
            if(indexOfINDEXED_DELIM >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if(bean == null)
                throw new IllegalArgumentException((new StringBuilder("Null property value for '")).append(name.substring(0, indexOfNESTED_DELIM)).append("'").toString());
            name = name.substring(indexOfNESTED_DELIM + 1);
        } while(true);
        indexOfINDEXED_DELIM = name.indexOf('[');
        if(bean instanceof Map)
            bean = ((Map)bean).get(name);
        else
        if(indexOfINDEXED_DELIM >= 0)
            bean = getIndexedProperty(bean, name);
        else
            bean = getSimpleProperty(bean, name);
        return bean;
    }

    public Object getProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return getNestedProperty(bean, name);
    }

    public PropertyDescriptor getPropertyDescriptor(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        do
        {
            int period = findNextNestedIndex(name);
            if(period < 0)
                break;
            String next = name.substring(0, period);
            int indexOfINDEXED_DELIM = next.indexOf('[');
            if(indexOfINDEXED_DELIM >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if(bean == null)
                throw new IllegalArgumentException((new StringBuilder("Null property value for '")).append(name.substring(0, period)).append("'").toString());
            name = name.substring(period + 1);
        } while(true);
        int left = name.indexOf('[');
        if(left >= 0)
            name = name.substring(0, left);
        if(bean == null || name == null)
            return null;
        PropertyDescriptor descriptors[] = getPropertyDescriptors(bean);
        if(descriptors != null)
        {
            for(int i = 0; i < descriptors.length; i++)
                if(name.equals(descriptors[i].getName()))
                    return descriptors[i];

        }
        return null;
    }

    private int findNextNestedIndex(String expression)
    {
        int bracketCount = 0;
        int i = 0;
        for(int size = expression.length(); i < size; i++)
        {
            char at = expression.charAt(i);
            switch(at)
            {
            default:
                break;

            case 46: // '.'
                if(bracketCount < 1)
                    return i;
                break;

            case 91: // '['
                bracketCount++;
                break;

            case 93: // ']'
                bracketCount--;
                break;
            }
        }

        return -1;
    }

    public PropertyDescriptor[] getPropertyDescriptors(Class beanClass)
    {
        if(beanClass == null)
            throw new IllegalArgumentException("No bean class specified");
        PropertyDescriptor descriptors[] = (PropertyDescriptor[])null;
        descriptors = (PropertyDescriptor[])descriptorsCache.get(beanClass);
        if(descriptors != null)
            return descriptors;
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(beanClass);
        }
        catch(IntrospectionException e)
        {
            return new PropertyDescriptor[0];
        }
        descriptors = beanInfo.getPropertyDescriptors();
        if(descriptors == null)
            descriptors = new PropertyDescriptor[0];
        descriptorsCache.put(beanClass, descriptors);
        return descriptors;
    }

    public PropertyDescriptor[] getPropertyDescriptors(Object bean)
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        else
            return getPropertyDescriptors(bean.getClass());
    }

    public Class getPropertyEditorClass(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor != null)
            return descriptor.getPropertyEditorClass();
        else
            return null;
    }

    public Class getPropertyType(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor == null)
            return null;
        if(descriptor instanceof IndexedPropertyDescriptor)
            return ((IndexedPropertyDescriptor)descriptor).getIndexedPropertyType();
        else
            return descriptor.getPropertyType();
    }

    public Method getReadMethod(PropertyDescriptor descriptor)
    {
        return null;
    }

    public Object getSimpleProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        if(name.indexOf('.') >= 0)
            throw new IllegalArgumentException("Nested property names are not allowed");
        if(name.indexOf('[') >= 0)
            throw new IllegalArgumentException("Indexed property names are not allowed");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor == null)
            throw new NoSuchMethodException((new StringBuilder("Unknown property '")).append(name).append("'").toString());
        Method readMethod = getReadMethod(descriptor);
        if(readMethod == null)
        {
            throw new NoSuchMethodException((new StringBuilder("Property '")).append(name).append("' has no getter method").toString());
        } else
        {
            Object value = invokeMethod(readMethod, bean, new Object[0]);
            return value;
        }
    }

    public Method getWriteMethod(PropertyDescriptor descriptor)
    {
        return null;
    }

    public boolean isReadable(Object bean, String name)
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        try
        {
            PropertyDescriptor desc = getPropertyDescriptor(bean, name);
            if(desc != null)
            {
                Method readMethod = desc.getReadMethod();
                if(readMethod == null && (desc instanceof IndexedPropertyDescriptor))
                    readMethod = ((IndexedPropertyDescriptor)desc).getIndexedReadMethod();
                return readMethod != null;
            }
        }
        catch(IllegalAccessException e)
        {
            return false;
        }
        catch(InvocationTargetException e)
        {
            return false;
        }
        catch(NoSuchMethodException e)
        {
            return false;
        }
        return false;
    }

    public boolean isWriteable(Object bean, String name)
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        try
        {
            PropertyDescriptor desc = getPropertyDescriptor(bean, name);
            if(desc != null)
            {
                Method writeMethod = desc.getWriteMethod();
                if(writeMethod == null && (desc instanceof IndexedPropertyDescriptor))
                    writeMethod = ((IndexedPropertyDescriptor)desc).getIndexedWriteMethod();
                return writeMethod != null;
            }
        }
        catch(IllegalAccessException e)
        {
            return false;
        }
        catch(InvocationTargetException e)
        {
            return false;
        }
        catch(NoSuchMethodException e)
        {
            return false;
        }
        return false;
    }

    public void setIndexedProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        int delim = name.indexOf('[');
        int delim2 = name.indexOf(']');
        if(delim < 0 || delim2 <= delim)
            throw new IllegalArgumentException((new StringBuilder("Invalid indexed property '")).append(name).append("'").toString());
        int index = -1;
        try
        {
            String subscript = name.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException((new StringBuilder("Invalid indexed property '")).append(name).append("'").toString());
        }
        name = name.substring(0, delim);
        setIndexedProperty(bean, name, index, value);
    }

    public void setIndexedProperty(Object bean, String name, int index, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor == null)
            throw new NoSuchMethodException((new StringBuilder("Unknown property '")).append(name).append("'").toString());
        if(descriptor instanceof IndexedPropertyDescriptor)
        {
            Method writeMethod = ((IndexedPropertyDescriptor)descriptor).getIndexedWriteMethod();
            if(writeMethod != null)
            {
                Object subscript[] = new Object[2];
                subscript[0] = new Integer(index);
                subscript[1] = value;
                try
                {
                    invokeMethod(writeMethod, bean, subscript);
                }
                catch(InvocationTargetException e)
                {
                    if(e.getTargetException() instanceof ArrayIndexOutOfBoundsException)
                        throw (ArrayIndexOutOfBoundsException)e.getTargetException();
                    else
                        throw e;
                }
                return;
            }
        }
        Method readMethod = descriptor.getReadMethod();
        if(readMethod == null)
            throw new NoSuchMethodException((new StringBuilder("Property '")).append(name).append("' has no getter method").toString());
        Object array = invokeMethod(readMethod, bean, new Object[0]);
        if(!array.getClass().isArray())
        {
            if(array instanceof List)
                ((List)array).set(index, value);
            else
                throw new IllegalArgumentException((new StringBuilder("Property '")).append(name).append("' is not indexed").toString());
        } else
        {
            Array.set(array, index, value);
        }
    }

    public void setNestedProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        int indexOfINDEXED_DELIM = -1;
        do
        {
            int delim = name.indexOf('.');
            if(delim < 0)
                break;
            String next = name.substring(0, delim);
            indexOfINDEXED_DELIM = next.indexOf('[');
            if(bean instanceof Map)
                bean = ((Map)bean).get(next);
            else
            if(indexOfINDEXED_DELIM >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if(bean == null)
                throw new IllegalArgumentException((new StringBuilder("Null property value for '")).append(name.substring(0, delim)).append("'").toString());
            name = name.substring(delim + 1);
        } while(true);
        indexOfINDEXED_DELIM = name.indexOf('[');
        if(bean instanceof Map)
        {
            PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
            if(descriptor == null)
                ((Map)bean).put(name, value);
            else
                setSimpleProperty(bean, name, value);
        } else
        if(indexOfINDEXED_DELIM >= 0)
            setIndexedProperty(bean, name, value);
        else
            setSimpleProperty(bean, name, value);
    }

    public void setProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        setNestedProperty(bean, name, value);
    }

    public void setSimpleProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if(bean == null)
            throw new IllegalArgumentException("No bean specified");
        if(name == null)
            throw new IllegalArgumentException("No name specified");
        if(name.indexOf('.') >= 0)
            throw new IllegalArgumentException("Nested property names are not allowed");
        if(name.indexOf('[') >= 0)
            throw new IllegalArgumentException("Indexed property names are not allowed");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if(descriptor == null)
            throw new NoSuchMethodException((new StringBuilder("Unknown property '")).append(name).append("'").toString());
        Method writeMethod = getWriteMethod(descriptor);
        if(writeMethod == null)
        {
            throw new NoSuchMethodException((new StringBuilder("Property '")).append(name).append("' has no setter method").toString());
        } else
        {
            Object values[] = new Object[1];
            values[0] = value;
            invokeMethod(writeMethod, bean, values);
            return;
        }
    }

    private Object invokeMethod(Method method, Object bean, Object values[])
        throws IllegalAccessException, InvocationTargetException
    {
        try
        {
            return method.invoke(bean, values);
        }
        catch(IllegalArgumentException e)
        {
            throw new IllegalArgumentException((new StringBuilder("Cannot invoke ")).append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append(" - ").append(e.getMessage()).toString());
        }
    }

    private Map descriptorsCache;
}
