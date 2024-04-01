package org.brijframework.json.schema.factories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {
	
	public static Object getInstance(String cls) {
		try {
			return Class.forName(cls).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			System.out.println(cls);
			e.printStackTrace();
		}
		return null;
	}

	public static  void setField(Object instance, Object fieldVal, String fieldName) {
		try {
			Field fieldInfo = null;
			try {
				fieldInfo = instance.getClass().getDeclaredField(fieldName);
			}catch (NoSuchFieldException e) {
			}
			if(fieldInfo==null) {
				fieldInfo=instance.getClass().getSuperclass().getDeclaredField(fieldName);
			}
			fieldInfo.setAccessible(true);
			fieldInfo.set(instance, fieldVal);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println(instance);
			e.printStackTrace();
		}
	}
	
	public static  Object getField(Object instance, String fieldName) {
		try {
			Field fieldInfo = null;
			try {
				fieldInfo = instance.getClass().getDeclaredField(fieldName);
			}catch (NoSuchFieldException e) {
			}
			if(fieldInfo==null) {
				fieldInfo=instance.getClass().getSuperclass().getDeclaredField(fieldName);
			}
			return  getField(instance, fieldInfo);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getField(Object instance, Field fieldInfo) {
		try {
			fieldInfo.setAccessible(true);
			return fieldInfo.get(instance);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println(instance);
			e.printStackTrace();
		}
		return null;
	}
}
