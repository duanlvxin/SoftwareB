package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.queue.QueueServiceImpl;
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
public class QueueController {

    @Autowired
    QueueServiceImpl QueueService = new QueueServiceImpl();

    @RequestMapping(value = "api/doctor/patient-late",method = RequestMethod.GET)
    public JSONObject reg_list(HttpServletRequest request) {
        return JSONObject.parseObject(QueueService.patient_late(Long.parseLong(request.getParameter("reg_id"))));
    }
}
