package com.example.demo.service.reg;

import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.RegMapper;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.model.Reg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RegServiceImpl implements com.example.demo.reg.RegService {
    @Autowired
    RegMapper regMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    DoctorMapper doctorMapper;

    @Override
    public String department_list() {
        List<Department> all;
        all = departmentMapper.selectAllDepartment();
        StringBuilder str = new StringBuilder();
        for (Department department : all) {
            str.append("{\"department_id\":");
            str.append(department.getDepartmentId());
            str.append(",\n\"department_name\":\"");
            str.append(department.getDepartmentName());
            str.append("\"},");
        }
        str.deleteCharAt(str.length() - 1);
        try {
            return "{\n" +
                    "    \"data\": [\n" +
                    str +
                    "    ],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取科室列表成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取科室列表失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String doctor_list(Long department_id) {
//        Date date = java.sql.Date.valueOf(params.get("date"));
        System.out.println(department_id);
        List<Doctor> doctors = doctorMapper.selectByDepartmentId(department_id);
        StringBuilder str = new StringBuilder();
        for (Doctor doctor : doctors) {
            str.append("{\"doctor_id\":");
            str.append(doctor.getDoctorId());
            str.append(",\n\"doctor_name\":\"");
            str.append(doctor.getDoctorName());
            str.append("\"},");
        }
        str.deleteCharAt(str.length() - 1);
        try {
            return "{\n" +
                    "    \"data\": [\n" +
                    str +
                    "    ],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取医生列表成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取医生列表失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String doctor_info(Long doctor_id, Date res_date) {
        Doctor doctor = doctorMapper.selectByPrimaryKey(doctor_id);
        Long department_id = doctor.getDepartmentId();
        String doctor_name = doctor.getDoctorName();
        String doctor_intro = doctor.getDoctorIntro() == null ? "" : doctor.getDoctorIntro();
        String doctor_email = doctor.getDoctorEmail() == null ? "" : doctor.getDoctorEmail();
        String doctor_mobile = doctor.getDoctorMobile() == null ? "" : doctor.getDoctorMobile();
        String doctor_tel = doctor.getDoctorTel() == null ? "" : doctor.getDoctorTel();
        String doctor_gender = doctor.getDoctorGender() ? "男" : "女";
        byte[] doctor_photo = doctor.getDoctorPho();
        System.out.println("doctorphoto:" + Arrays.toString(doctor_photo));
        String encoded_photo = "";
        if (doctor_photo != null) {
            final Base64.Encoder encoder = Base64.getEncoder();
            encoded_photo = "data:image/jpeg;base64," + encoder.encodeToString(doctor_photo);
        }
        int am_remainder = 5 - regMapper.countReserved(doctor_id, res_date, false);
        int pm_remainder = 5 - regMapper.countReserved(doctor_id, res_date, true);
        try {
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"department_id\":" + department_id + ",\n" +
                    "        \"doctor_name\":\"" + doctor_name + "\",\n" +
                    "        \"doctor_intro\":\"" + doctor_intro + "\",\n" +
                    "        \"doctor_email\":\"" + doctor_email + "\",\n" +
                    "        \"doctor_mobile\":\"" + doctor_mobile + "\",\n" +
                    "        \"doctor_tel\":\"" + doctor_tel + "\",\n" +
                    "        \"doctor_gender\":\"" + doctor_gender + "\",\n" +
                    "        \"doctor_photo\":\"" + encoded_photo + "\",\n" +
                    "        \"am_remainder\":" + am_remainder + ",\n" +
                    "        \"pm_remainder\":" + pm_remainder + ",\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取医生信息成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取医生信息失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }


    @Override
    public String reg_submit(Map<String, String> params) {
        Long patient_id = Long.parseLong(params.get("patient_id"));
        Long doctor_id = Long.parseLong(params.get("doctor_id"));
        Date res_date = java.sql.Date.valueOf(params.get("res_date"));
        Boolean period = Boolean.parseBoolean(params.get("period"));

        Reg reg = new Reg();
        reg.setRegId(null);
        reg.setDoctorId(doctor_id);
        reg.setPatientId(patient_id);
        Date cur_time = new Date();
        reg.setRegTime(cur_time);
        reg.setResDate(res_date);
        reg.setPeriod(period);
        reg.setAmount((float) 15.00);
        reg.setState(0);
        int reserved = regMapper.countReserved(doctor_id, res_date, period);
        reg.setSerialNum(reserved + 1);
        Long reg_id = 0L;


        try {
            regMapper.insert(reg);
            reg_id = regMapper.selectByResDatePeriodSerialNum(res_date, period, reserved + 1).getRegId();
            SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reg_time = myfmt.format(cur_time);
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"reg_id\":" + reg_id + ",\n" +
                    "        \"reg_time\":\"" + reg_time + "\",\n" +
                    "        \"amount\":" + 15.00 + "\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"挂号成功\",\n" +
                    "        \"status\": 201\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"挂号失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }
}
