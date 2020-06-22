package com.example.demo.reg;

import java.util.Map;

public interface RegService {
    String department_list();
    String doctor_list(Map<String, Long> params);
    String doctor_info(Map<String, String> params);
//    String submit(Map<String, String> params);
}
