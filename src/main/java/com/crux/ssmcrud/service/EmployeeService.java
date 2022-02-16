package com.crux.ssmcrud.service;

import com.crux.ssmcrud.bean.BeanAllPropertyException;
import com.crux.ssmcrud.bean.Employee;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 员工业务层
 * @version 1.0-SNAPSHOT
 * @since 2022-1-25
 */
public interface EmployeeService{
	/**
	 * 获取所有员工
	 * @return 所有员工的集合
	 */
	List<Employee> getEmployeeAll();

	/**
	 * 获取指定页码的员工分页信息
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @param navigatePages 分页条长度
	 * @return 员工分页信息
	 */
	PageInfo<Employee> getEmployeePage(int pageNum, int pageSize, int navigatePages);

	/**
	 * 获取指定id的员工
	 * @param id 员工ID
	 * @return 此员工
	 */
	Employee getEmployee(int id);

	/**
	 * 获取指定姓名的员工
	 * @param name 员工姓名
	 * @return 此员工
	 */
	Employee getEmployee(String name);

	/**
	 * 添加员工
	 * @param employee 员工信息，必须包括姓名、邮箱、性别、部门编号信息，必须不含编号
	 * @return 影响数据的条数
	 * @throws BeanAllPropertyException 如果姓名、邮箱、性别、部门编号有一项为空值，或者姓名已存在，则抛出此异常表示错误的员工属性
	 */
	int addEmployee(Employee employee) throws BeanAllPropertyException;

	/**
	 * 修改员工
	 * @param employee 员工信息
	 * @return 影响数据的条数
	 * @throws BeanAllPropertyException 如果姓名、邮箱已存在，则抛出此异常表示错误的员工属性
	 */
	int updateEmployee(Employee employee) throws BeanAllPropertyException;

	/**
	 * 删除员工
	 * @param id 员工ID
	 * @return 影响数据的条数
	 */
	int deleteEmployee(int id);

	/*default int batchDeleteEmployee(int[] ids){
		int index = 0;
		for(int id : ids) index += deleteEmployee(id);
		return index;
	}*/

	/**
	 * 批量删除员工
	 * @param ids 员工ID集合
	 * @return 影响数据的条数
	 */
	int batchDeleteEmployee(List<Integer> ids);

	/**
	 * 检查员工姓名是否存在
	 * @param name 员工姓名
	 * @return 如果存在，返回true
	 */
	boolean existsName(String name);

	/**
	 * 检查邮箱是否存在
	 * @param email 邮箱
	 * @return 如果存在，返回true
	 */
	boolean existsEmail(String email);
}
