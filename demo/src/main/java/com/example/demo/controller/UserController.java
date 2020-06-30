package com.example.demo.controller;

import com.example.demo.service.user.UserServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //病人注册
    @RequestMapping(value = "api/patient/register",method = RequestMethod.POST)
    public JSONObject register(@RequestBody Map<String, String> params) {
        return JSONObject.parseObject(userService.register(params));
    }

    //病人登录
    @RequestMapping(value = "api/patient/login",method = RequestMethod.POST)
    public JSONObject login(@RequestBody Map<String, String> params,HttpServletRequest request) {
        return JSONObject.parseObject(userService.patient_login(params,request));
    }

    //医生登录
    @RequestMapping(value="api/doctor/login", method = RequestMethod.POST)
    public JSONObject doctor_login(@RequestBody Map<String, String> params){
        return JSONObject.parseObject(userService.doctor_login(params));
    }

    //医生修改密码
    @RequestMapping(value="api/doctor/modify-password",method = RequestMethod.POST)
    public JSONObject doctor_modify_password(@RequestBody Map<String,String> params){
        return JSONObject.parseObject(userService.doctor_modify_password(params));
    }

    //医生修改个人信息
    @RequestMapping(value="api/doctor/modify-info",method = RequestMethod.POST)
    public JSONObject doctor_modify_info(@RequestBody Map<String,String> params){
        return JSONObject.parseObject(userService.doctor_modify_info(params));
    }
}
