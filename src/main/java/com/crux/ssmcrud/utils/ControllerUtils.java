package com.crux.ssmcrud.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 辅助请求处理器的一些工具
 * @since 2022-2-8
 */
public final class ControllerUtils{

	private ControllerUtils(){}

	/**
	 * 处理校验结果，返回封装错误数据
	 * @param result 校验结果
	 * @return 如果校验存在错误，将错误封装后返回，否则返回空值
	 */
	@Nullable
	public static Map<String,String> getErrorData(@NonNull BindingResult result){
		if(Objects.requireNonNull(result).hasErrors()){
			return result.getFieldErrors().stream().collect(Collectors.toMap(
					FieldError::getField, f -> Objects.requireNonNullElse(f.getDefaultMessage(), f.getField() + "非法")));
		}
		return null;
	}
}
