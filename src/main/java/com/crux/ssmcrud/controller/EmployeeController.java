package com.crux.ssmcrud.controller;

import com.crux.ssmcrud.bean.Employee;
import com.crux.ssmcrud.service.EmployeeService;
import com.crux.ssmcrud.utils.ControllerUtils;
import com.crux.ssmcrud.utils.ResponseMessage;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 员工前端控制器
 * @since 2022-1-25
 * @version 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController{

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService){
		this.employeeService = employeeService;
	}

	/**
	 * 根据分页信息查询员工
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示记录数
	 * @param navigatePages 分页条
	 * @param model 模型
	 * @return 转发到list.html页面
	 */
	@GetMapping({"/list", "/list.html"})
	public String getDefaultEmployeePage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
										 @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
										 @RequestParam(value = "navigate", defaultValue = "5") int navigatePages,
										 Model model){
		PageInfo<?> pageInfo = employeeService.getEmployeePage(pageNum, pageSize, navigatePages);
		model.addAttribute("pageInfo", pageInfo);
		return "list";
	}

	/**
	 * ajax请求员工分页信息
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @param navigatePages 分页条长度
	 * @return 员工分页数据
	 */
	@ResponseBody
	@GetMapping(params = "params=all")
	public ResponseMessage<String,PageInfo<Employee>> getAjaxEmployeePage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
																		  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
																		  @RequestParam(value = "navigate", defaultValue = "5") int navigatePages){
		return ResponseMessage.success("pageInfo", employeeService.getEmployeePage(pageNum, pageSize, navigatePages));
	}

	@ResponseBody
	@GetMapping("/{id}")
	public ResponseMessage<String,Employee> getEmployeeById(@PathVariable("id") int id){
		Employee e = employeeService.getEmployee(id);
		return Objects.isNull(e) ?
				ResponseMessage.failure("找不到id为" + id + "的员工", null, null) :
				ResponseMessage.success("employee", e);
	}

	/**
	 * 添加员工
	 * @param employee 根据请求参数：empName,gender,email,deptId封装为员工实体，参数不能包含empId；并通过JSR303检查员工实体的合法性
	 * @param result 通过JSR303校验员工后封装的结果
	 * @return 响应信息
	 */
	@ResponseBody
	@PostMapping(params = {"empName", "gender", "email", "deptId", "!empId"})
	public ResponseMessage<String,String> addEmployee(@Valid Employee employee, BindingResult result){
		/*if(result.hasErrors()){
			//Map<String,String> data = new HashMap<>();
			//result.getFieldErrors().forEach(f -> data.put(f.getField(), f.getDefaultMessage()));
			return ResponseMessage.failure("未能成功保存", ControllerUtils.getErrorData(result));
		}*/
		var data = ControllerUtils.getErrorData(result);
		if(data != null){
			return ResponseMessage.failure("未能成功保存", data);
		}
		employeeService.addEmployee(employee);
		return ResponseMessage.success("保存成功", null, null);
	}

	/**
	 * 修改员工
	 * @param employee 根据请求参数：empId,empName,gender,email,deptId封装为员工实体；并通过JSR303检查员工实体的合法性
	 * @param result 通过JSR303校验员工后封装的结果
	 * @return 响应信息
	 */
	@ResponseBody
	@PutMapping("/{id}")
	public ResponseMessage<String,String> updateEmployee(@PathVariable("id") int id, @Valid Employee employee, BindingResult result){
		employee.setEmpId(id);
		var data = ControllerUtils.getErrorData(result);
		if(data != null){
			return ResponseMessage.failure("未能成功保存", data);
		}
		employeeService.updateEmployee(employee);
		return ResponseMessage.success("保存成功", null, null);
	}

	/**
	 * 删除员工
	 * @param ids 要删除的id，如果为多个，则用&分隔
	 * @param batch 是否批量删除，即一次删除多个员工
	 * @return 响应信息
	 */
	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseMessage<?,?> deleteEmployee(@PathVariable("id") String ids,
											   @RequestParam(name = "batch", required = false) boolean batch){
		if(batch){
			var idList = Arrays.stream(ids.split("&")).map(Integer::parseInt).collect(Collectors.toList());
			int result = employeeService.batchDeleteEmployee(idList);
			return ResponseMessage.success("删除了" + result + "个员工", "result", result);
		}else{
			int result = employeeService.deleteEmployee(Integer.parseInt(ids));
			return result < 1 ? ResponseMessage.failure("没有id为" + ids + "的员工") : ResponseMessage.success("删除成功");
		}
	}

	/*@ResponseBody
	@DeleteMapping
	public ResponseMessage<String,Integer> batchDeleteEmployee(@RequestParam("id[]") int[] ids){
		int result = employeeService.batchDeleteEmployee(ids);
		return ResponseMessage.success("删除了" + result + "个员工", "result", result);
	}*/
}
