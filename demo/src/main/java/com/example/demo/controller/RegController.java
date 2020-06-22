package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.reg.RegServiceImpl;
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
public class RegController {

    @Autowired
    RegServiceImpl RegService = new RegServiceImpl();

    @RequestMapping("/")
    public String test(){
        return "hello";
    }

    @RequestMapping(value = "api/patient/department-list",method = RequestMethod.GET)
    public JSONObject department_list() {
        return JSONObject.parseObject(RegService.department_list());
    }

    @RequestMapping(value = "api/patient/doctor-list",method = RequestMethod.GET)
    public JSONObject doctor_list(@RequestBody Map<String, Long> params) {
        return JSONObject.parseObject(RegService.doctor_list(params));
    }

    @RequestMapping(value = "api/patient/doctor-list",method = RequestMethod.GET)
    public JSONObject doctor_info(@RequestBody Map<String, String> params) {
        return JSONObject.parseObject(RegService.doctor_info(params));
    }
}
