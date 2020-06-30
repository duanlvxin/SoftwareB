package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.reg.RegServiceImpl;
import com.example.demo.service.userinfo.UserinfoService;
import com.example.demo.service.userinfo.UserinfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class checkControl {

    @Autowired
    UserinfoServiceImpl userinfoService = new UserinfoServiceImpl();

    //获取所有病历
    @RequestMapping(value = "api/doctor/check-password", method = RequestMethod.POST)
    public JSONObject getAllMedrec(@RequestBody Map<String,String> params) {
        return JSONObject.parseObject(userinfoService.checkPassword(params));
    }
}
