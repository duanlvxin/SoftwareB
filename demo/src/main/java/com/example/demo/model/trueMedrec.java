package com.example.demo.model;

public class trueMedrec {
    private Long medrecId;

    private String doctorName;

    private String attendDate;

    private String conditions;

    private String departmentName;

    public String getDepartmentName(){
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }

    public Long getMedrecId() {
        return medrecId;
    }

    public void setMedrecId(Long medrecId) {
        this.medrecId = medrecId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(String attendDate) {
        this.attendDate = attendDate;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions == null ? null : conditions.trim();
    }

    @Override
    public String toString() {
        return "{" +
                "medrecId:" + medrecId +
                ", doctorName:\"" + doctorName + "\"" +
                ", departmentName:\"" + departmentName + "\"" +
                ", attendDate:\"" + attendDate + "\"" +
                ", conditions:\"" + conditions + "\"" +
                '}';
    }
}
