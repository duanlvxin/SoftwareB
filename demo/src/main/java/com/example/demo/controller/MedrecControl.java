package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.medrec.MedrecServiceImpl;
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
public class MedrecControl {

    @Autowired
    MedrecServiceImpl medrecService = new MedrecServiceImpl();

    @RequestMapping(value = "api/patient/all-medrec", method = RequestMethod.GET)
    public JSONObject getAllMedrec(HttpServletRequest request) {
        return JSONObject.parseObject(medrecService.getAllMedrec(Long.parseLong(request.getParameter("patient_id"))));
    }

    @RequestMapping(value = "api/patient/single-medrec", method = RequestMethod.GET)
    public JSONObject getSingleMedrec(HttpServletRequest request) {
        return JSONObject.parseObject(medrecService.getSingleMedrec(Long.parseLong(request.getParameter("medrec_id"))));
    }

    @RequestMapping(value = "api/doctor/add-medrec", method = RequestMethod.POST)
    public JSONObject addMedrec(@RequestBody JSONObject params) {
        return JSONObject.parseObject(medrecService.addMedrec(params));
    }
}
