package com.example.demo.model;

import java.util.Date;

public class Patient {
    private Integer patientId;

    private String patientUser;

    private String patientPassword;

    private String patientMobile;

    private Date birthday;

    private String address;

    private String patientName;

    private Boolean patientGender;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(String patientUser) {
        this.patientUser = patientUser == null ? null : patientUser.trim();
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword == null ? null : patientPassword.trim();
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile == null ? null : patientMobile.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public Boolean getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Boolean patientGender) {
        this.patientGender = patientGender;
    }
}