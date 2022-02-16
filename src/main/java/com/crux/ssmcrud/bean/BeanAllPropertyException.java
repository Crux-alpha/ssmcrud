package com.crux.ssmcrud.bean;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.InvalidPropertyException;

import java.util.List;

/**
 * 实体所有异常字段的异常
 * 它表示一个实体所有的异常字段，这些字段异常信息被封装在InvalidPropertyException中，可通过getAllPropertyException()
 * 获取所有的异常字段异常信息
 * @since 2022-1-25
 */
public class BeanAllPropertyException extends FatalBeanException{
	private final List<? extends InvalidPropertyException> allPropertyException;

	public BeanAllPropertyException(String msg, List<? extends InvalidPropertyException> allPropertyException){
		super(msg);
		this.allPropertyException = allPropertyException;
	}

	public BeanAllPropertyException(String msg, Throwable cause, List<? extends InvalidPropertyException> allPropertyException){
		super(msg, cause);
		this.allPropertyException = allPropertyException;
	}

	/**
	 * 获取所有的异常字段异常信息，它们都被封装在InvalidPropertyException中
	 * @return 所有的异常字段异常信息
	 */
	public List<? extends InvalidPropertyException> getAllPropertyException(){
		return allPropertyException;
	}
}
