package com.example.demo.controller;

import com.example.demo.user.UserServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping("/")
    public String test(){
        return "hello";
    }

    @RequestMapping(value = "api/patient/register",method = RequestMethod.PUT)
    public JSONObject register(HttpServletRequest request) {
        String username = request.getParameter("patient_user");
        String password = request.getParameter("patient_password");
        String mobile = request.getParameter("patient_mobile");
        Date birthday = Date.valueOf(request.getParameter("birthday"));
        String address = request.getParameter("address");
        String patient_name = request.getParameter("patient_name");
        Boolean patient_gender = Boolean.parseBoolean(request.getParameter("patient_gender"));

        return JSONObject.parseObject(userService.register(username, password, mobile, birthday, address, patient_name,
                patient_gender));
    }

    @RequestMapping(value = "api/patient/login",method = RequestMethod.POST)
    public JSONObject login(@RequestParam Map<String, String> params) {
        return JSONObject.parseObject(userService.patient_login(params));
    }

    @RequestMapping("api/docter/login")
    public JSONObject doctor_login(HttpServletRequest request){
        return JSONObject.parseObject(userService.doctor_login(request));
    }
}
