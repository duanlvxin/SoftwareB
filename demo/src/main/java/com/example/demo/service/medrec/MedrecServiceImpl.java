package com.example.demo.service.medrec;

import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.MedrecMapper;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Medrec;
import com.example.demo.model.Drug;
import com.example.demo.model.trueDrug;
import com.example.demo.model.trueMedrec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
            String doctor_name = doctorMapper.selectByPrimaryKey(result.getDoctorId()).getDoctorName();
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
}