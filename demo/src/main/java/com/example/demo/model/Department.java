package com.example.demo.model;

public class Department {
    private Long departmentId;

    private String departmentName;

    private String departmentIntro;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentIntro() {
        return departmentIntro;
    }

    public void setDepartmentIntro(String departmentIntro) {
        this.departmentIntro = departmentIntro == null ? null : departmentIntro.trim();
    }
}