package com.example.demo.model;

import common.utils.age.computeAgeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class patientInfo {

    private Long patient_id;
    private String patient_name;
    private Date birthday;
    private int patient_gender;
    private String address;
    private Long reg_id;

    public Date getBirthday() {
        return birthday;
    }

    public int getPatient_gender() {
        return patient_gender;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public Long getReg_id() {
        return reg_id;
    }

    public String getAddress() {
        return address;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPatient_gender(int patient_gender) {
        this.patient_gender = patient_gender;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public void setReg_id(Long reg_id) {
        this.reg_id = reg_id;
    }

    @Override
    public String toString(){
        String truePatientGender = patient_gender==1?"男":"女";
        computeAgeHelper ageHelper = new computeAgeHelper();
        int age = 0;
        String formatBirthday = "";
        try{
            Date format=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(birthday.toString());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            formatBirthday = sdf.format(format);
            age = ageHelper.computeAge(birthday);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{" +
                " patient_id:" + patient_id +
                " ,reg_id:" + reg_id+
                ", patient_name:\"" + patient_name + "\"" +
                ", birthday:\"" + formatBirthday + "\"" +
                ", patient_age:\"" + age + "\"" +
                ", address:\"" + address + "\"" +
                ", patient_gender:\"" + truePatientGender + "\"" +
                '}';
    }
}
