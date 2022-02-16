package com.crux.ssmcrud.resolver;

import com.crux.ssmcrud.bean.BeanAllPropertyException;
import com.crux.ssmcrud.utils.ResponseMessage;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.MethodInvocationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class BeanExceptionResolver{

	@ResponseBody
	@ExceptionHandler(BeanAllPropertyException.class)
	public ResponseMessage<String,String> invalidPropertyExceptionResolver(BeanAllPropertyException e){
		List<? extends InvalidPropertyException> allEx = e.getAllPropertyException();
		var data = allEx.stream().collect(Collectors.toMap(InvalidPropertyException::getPropertyName,
				m -> {
					String message = Objects.requireNonNull(m.getMessage());
					return message.substring(message.lastIndexOf("]: ") + 3);
				}));
		/*String message = Objects.requireNonNull(e.getMessage());
		message = message.substring(message.lastIndexOf("]: ") + 3);*/
		return ResponseMessage.failure("保存失败", data);
	}

	@ResponseBody
	@ExceptionHandler(MethodInvocationException.class)
	public ResponseMessage<?,?> methodInvocationExceptionResolver(MethodInvocationException e){
		e.printStackTrace();
		return ResponseMessage.failure("服务器出现异常,请稍后再试");
	}
}
