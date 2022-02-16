package com.crux.ssmcrud.controller;

import com.crux.ssmcrud.bean.Department;
import com.crux.ssmcrud.service.DepartmentService;
import com.crux.ssmcrud.utils.ResponseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门请求处理器
 * @since 2022-1-27
 * @version 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/department")
public class DepartmentController{

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService){
		this.departmentService = departmentService;
	}

	/**
	 * ajax请求获取所有部门信息
	 * @return 所有部门
	 */
	@ResponseBody
	@GetMapping(params = "params=all")
	public ResponseMessage<String,List<Department>> getDepartmentAll(){
		return ResponseMessage.success("departmentList", departmentService.getDepartmentAll());
	}
}
