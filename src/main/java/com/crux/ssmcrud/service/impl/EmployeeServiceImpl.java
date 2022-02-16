package com.crux.ssmcrud.service.impl;

import com.crux.ssmcrud.bean.BeanAllPropertyException;
import com.crux.ssmcrud.bean.Employee;
import com.crux.ssmcrud.bean.EmployeeExample;
import com.crux.ssmcrud.dao.EmployeeMapper;
import com.crux.ssmcrud.service.EmployeeService;
import com.crux.ssmcrud.utils.ExceptionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工业务层
 * @since 2022-1-25
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeMapper employeeMapper;

	public EmployeeServiceImpl(EmployeeMapper employeeMapper){
		this.employeeMapper = employeeMapper;
	}

	@Override
	public List<Employee> getEmployeeAll(){
		return employeeMapper.selectCompleteByExample(null);
	}

	@Override
	public PageInfo<Employee> getEmployeePage(int pageNum, int pageSize, int navigatePages){
		PageHelper.startPage(pageNum, pageSize);
		return PageInfo.of(employeeMapper.selectCompleteByExample(null), navigatePages);
	}

	@Override
	public Employee getEmployee(int id){
		return employeeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Employee getEmployee(String name){
		var example = new EmployeeExample();
		example.createCriteria().andEmpNameEqualTo(name);
		return employeeMapper.selectCompleteByExample(example).get(0);
	}

	@Override
	public int addEmployee(Employee employee) throws BeanAllPropertyException{
		ExceptionUtils.checkBeanRequireNonNull(employee, "empId", "dept");
		var rules = ExceptionUtils.getEmptyRuleMap(String.class);
		rules.put("empName", v -> existsName(v) ? "员工名已存在" : null);
		rules.put("email", v -> existsEmail(v) ? "邮箱已存在" : null);
		return employeeMapper.insertSelective(ExceptionUtils.checkBeanRules(employee, rules));
	}

	@Override
	public int updateEmployee(Employee employee) throws BeanAllPropertyException{
		var rules = ExceptionUtils.getEmptyRuleMap(String.class);
		rules.put("email", v -> existsEmail(v) ? "邮箱已存在" : null);
		return employeeMapper.updateByPrimaryKeySelective(ExceptionUtils.checkBeanRules(employee, rules));
	}

	@Override
	public int deleteEmployee(int id){
		return employeeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int batchDeleteEmployee(List<Integer> ids){
		var example = new EmployeeExample();
		example.createCriteria().andEmpIdIn(ids);
		return employeeMapper.deleteByExample(example);
	}

	@Override
	public boolean existsName(String name){
		var example = new EmployeeExample();
		example.createCriteria().andEmpNameEqualTo(name);
		return employeeMapper.countByExample(example) > 0;
	}

	@Override
	public boolean existsEmail(String email){
		var example = new EmployeeExample();
		example.createCriteria().andEmailEqualTo(email);
		return employeeMapper.countByExample(example) > 0;
	}
}