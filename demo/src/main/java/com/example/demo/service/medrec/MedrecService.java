package com.example.demo.service.medrec;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface MedrecService {

    String getAllMedrec(Long patient_id);
    String getSingleMedrec(Long medrec_id);
    String addMedrec(JSONObject params);
}
