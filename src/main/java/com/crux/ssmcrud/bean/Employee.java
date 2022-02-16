package com.crux.ssmcrud.bean;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Employee {

    private Integer empId;
    @Pattern(regexp = "(^[\u4E00-\u9FA5]{2,4}$)", message = "非法的员工姓名")
    private String empName;
    private String gender;
    @Email(message = "非法的邮箱格式")
    private String email;
    private Integer deptId;
    private Department dept;

    public Employee(){}

    public Employee(String empName, String gender, String email, Integer deptId){
        this(null, empName, gender, email, deptId);
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer deptId){
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.deptId = deptId;
    }

    public Employee(Integer empId, String empName, String gender, String email, Department dept){
        this(empId, empName, gender, email, dept != null ? dept.getDeptId() : null);
        this.dept = dept;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Department getDept(){
        return dept;
    }

    public void setDept(Department dept){
        this.dept = dept;
    }

    @Override
    public final boolean equals(Object obj){
        if(this == obj) return true;
        if(obj instanceof Employee e) return Objects.equals(empId, e.empId);
        return false;
    }

    @Override
    public final int hashCode(){
        return Objects.hash(empId);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() +
                "[编号=" + empId +
                ",姓名=" + empName +
                ",性别=" + gender +
                ",邮箱=" + email +
                ",部门=" + (dept == null ? deptId : dept) + ']';
    }
}