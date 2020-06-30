package com.example.demo.service.user;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import com.example.demo.service.Session.keySession;
import com.example.demo.service.Session.tokenSession;
import common.utils.RSA.RSAUtils;
import common.utils.token.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
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
        try{
            patient.setPatientId(null);
            patient.setPatientUser(username);
            //解密password存入数据库
            keySession keysession = context.getBean(keySession.class);
            String privateKey = keysession.getPrivateKey();
            try{
                patient.setPatientPassword(RSAUtils.decryptByPrivateKey(password,privateKey));
            }
            catch (Exception e){
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"解密失败\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
            patient.setPatientMobile(mobile);
            patient.setBirthday(birthday);
            patient.setAddress(address);
            patient.setPatientName(patient_name);
            patient.setPatientGender(patient_gender);
            Long patient_id = 0L;
            Patient result = patientMapper.selectByUsername(username);
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

        keySession keysession = context.getBean(keySession.class);
        String privateKey = keysession.getPrivateKey();
        String decodedPassword = "";
        try{
            decodedPassword = RSAUtils.decryptByPrivateKey(password,privateKey);
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
//        computeAgeHelper ageHelper = new computeAgeHelper();
//        int age = 0;
//        String formatBirthday = "";
//        Date birthday = result.getBirthday();
//        try{
//            Date format=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(birthday.toString());
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//            formatBirthday = sdf.format(format);
//            age = ageHelper.computeAge(birthday);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //生成token
        TokenTools tokenTools = new TokenTools();
        create_token = tokenTools.generateToken(result.getPatientId().toString());
        return "{\n" +
                "    \"data\": {\n" +
                "        \"patient_id\": "+ result.getPatientId() +",\n" +
                "        \"patient_name\": \""+ result.getPatientName() +"\",\n" +
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

        keySession keysession = context.getBean(keySession.class);
        String privateKey = keysession.getPrivateKey();
        String decodedPassword = "";
        try{
            decodedPassword = RSAUtils.decryptByPrivateKey(password,privateKey);
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
        //生成token
        TokenTools tokenTools = new TokenTools();
        create_token = tokenTools.generateToken(result.getDoctorId().toString());
        //保存token
        tokenSession tokensession = context.getBean(tokenSession.class);
        tokensession.setToken(create_token);
        System.out.println(Arrays.toString(result.getDoctorPho()));
        return "{\n" +
                "    \"data\": {\n" +
                "        \"doctor_id\": "+ result.getDoctorId() +",\n" +
                "        \"token\": \"" + create_token + "\",\n" +
                "    },\n" +
                "    \"meta\": {\n" +
                "        \"msg\": \"登录成功\",\n" +
                "        \"status\": 200\n" +
                "    }\n" +
                "}";
    }

    @Override
    public String doctor_modify_password(Map<String, String> params) {
        Long doctor_id = Long.parseLong(params.get("doctor_id"));
        String password = params.get("doctor_password");
        //解密
//        String decodedPassword = password;
        keySession keysession = context.getBean(keySession.class);
        String privateKey = keysession.getPrivateKey();
        String decodedPassword = "";
        try{
            decodedPassword = RSAUtils.decryptByPrivateKey(password,privateKey);
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

        try{
            doctorMapper.updatePassword(doctor_id,decodedPassword);
            return "{\n" +
                    "    \"data\":{\n" +
                    "        \"doctor_id\":"+ doctor_id +"\n" +
                    "    },\n" +
                    "    \"meta\":{\n" +
                    "        \"msg\": \"修改成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"修改密码失败！\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }

    }

    @Override
    public String doctor_modify_info(Map<String, String> params) {
        Long doctor_id = Long.parseLong(params.get("doctor_id"));
        String doctor_email = params.get("doctor_email");
        String doctor_mobile = params.get("doctor_mobile");
        String doctor_intro = params.get("doctor_intro");

        final Base64.Decoder decoder = Base64.getDecoder();
        String doctor_pho = params.get("doctor_pho");
        System.out.println("pho:"+doctor_pho);
        byte[] save_doctor_pho = decoder.decode(doctor_pho);

        try {
            if(doctor_pho.length()==0){
                 save_doctor_pho = doctorMapper.selectByPrimaryKey(doctor_id).getDoctorPho();
            }
            doctorMapper.updateInfo(doctor_id, doctor_email, doctor_mobile, doctor_intro, save_doctor_pho);

            return "{\n" +
                    "    \"data\":{\n" +
                    "        \"doctor_id\":" + doctor_id + "\n" +
                    "    },\n" +
                    "    \"meta\":{\n" +
                    "        \"msg\": \"修改成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"修改个人信息失败！\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n"+
                    "}";

        }
    }
}
