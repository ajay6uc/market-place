package com.marketplace.shared.common.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {
   
	
    private static final Set<Class<?>> primitiveTypes = new HashSet<Class<?>>() {
    {
        add(Boolean.class);
        add(Character.class);
        add(Byte.class);
        add(Short.class);
        add(Integer.class);
        add(Long.class);
        add(Float.class);
        add(Double.class);
        add(Void.class);
    }
    };

    @Override
    public void copyProperty(Object dest, String name, Object value)
        throws IllegalAccessException, InvocationTargetException {
    if (value == null)
        return;

    if (primitiveTypes.contains(value.getClass())) {
        super.copyProperty(dest, name, value);
    } else {
       
        super.copyProperty(dest, name, value);
      }
    }
}