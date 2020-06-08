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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    public String patient_login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = request.getHeader("Authorization");
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
        int age=0;
        String birthday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(result.getBirthday());
        try{
            age = ageHelper.computeAge(result.getBirthday());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(token==null){
            //生成token
            create_token = TokenTools.createToken(request,username);
            request.getSession().setAttribute(username, create_token);
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"patient_id\": "+ result.getPatientId() +",\n" +
                    "        \"patient_name\": \""+ result.getPatientName() +"\",\n" +
                    "        \"patient_user\": \""+ result.getPatientUser()+"\",\n" +
                    "        \"patient_mobile\": \""+ result.getPatientMobile() +"\",\n" +
                    "        \"birthday\": \"" + birthday +"\",\n" +
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
        else{
            if(!TokenTools.judgeTokenIsEqual(request,"Authorization",username)){
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"登录失败，token不正确\",\n" +
                        "        \"status\": 403\n" +
                        "    }\n" +
                        "}";
            }
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"patient_id\": "+ result.getPatientId() +",\n" +
                    "        \"patient_name\": \""+ result.getPatientName() +"\",\n" +
                    "        \"patient_user\": \""+ result.getPatientUser()+"\",\n" +
                    "        \"patient_mobile\": \""+ result.getPatientMobile() +"\",\n" +
                    "        \"birthday\": \"" + birthday +"\",\n" +
                    "        \"patient_age\": " + age + ",\n" +
                    "        \"address\": \""+ result.getAddress() +"\",\n" +
                    "        \"token\": \""+ token+"\"\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }
    }

    @Override
    public String doctor_login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = request.getHeader("Authorization");
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
        if(token==null){
            //生成token
            create_token = TokenTools.createToken(request,username);
            request.getSession().setAttribute(username, create_token);
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"doctor_id\": "+ result.getDoctorId() +",\n" +
                    "        \"doctor_name\": \"" + result.getDoctorName() + "\",\n" +
                    "        \"doctor_user\": \"xxxxx\",\n" +
                    "        \"doctor_intro\": \"xxxxxxxxxxxxxxxxxxxxxxx\",\n" +
                    "        \"doctor_email\": \"xxxxx@qq.com\",\n" +
                    "        \"doctor_mobile\": \"13899999999\",\n" +
                    "        \"doctor_tel\": \"0755-22222222\",\n" +
                    "        \"doctor_pho\": \"\",\n" +
                    "        \"department_name\": \"呼吸科\",\n" +
                    "        \"token\": \"xxxxxxxx\",\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }
        else{
            if(!TokenTools.judgeTokenIsEqual(request,"Authorization",username)){
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"登录失败，token不正确\",\n" +
                        "        \"status\": 403\n" +
                        "    }\n" +
                        "}";
            }
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"doctor_id\": 20,\n" +
                    "        \"doctor_name\": \"xxxx\",\n" +
                    "        \"doctor_user\": \"xxxxx\",\n" +
                    "        \"doctor_intro\": \"xxxxxxxxxxxxxxxxxxxxxxx\",\n" +
                    "        \"doctor_email\": \"xxxxx@qq.com\",\n" +
                    "        \"doctor_mobile\": \"13899999999\",\n" +
                    "        \"doctor_tel\": \"0755-22222222\",\n" +
                    "        \"doctor_pho\": \"\",\n" +
                    "        \"department_name\": \"呼吸科\",\n" +
                    "        \"token\": \"xxxxxxxx\",\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"登录成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }
    }
}
