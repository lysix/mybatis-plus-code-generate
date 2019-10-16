package com.common;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenericsUtils {
	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager<Book>
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager<Book>
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 实例化泛型dto对象<K>
	 * @return K
	 */
	public static Object newObject(Class superClass, int index){
		Object newUsr;
		try {
//            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericInterfaces()[index];
			// 通过反射获取model的真实类型
			ParameterizedType pt = (ParameterizedType) superClass.getGenericSuperclass();
			Class clazz = getSuperClassGenricType(superClass,index);
			// 通过反射创建model的实例
			newUsr = clazz.newInstance();
		} catch (Exception e) {
			log.error("实例化泛型对象异常:"+superClass,e);
			throw new RuntimeException(e);
		}
		return newUsr;
	}

	public static <K,E> List<K> getBeanByClass(List<E> records,Class targetclassType) throws IllegalAccessException, InstantiationException {
		List<K> desList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(records)) {
			int recordLength = records.size();
			for (int i = 0; i < recordLength; i++) {
				E sourceModel = (E) records.get(i);
				K dto = (K) targetclassType.newInstance();
				// bean 属性对象
				BeanUtils.copyProperties(sourceModel, dto);
				desList.add(dto);
			}
		}

		return desList;
	}
}