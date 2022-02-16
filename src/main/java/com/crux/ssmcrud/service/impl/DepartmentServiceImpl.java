package com.crux.ssmcrud.service.impl;

import com.crux.ssmcrud.bean.Department;
import com.crux.ssmcrud.dao.DepartmentMapper;
import com.crux.ssmcrud.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{

	private final DepartmentMapper departmentMapper;

	public DepartmentServiceImpl(DepartmentMapper departmentMapper){
		this.departmentMapper = departmentMapper;
	}

	@Override
	public List<Department> getDepartmentAll(){
		return departmentMapper.selectByExample(null);
	}

	@Override
	public PageInfo<Department> getDepartmentPage(int pageNum, int pageSize, int navigatePages){
		PageHelper.startPage(pageNum, pageSize);
		return PageInfo.of(getDepartmentAll(), navigatePages);
	}
}
