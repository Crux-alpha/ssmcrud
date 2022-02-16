package com.crux.ssmcrud.utils;

import com.crux.ssmcrud.bean.BeanAllPropertyException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.MethodInvocationException;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.util.StringUtils;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

/**
 * 异常工具类
 */
public final class ExceptionUtils{

	private ExceptionUtils(){}

	/**
	 * 用于检查实体属性是否为空，如果存在空值，则抛出BeanAllPropertyException，并提示哪个属性为空
	 * @param bean 被检查的实体
	 * @param excludeArgs 可选排除的属性
	 * @return 如果没有异常，则返回该实体
	 * @throws BeanAllPropertyException 如果实体属性存在空值，抛出此异常
	 */
	public static <T> T checkBeanRequireNonNull(T bean, String... excludeArgs) throws BeanAllPropertyException{
		Field[] fields = bean.getClass().getDeclaredFields();
		List<NullValueInNestedPathException> allEx = new LinkedList<>();
		for(var field : fields){
			String fieldName = field.getName();
			Object fieldValue = getFieldValue(bean, fieldName);
			if((fieldValue == null || (fieldValue instanceof String s && s.isEmpty()))
					&& Arrays.stream(excludeArgs).noneMatch(fieldName::equals)){
				allEx.add(new NullValueInNestedPathException(bean.getClass(), fieldName, fieldName + "为空"));
			}
		}
		if(!allEx.isEmpty()) throw new BeanAllPropertyException("实体" + bean.getClass().getName() + "的属性存在空值", allEx);
		return bean;
	}

	/**
	 * 根据给定规则检查实体属性
	 * @param bean 被检查的实体
	 * @param rules 规则映射，其中Key为属性名，Value为规则，如果该属性未通过检查，则返回错误信息用以抛出BeanAttributeException
	 * @param <U> 被检查的属性的类型
	 * @return 如果没有异常，则返回该实体
	 * @throws BeanAllPropertyException 如果实体中的属性未通过检查，抛出此异常
	 */
	public static <T,U> T checkBeanRules(T bean, Map<String,Function<U,String>> rules) throws BeanAllPropertyException{
		List<InvalidPropertyException> allEx = new LinkedList<>();
		for(var entry : rules.entrySet()){
			String fieldName = entry.getKey();
			U value = (U)getFieldValue(bean, fieldName);
			String msg = entry.getValue().apply(value);
			if(msg != null){
				allEx.add(new InvalidPropertyException(bean.getClass(), fieldName, msg));
			}
		}
		if(!allEx.isEmpty()) throw new BeanAllPropertyException("实体" + bean.getClass().getName() + "的属性未通过检查", allEx);
		return bean;
	}

	/**
	 * 通过getter获取实体指定属性名的属性值
	 * @param bean 实体
	 * @param fieldName 属性名
	 * @param args getter如果有参数，则传入
	 * @return getter的返回值
	 * @throws MethodInvocationException 如果getter不可访问或没有getter，则抛出此异常
	 */
	private static Object getFieldValue(Object bean, String fieldName, Object... args) throws MethodInvocationException{
		try{
			String getter = "get" + StringUtils.capitalize(fieldName);
			return bean.getClass().getMethod(getter).invoke(bean, args);
		}catch(InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
			throw new MethodInvocationException(new PropertyChangeEvent(bean, fieldName, null, null), e);
		}
	}

	public static Map<String,Function<Object,String>> getEmptyRuleMap(){
		return new HashMap<>();
	}

	public static <U> Map<String,Function<U,String>> getEmptyRuleMap(Class<U> propertyClass){
		return new HashMap<>();
	}
}
