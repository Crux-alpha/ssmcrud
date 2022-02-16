package com.crux.ssmcrud.service;

import com.crux.ssmcrud.bean.Department;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DepartmentService{

	List<Department> getDepartmentAll();

	PageInfo<Department> getDepartmentPage(int pageNum, int pageSize, int navigatePages);
}
