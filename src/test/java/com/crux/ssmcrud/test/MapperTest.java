package com.crux.ssmcrud.test;

import com.crux.ssmcrud.bean.Department;
import com.crux.ssmcrud.bean.Employee;
import com.crux.ssmcrud.dao.DepartmentMapper;
import com.crux.ssmcrud.dao.EmployeeMapper;
import com.crux.ssmcrud.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)	//执行该类的单元测试时使用SpringExtension来完成测试
@ContextConfiguration("classpath:spring.xml")	//spring.xml文件路径
public class MapperTest{

	@Autowired
	protected DepartmentMapper deptMapper;
	@Autowired
	protected EmployeeMapper empMapper;
	//@Autowired
	protected SqlSession batchSqlSession;
	@Autowired
	protected EmployeeService employeeService;

	@Test
	protected void testBeanGen(){
		System.out.println(deptMapper);
	}

	@Test
	protected void testInsertDepartment(){
		int result = 0;
		result += deptMapper.insertSelective(new Department("开发部"));
		result += deptMapper.insertSelective(new Department("测试部"));
		System.out.println(result);
	}

	@Test
	protected void testInsertEmployee(){
		int result = 0;
		var empMapper = batchSqlSession.getMapper(EmployeeMapper.class);
		for(int id = 1;id <= 1000;id++){
			var uuid = UUID.randomUUID();
			String name = uuid.toString().substring(0, 5);
			String gender = id % 3 == 0 ? "M" : "F";
			result += empMapper.insertSelective(new Employee(id, name, gender, name + "@crux.com", 1));
		}
		System.out.println(result);
	}

	@Test
	protected void testService(){
		employeeService.getEmployeeAll().forEach(System.out::println);
	}

	@Test
	protected void testPageHelper(){
		PageInfo<Employee> pageInfo = employeeService.getEmployeePage(1, 5, 5);
	}

	@Test
	protected void testDeleteEmployee(){
		int i = 0;
		i += employeeService.deleteEmployee(1002);
		i += employeeService.deleteEmployee(1003);
		i += employeeService.deleteEmployee(1004);
		System.out.println(i);
	}
}
