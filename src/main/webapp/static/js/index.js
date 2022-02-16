appPath = location.pathname.substring(0, location.pathname.indexOf('/', 1));
empNameReg = /^[\u4E00-\u9FA5]{2,4}$/;
emailReg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
/**
 * 使用ajax请求员工信息发送给服务器，并接收JSON
 * @param event 请求参数
 */
function requestEmployeePageInfo(event){
	$.getJSON(appPath + "/employee", {params:"all", pageNum:event.pageNum, pageSize:event.pageSize, navigate:event.navigate}, function(json){
		if(json.code === 400){
			alert(json.msg);
			return;
		}
		var pageInfo = json.data.pageInfo;
		if(event.isFirstLoad) addData(pageInfo);    //如果首次进入页面，加载数据
		else updateData(pageInfo);  //更新数据
		//更新分页信息
		$("#page-msg>span:eq(0)").text(pageInfo.pageNum);
		$("#page-msg>span:eq(1)").text(pageInfo.pages);
		$("#page-msg>span:eq(2)").text(pageInfo.total);
		//$("#page-msg").text("当前第" + pageInfo.pageNum + "页,共" + pageInfo.pages + "页," + pageInfo.total + "条记录");
		//更新分页条
		var pageInfoLi = $("#page-info-ul>li");
		var firstPageLi = pageInfoLi.first().add(pageInfoLi.eq(1));
		var lastPageLi = pageInfoLi.last().add(pageInfoLi.eq(-2));
		firstPageLi.attr("class", pageInfo.isFirstPage ? "disabled" : null);
		firstPageLi.children().attr("class", pageInfo.isFirstPage ? null : "page-btn");
		lastPageLi.attr("class", pageInfo.isLastPage ? "disabled" : null);
		lastPageLi.children().attr("class", pageInfo.isLastPage ? null : "page-btn");
	});
}

/**
 * 获取员工
 * @param id 员工id
 * @param callback	包含员工的回调函数
 */
function getEmployee(id, callback){
	$.ajax({
		url : appPath + "/employee/" + id,
		type : "GET",
		async : false,
		success : function(json){
			if(json.code === 200) callback(json.data.employee);
			else alert(json.msg);
		},
		dataType : "json"
	})
}

/**
 * 添加/加载数据(首次进入页面)
 * 处理json
 * @param pageInfo 分页信息json
 */
function addData(pageInfo){
	//添加员工信息
	for(var i=0;i<pageInfo.list.length;i++){
		var emp = pageInfo.list[i];
		var empId = JSON.stringify(emp.empId);
		var empName = JSON.stringify(emp.empName);
		var gender = JSON.stringify(emp.gender) === '"M"' ? "男" : "女";
		var email = JSON.stringify(emp.email);
		var deptName = JSON.stringify(emp.dept.deptName);
		$(
			"<tr>" +
			"	<td><input type='checkbox' class='emp-checkbox'></td>" +
			"   <td>" + empId + "</td>" +
			"   <td>" + empName.substring(1, empName.length-1) + "</td>" +
			"   <td>" + gender + "</td>" +
			"   <td>" + email.substring(1, email.length-1) + "</td>" +
			"   <td>" + deptName.substring(1, deptName.length-1) + "</td>" +
			"   <td>" +
			"       <button class='btn btn-primary btn-sm' type='button'>" +
			"           <span class='glyphicon glyphicon-pencil'></span>修改" +
			"       </button>" +
			"       <button class='btn btn-danger btn-sm' type=\"button\">" +
			"           <spam class='glyphicon glyphicon-trash'></spam>删除" +
			"       </button>" +
			"   </td>" +
			"</tr>"
		).appendTo($("#employee-data-tbl"));
	}
	//添加分页条
	var pageInfoLi = $("#page-info-ul>li");
	for(var pn=0;pn<pageInfo.navigatepageNums.length;pn++){
		var num = pageInfo.navigatepageNums[pn];
		var li = $("<li><a href='#'>"+ num +"</a></li>");
		if(pageInfo.pageNum === num) li.attr("class", "active");
		else li.children().attr("class", "page-btn");
		li.insertBefore(pageInfoLi.eq(-2));
	}
}

/**
 * 执行更新数据(换页)操作
 * 处理json
 * @param pageInfo  分页信息json
 */
function updateData(pageInfo){
	var empDataTr = $("#employee-data-tbl tr:gt(0)");
	for(var i=0;i<empDataTr.length;i++){    //处理员工信息
		var empDataTd = empDataTr.eq(i).children();
		if(i < pageInfo.list.length){
			empDataTd.show();
			var emp = pageInfo.list[i];
			var empId = JSON.stringify(emp.empId);
			var empName = JSON.stringify(emp.empName);
			var gender = JSON.stringify(emp.gender) === '"M"' ? "男" : "女";
			var email = JSON.stringify(emp.email);
			var deptName = JSON.stringify(emp.dept.deptName);
			empDataTd.eq(1).text(empId);
			empDataTd.eq(2).text(empName.substring(1, empName.length-1));
			empDataTd.eq(3).text(gender);
			empDataTd.eq(4).text(email.substring(1, email.length-1));
			empDataTd.eq(5).text(deptName.substring(1, deptName.length-1));
		}else{
			empDataTd.hide();
		}
	}

	//处理分页条
	var pageInfoLi = $("#page-info-ul>li");
	for(var pn=0;pn<pageInfo.navigatepageNums.length;pn++){
		var num = pageInfo.navigatepageNums[pn];
		var li = pageInfoLi.eq(pn + 2);
		if(li.is(pageInfoLi.eq(-2))) $("<li><a href='#'>"+ num +"</a></li>").insertBefore(pageInfoLi.eq(-2));
		else li.children().text(num);
		li.attr("class", pageInfo.pageNum === num ? "active" : null);
		li.children().attr("class", pageInfo.pageNum === num ? null : "page-btn");
	}
}

/**
 * 更新模态框中的部门列表
 * @param event 请求参数
 * @param selectElement 下拉列表元素
 * @param checkedDept 要选中的部门名(如果有)
 */
function updateDepartment(event, selectElement, checkedDept){
	selectElement.empty();
	$.ajax({
		url : appPath + "/department",
		type : "GET",
		async : false,
		data : {params:event.params},
		success : function(json){
			if(json.code === 400){
				alert(json.msg);
				return;
			}
			var deptList = json.data.departmentList;
			for(var i=0;i<deptList.length;i++){
				var option = $("<option></option>");
				var deptName = deptList[i].deptName;
				var deptId = deptList[i].deptId;
				option.val(deptId);
				option.text(deptName);
				if(deptId === checkedDept) option.prop("selected", true);
				option.appendTo(selectElement);
			}
		},
		dataType : "json"
	})
}

/**
 * 更新表单提示信息
 * @param inputElement 要更新的input
 * @param status 状态，true表示成功，false表示有错误
 * @param message 要提示的信息
 */
function checkFormTips(inputElement, status, message){
	if(status){
		inputElement.parent("div").removeClass("has-error");
		inputElement.next("span").empty();
	}else{
		inputElement.parent("div").addClass("has-error");
		inputElement.next("span").text(message);
	}
}

/**
 * 更改员工模态框的状态
 * 模态框有新增员工和修改员工两种状态，通过isPut区分
 * @param isPut 是否是修改员工，否则就是新增员工
 * @param empId 如果是修改员工，传入员工编号以便回显数据
 */
function modalPattern(isPut, empId){
	var nameIpt = $("#emp-name-ipt");
	var emailIpt = $("#emp-email-ipt");
	checkFormTips(nameIpt, true);
	checkFormTips(emailIpt, true);
	var formAllIpt = $("#emp-form input");
	//var idIpt = formAllIpt.filter("[name=empId]");
	var methodIpt = formAllIpt.filter("[name=_method]");
	var genderIpt = formAllIpt.filter("[name=gender]");
	$("#employee").text(isPut ? "修改员工" : "新增员工");
	methodIpt.prop("disabled", !isPut);
	nameIpt.prop("readonly", isPut);
	//idIpt.prop("disabled", !isPut);
	var saveBtn = $("#emp-save-btn");
	if(isPut){
		saveBtn.attr("save-id", empId);
		getEmployee(empId, function(emp){
			nameIpt.val(emp.empName);
			var isMen = emp.gender === "M";
			genderIpt.first().prop("checked", isMen);
			genderIpt.last().prop("checked", !isMen);
			emailIpt.val(emp.email);
			updateDepartment({params:"all"}, $("#emp-form select[name=deptId]"), emp.deptId);
		})
	}else{
		saveBtn.removeAttr("save-id");
		genderIpt.first().prop("checked", true);
		formAllIpt.filter("[type=text]").val(null);
		updateDepartment({params:"all"}, $("#emp-form select[name=deptId]"));
	}
}