package com.example.demo.service.reg;

import com.example.demo.mapper.RegMapper;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegServiceImpl implements RegService {
    @Autowired
    RegMapper regMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    DoctorMapper doctorMapper;

    @Override
    public String department_list() {
        List<Department> all;
        all=departmentMapper.selectAllDepartment();
        StringBuilder str = new StringBuilder();
        for(Department department:all){
            str.append("{\"department_id\":");
            str.append(department.getDepartmentId());
            str.append(",\n\"department_name\":\"");
            str.append(department.getDepartmentName());
            str.append("\"},");
        }
        str.deleteCharAt(str.length()-1);
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
        }
        catch (Exception e){
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

        List<Doctor> doctors = doctorMapper.selectByDepartmentId(department_id);
        StringBuilder str = new StringBuilder();
        for(Doctor doctor:doctors){
            str.append("{\"doctor_id\":");
            str.append(doctor.getDoctorId());
            str.append(",\n\"doctor_name\":\"");
            str.append(doctor.getDoctorName());
            str.append("\"},");
        }
        str.deleteCharAt(str.length()-1);
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
        }
        catch (Exception e){
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
    public String doctor_info(Long doctor_id) {
       return null;
    }


//    @Override
//    public String submit(Map<String, String> params) {
//        String date = params.get("date");
//        String department = params.get("department");
//        String doctor_id = params.get("doctor_id");
//        String period = params.get("period");
//
//        Patient patient = new Patient();
//        patient.setPatientId(null);
//        patient.setPatientUser(username);
//        patient.setPatientPassword(password);
//        patient.setPatientMobile(mobile);
//        patient.setBirthday(birthday);
//        patient.setAddress(address);
//        patient.setPatientName(patient_name);
//        patient.setPatientGender(patient_gender);
//        Long patient_id = 0L;
//        Patient result = patientMapper.selectByUsername(username);
//        try{
//            if(result==null){
//                patientMapper.insert(patient);
//                patient_id = patientMapper.selectByUsername(username).getPatientId();
//                return "{\n" +
//                        "    \"data\": {\n" +
//                        "        \"patient_id\":"+ patient_id +"\n" +
//                        "    },\n" +
//                        "    \"meta\": {\n" +
//                        "        \"msg\": \"注册成功\",\n" +
//                        "        \"status\": 201\n" +
//                        "    }\n" +
//                        "}";
//            }
//            else{
//                return "{\n" +
//                        "    \"data\": [],\n" +
//                        "    \"meta\": {\n" +
//                        "        \"msg\": \"用户已注册\",\n" +
//                        "        \"status\": 205\n" +
//                        "    }\n" +
//                        "}";
//            }
//        }
//        catch (Exception e){
//            return "{\n" +
//                    "    \"data\": [],\n" +
//                    "    \"meta\": {\n" +
//                    "        \"msg\": \"注册失败\",\n" +
//                    "        \"status\": 500\n" +
//                    "    }\n" +
//                    "}";
//        }
//    }
}
