package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.reg.RegServiceImpl;
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

    @RequestMapping(value = "api/patient/department-list",method = RequestMethod.GET)
    public JSONObject department_list() {
        return JSONObject.parseObject(RegService.department_list());
    }

    @RequestMapping(value = "api/patient/doctor-list",method = RequestMethod.GET)
    public JSONObject doctor_list(HttpServletRequest request) {
        return JSONObject.parseObject(RegService.doctor_list(Long.parseLong(request.getParameter("department_id"))));
    }

    @RequestMapping(value = "api/patient/doctor-info",method = RequestMethod.GET)
    public JSONObject doctor_info(HttpServletRequest request) {
        return JSONObject.parseObject(RegService.doctor_info(Long.parseLong(request.getParameter("doctor_id")),
                java.sql.Date.valueOf(request.getParameter("res_date"))));
    }

    @RequestMapping(value = "api/doctor/doctor-info",method = RequestMethod.GET)
    public JSONObject full_doctor_info(HttpServletRequest request) {
        return JSONObject.parseObject(RegService.full_doctor_info(Long.parseLong(request.getParameter("doctor_id"))));
    }

    //获取挂号病人信息
    @RequestMapping(value="api/doctor/patient-info", method = RequestMethod.GET)
    public JSONObject patient_info(HttpServletRequest request){
        return JSONObject.parseObject(RegService.patient_info(Long.parseLong(request.getParameter("doctor_id"))));
    }

    @RequestMapping(value = "api/patient/reg-submit",method = RequestMethod.POST)
    public JSONObject reg_submit(@RequestBody Map<String, String> params) {
        return JSONObject.parseObject(RegService.reg_submit(params));
    }

    @RequestMapping(value = "api/patient/reg-list",method = RequestMethod.GET)
    public JSONObject reg_list(HttpServletRequest request) {
        return JSONObject.parseObject(RegService.reg_list(Long.parseLong(request.getParameter("patient_id"))));
    }
}
