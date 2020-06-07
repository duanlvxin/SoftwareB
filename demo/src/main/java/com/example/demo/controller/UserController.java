package com.example.demo.controller;

import com.example.demo.user.UserServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping("/")
    public String test(){
        return "hello";
    }

    @RequestMapping("api/patient/register")
    public JSONObject register(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        Date birthday = Date.valueOf(request.getParameter("birthday"));
        String address = request.getParameter("address");
        String patient_name = request.getParameter("patient_name");
        Boolean patient_gender = Boolean.parseBoolean(request.getParameter("patient_gender"));

        return JSONObject.parseObject(userService.register(username,password,mobile,birthday,address,patient_name,
                patient_gender));
    }

    @RequestMapping("api/patient/login")
    public JSONObject patient_login(HttpServletRequest request){
        return JSONObject.parseObject(userService.patient_login(request));
    }

    @RequestMapping("api/patient/login")
    public JSONObject doctor_login(HttpServletRequest request){
        return JSONObject.parseObject(userService.doctor_login(request));
    }
}
