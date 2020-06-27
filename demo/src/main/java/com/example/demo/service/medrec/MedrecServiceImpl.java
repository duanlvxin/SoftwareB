package com.example.demo.service.medrec;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MedrecServiceImpl implements MedrecService {

    @Autowired
    MedrecMapper medrecMapper;

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    PatientMapper patientMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    PrescribeMapper prescribeMapper;

    @Autowired
    RegMapper regMapper;

    @Override
    public String getAllMedrec(Long patient_id){
        try{
            List<Medrec> result = medrecMapper.selectByPatientId(patient_id);
            String data = "";
            if(result.size()==0){
                return "{\n" +
                        "    \"data\": {\n" +
                        "        \" patient_id \": " + patient_id + ",\n" +
                        "        \" medrec\":[\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取成功\",\n" +
                        "        \"status\": 200\n" +
                        "    }\n" +
                        "}";
            }
            StringBuilder medrecData = new StringBuilder();
            trueMedrec trueMedrecItem = new trueMedrec();
            System.out.println(result.size());
            for(Medrec medrec:result){
                trueMedrecItem.setMedrecId(medrec.getMedrecId());
                //这里需要获取医生名字
                String doctor_name = doctorMapper.selectByPrimaryKey(medrec.getDoctorId()).getDoctorName();
                String department_name = departmentMapper.selectByPrimaryKey(doctorMapper.selectByPrimaryKey(medrec.getDoctorId()).getDepartmentId()).getDepartmentName();
                trueMedrecItem.setDoctorName(doctor_name);
                trueMedrecItem.setDepartmentName(department_name);
                SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String attendDate = myfmt.format(medrec.getAttendDate());
                trueMedrecItem.setAttendDate(attendDate);
                System.out.println(attendDate);
                trueMedrecItem.setConditions(medrec.getConditions());
                medrecData.append(trueMedrecItem.toString());
                medrecData.append(",");
            }
            medrecData.delete(medrecData.length()-1,medrecData.length());
            System.out.println(medrecData);
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \" patient_id \": " + patient_id + ",\n" +
                    "        \" medrec\":[" + medrecData + "]\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取所有病历失败!\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String getSingleMedrec(Long medrec_id){
        try{
            Medrec result = medrecMapper.selectByPrimaryKey(medrec_id);
            if(result==null){
                return "{\n" +
                        "    \"data\": {\n" +
                        "    },\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"该病例不存在！\",\n" +
                        "        \"status\": 404\n" +
                        "    }\n" +
                        "}";
            }
            String patient_name = patientMapper.selectByPrimaryKey(result.getPatientId()).getPatientName();
            String codition = result.getConditions();
            String advice = result.getAdvice();
            java.util.Date attend_date = result.getAttendDate();
            SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_attend_date = myfmt.format(attend_date);
            String department_name = departmentMapper.selectByPrimaryKey(doctorMapper.selectByPrimaryKey(result.getDoctorId()).getDepartmentId()).getDepartmentName();

            Doctor doctor = doctorMapper.selectByPrimaryKey(result.getDoctorId());
            String doctor_name = doctor.getDoctorName();
            String doctor_mobile =doctor.getDoctorMobile();
            String doctor_email = doctor.getDoctorEmail();

            String data = "";
            StringBuilder drugsData = new StringBuilder();
            List<trueDrug> drugs = medrecMapper.getSingleMedrec(medrec_id);
            System.out.println("size:"+drugs.size());
            for(trueDrug drug:drugs){
                drugsData.append(drug.toString());
                drugsData.append(",");
            }
            System.out.println("drugsData:"+drugsData);
            drugsData.delete(drugsData.length()-1,drugsData.length());
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"medrec_id \": " + medrec_id + ",\n" +
                    "        \"attend_date \": \"" + format_attend_date +"\",\n" +
                    "        \"patient_name\": \""+ patient_name +"\",\n" +
                    "        \"doctor_name \": \"" + doctor_name +"\",\n" +
                    "        \"doctor_mobile \": \"" + doctor_mobile +"\",\n" +
                    "        \"doctor_email \": \"" + doctor_email +"\",\n" +
                    "        \"department_name \": \"" + department_name +"\",\n" +
                    "        \"condition\": \""+ codition +"\",\n" +
                    "        \"advice\": \""+ advice +"\",\n" +
                    "        \"drug\":[" + drugsData + "]\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取单个病历失败!\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String addMedrec(JSONObject params){
        Long patient_id = Long.parseLong(params.get("patient_id").toString());
        Long doctor_id = Long.parseLong(params.get("doctor_id").toString());
        String advice = params.get("advice").toString();
        String condition = params.get("condition").toString();

        //不能用parseArray！！会变成等号
        JSONArray drugs = params.getJSONArray("drug");
        Long reg_id = Long.parseLong(params.get("reg_id").toString());
        java.util.Date date = new Date();// 获取当前时间

        Medrec record = new Medrec();
        record.setDoctorId(doctor_id);
        record.setPatientId(patient_id);
        record.setAdvice(advice);
        record.setConditions(condition);
        record.setAttendDate(date);
        medrecMapper.insert(record);

        //获取插入记录的MedrecId(自增)
        System.out.println("id:"+record.getMedrecId());
        Long medrec_id = record.getMedrecId();
        regMapper.updateState(reg_id,"3");

        for(int i=0;i<drugs.size();i++){
            JSONObject jsonObject = drugs.getJSONObject(i);
            Long drug_id = Long.parseLong(jsonObject.get("drug_id").toString());
            System.out.println(drug_id);
            int drug_num = Integer.parseInt(jsonObject.get("drug_num").toString());
            try{
                Prescribe prescribe = new Prescribe();
                prescribe.setMedrecId(medrec_id);
                prescribe.setDrugId(drug_id);
                prescribe.setDrugNum(drug_num);
                prescribeMapper.insert(prescribe);
            }catch (Exception e){
                e.printStackTrace();
                return "{\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"添加病历失败!\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
        }

        return "{\n" +
                "    \"data\": {\n" +
                "    \"medrec_id\":"+ medrec_id +
                "},"+
                "    \"meta\": {\n" +
                "        \"msg\": \"添加病历成功!\",\n" +
                "        \"status\": 200\n" +
                "    }\n" +
                "}";
    }
}
