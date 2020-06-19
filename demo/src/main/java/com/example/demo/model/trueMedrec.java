package com.example.demo.model;

import java.util.Date;

public class trueMedrec {
    private Long medrecId;

    private String doctorName;

    private Date attendDate;

    private String advice;

    private String conditions;

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

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
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
                ", attendDate:\"" + attendDate + "\"" +
                ", advice:\"" + advice + "\"" +
                ", conditions:\"" + conditions + "\"" +
                '}';
    }
}
