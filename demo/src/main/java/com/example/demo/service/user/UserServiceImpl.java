package com.example.demo.service.user;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import com.example.demo.service.Session.keySession;
import common.utils.RSA.RSAUtils2;
import common.utils.age.computeAgeHelper;
import common.utils.token.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    ApplicationContext context;

    @Override
    public String register(Map<String, String> params) {
        String username = params.get("patient_user");
        String password = params.get("patient_password");
        String mobile = params.get("patient_mobile");
        Date birthday = java.sql.Date.valueOf(params.get("birthday"));
        String address = params.get("address");
        String patient_name = params.get("patient_name");
        Boolean patient_gender = Boolean.parseBoolean(params.get("patient_gender"));

        Patient patient = new Patient();
        patient.setPatientId(null);
        patient.setPatientUser(username);
        patient.setPatientPassword(password);
        patient.setPatientMobile(mobile);
        patient.setBirthday(birthday);
        patient.setAddress(address);
        patient.setPatientName(patient_name);
        patient.setPatientGender(patient_gender);
        Long patient_id = 0L;
        Patient result = patientMapper.selectByUsername(username);
        try{
            if(result==null){
                patientMapper.insert(patient);
                patient_id = patientMapper.selectByUsername(username).getPatientId();
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
    public String patient_login(Map<String, String> params,HttpServletRequest request) {
        String username = params.get("username");
        String password = params.get("password");

//        System.out.println("session1:"+request.getSession());
//        System.out.println("privatekey:"+request.getSession().getAttribute("privateKey"));
//        String privateKey = request.getSession().getAttribute("privateKey").toString();
        keySession keysession = context.getBean(keySession.class);
        String privateKey = keysession.getPrivateKey();
        String decodedPassword = "";
        System.out.println("password:"+password);
        System.out.println("privatekey:"+privateKey);
        try{
            decodedPassword = RSAUtils2.decryptByPrivateKey(password,privateKey);
            System.out.println("解密后文字: \r\n" + decodedPassword);
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"解密失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }

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
        if(!decodedPassword.equals(correct_password)){
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
    public String doctor_login(Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Doctor result = null;
        try{
            result = doctorMapper.selectByUsername(username);
        }catch (Exception e){
            System.out.println(e);
        }
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
        System.out.println(Arrays.toString(result.getDoctorPho()));
        return "{\n" +
                "    \"data\": {\n" +
                "        \"doctor_id\": "+ result.getDoctorId() +",\n" +
                "        \"doctor_name\": \"" + result.getDoctorName() + "\",\n" +
                "        \"doctor_user\": \"" + result.getDoctorUser() + "\",\n" +
                "        \"doctor_intro\": \""+ result.getDoctorIntro() +"\",\n" +
                "        \"doctor_email\": \"" + result.getDoctorEmail() + "\",\n" +
                "        \"doctor_mobile\": \"" + result.getDoctorMobile() + "\",\n" +
                "        \"doctor_tel\": \"" + result.getDoctorTel() + "\",\n" +
                "        \"doctor_pho\": \"" + Arrays.toString(result.getDoctorPho()) + "\",\n" +
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
