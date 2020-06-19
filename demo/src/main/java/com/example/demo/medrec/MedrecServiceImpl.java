package com.example.demo.medrec;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.mapper.MedrecMapper;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Medrec;
import com.example.demo.model.Patient;
import com.example.demo.model.trueMedrec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MedrecServiceImpl implements MedrecService {

    @Autowired
    MedrecMapper medrecMapper;

    @Autowired
    DoctorMapper doctorMapper;

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
                trueMedrecItem.setDoctorName(doctor_name);
                trueMedrecItem.setAdvice(medrec.getAdvice());
                trueMedrecItem.setAttendDate(medrec.getAttendDate());
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
        return "{\n" +
                "    \"meta\": {\n" +
                "        \"msg\": \"获取病历成功!\",\n" +
                "        \"status\": 200\n" +
                "    }\n" +
                "}";
    }
}
