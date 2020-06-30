package com.example.demo.service.home;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public String doctor_list(String department_id, int total, int add_num) {
        List<Doctor> doctors;
        int start=total;
        int cnt;
        if(department_id.length()==0) {
            cnt=doctorMapper.count();
        }
        else {
            cnt=doctorMapper.countByDepartmentId(Long.parseLong(department_id));
        }
        if(cnt==0) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"没有符合要求的医生\",\n" +
                        "        \"status\": 404\n" +
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
        if(start>cnt-1) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"查询超出范围\",\n" +
                        "        \"status\": 400\n" +
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
        if(department_id.length()==0) {
            doctors = doctorMapper.selectOnPage(start, add_num);
        }
        else {
            doctors=doctorMapper.selectByDepartmentIdOnPage(Long.parseLong(department_id),start,add_num);
        }
        StringBuilder str = new StringBuilder();
        byte[] doctor_photo;
        String encoded_photo;
        Base64.Encoder encoder;
        for (Doctor doctor : doctors) {
            str.append("{\"doctor_id\":");
            str.append(doctor.getDoctorId());
            str.append(",\n\"doctor_name\":\"");
            str.append(doctor.getDoctorName());
            str.append("\",\n\"department_name\":\"");
            str.append(departmentMapper.selectByPrimaryKey(doctor.getDepartmentId()).getDepartmentName());
            str.append("\",\n\"doctor_info\":\"");
            str.append(doctor.getDoctorIntro());
            str.append("\",\n\"doctor_email\":\"");
            str.append(doctor.getDoctorEmail());
            str.append("\",\n\"doctor_mobile\":\"");
            str.append(doctor.getDoctorMobile());
            str.append("\",\n\"doctor_tel\":\"");
            str.append(doctor.getDoctorTel());
            doctor_photo = doctor.getDoctorPho();
            encoded_photo = "";
            if (doctor_photo != null) {
                encoder = Base64.getEncoder();
                encoded_photo = "data:image/jpeg;base64," + encoder.encodeToString(doctor_photo);
            }
            str.append("\",\n\"doctor_photo\":\"");
            str.append(encoded_photo);
            str.append("\",\n\"doctor_gender\":\"");
            str.append(doctor.getDoctorGender()?"男":"女");
            str.append("\"},");
        }
        str.deleteCharAt(str.length() - 1);
        total += doctors.size();
        boolean at_end=cnt==total;
        try {
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"total\":" + total + ",\n" +
                    "        \"at_end\":" + at_end + ",\n" +
                    "        \"doctor\":[" + str + "],\n" +
                    "    },\n" +
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
}
