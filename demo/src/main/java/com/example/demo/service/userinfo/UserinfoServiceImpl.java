package com.example.demo.service.userinfo;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.service.Session.keySession;
import common.utils.RSA.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    ApplicationContext context;

    //检查旧密码是否正确
    public String checkPassword(HttpServletRequest request){
        Long doctor_id = Long.parseLong(request.getParameter("doctor_id"));
        String password = request.getParameter("doctor_password");

        //解密
        keySession keysession = context.getBean(keySession.class);
        String privateKey = keysession.getPrivateKey();
        String decodedPassword = "";
        try{
            decodedPassword = RSAUtils.decryptByPrivateKey(password,privateKey);
            System.out.println("解密后文字: \r\n" + decodedPassword);
        }catch (Exception e){
//            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"解密失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }

        try{
            String true_password = doctorMapper.selectByPrimaryKey(doctor_id).getDoctorPassword();
            if(!decodedPassword.equals(true_password)){
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"密码不正确！\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
            else{
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"密码正确！\",\n" +
                        "        \"status\": 200\n" +
                        "    }\n" +
                        "}";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"医生不存在！\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }
}
