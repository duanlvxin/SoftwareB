package com.example.demo.model;

import java.util.Base64;

public class Doctor {
    private Long doctorId;

    private Long departmentId;
    private String departmentName;

    private String doctorName;

    private String doctorUser;

    private String doctorPassword;

    private String doctorIntro;

    private String doctorEmail;

    private String doctorMobile;

    private String doctorTel;

    private Boolean doctorGender;

    private byte[] doctorPho;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

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
        this.departmentName = departmentName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }

    public String getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(String doctorUser) {
        this.doctorUser = doctorUser == null ? null : doctorUser.trim();
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword == null ? null : doctorPassword.trim();
    }

    public String getDoctorIntro() {
        return doctorIntro;
    }

    public void setDoctorIntro(String doctorIntro) {
        this.doctorIntro = doctorIntro == null ? null : doctorIntro.trim();
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail == null ? null : doctorEmail.trim();
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile == null ? null : doctorMobile.trim();
    }

    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel == null ? null : doctorTel.trim();
    }

    public Boolean getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(Boolean doctorGender) {
        this.doctorGender = doctorGender;
    }

    public byte[] getDoctorPho() {
        return doctorPho;
    }

    public void setDoctorPho(byte[] doctorPho) {
        this.doctorPho = doctorPho;
    }

    @Override
    public String toString(){
        String doctor_gender = doctorGender ? "男" : "女";
        doctorIntro = doctorIntro==null?"":doctorIntro;
        String encoded_photo = "";
        if (doctorPho != null) {
            final Base64.Encoder encoder = Base64.getEncoder();
            encoded_photo = "data:image/jpeg;base64," + encoder.encodeToString(doctorPho);
        }
        return "{\n" +
                "        \"doctor_id\":"+ doctorId +",\n" +
                "        \"doctor_name\":\""+ doctorName +"\",\n" +
                "        \"doctor_user\":\""+ doctorUser +"\",\n" +
                "        \"doctor_password\":\""+ doctorPassword +"\",\n" +
                "        \"department_name\":\"" + departmentName + "\",\n" +
                "        \"doctor_email\":\""+ doctorEmail +"\",\n" +
                "        \"doctor_mobile\":\"" + doctorMobile +"\",\n" +
                "        \"doctor_tel\":\""+ doctorTel +"\",\n" +
                "        \"doctor_gender\":\""+ doctor_gender + "\",\n" +
                "        \"doctor_intro\":\""+ doctorIntro +"\",\n" +
                "        \"doctor_pho\":\""+ encoded_photo +"\"\n" +
                "\t}";
    }
}