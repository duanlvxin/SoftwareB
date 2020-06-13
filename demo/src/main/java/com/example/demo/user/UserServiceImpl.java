package com.example.demo.user;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import common.utils.age.computeAgeHelper;
import common.utils.token.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PatientMapper patientMapper;
    DoctorMapper doctorMapper;

    @Override
    public String register(String username, String password, String mobile, Date birthday, String address, String patient_name,
                           Boolean patient_gender) {
        Patient patient = new Patient();
        int patient_id = (int)System.currentTimeMillis();
        patient.setPatientId(patient_id);
        patient.setPatientUser(username);
        patient.setPatientPassword(password);
        patient.setPatientMobile(mobile);
        patient.setBirthday(birthday);
        patient.setAddress(address);
        patient.setPatientName(patient_name);
        patient.setPatientGender(patient_gender);

        try{
            if(patientMapper.selectByUsername(username)==null){
                System.out.println(patientMapper.selectByUsername(username));
                patientMapper.insert(patient);
                return "{\n" +
                        "    \"data\": {\n" +
                        "        \"patient_id\":"+ patient_id +"\n" +
                        "    },\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"注册成功\",\n" +
                        "        \"status\": 201\n" +
                        "    }\n" +
                        "}";
            }
            else{
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"用户已注册\",\n" +
                        "        \"status\": 205\n" +
                        "    }\n" +
                        "}";
            }
        }
        catch (Exception e){
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"注册失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String patient_login(Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Patient result = patientMapper.selectByUsername(username);
        if(result==null){
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录失败,用户不存在\",\n" +
                    "        \"status\": 403\n" +
                    "    }\n" +
                    "}";
        }
        String correct_password = result.getPatientPassword();
        if(!password.equals(correct_password)){
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录失败，用户名或密码错误\",\n" +
                    "        \"status\": 403\n" +
                    "    }\n" +
                    "}";
        }

        String create_token;
        computeAgeHelper ageHelper = new computeAgeHelper();
        int age = 0;
        String formatBirthday = "";
        Date birthday = result.getBirthday();
        try{
            Date format=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(birthday.toString());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            formatBirthday = sdf.format(format);
            age = ageHelper.computeAge(birthday);
        }catch (Exception e){
            e.printStackTrace();
        }
        //生成token
        create_token = TokenTools.createToken(username);
        return "{\n" +
                "    \"data\": {\n" +
                "        \"patient_id\": "+ result.getPatientId() +",\n" +
                "        \"patient_name\": \""+ result.getPatientName() +"\",\n" +
                "        \"patient_user\": \""+ result.getPatientUser()+"\",\n" +
                "        \"patient_mobile\": \""+ result.getPatientMobile() +"\",\n" +
                "        \"birthday\": \"" + formatBirthday +"\",\n" +
                "        \"patient_age\": " + age + ",\n" +
                "        \"address\": \""+ result.getAddress() +"\",\n" +
                "        \"token\": \""+ create_token+"\"\n" +
                "    },\n" +
                "    \"meta\": {\n" +
                "        \"msg\": \"登录成功\",\n" +
                "        \"status\": 200\n" +
                "    }\n" +
                "}";
    }

    @Override
    public String doctor_login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Doctor result = doctorMapper.selectByUsername(username);
        if(result==null){
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录失败,用户不存在\",\n" +
                    "        \"status\": 403\n" +
                    "    }\n" +
                    "}";
        }
        String correct_password = result.getDoctorPassword();
        if(!password.equals(correct_password)){
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录失败，用户名或密码错误\",\n" +
                    "        \"status\": 403\n" +
                    "    }\n" +
                    "}";
        }

        String create_token;
        //生成token
        create_token = TokenTools.createToken(username);
        return "{\n" +
                "    \"data\": {\n" +
                "        \"doctor_id\": "+ result.getDoctorId() +",\n" +
                "        \"doctor_name\": \"" + result.getDoctorName() + "\",\n" +
                "        \"doctor_user\": \"" + result.getDoctorUser() + "\",\n" +
                "        \"doctor_intro\": \""+ result.getDoctorIntro() +"\",\n" +
                "        \"doctor_email\": \"" + result.getDoctorEmail() + "\",\n" +
                "        \"doctor_mobile\": \"" + result.getDoctorMobile() + "\",\n" +
                "        \"doctor_tel\": \"" + result.getDoctorTel() + "\",\n" +
                "        \"doctor_pho\": \"" + new String(result.getDoctorPho()) + "\",\n" +
                "        \"department_name\": \""+ result.getDepartmentId() +"\",\n" +
                "        \"token\": \"" + create_token + "\",\n" +
                "    },\n" +
                "    \"meta\": {\n" +
                "        \"msg\": \"登录成功\",\n" +
                "        \"status\": 200\n" +
                "    }\n" +
                "}";
    }
}
