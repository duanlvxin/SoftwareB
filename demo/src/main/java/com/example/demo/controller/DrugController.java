package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.drug.DrugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Integer.parseInt;

@RestController
@EnableAutoConfiguration
public class DrugController {

    @Autowired
    DrugServiceImpl DrugService = new DrugServiceImpl();

    @RequestMapping(value = "api/doctor/drug-list",method = RequestMethod.GET)
    public JSONObject doctor_list(HttpServletRequest request) {
        return JSONObject.parseObject(DrugService.drug_list(
                request.getParameter("drug_search"),
                parseInt(request.getParameter("page_num")),
                parseInt(request.getParameter("page_size"))));
    }
}