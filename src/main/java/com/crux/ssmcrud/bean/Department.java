package com.crux.ssmcrud.bean;

import java.util.Objects;

public class Department {

    private Integer deptId;
    private String deptName;

    public Department(){
    }

    public Department(String deptName){
        this(null, deptName);
    }

    public Department(Integer deptId, String deptName){
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    @Override
    public final boolean equals(Object obj){
        if(this == obj) return true;
        if(obj instanceof Department d) return Objects.equals(deptId, d.deptId);
        return false;
    }

    @Override
    public final int hashCode(){
        return Objects.hash(deptId);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() +
                "[编号=" + deptId +
                ",名称=" + deptName + ']';
    }
}