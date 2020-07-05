package com.example.demo.service.reg;

import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.mapper.RegMapper;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.model.Reg;
import com.example.demo.model.patientInfo;
import com.example.demo.service.Session.keySession;
import common.utils.RSA.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RegServiceImpl implements RegService {
    @Autowired
    RegMapper regMapper;
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    ApplicationContext context;

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
    public String patient_info(Long doctor_id){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String formate_date =  sdf.format(date);
        String true_date = formate_date.substring(0,10);
        String period = formate_date.substring(11,19);
        System.out.println(period);
//        if(period.compareTo("08:00:00")<0 || (period.compareTo("12:00:00")>0 && period.compareTo("14:00:00")<0) ||
//        period.compareTo("17:00:00")>0){
//            return "{\n" +
//                    "    \"data\": [],\n" +
//                    "    \"meta\": {\n" +
//                    "        \"msg\": \"不在有效工作时间内,无权查看！\",\n" +
//                    "        \"status\": 404\n" +
//                    "    }\n" +
//                    "}";
//        }


        int period_code = 1;
        if(period.compareTo("12:00:00")<=0){
            period_code = 0;
        }
        try{
            patientInfo result = regMapper.getRegPatientInfo(doctor_id,true_date,period_code,3);
            if(result==null){
                result = regMapper.getRegPatientInfo(doctor_id,true_date,period_code,2);
            }
            if(result==null){
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"暂无挂号病人\",\n" +
                        "        \"status\": 404\n" +
                        "    }\n" +
                        "}";
            }
            else{
                regMapper.updateState(result.getReg_id(),3);
                return "{\n" +
                        "    \"data\":" + result.toString() +",\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取挂号病人信息成功\",\n" +
                        "        \"status\": 200\n" +
                        "    }\n" +
                        "}";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取挂号病人信息失败\",\n" +
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
        reg.setState(1);
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

    @Override
    public String full_doctor_info(Long doctor_id){
        try{
            Doctor doctor = doctorMapper.selectByPrimaryKey(doctor_id);
//            String doctor_password = doctor.getDoctorPassword();
//            keySession keysession = context.getBean(keySession.class);
//            String publicKey = keysession.getPublicKey();
//            if(publicKey==null){
//                return "{\n" +
//                        "    \"data\": [],\n" +
//                        "    \"meta\": {\n" +
//                        "        \"msg\": \"请先获取公钥！\",\n" +
//                        "        \"status\": 500\n" +
//                        "    }\n" +
//                        "}";
//            }
//            String encoded_password = RSAUtils.encryptByPublicKey(doctor_password,publicKey);
            String department_name = departmentMapper.selectByPrimaryKey(doctor.getDepartmentId()).getDepartmentName();
//            doctor.setDoctorPassword(encoded_password);
            doctor.setDepartmentName(department_name);
            return "{\n" +
                    "    \"data\":\n" + doctor.toString() +
                    ",\n" +
                    "\t\"meta\":{\n" +
                    "    \t\"msg\": \"获取成功\",\n" +
                    "    \t\"status\": 200\n" +
                    "\t}\n" +
                    "}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取医生信息失败\",\n" +
                    "    \t\"status\": 500\n" +
                    "\t}\n" +
                    "}";
        }
    }

    @Override
    public String reg_list(Long patient_id) {
        List<Reg> regs;
        int total = regMapper.countByPatientIdWithoutState4(patient_id);
        if(total==0) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"无挂号可查看\",\n" +
                        "        \"status\": 404\n" +
                        "    }\n" +
                        "}";
            } catch (Exception e) {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取挂号列表失败\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
        }
        regs = regMapper.selectByPatientIdWithoutState4(patient_id);
        StringBuilder str = new StringBuilder();
        String period;
        SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat datetime_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Reg reg : regs) {
            str.append("{\"reg_id\":");
            str.append(reg.getRegId());
            str.append(",\n\"department_name\":\"");
            str.append(departmentMapper.selectByPrimaryKey(doctorMapper.selectByPrimaryKey(reg.getDoctorId()).getDepartmentId()).getDepartmentName());
            str.append("\",\n\"doctor_name\":\"");
            str.append(doctorMapper.selectByPrimaryKey(reg.getDoctorId()).getDoctorName());
            str.append("\",\n\"patient_name\":\"");
            str.append(patientMapper.selectByPrimaryKey(reg.getPatientId()).getPatientName());
            str.append("\",\n\"reg_time\":\"");
            str.append(datetime_fmt.format(reg.getRegTime()));
            str.append("\",\n\"res_date\":\"");
            str.append(date_fmt.format(reg.getResDate()));
            str.append("\",\n\"period\":\"");
            period=reg.getPeriod()?"下午":"上午";
            str.append(period);
            str.append("\",\n\"amount\":");
            str.append(reg.getAmount());
            str.append(",\n\"serial_num\":");
            str.append(reg.getSerialNum());
            str.append(",\n\"state\":");
            str.append(reg.getState());
            str.append("},");
        }
        str.deleteCharAt(str.length() - 1);
        try {
            return "{\n" +
                    "    \"data\": [\n" +
                    str +
                    "    ],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取挂号列表成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取挂号列表失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }
}
