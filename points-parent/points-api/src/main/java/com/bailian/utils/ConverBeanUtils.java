package com.bailian.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类值复制
 *
 * @ClassName: ConverBeanUtils
 * @author 邹永乐
 * @date 2015年3月4日 上午10:28:32
 * @version V1.0
 *
 */
public class ConverBeanUtils {

	private static void getField(List<Field> fields, Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		Field[] sourcefields = clazz.getDeclaredFields();
		for (Field field : sourcefields) {
			if (field != null) {
				fields.add(field);
			}
		}
		if (clazz.getSuperclass() != Object.class) {
			getField(fields, clazz.getSuperclass());
		}
	}

	public static void conver(Object sourceObj, Object destinObj) throws IllegalArgumentException, IllegalAccessException {
		if (sourceObj == null || destinObj == null)
			return;
		List<Field> sourcefields = new ArrayList<Field>();
		List<Field> destinfields = new ArrayList<Field>();

		getField(sourcefields, sourceObj.getClass());
		getField(destinfields, destinObj.getClass());

		for (Field sf : sourcefields) {
			sf.setAccessible(true); // 设置些属性是可以访问的
			if ("serialVersionUID".equals(sf.getName())) {
				continue;
			}
			Object val = sf.get(sourceObj);// 得到此属性的值
			if (val != null) {
				for (Field df : destinfields) {
					df.setAccessible(true); // 设置些属性是可以访问的
					if (sf.getName().equals(df.getName())) {
						// 说明属性名称相同
						if (sf.getType().equals(df.getType())) {
							// 说明类型还相同
							df.set(destinObj, val);
						} else {
							if (sf.getType().toString().endsWith("Long") && df.getType().toString().endsWith("Timestamp")) {
								Long tm = (Long) val;
								df.set(destinObj, new Timestamp(tm));
							} else if (sf.getType().toString().endsWith("Timestamp") && df.getType().toString().endsWith("Long")) {
								Timestamp tm = (Timestamp) val;
								df.set(destinObj, tm.getTime());
							}
							if (sf.getType().toString().endsWith("BigDecimal") && df.getType().toString().endsWith("String")) {
								BigDecimal tm = (BigDecimal) val;
								df.set(destinObj, tm.toString());
							} else if (sf.getType().toString().endsWith("String") && df.getType().toString().endsWith("BigDecimal")) {
								df.set(destinObj, new BigDecimal(val.toString()));
							}
						}
					}
				}
			}
		}
	}

	public static void conver(Object sourceObj, Object destinObj, String prefix) throws IllegalArgumentException, IllegalAccessException {
		if (sourceObj == null || destinObj == null)
			return;
		List<Field> sourcefields = new ArrayList<Field>();
		List<Field> destinfields = new ArrayList<Field>();

		getField(sourcefields, sourceObj.getClass());
		getField(destinfields, destinObj.getClass());

		for (Field sf : sourcefields) {
			sf.setAccessible(true); // 设置些属性是可以访问的
			if ("serialVersionUID".equals(sf.getName())) {
				continue;
			}
			Object val = sf.get(sourceObj);// 得到此属性的值
			if (val != null) {
				for (Field df : destinfields) {
					df.setAccessible(true); // 设置些属性是可以访问的
					if (sf.getName().equals(df.getName()) || sf.getName().toUpperCase().equals((prefix + df.getName()).toUpperCase()) || (prefix + sf.getName()).toUpperCase().endsWith(df.getName().toUpperCase())) {
						// 说明属性名称相同
						if (sf.getType().equals(df.getType())) {
							// 说明类型还相同
							df.set(destinObj, val);
						} else {
							if (sf.getType().toString().endsWith("Long") && df.getType().toString().endsWith("Timestamp")) {
								Long tm = (Long) val;
								df.set(destinObj, new Timestamp(tm));
							} else if (sf.getType().toString().endsWith("Timestamp") && df.getType().toString().endsWith("Long")) {
								Timestamp tm = (Timestamp) val;
								df.set(destinObj, tm.getTime());
							}
							if (sf.getType().toString().endsWith("BigDecimal") && df.getType().toString().endsWith("String")) {
								BigDecimal tm = (BigDecimal) val;
								df.set(destinObj, tm.toString());
							} else if (sf.getType().toString().endsWith("String") && df.getType().toString().endsWith("BigDecimal")) {
								df.set(destinObj, new BigDecimal(val.toString()));
							}
						}
					}
				}
			}
		}
	}
}
