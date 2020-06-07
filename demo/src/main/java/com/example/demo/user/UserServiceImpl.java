package com.example.demo.user;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import common.utils.token.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PatientMapper patientMapper;
    DoctorMapper doctorMapper;

    @Override
    public String register(String username, String password, String mobile, Date birthday, String address, String patient_name,
                           Boolean patient_gender) {
        Patient patient = new Patient();
        patient.setPatientId((int)System.currentTimeMillis());
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
                return "{\"code\":200,\"msg\":\"注册成功\",\"data\":[]}";
            }
            else{
                return "{\"code\":300,\"msg\":\"该用户已经注册过，不能重复注册!\",\"data\":[]}";
            }
        }
        catch (Exception e){
            return "{\"code\":400,\"msg\":\"注册失败\",\"data\":[]}";
        }
    }

    @Override
    public String patient_login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = request.getHeader("Authorization");
        Patient result = patientMapper.selectByUsername(username);
        if(result==null){
            return "{\"code\":401,\"msg\":\"登录失败,用户不存在!\",\"data\":[]}";
        }
        String correct_password = result.getPatientPassword();
        if(!password.equals(correct_password)){
            return "{\"code\":402,\"msg\":\"登录失败,用户名或密码错误!\",\"data\":[]}";
        }

        String create_token;
        if(token==null){
            //生成token
            create_token = TokenTools.createToken(request,username);
            request.getSession().setAttribute(username, create_token);
            return "{\"code\":201,\"msg\":\"登录成功\",\"data\":{\"token\":" + "\""+create_token+"\""+ "}}";
        }
        else{
            if(!TokenTools.judgeTokenIsEqual(request,"Authorization",username)){
                return "{\"code\":403,\"msg\":\"登录失败,token不正确!\",\"data\":[]}";
            }
            return "{\"code\":200,\"msg\":\"登录成功\",\"data\":[]}";
        }
    }

    @Override
    public String doctor_login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = request.getHeader("Authorization");
        Doctor result = doctorMapper.selectByUsername(username);
        if(result==null){
            return "{\"code\":401,\"msg\":\"登录失败,用户不存在!\",\"data\":[]}";
        }
        String correct_password = result.getDoctorPassword();
        if(!password.equals(correct_password)){
            return "{\"code\":402,\"msg\":\"登录失败,用户名或密码错误!\",\"data\":[]}";
        }

        String create_token;
        if(token==null){
            //生成token
            create_token = TokenTools.createToken(request,username);
            request.getSession().setAttribute(username, create_token);
            return "{\"code\":201,\"msg\":\"登录成功\",\"data\":{\"token\":" + "\""+create_token+"\""+ "}}";
        }
        else{
            if(!TokenTools.judgeTokenIsEqual(request,"Authorization",username)){
                return "{\"code\":403,\"msg\":\"登录失败,token不正确!\",\"data\":[]}";
            }
            return "{\"code\":200,\"msg\":\"登录成功\",\"data\":[]}";
        }
    }
}
