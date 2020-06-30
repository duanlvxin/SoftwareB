package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
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

    //check
    @RequestMapping(value = "api/doctor/check-password", method = RequestMethod.GET)
    public JSONObject checkPassword(HttpServletRequest request) {
        return JSONObject.parseObject(userinfoService.checkPassword(request));
    }
}
