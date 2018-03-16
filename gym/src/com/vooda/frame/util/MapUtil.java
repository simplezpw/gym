package com.vooda.frame.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	public static <T> T convertMap(Class<T> type, Map<String, Object> map) {
		BeanInfo beanInfo;
		T t = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
			t = type.newInstance(); // 创建 JavaBean 对象
			// 给 JavaBean 对象的属性赋值
			for (PropertyDescriptor descriptor :beanInfo.getPropertyDescriptors()) {
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					try {
						descriptor.getWriteMethod().invoke(t, map.get(propertyName));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return t;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })   
    public static Map convertBean(Object bean){   
        Class type = bean.getClass();   
        Map returnMap = new HashMap();   
        BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(type);
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();   
	        for (int i = 0; i< propertyDescriptors.length; i++) {   
	            PropertyDescriptor descriptor = propertyDescriptors[i];   
	            String propertyName = descriptor.getName();   
	            if (!propertyName.equals("class")) {   
	                Method readMethod = descriptor.getReadMethod();   
	                Object result = readMethod.invoke(bean, new Object[0]);   
	                if (result != null) {   
	                    returnMap.put(propertyName, result);   
	                } else {   
	                    returnMap.put(propertyName, "");   
	                }   
	            }   
	        }  
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return returnMap;   
    }   
}
