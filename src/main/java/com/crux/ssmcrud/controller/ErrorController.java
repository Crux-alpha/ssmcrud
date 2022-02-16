package com.crux.ssmcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面请求处理器
 * 当服务器出现问题时，会通过该处理器将页面跳转到错误页面
 * @since 2022-2-5
 * @version 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/error")
public class ErrorController{

	/**
	 * 默认错误页面
	 */
	@RequestMapping
	public String defaultError(){
		return "error";
	}

	/**
	 * 员工错误页面
	 */
	@RequestMapping("/employee")
	public String employeeError(){
		return "employee_error";
	}
}
