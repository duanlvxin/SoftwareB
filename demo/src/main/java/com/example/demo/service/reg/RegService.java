package com.example.demo.service.reg;

import java.util.Date;
import java.util.Map;

public interface RegService {
    String department_list();
    String doctor_list(Long department_id);
    String doctor_info(Long doctor_id, Date res_date);
    String patient_info(Long doctor_id);
    String reg_submit(Map<String, String> params);
    String reg_list(Long patient_id);
}
