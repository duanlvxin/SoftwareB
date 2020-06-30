package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.home.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Integer.parseInt;
import java.lang.Long;

@RestController
@EnableAutoConfiguration
public class HomeController {

    @Autowired
    HomeServiceImpl HomeService = new HomeServiceImpl();

    @RequestMapping(value = "api/home/doctor-list",method = RequestMethod.GET)
    public JSONObject doctor_list(HttpServletRequest request) {
        return JSONObject.parseObject(HomeService.doctor_list(
                request.getParameter("department_id"),
                parseInt(request.getParameter("total")),
                parseInt(request.getParameter("add_num"))));
    }
}