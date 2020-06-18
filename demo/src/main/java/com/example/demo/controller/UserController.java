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

    @RequestMapping(value = "api/patient/register",method = RequestMethod.POST)
    public JSONObject register(@RequestBody Map<String, String> params) {
        return JSONObject.parseObject(userService.register(params));
    }

    @RequestMapping(value = "api/patient/login",method = RequestMethod.POST)
    public JSONObject login(@RequestBody Map<String, String> params,HttpServletRequest request) {
        return JSONObject.parseObject(userService.patient_login(params,request));
    }

    @RequestMapping(value="api/doctor/login", method = RequestMethod.POST)
    public JSONObject doctor_login(@RequestBody Map<String, String> params){
        return JSONObject.parseObject(userService.doctor_login(params));
    }
}
